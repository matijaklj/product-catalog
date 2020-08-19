# Sample Product Catalog application
Sample application to showcase and test the Kumuluz Axon Extension to develop microservices with the CQRS pattern.

Run Axon Server in docker:
```
docker run -d -p 8024:8024 -p 8124:8124 -p 8224:8224 --name axonserver axoniq/axonserver
```

Run Postgres in docker:
```
docker run -d --name pg-jpa -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=productCatalog -p 5432:5432 postgres:latest -c max_connections=500
```

Run MongoDb in docker:
```
docker run -p 27017:27017 --name mongo-db -d mongo:latest
```

### todo
