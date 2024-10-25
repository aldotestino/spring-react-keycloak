import type { QueryClient } from '@tanstack/react-query';
import type { AuthContextProps } from 'react-oidc-context';
import { DevTools } from '@/lib/dev-tools';
import { createRootRouteWithContext, Outlet } from '@tanstack/react-router';

export const Route = createRootRouteWithContext<{
  queryClient: QueryClient;
  auth: AuthContextProps;
}>()({
  component: RootComponent,
});

function RootComponent() {
  return (
    <>
      <Outlet />
      <DevTools />
    </>
  );
}
