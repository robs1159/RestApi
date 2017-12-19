package ca.ulaval.glo4002.crmInterface.application.repositories.crm;

import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.HttpRequester;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ProductRepository;

import java.io.FileNotFoundException;

public class CrmProductRepository implements ProductRepository {

    private static final String PRODUCTS_URL = "http://localhost:8080/products/";

    private HttpRequester httpRequester = new HttpRequester();

    public CrmProductRepository() {
        this.httpRequester = new HttpRequester();
    }

    public CrmProductRepository(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }

    @Override
    public ProductDto getProduct(ProductId productId) throws ProductNotFoundException {
        try {
            return httpRequester.getDtoFromUrl(PRODUCTS_URL + productId, ProductDto.class);
        } catch (FileNotFoundException e) {
            throw new ProductNotFoundException(productId);
        }
    }
}
