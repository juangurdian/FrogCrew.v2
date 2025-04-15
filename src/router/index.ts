import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  // Admin routes
  {
    path: '/admin',
    component: () => import('../layouts/AdminLayout.vue'),
    children: [
      {
        path: '',
        redirect: { name: 'AdminDashboard' }
      },
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('../views/admin/AdminDashboard.vue')
      },
      {
        path: 'crew',
        name: 'AdminCrewList',
        component: () => import('../views/admin/CrewList.vue')
      },
      {
        path: 'crew/invite',
        name: 'AdminInviteCrew',
        component: () => import('../views/admin/InviteCrew.vue')
      },
      {
        path: 'events',
        name: 'AdminEvents',
        component: () => import('../views/admin/Events.vue')
      },
      {
        path: 'events/create',
        name: 'AdminCreateEvent',
        component: () => import('../views/admin/CreateEvent.vue')
      },
      {
        path: 'schedule/create',
        name: 'AdminCreateSchedule',
        component: () => import('../views/admin/CreateSchedule.vue')
      },
      {
        path: 'reports/attendance',
        name: 'AdminAttendanceReports',
        component: () => import('../views/admin/AttendanceReports.vue')
      },
      {
        path: 'reports/financial',
        name: 'AdminFinancialReports',
        component: () => import('../views/admin/FinancialReports.vue')
      },
      {
        path: 'availability',
        name: 'AdminManageAvailability',
        component: () => import('../views/admin/ManageAvailability.vue')
      },
      {
        path: 'shift-approvals',
        name: 'AdminShiftApprovals',
        component: () => import('../views/admin/ShiftApprovals.vue')
      },
      {
        path: 'reports',
        name: 'AdminReports',
        component: () => import('../views/admin/Reports.vue')
      }
    ]
  },
  // Crew routes
  {
    path: '/crew',
    component: () => import('../layouts/CrewLayout.vue'),
    children: [
      {
        path: '',
        redirect: { name: 'CrewDashboard' }
      },
      {
        path: 'dashboard',
        name: 'CrewDashboard',
        component: () => import('../views/crew/CrewDashboard.vue')
      },
      {
        path: 'schedule',
        name: 'CrewSchedule',
        component: () => import('../views/crew/Schedule.vue')
      },
      {
        path: 'availability',
        name: 'CrewAvailability',
        component: () => import('../views/crew/Availability.vue')
      },
      {
        path: 'events',
        name: 'CrewEvents',
        component: () => import('../views/crew/Events.vue')
      },
      {
        path: 'shift-exchange',
        name: 'CrewShiftExchange',
        component: () => import('../views/crew/ShiftExchange.vue')
      },
      {
        path: 'profile',
        name: 'CrewProfile',
        component: () => import('../views/crew/Profile.vue')
      },
      {
        path: 'settings',
        name: 'CrewSettings',
        component: () => import('../views/crew/Settings.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router 