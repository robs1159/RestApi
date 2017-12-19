package ca.ulaval.glo4002.billing.application.exceptions;

import ca.ulaval.glo4002.crmInterface.domain.ProductId;

public class BillItemAsANegativeValueException extends RuntimeException {

    private final ProductId productId;

    public BillItemAsANegativeValueException(ProductId productId) {
        this.productId = productId;
    }

    public ProductId getProductId() {
        return productId;
    }

}
