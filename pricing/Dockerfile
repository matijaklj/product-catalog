FROM openjdk
RUN mkdir /app
WORKDIR /app
ADD ./target/ /app
CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]