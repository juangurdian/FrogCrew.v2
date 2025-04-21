import api from './api';

const crewDashboardService = {
  /**
   * Get all crew dashboard data
   */
  getCrewDashboard: async () => {
    const response = await api.get('/api/crew/dashboard');
    return response.data;
  },

  /**
   * Get upcoming shifts for the logged-in crew member
   */
  getUpcomingShifts: async () => {
    const response = await api.get('/api/crew/dashboard/upcoming-shifts');
    return response.data;
  },

  /**
   * Get notifications for the logged-in crew member
   */
  getNotifications: async () => {
    const response = await api.get('/api/crew/dashboard/notifications');
    return response.data;
  },

  /**
   * Get availability hours for the logged-in crew member
   */
  getAvailabilityHours: async () => {
    const response = await api.get('/api/crew/dashboard/availability-hours');
    return response.data;
  },

  /**
   * Update crew member availability
   */
  updateAvailability: async (availabilityData) => {
    const response = await api.post('/api/crew/availability', availabilityData);
    return response.data;
  },

  /**
   * Request time off
   */
  requestTimeOff: async (timeOffRequest) => {
    const response = await api.post('/api/crew/time-off', timeOffRequest);
    return response.data;
  }
};

export { crewDashboardService }; 