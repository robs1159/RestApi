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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    public void givenValidClientId_whenFindClientById_thenReturnClientId() throws ClientNotFoundException {
        clientRepository.getClient(VALID_CLIENT_ID);
        verify(clientRepository).getClient(VALID_CLIENT_ID);
    }


    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductID_whenFindProductById_thenThrowExeption() throws ProductNotFoundException {
        willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID)).given(productRepository).exist(INVALID_PRODUCT_ID);

        productRepository.exist(INVALID_PRODUCT_ID);
    }

    @Test
    public void givenValidProductId_whenFindProductIdById_thenReturnProductId() throws ProductNotFoundException {
        productRepository.exist(VALID_PRODUCT_ID);
        verify(productRepository).exist(VALID_PRODUCT_ID);
    }

    @Test
    public void givenValidClientId_whenGetDefaultTerm_ThenReturnGoodDueTerm() throws ClientNotFoundException {
        willReturn(DueTerm.DAYS90).given(clientRepository).getDefaultDueTerm(VALID_CLIENT_ID);

        assertSame(DueTerm.DAYS90, clientRepository.getDefaultDueTerm(VALID_CLIENT_ID));
    }

    @Test
    public void givenValidClientId_whenValidateIfClientExist_ThenReturnFalse() throws ClientNotFoundException {
        willReturn(VALID_CLIENT).given(clientRepository).getClient(VALID_CLIENT_ID);

        assertTrue(clientRepository.getClient(VALID_CLIENT_ID) != null);
        verify(clientRepository).getClient(VALID_CLIENT_ID);
    }
}
