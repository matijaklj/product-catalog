package com.demo.pc.read.api;

import com.demo.pc.read.dtos.PageItems;
import com.demo.pc.read.dtos.ProductDto;
import com.demo.pc.read.eventHandlers.ProductEventHandlers;
import com.demo.pc.read.queries.FetchProductListQuery;
import com.demo.pc.read.queries.FetchProductSummaryQuery;
import org.axonframework.config.Configuration;
import org.axonframework.queryhandling.QueryGateway;
import org.eclipse.jetty.http.HttpStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
    private QueryGateway queryGateway;

    @Inject
    private Configuration configuration;

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {

        CompletableFuture future = queryGateway.query(new FetchProductSummaryQuery(id), ProductDto.class);

        try {
            return Response.ok(future.get()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(HttpStatus.NOT_FOUND_404).build();
        }
    }

    @GET
    public Response getProducts(@QueryParam("skip") int skip,
                                @DefaultValue("20") @QueryParam("take") int take,
                                @QueryParam("s") String search) {

        CompletableFuture future = queryGateway.query(new FetchProductListQuery(skip, take, search), PageItems.class);

        try {
            return Response.ok(future.get()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(HttpStatus.NOT_FOUND_404).build();
        }
    }
}
