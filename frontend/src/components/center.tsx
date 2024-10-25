import type { ComponentProps } from 'react';
import { cn } from '@/lib/utils';

function Center({ children, className, ...props }: ComponentProps<'div'>) {
  return (
    <div className={cn('h-screen p-4 grid place-items-center', className)} {...props}>
      <div className="flex flex-col items-center space-y-2">
        {children}
      </div>
    </div>
  );
}

export default Center;
