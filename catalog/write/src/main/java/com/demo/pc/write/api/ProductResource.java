package com.demo.pc.write.api;

import com.demo.pc.common.api.commands.AddProductCategoryCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.write.dtos.ProductDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("products")
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CommandGateway commandGateway;

    @PersistenceContext
    private EntityManager entityManager;

    @POST
    public Response createProduct(CreateProductCmd cmd) {

        CompletableFuture<String> futureResult = commandGateway.send(cmd);

        try {
            return Response.created(null).entity(futureResult.get()).build();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("{id}/categories/{categoryId}")
    public Response createProduct(@PathParam("id") String id, @PathParam("categoryId") String categoryId) {

        // todo validation

        CompletableFuture<String> futureResult = commandGateway.send(new AddProductCategoryCmd(id, categoryId));

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("test")
    public Response testCreateProduct() {

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 300; i < 305; i++) {
            CreateProductCmd cmd = new CreateProductCmd(Integer.toString(i), "test product " + i, "test desc");

            futures.add(commandGateway.send(cmd));
        }

        List<String> ids = new ArrayList<>();
        for(CompletableFuture<String> f : futures) {
            try {
                ids.add(f.get());
            } catch (Exception e) {
                logger.warn("ERROR TEST");
                logger.warn(e.getMessage());
            }
        }
        return Response.ok(ids).build();
    }
}
