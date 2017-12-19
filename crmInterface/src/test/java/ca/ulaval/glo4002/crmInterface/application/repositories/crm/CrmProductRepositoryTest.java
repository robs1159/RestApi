package ca.ulaval.glo4002.crmInterface.application.repositories.crm;

import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.HttpRequester;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

public class CrmProductRepositoryTest {

    private static final ProductId VALID_PRODUCT_ID = new ProductId(123);
    private static final ProductId INVALID_PRODUCT_ID = new ProductId(456);

    private HttpRequester httpRequester;
    private CrmProductRepository crmProductCommunicator;

    private ProductDto productDto;

    @Before
    public void setup() throws Exception {
        httpRequester = mock(HttpRequester.class);

        crmProductCommunicator = new CrmProductRepository(httpRequester);

        productDto = new ProductDto();

        when(httpRequester.getDtoFromUrl(contains(VALID_PRODUCT_ID.toString()), any())).thenReturn(productDto);
        doThrow(new FileNotFoundException()).when(httpRequester).getDtoFromUrl(contains(INVALID_PRODUCT_ID.toString()), any());
    }

    @Test
    public void givenAValidProductId_whenGettingTheProduct_thenReturnTheProduct() throws Exception {
        ProductDto productDto = crmProductCommunicator.getProduct(VALID_PRODUCT_ID);

        assertNotNull(productDto);
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenAnInvalidProductId_WhenGettingTheProduct_thenThrowsAnException() throws Exception {
        crmProductCommunicator.getProduct(INVALID_PRODUCT_ID);
    }
}