FROM alpine:edge
MAINTAINER coarsehorse
COPY . /anotes
RUN apk add --no-cache \
    openjdk11 \
    maven
WORKDIR /anotes
RUN mvn clean install
WORKDIR /anotes/target
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "anotes-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
