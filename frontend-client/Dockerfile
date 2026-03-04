# Stage 1: Build the Angular app
FROM node:20-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# וודא שהפקודה כאן תואמת למה שיש לך (production/development)
RUN npm run build --configuration=production

# Stage 2: Serve with Nginx
FROM nginx:alpine
# מעתיק את קבצי האתר שבנינו
COPY --from=build /app/dist/ev-fleet-frontend/browser /usr/share/nginx/html

# --- השורה החדשה שצריך להוסיף ---
COPY nginx.conf /etc/nginx/conf.d/default.conf 
# --------------------------------

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]