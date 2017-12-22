package ca.ulaval.glo4002.billing.domain.repositories;

import ca.ulaval.glo4002.billing.application.dto.ProductDto;
import ca.ulaval.glo4002.billing.domain.ProductId;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;

public interface ProductRepository {
    String PRODUCTS_URL = "http://localhost:8080/products/";

    ProductDto exist(ProductId productId) throws ProductNotFoundException;
}
