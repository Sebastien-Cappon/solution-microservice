FROM node:18-alpine

WORKDIR /app

COPY package*.json ./
COPY . .

RUN npm install
RUN npm install -g @angular/cli

EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]