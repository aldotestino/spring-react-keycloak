import type { Message } from '@/lib/types';
import { useAuth } from 'react-oidc-context';

function MessageItem({ content, userId }: Message) {
  const auth = useAuth();

  const userName = auth.user?.profile.sub === userId ? 'You' : userId;

  return (
    <div className="py-2 space-y-1">
      <p className="text-muted-foreground text-sm font-semibold min-w-0 truncate">{userName}</p>
      <p>{content}</p>
    </div>
  );
}

export default MessageItem;
