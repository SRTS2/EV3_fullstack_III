const KONG_URL = process.env.KONG_URL || 'http://localhost:8000';

/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone',
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: `${KONG_URL}/api/:path*`,
      },
    ];
  },
};

module.exports = nextConfig;
