FROM openjdk:17

#WORKDIR /root

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/app-0.0.1-SNAPSHOT.jar ${JAR_PATH}/app-0.0.1-SNAPSHOT.jar

#CMD ["java","-jar","./build/libs/app-0.0.1-SNAPSHOT.jar"]
CMD java -jar -Dspring.profiles.active=${active} app-0.0.1-SNAPSHOT.jar