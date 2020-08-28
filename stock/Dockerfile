FROM openjdk
RUN mkdir /app
WORKDIR /app
ADD ./target/ /app
EXPOSE 8080
CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]