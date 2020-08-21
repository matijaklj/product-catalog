FROM openjdk
RUN mkdir /app
WORKDIR /app
ADD ./target/ /app
EXPOSE 7070
ENV PORT=7070
CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]