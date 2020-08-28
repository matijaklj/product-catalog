package com.demo.pc.read.api;

import com.demo.pc.read.dtos.PageItems;
import com.demo.pc.read.dtos.ProductDto;
import com.demo.pc.read.eventHandlers.ProductEventHandlers;
import com.demo.pc.read.queries.FetchProductListQuery;
import com.demo.pc.read.queries.FetchProductSummaryQuery;
import org.axonframework.config.Configuration;
import org.axonframework.queryhandling.QueryGateway;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("products")
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Inject
    private QueryGateway queryGateway;

    @Inject
    private Configuration configuration;

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {

        logger.info("sending query: Get Product id: " + id);

        CompletableFuture<ProductDto> future = queryGateway.query(new FetchProductSummaryQuery(id), ProductDto.class);

        try {
            return Response.ok().entity(future.get()).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

    }

    @GET
    public Response getProducts(@QueryParam("skip") int skip,
                                @DefaultValue("20") @QueryParam("take") int take,
                                @QueryParam("s") String search) {
        logger.info("sending query: Get Products skip: " + skip + ", take: " + take + ", search: " + search);

        CompletableFuture<PageItems> future = queryGateway.query(new FetchProductListQuery(skip, take, search), PageItems.class);

        try {
            return Response.ok().entity(future.get()).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
