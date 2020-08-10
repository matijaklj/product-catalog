package com.demo.pc.write.api;

import com.demo.pc.common.api.commands.CreateCategoryCmd;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("categories")
public class CategoryResource {

    @Inject
    private CommandGateway commandGateway;

    @POST
    public Response createCategory(CreateCategoryCmd createCategoryCmd) {

        CompletableFuture<String> futureResult = commandGateway.send(createCategoryCmd);

        try {
            return Response.ok(futureResult.get()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
