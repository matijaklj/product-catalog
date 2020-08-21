FROM openjdk
RUN mkdir /app
WORKDIR /app
ADD ./target/ /app
EXPOSE 9090
ENV KUMULUZEE_DATASOURCES[0]_CONNECTION-URL=jdbc:postgresql://postgres-db:5432/productCatalog
ENV KUMULUZEE_MONGODB_CONNECTION-STRING=mongodb://mongo-db
ENV AXON_AXONSERVER_SERVERS=axon-server
ENV PORT=9090
CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]