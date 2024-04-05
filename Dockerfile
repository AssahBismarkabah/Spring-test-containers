FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer = "awambengrodrick@gmail.com"

WORKDIR /app

COPY target/test-0.0.1-SNAPSHOT.jar .
 
COPY run.sh .

RUN chmod +x run.sh

CMD [ "sh", "run.sh" ]