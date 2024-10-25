import type { Message } from '../types';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from 'react-oidc-context';
import { apiClient } from './client';

export function useSendMessage() {
  const auth = useAuth();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (content: string) => {
      const { data } = await apiClient.post<Message>('/message', { content }, {
        headers: {
          Authorization: `Bearer ${auth.user!.access_token}`,
        },
      });
      return data;
    },
    onSuccess: (data) => {
      queryClient.setQueryData<Message[]>(['messages'], (messages) => {
        return messages ? [...messages, data] : [data];
      });
    },
  });
}
