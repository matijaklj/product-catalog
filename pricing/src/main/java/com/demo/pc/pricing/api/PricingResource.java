package com.demo.pc.pricing.api;

import com.demo.pc.common.api.commands.AddProductStockCmd;
import com.demo.pc.common.api.commands.CreateProductPriceCmd;
import com.demo.pc.common.api.commands.RemoveProductPriceCmd;
import com.demo.pc.common.api.commands.UpdateProductPriceCmd;
import com.demo.pc.pricing.dtos.PriceDto;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("pricing")
public class PricingResource {

    @Inject
    private CommandGateway commandGateway;

    @POST
    public CompletableFuture<String> createProductPrice(PriceDto priceDto) {
        CompletableFuture<String> futureResult = commandGateway.send(
                new CreateProductPriceCmd(priceDto.getProductId() + "_price",
                        priceDto.getProductId(),
                        priceDto.getValue(),
                        null,
                        null)
        );

        return futureResult;
    }

    @PUT
    public CompletableFuture<String> updateProductPrice(PriceDto priceDto) {
        CompletableFuture<String> futureResult = commandGateway.send(
                new UpdateProductPriceCmd(priceDto.getProductId()  + "_price",
                        priceDto.getProductId(),
                        priceDto.getValue(),
                        null,
                        null)
        );

        return futureResult;
    }

    @DELETE
    @Path("{id}")
    public Response removeProductPrice(@PathParam("id") String id) {
        CompletableFuture<String> futureResult = commandGateway.send(new RemoveProductPriceCmd(id));

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
