import { useSendMessage } from '@/lib/api/mutations';
import { Send } from 'lucide-react';
import { useRef } from 'react';
import { Button } from './ui/button';
import { Input } from './ui/input';

function MessageInput() {
  const form = useRef<HTMLFormElement>(null);

  const sendMessage = useSendMessage();

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (form.current) {
      const message = form.current.elements.namedItem('message') as HTMLInputElement;
      await sendMessage.mutateAsync(message.value);
      form.current.reset();
    }
  }

  return (
    <form ref={form} onSubmit={handleSubmit} className="p-4 grid grid-cols-[1fr,auto] gap-2">
      <Input name="message" />
      <Button type="submit">
        Send
        <Send />
      </Button>
    </form>
  );
}

export default MessageInput;
