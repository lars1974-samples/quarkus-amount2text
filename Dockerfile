####
# This multistage docker build first build a builder image then the real image. Build it like this:
#
# docker build -t quarkus/amount2text .
#
# Then run the container using:
#
# docker run -i --rm -p 8081:8080 quarkus/amount2text
#
###
FROM quay.io/quarkus/centos-quarkus-maven:20.2.0-java11 AS build
USER root
WORKDIR /home/gradle/src
COPY . .
RUN ./gradlew build -i -Dquarkus.package.type=native


FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3
COPY --from=build /home/gradle/src/build/*-runner /work/application
WORKDIR /work
RUN chown -R 1001 .
EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
