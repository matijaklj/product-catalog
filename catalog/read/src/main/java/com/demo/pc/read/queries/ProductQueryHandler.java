package com.demo.pc.read.queries;

import com.demo.pc.read.dtos.PageItems;
import com.demo.pc.read.dtos.ProductDto;
import com.demo.pc.read.repositories.ProductRepository;
import org.axonframework.queryhandling.QueryExecutionException;
import org.axonframework.queryhandling.QueryHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductQueryHandler {

    @Inject
    private ProductRepository productRepository;

    @QueryHandler
    public ProductDto handle(FetchProductSummaryQuery query) {
        return productRepository.getProduct(query.getProductId());
    }

    @QueryHandler
    public PageItems<ProductDto> handle(FetchProductListQuery query) {
        return productRepository.getProducts(query.getSkip(), query.getTakeCount(), query.getSearch());
    }
}
