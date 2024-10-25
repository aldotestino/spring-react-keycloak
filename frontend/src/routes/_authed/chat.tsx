import MessageInput from '@/components/message-input';
import MessageItem from '@/components/message-item';
import { Button } from '@/components/ui/button';
import { messagesQuery } from '@/lib/api/queries';
import { useSuspenseQuery } from '@tanstack/react-query';
import { createFileRoute, useNavigate } from '@tanstack/react-router';
import { useAuth } from 'react-oidc-context';

export const Route = createFileRoute('/_authed/chat')({
  loader: ({ context: { queryClient, auth } }) =>
    queryClient.ensureQueryData(messagesQuery(auth.user!.access_token)),
  component: ChatPage,
});

function ChatPage() {
  const auth = useAuth();
  const { data } = useSuspenseQuery(messagesQuery(auth.user!.access_token));

  async function handleLogout() {
    await auth.signoutRedirect();
  }

  return (
    <div className="h-screen w-full max-w-screen-md mx-auto grid grid-rows-[auto,1fr,auto] overflow-y-none">
      <header className="p-4 flex items-center justify-between">
        <h1 className="text-2xl font-semibold">Chat</h1>
        <Button type="button" onClick={handleLogout}>Log Out</Button>
      </header>
      <div className="overflow-y-auto p-4 divide-y">
        {data.map(message => <MessageItem key={message.id} {...message} />)}
      </div>
      <MessageInput />
    </div>
  );
}
