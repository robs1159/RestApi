package ca.ulaval.glo4002.billing.domain.exceptions;

import ca.ulaval.glo4002.billing.domain.ProductId;

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
