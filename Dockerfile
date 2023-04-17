FROM java:8
MAINTAINER mjz_comment
ADD miz_comment-0.0.1-SNAPSHOT.jar comment.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","comment.jar"]

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/.urandom","-jar","/comment.jar"]
