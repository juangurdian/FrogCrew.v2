import api from './api';

export const shiftService = {
  getAllShifts() {
    return api.get('/api/shifts');
  },
  
  getShiftById(id: number) {
    return api.get(`/api/shifts/${id}`);
  },
  
  getUpcomingShifts() {
    return api.get('/api/shifts/upcoming');
  },
  
  createShift(shiftData: any) {
    return api.post('/api/shifts', shiftData);
  },
  
  updateShift(id: number, shiftData: any) {
    return api.put(`/api/shifts/${id}`, shiftData);
  },
  
  deleteShift(id: number) {
    return api.delete(`/api/shifts/${id}`);
  },
  
  getShiftAssignments(shiftId: number) {
    return api.get(`/api/shifts/${shiftId}/assignments`);
  },
  
  assignCrewToShift(shiftId: number, userId: number, positionId: number) {
    return api.post(`/api/shifts/${shiftId}/assignments`, {
      userId,
      positionId
    });
  },
  
  removeCrewFromShift(assignmentId: number) {
    return api.delete(`/api/shift-assignments/${assignmentId}`);
  }
};

export default shiftService; 