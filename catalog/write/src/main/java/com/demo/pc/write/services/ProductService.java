package com.demo.pc.write.services;

import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.write.dtos.ProductDto;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
// todo remove
public class ProductService {

    @Inject
    private CommandGateway commandGateway;

    public String createNewProduct(ProductDto productDto) throws Exception {
        //UUID uuid = Generators.randomBasedGenerator().generate();

        /*Product p = em.find(Product.class, uuid.toString());

        while(p != null) {
            uuid = Generators.randomBasedGenerator().generate();

            p = em.find(Product.class, uuid.toString());
        }


        CreateProductCmd cmd = new CreateProductCmd(uuid.toString(),
                productDto.getName(),
                productDto.getDescription());

        CompletableFuture<String> futureResult = commandGateway.send(cmd);

        return futureResult.get();
         */
        return null;
    }
}
