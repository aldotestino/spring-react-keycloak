import type { AuthProviderProps } from 'react-oidc-context';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { createRouter, RouterProvider } from '@tanstack/react-router';
import { Loader2 } from 'lucide-react';
import ReactDOM from 'react-dom/client';
import { AuthProvider, useAuth } from 'react-oidc-context';
import Center from './components/center';
import { routeTree } from './routeTree.gen';
import './styles/index.css';

const queryClient = new QueryClient();

const oidcConfig: AuthProviderProps = {
  authority: 'http://localhost:8085/realms/test',
  client_id: 'frontend',
  redirect_uri: 'http://localhost:3001/chat',
  post_logout_redirect_uri: 'http://localhost:3001/',
};

// Set up a Router instance
const router = createRouter({
  routeTree,
  context: {
    queryClient,
    auth: undefined!,
  },
  defaultPreload: 'intent',
  defaultPreloadStaleTime: 0,
});

// Register things for typesafety
declare module '@tanstack/react-router' {
  // eslint-disable-next-line ts/ban-ts-comment
  // @ts-ignore
  type Register = {
    router: typeof router;
  };
}

const rootElement = document.getElementById('app')!;

// eslint-disable-next-line react-refresh/only-export-components
function RouterProviderWithAuth() {
  const auth = useAuth();

  if (auth.isLoading) {
    return (
      <Center className="text-muted-foreground">
        <Loader2 className="size-10 animate-spin" />
        <p className="font-semibold text-lg">Loading...</p>
      </Center>
    );
  }

  return (
    <RouterProvider router={router} context={{ auth }} />
  );
}

if (!rootElement.innerHTML) {
  const root = ReactDOM.createRoot(rootElement);
  root.render(
    <QueryClientProvider client={queryClient}>
      <AuthProvider {...oidcConfig}>
        <RouterProviderWithAuth />
      </AuthProvider>
    </QueryClientProvider>,
  );
}
