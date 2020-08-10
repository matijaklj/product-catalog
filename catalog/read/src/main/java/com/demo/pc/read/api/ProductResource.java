package com.demo.pc.read.api;

import com.demo.pc.read.command.ProductDto;
import com.demo.pc.read.eventHandlers.ProductEventHandlers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentMap;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("products")
public class ProductResource {

    @Inject
    private ConcurrentMap<String, ProductDto> productDBMap;

    @Inject
    private ProductEventHandlers productEventHandlers;

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {

        ProductDto p = productDBMap.get(id);

        if (p == null) {
            return Response.noContent().build();
        } else
            return Response.ok(p).build();
    }
}
