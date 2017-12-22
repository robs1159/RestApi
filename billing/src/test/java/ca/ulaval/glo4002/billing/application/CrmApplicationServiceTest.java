package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.repositories.crm.CrmClientRepository;
import ca.ulaval.glo4002.billing.application.repositories.crm.CrmProductRepository;
import ca.ulaval.glo4002.billing.domain.Client;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import ca.ulaval.glo4002.billing.domain.ProductId;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class CrmApplicationServiceTest {

    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    private static ProductId INVALID_PRODUCT_ID;
    private static ProductId VALID_PRODUCT_ID;

    private static ClientId INVALID_CLIENT_ID = new ClientId(0);
    private static ClientId VALID_CLIENT_ID = new ClientId(1);

    private static Client VALID_CLIENT;

    @Before
    public void setup() {
        clientRepository = mock(CrmClientRepository.class);
        productRepository = mock(CrmProductRepository.class);
        VALID_CLIENT = new Client(new ClientId(), DueTerm.DAYS30);
    }

    @Test(expected = ClientNotFoundException.class)
    public void givenInvalidClientId_whenFindClientById_thenThrowExeption() throws ClientNotFoundException {
        willThrow(new ClientNotFoundException(INVALID_CLIENT_ID)).given(clientRepository).getClient(INVALID_CLIENT_ID);

        clientRepository.getClient(INVALID_CLIENT_ID);
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductID_whenFindProductById_thenThrowExeption() throws ProductNotFoundException {
        willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID)).given(productRepository).exist(INVALID_PRODUCT_ID);

        productRepository.exist(INVALID_PRODUCT_ID);
    }
}
