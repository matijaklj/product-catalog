FROM openjdk
RUN mkdir /app
WORKDIR /app
ADD ./target/ /app
EXPOSE 8080
#ENV KUMULUZEE_DATASOURCES[0]_CONNECTION-URL=jdbc:postgresql://postgres-db:5432/productCatalog
#ENV AXON_AXONSERVER_SERVERS=axon-server
#ENV PORT=80
CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]

#FROM openjdk

#RUN mkdir /app

#WORKDIR /app

#ADD ./target/write-1.0-SNAPSHOT.jar /app

#EXPOSE 8080

#ENV KUMULUZEE_DATASOURCES[0]_CONNECTION-URL=jdbc:postgresql://postgres-db:5432/productCatalog
#ENV AXON_AXONSERVER_SERVERS=axon-server

#CMD ["java", "-jar", "write-1.0-SNAPSHOT.jar"]