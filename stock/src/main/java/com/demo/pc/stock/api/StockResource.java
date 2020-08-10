package com.demo.pc.stock.api;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("read")
public class StockResource {


    @POST
    public Response createStock(@PathParam("id") String id) {
        // todo create read
        return Response.ok().build();
    }

    @POST
    @Path("{id}/add")
    public Response addToStock(@PathParam("id") String id) {
        // todo add to read
        return Response.ok().build();
    }

    @POST
    @Path("{id}/remove")
    public Response removeFromStock(@PathParam("id") String id) {
        // todo remove from read
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Response getStock(@PathParam("id") String id) {
        // todo get product read tega ubistvu ne rabimo....
        return Response.ok().build();
    }
}
