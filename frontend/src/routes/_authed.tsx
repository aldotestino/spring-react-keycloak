import { createFileRoute, Outlet, redirect } from '@tanstack/react-router';

export const Route = createFileRoute('/_authed')({
  beforeLoad: ({ context: { auth } }) => {
    if (!auth.isAuthenticated) {
      throw redirect({
        to: '/',
      });
    }
  },
  component: Outlet,
});
