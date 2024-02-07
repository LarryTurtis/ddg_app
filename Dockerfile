FROM node:13.12.0-alpine as ui

# set working directory
WORKDIR /ui-client
COPY ./client/package.json ./
COPY ./client/package-lock.json ./
COPY ./client ./
RUN npm i
RUN npm run build

FROM openjdk:8 as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
COPY --from=ui /ui-client/build/ src/main/resources/static/
RUN ./mvnw package

FROM openjdk:8
WORKDIR /usr/src/rest-service
COPY --from=build /app/target/*.jar app.jar
RUN addgroup --system gk && adduser --system gk --ingroup gk
RUN chown -R gk:gk .
USER gk:gk
ENTRYPOINT ["java","-jar","app.jar"]
