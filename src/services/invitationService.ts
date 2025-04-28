import api from './api';

interface SingleInvite {
    email: string;
    position: string;
    message?: string;
}

interface Invitation {
    id: number;
    email: string;
    position: string;
    message?: string;
    status: 'PENDING' | 'ACCEPTED' | 'DECLINED' | 'CANCELLED' | 'EXPIRED';
    token: string;
    createdAt: string;
    updatedAt: string;
    expiresAt: string;
}

const invitationService = {
    /**
     * Send a single invitation
     */
    sendSingleInvite: async (invite: SingleInvite): Promise<Invitation> => {
        const response = await api.post('/api/invitations/single', invite);
        return response.data;
    },

    /**
     * Send bulk invitations via CSV file
     */
    sendBulkInvite: async (file: File): Promise<Invitation[]> => {
        const formData = new FormData();
        formData.append('file', file);
        const response = await api.post('/api/invitations/bulk', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    },

    /**
     * Get recent invitations
     */
    getRecentInvitations: async (): Promise<Invitation[]> => {
        const response = await api.get('/api/invitations/recent');
        return response.data;
    },

    /**
     * Resend an invitation
     */
    resendInvitation: async (id: number): Promise<Invitation> => {
        const response = await api.post(`/api/invitations/${id}/resend`);
        return response.data;
    },

    /**
     * Cancel an invitation
     */
    cancelInvitation: async (id: number): Promise<void> => {
        await api.post(`/api/invitations/${id}/cancel`);
    }
};

export { invitationService, type Invitation, type SingleInvite }; 