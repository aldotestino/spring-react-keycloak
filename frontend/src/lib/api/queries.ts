import type { Message } from '../types';
import { queryOptions } from '@tanstack/react-query';
import { apiClient } from './client';

export function messagesQuery(accessToken: string) {
  return queryOptions({
    queryKey: ['messages'],
    queryFn: async () => {
      const { data } = await apiClient.get<Message[]>('/message', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return data;
    },
  });
};
