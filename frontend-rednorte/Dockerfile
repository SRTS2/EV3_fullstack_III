FROM node:20-alpine AS builder
ARG KONG_URL=http://kong:8000
ENV KONG_URL=$KONG_URL
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM node:20-alpine AS runner
WORKDIR /app
ENV NODE_ENV=production
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/public ./public
EXPOSE 80
ENV PORT=80
CMD ["node", "server.js"]
