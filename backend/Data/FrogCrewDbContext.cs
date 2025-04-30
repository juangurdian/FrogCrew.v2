// Data/FrogCrewDbContext.cs
using Microsoft.EntityFrameworkCore;
using FrogCrewBackend.Models;

namespace FrogCrewBackend.Data;
public class FrogCrewDbContext : DbContext
{
    public DbSet<CrewMember> CrewMembers { get; set; }

    public FrogCrewDbContext(DbContextOptions<FrogCrewDbContext> options)
        : base(options) { }
}