package com.demo.pc.write.api;

import com.demo.pc.common.api.commands.AddProductCategoryCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.common.api.commands.UpdateProductCmd;
import com.demo.pc.common.api.exceptions.CategoryAlreadyAddedException;
import com.demo.pc.write.entities.Product;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    private CommandGateway commandGateway;

    @POST
    public CompletableFuture<Product> createProduct(CreateProductCmd cmd) {

        logger.info("Sending command:: " + cmd.toString());

        CompletableFuture<Product> futureResult = commandGateway.send(cmd);


        return futureResult;
    }

    @PUT
    public CompletableFuture<Product> editProduct(UpdateProductCmd cmd) {

        logger.info("Sending command:: " + cmd.toString());

        CompletableFuture<Product> futureResult = commandGateway.send(cmd);


        return futureResult;
    }

    @POST
    @Path("{id}/categories/{categoryId}")
    public CompletableFuture<String> addProductCategory(@PathParam("id") String id, @PathParam("categoryId") String categoryId) {

        AddProductCategoryCmd cmd = new AddProductCategoryCmd(id, categoryId);

        logger.info("Sending command:: " + cmd.toString());

        CompletableFuture<String> futureResult = commandGateway.send(cmd);

        CompletableFuture<String> res = futureResult
                .handle((s,e) -> {
                    if (e == null)
                        return s;
                    if (e instanceof CategoryAlreadyAddedException){
                        s = "0";
                        return s;
                    } else
                        throw new RuntimeException("Computation error!");
                });

        return res;
    }

}
