FROM node:20-alpine AS builder
ARG NEXT_PUBLIC_KONG_URL=http://localhost:8000
ENV NEXT_PUBLIC_KONG_URL=$NEXT_PUBLIC_KONG_URL
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/out /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
