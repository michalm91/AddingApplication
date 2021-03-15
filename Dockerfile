FROM maven:3.6.3-jdk-11
ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY . .
RUN mvn clean install
EXPOSE 8099
ENTRYPOINT mvn spring-boot:run -Drun.jvmArguments="-XX:MaxRAMPercentage=70" -pl decertoAplication
