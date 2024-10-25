import Center from '@/components/center';
import { Button, buttonVariants } from '@/components/ui/button';
import { createFileRoute, Link } from '@tanstack/react-router';
import { useAuth } from 'react-oidc-context';

export const Route = createFileRoute('/')({
  component: HomePage,
});

function HomePage() {
  const { signinRedirect, isAuthenticated, user } = useAuth();

  async function handleLogin() {
    await signinRedirect();
  }

  if (!isAuthenticated) {
    return (
      <Center>
        <h1 className="text-2xl font-semibold">Welcome!</h1>
        <Button type="button" onClick={handleLogin}>Log In</Button>
      </Center>
    );
  }

  return (
    <Center>
      <h1 className="text-2xl font-semibold">
        Welcome,
        {' '}
        {user?.profile.name}
      </h1>
      <Link to="/chat" className={buttonVariants()}>Go to Chat</Link>
    </Center>
  );
}
