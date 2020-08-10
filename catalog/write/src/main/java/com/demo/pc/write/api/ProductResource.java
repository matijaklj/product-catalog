package com.demo.pc.write.api;

import com.demo.pc.common.api.commands.AddProductCategoryCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("products")
public class ProductResource {

    @Inject
    private CommandGateway commandGateway;

    //@Inject
    //private EntityManager entityManager;

    @POST
    public Response createProduct(CreateProductCmd createProductCmd) {

        CompletableFuture<String> futureResult = commandGateway.send(createProductCmd);

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
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
}
