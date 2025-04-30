using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using frogcrewdb.mv.db; 
using frogcrewdb.trace.db; 

namespace FrogCrewBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CrewMembersController : ControllerBase
    {
        private readonly FrogCrewDbContext _context;

        public CrewMembersController(FrogCrewDbContext context)
        {
            _context = context;
        }

        // GET: api/CrewMembers
        [HttpGet]
        public async Task<ActionResult<IEnumerable<CrewMember>>> GetCrewMembers()
        {
            return await _context.CrewMembers.ToListAsync();
        }

        // GET: api/CrewMembers/5
        [HttpGet("{id}")]
        public async Task<ActionResult<CrewMember>> GetCrewMember(int id)
        {
            var crewMember = await _context.CrewMembers.FindAsync(id);
            if (crewMember == null)
            {
                return NotFound();
            }
            return crewMember;
        }

        // POST: api/CrewMembers
        [HttpPost]
        public async Task<ActionResult<CrewMember>> CreateCrewMember(CrewMember crewMember)
        {
            // Validate required fields
            if (string.IsNullOrEmpty(crewMember.FirstName) || 
                string.IsNullOrEmpty(crewMember.LastName) ||
                string.IsNullOrEmpty(crewMember.Email) || 
                string.IsNullOrEmpty(crewMember.PhoneNumber) ||
                string.IsNullOrEmpty(crewMember.Role) || 
                string.IsNullOrEmpty(crewMember.QualifiedPositions))
            {
                return BadRequest("All fields are required.");
            }

            // Validate email format
            if (!IsValidEmail(crewMember.Email))
            {
                return BadRequest("Invalid email format.");
            }

            // Check for unique email (Business Rule BR-1)
            if (_context.CrewMembers.Any(cm => cm.Email == crewMember.Email))
            {
                return Conflict("Email already exists.");
            }

            // Validate phone number format (Business Rule BR-3, assuming 999-999-9999)
            if (!Regex.IsMatch(crewMember.PhoneNumber, @"\d{3}-\d{3}-\d{4}"))
            {
                return BadRequest("Phone number must be in the format 999-999-9999.");
            }

            try
            {
                _context.CrewMembers.Add(crewMember);
                await _context.SaveChangesAsync();
                return CreatedAtAction(nameof(GetCrewMember), new { id = crewMember.Id }, crewMember);
            }
            catch (Exception)
            {
                return StatusCode(500, "An error occurred while creating the crew member.");
            }
        }

        // PUT: api/CrewMembers/5
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateCrewMember(int id, CrewMember updatedCrewMember)
        {
            if (id != updatedCrewMember.Id)
            {
                return BadRequest("ID mismatch.");
            }

            var crewMember = await _context.CrewMembers.FindAsync(id);
            if (crewMember == null)
            {
                return NotFound();
            }

            crewMember.FirstName = updatedCrewMember.FirstName;
            crewMember.LastName = updatedCrewMember.LastName;
            crewMember.Email = updatedCrewMember.Email;
            crewMember.PhoneNumber = updatedCrewMember.PhoneNumber;
            crewMember.Role = updatedCrewMember.Role;
            crewMember.QualifiedPositions = updatedCrewMember.QualifiedPositions;

            
            if (string.IsNullOrEmpty(crewMember.FirstName) || 
                string.IsNullOrEmpty(crewMember.LastName) ||
                string.IsNullOrEmpty(crewMember.Email) || 
                string.IsNullOrEmpty(crewMember.PhoneNumber) ||
                string.IsNullOrEmpty(crewMember.Role) || 
                string.IsNullOrEmpty(crewMember.QualifiedPositions))
            {
                return BadRequest("All fields are required.");
            }

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CrewMemberExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCrewMember(int id)
        {
            var crewMember = await _context.CrewMembers.FindAsync(id);
            if (crewMember == null)
            {
                return NotFound();
            }
            _context.CrewMembers.Remove(crewMember);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        // Helper method to check if a crew member exists
        private bool CrewMemberExists(int id)
        {
            return _context.CrewMembers.Any(e => e.Id == id);
        }

        // Helper method to validate email format
        private bool IsValidEmail(string email)
        {
            try
            {
                var addr = new System.Net.Mail.MailAddress(email);
                return addr.Address == email;
            }
            catch
            {
                return false;
            }
        }
    }
}