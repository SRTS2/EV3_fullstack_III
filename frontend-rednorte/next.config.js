/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'export',
  images: {
    unoptimized: true,
  },
  env: {
    NEXT_PUBLIC_KONG_URL: process.env.NEXT_PUBLIC_KONG_URL || 'http://localhost:8000',
  },
}

module.exports = nextConfig
