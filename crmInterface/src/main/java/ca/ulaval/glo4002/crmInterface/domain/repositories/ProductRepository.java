package ca.ulaval.glo4002.crmInterface.domain.repositories;

import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;

public interface ProductRepository {

    ProductDto getProduct(ProductId productId) throws ProductNotFoundException;
}
