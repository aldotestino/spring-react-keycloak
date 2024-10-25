import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import React from 'react';

const TanStackRouterDevtools
// eslint-disable-next-line ts/ban-ts-comment
// @ts-ignore
  = import.meta.env.MODE === 'production'
    ? () => null // Render nothing in production
    : React.lazy(() =>
    // Lazy load in development
      import('@tanstack/router-devtools').then(res => ({
        default: res.TanStackRouterDevtools,
        // For Embedded Mode
        // default: res.TanStackRouterDevtoolsPanel
      })),
    );

export function DevTools() {
  return (
    <>
      <ReactQueryDevtools buttonPosition="bottom-right" />
      <TanStackRouterDevtools position="bottom-right" />
    </>
  );
}
