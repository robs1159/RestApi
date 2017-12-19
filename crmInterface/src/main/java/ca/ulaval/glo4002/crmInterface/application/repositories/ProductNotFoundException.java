package ca.ulaval.glo4002.crmInterface.application.repositories;

import ca.ulaval.glo4002.crmInterface.domain.ProductId;

import java.io.FileNotFoundException;

public class ProductNotFoundException extends FileNotFoundException {

    private final ProductId productId;

    public ProductNotFoundException(ProductId productId) {
        this.productId = productId;
    }

    public ProductId getProductId() {
        return productId;
    }
}
