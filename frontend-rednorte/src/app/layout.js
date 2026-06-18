'use client';

import './globals.css';
import { AuthProvider } from '@/components/AuthProvider';
import Navbar from '@/components/Navbar';
import { usePathname } from 'next/navigation';

export default function RootLayout({ children }) {
  const pathname = usePathname();
  const isAuthPage = pathname === '/login' || pathname === '/register';

  return (
    <html lang="es">
      <body>
        <AuthProvider>
          {!isAuthPage && <Navbar />}
          <div className="container">
            {children}
          </div>
        </AuthProvider>
      </body>
    </html>
  );
}
