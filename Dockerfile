FROM gradle:7.2.0-jdk11-alpine as builder
RUN mkdir /build
WORKDIR /build
COPY --chown=gradle:gradle . /build
RUN gradle shadowJar --no-daemon

FROM gcr.io/distroless/java11-debian11:nonroot
# FROM eclipse-temurin:11-jre-alpine
# RUN mkdir /app
# RUN adduser -D noroot

WORKDIR /app
COPY --from=builder /build/build/libs/*-uberjar.jar /app/app.jar
#--chown=noroot:noroot

ENV PORT=8080
ENV CONTAINER=true

EXPOSE 8080

#USER noroot
CMD ["/app/app.jar"]
