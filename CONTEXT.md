# FrogCrew Project Context

## 1. Project Overview

**Project Name:** FrogCrew  
**Client/Domain:** TCU Sports Broadcasting  
**Purpose:**  
FrogCrew is a modern, web-based scheduling and management system designed to streamline the crew assignment process for TCU athletic events. By automating availability submissions, schedule generation, and communications, FrogCrew minimizes manual errors, saves administrative time, and ensures that crew members are accurately scheduled.

**Vision Statement:**  
Provide a smooth, intuitive, and visually modern interface that efficiently organizes crew schedules, allows real-time notifications, and enables administrators and staff to manage events effectively—all while embracing TCU’s brand identity.

---

## 2. Requirements & Use Cases

### Business Requirements & Success Metrics
- **Time Savings:** Reduce scheduling time by 90% through automation.
- **Error Reduction:** Achieve at least 95% error-free crew schedules.
- **Communication:** Centralize notifications to decrease manual communications by 75%.
- **User Satisfaction:** Reach a 90% satisfaction rate based on post-launch surveys.

### Key Functional Use Cases
- **User Authentication & Registration:**  
  - Login (common for both crew and admins)  
  - Signup / Registration (invitation-based account creation)
- **Crew Member Workflows:**  
  - Create, edit, and view individual profiles  
  - Submit and edit availability for upcoming events  
  - View personal scheduled games vs. the general game schedule  
  - Request shift exchanges and respond to incoming requests  
  - View crew lists and other crew members' profiles
- **Administration Workflows:**  
  - Invite new crew members  
  - Manage crew members (view, edit, and delete profiles)  
  - Create, edit, and publish game schedules  
  - Assign crew to scheduled games (scheduling)  
  - Approve shift exchange requests  
  - Generate various reports (financial, crew positions, individual assignments)  
  - Configure crew list templates

### Non-Functional Requirements
- **Performance:** Fast load times and smooth transitions  
- **Scalability:** Ability to handle multiple events and large numbers of crew members  
- **Security:** Implement role-based access, ensuring data is restricted to admins or crew members as appropriate  
- **Usability:** Intuitive navigation with responsive design for both desktop and mobile devices

---

## 3. Tech Stack

### Front-End
- **Framework:** Vue 3 (using Vue CLI)
- **Routing & State Management:** Vue Router; optionally Vuex for state management
- **UI Components & Styling:**  
  - **Recommended:** Vuetify (comprehensive Material Design components)  
  - **Alternatively:** Tailwind CSS (utility-first, highly customizable)  
- **Build Tool:** Vue CLI

### Back-End (Future Integration)
- **Framework:** Spring Boot (for RESTful API, data persistence, and security)
- **Database:** PostgreSQL or MySQL

### Additional Tools
- **Version Control:** Git (GitHub repository: [https://github.com/juangurdian/FrogCrew.git](https://github.com/juangurdian/FrogCrew.git))
- **CI/CD:** GitHub Actions or similar pipelines for automated testing and deployment
- **Design Assets:**  
  - TCU-themed color palette (e.g., TCU Purple `#4d2e7d`)  
  - Frog mascot to be used as a brand icon in headers, dashboards, and login screens

---

## 4. Style & Theme

- **Look & Feel:**  
  - Smooth and modern with clean lines, ample whitespace, and a responsive layout  
  - Visual elements that provide clear feedback for user actions (e.g., notifications, quick buttons)
- **TCU Branding:**  
  - Primary color: TCU Purple (`#4d2e7d`)  
  - Complementary colors: Whites, light grays, and subtle accent colors  
  - Incorporate the frog mascot as a recognizable emblem throughout the app

---

## 5. Phased Development Plan

### Phase 1: Requirements & Architecture Design
- **Tasks:**  
  - Finalize business requirements, use cases, and stakeholder interviews  
  - Define user flows for both Admin and Crew Member roles  
  - Create initial wireframes and mockups for key screens (e.g., dashboards, forms, notifications)
- **Deliverables:**  
  - Comprehensive Requirements Document  
  - Wireframes for all major pages

### Phase 2: Infrastructure & Project Setup
- **Tasks:**  
  - Set up the project repository with an organized folder structure (see below)  
  - Initialize the Vue project with Vue CLI  
  - Integrate global CSS framework (Vuetify or Tailwind CSS) and configure Vue Router for role-based routes  
- **Deliverables:**  
  - Organized folder structure and base project configuration

**Recommended Directory Structure:**


### Phase 3: Core Functionality Implementation
- **Admin Workflow:**  
  - Develop the Admin Dashboard (with sidebar, summary widgets, quick action buttons, notifications)  
  - Create and integrate individual admin pages (Invite Crew Member, Manage Crew Members, Create/Edit Game Schedule, Shift Approvals, Reports)
- **Crew Member Workflow:**  
  - Build the Staff Dashboard (personal schedules, availability submission, shift exchange, notifications)  
  - Create and integrate essential crew pages (Profile, Submit/Edit Availability, Request/Respond to Shift Exchange, Crew List, Game Schedule)

### Phase 4: API Integration & Role-Based Controls
- **Develop RESTful APIs:** Use Spring Boot to create the back-end services  
- **Integrate APIs with Front-End:** Connect your Vue components to dynamically load data  
- **Implement Authentication & Route Guards:** Enforce role-based access so admins only access admin routes and crew members only access staff routes

### Phase 5: Testing, Feedback & Refinement
- **User Acceptance Testing:** Validate flows with both admin and crew member users  
- **Performance & Security Testing:** Ensure responsive performance and secure access  
- **UI/UX Enhancements:** Refine visuals and interactions based on user feedback

### Phase 6: Deployment & Monitoring
- **Deploy to Staging:** Release the application on a staging environment  
- **Monitor & Gather Feedback:** Use monitoring tools and user feedback for iterative improvements  
- **Plan Iterative Updates:** Update features and address issues based on real-world usage

---

## 6. Style & Modern Feel

- **Smooth, Responsive Design:**  
  Use a modern CSS framework (Tailwind CSS or Vuetify) to build a clean interface with fluid transitions, responsive layouts, and user-friendly components.
- **TCU-Themed:**  
  Emphasize TCU’s branding by incorporating TCU Purple (`#4d2e7d`), and use complementary colors to create a sleek modern look.  
- **Consistent Branding:**  
  Use the frog mascot as a recurring brand element (in headers, dashboards, login screens, etc.) for a unified brand identity.
- **User-Centric Design:**  
  Develop clear navigation, quick action buttons, and visual feedback (notifications, summary widgets) to enhance the overall user experience.

---

## 7. Conclusion

This document provides a comprehensive context and plan for developing the FrogCrew application. With clear requirements, a defined tech stack, a unified style guide, and a phased development plan, the project is positioned to deliver a smooth, modern, and efficient scheduling tool tailored to TCU Sports Broadcasting’s needs. Each phase is designed to be manageable, ensuring that you can iterate and improve based on real user feedback.

---

*Prepared by: [Your Name]*  
*Date: [Current Date]*
