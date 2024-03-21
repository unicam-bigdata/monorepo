# Use the official Node.js image as a base
FROM node:latest

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

# Expose port 3000 to the outside world
EXPOSE 3000

# Start the React development server
CMD ["npm", "start"]

#Stage 1
#FROM node:14-alpine as builder
#WORKDIR /app
#COPY package*.json .
#COPY package*.lock .
#RUN npm install
#COPY . .
#ENV REACT_APP_BACKEND_URL=http://backend:8080
#RUN npm start

#Stage 2
#FROM nginx:1.19.0
#WORKDIR /usr/share/nginx/html
#RUN rm -rf ./*
#COPY --from=builder /app/build .
#EXPOSE 80

#ENTRYPOINT ["nginx", "-g", "daemon off;"]