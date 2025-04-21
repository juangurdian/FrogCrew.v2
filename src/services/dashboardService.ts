import api from './api';

export const dashboardService = {
  // Main endpoint to get all dashboard data at once
  getDashboard() {
    return api.get('/api/dashboard');
  },
  
  // Individual endpoints for specific dashboard sections
  getSummary() {
    return api.get('/api/dashboard/summary');
  },
  
  getCrewPositions() {
    return api.get('/api/dashboard/crew-positions');
  },
  
  getShiftCoverage() {
    return api.get('/api/dashboard/shift-coverage');
  },
  
  getRecentActivities() {
    return api.get('/api/dashboard/recent-activities');
  },
  
  getUpcomingShifts() {
    return api.get('/api/dashboard/upcoming-shifts');
  },
  
  getCalendarDays() {
    return api.get('/api/dashboard/calendar-days');
  }
};

export default dashboardService; 