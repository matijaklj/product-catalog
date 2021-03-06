package com.demo.pc.stock.api;

import com.demo.pc.common.api.commands.AddProductStockCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.common.api.commands.CreateProductStockCmd;
import com.demo.pc.common.api.commands.RemoveProductStockCmd;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("stock")
public class StockResource {

    @Inject
    private CommandGateway commandGateway;

    /*@POST
    @Path("{id}")
    public Response createStock(@PathParam("id") String id) {
        CompletableFuture<String> futureResult = commandGateway.send(new CreateProductStockCmd(id, id, 0));

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }*/

    @POST
    @Path("{id}/add/{quantity}")
    public Response addToStock(@PathParam("id") String id, @PathParam("quantity") int quantity) {
        CompletableFuture<String> futureResult = commandGateway.send(new AddProductStockCmd(id + "_stock", id, quantity));

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("{id}/remove/{quantity}")
    public Response removeFromStock(@PathParam("id") String id, @PathParam("quantity") int quantity) {
        CompletableFuture<String> futureResult = commandGateway.send(new RemoveProductStockCmd(id + "_stock", id, quantity));

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getStock(@PathParam("id") String id) {
        // todo get product read tega ubistvu ne rabimo....
        return Response.ok().build();
    }

}
