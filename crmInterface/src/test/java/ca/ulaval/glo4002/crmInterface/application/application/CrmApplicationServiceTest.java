package ca.ulaval.glo4002.crmInterface.application.application;

import ca.ulaval.glo4002.crmInterface.application.CrmApplicationService;
import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.crm.CrmClientRepository;
import ca.ulaval.glo4002.crmInterface.application.repositories.crm.CrmProductRepository;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ProductRepository;
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
    private CrmApplicationService crmApplicationService;

    private static ProductId INVALID_PRODUCT_ID;
    private static ProductId VALID_PRODUCT_ID;

    private static ClientId INVALID_CLIENT_ID = new ClientId(0);
    private static ClientId VALID_CLIENT_ID = new ClientId(1);

    private static ClientDto VALID_CLIENTDTO = new ClientDto();

    @Before
    public void setup() {
        clientRepository = mock(CrmClientRepository.class);
        productRepository = mock(CrmProductRepository.class);
        VALID_CLIENTDTO.defaultTerm = DueTerm.DAYS30;

        crmApplicationService = new CrmApplicationService(clientRepository, productRepository);
    }


    @Test(expected = ClientNotFoundException.class)
    public void givenInvalidClientId_whenFindClientById_thenThrowExeption() throws ClientNotFoundException {
        willThrow(new ClientNotFoundException(INVALID_CLIENT_ID)).given(clientRepository).getClient(INVALID_CLIENT_ID);

        crmApplicationService.findClientById(INVALID_CLIENT_ID);
    }

    @Test
    public void givenValidClientId_whenFindClientById_thenReturnClientId() throws ClientNotFoundException {
        crmApplicationService.findClientById(VALID_CLIENT_ID);
        verify(clientRepository).getClient(VALID_CLIENT_ID);
    }


    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductID_whenFindProductById_thenThrowExeption() throws ProductNotFoundException {
        willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID)).given(productRepository).getProduct(INVALID_PRODUCT_ID);

        crmApplicationService.findProductById(INVALID_PRODUCT_ID);
    }

    @Test
    public void givenValidProductId_whenFindProductIdById_thenReturnProductId() throws ProductNotFoundException {
        crmApplicationService.findProductById(VALID_PRODUCT_ID);
        verify(productRepository).getProduct(VALID_PRODUCT_ID);
    }

    @Test
    public void givenValidClientId_whenGetDefaultTerm_ThenReturnGoodDueTerm() throws ClientNotFoundException {
        willReturn(DueTerm.DAYS90).given(clientRepository).getDefaultDueTerm(VALID_CLIENT_ID);

        assertSame(DueTerm.DAYS90, clientRepository.getDefaultDueTerm(VALID_CLIENT_ID));
    }

    @Test
    public void givenValidClientId_whenValidateIfClientExist_ThenReturnFalse() throws ClientNotFoundException {
        willReturn(VALID_CLIENTDTO).given(clientRepository).getClient(VALID_CLIENT_ID);

        assertTrue(crmApplicationService.isClientExist(VALID_CLIENT_ID));
        verify(clientRepository).getClient(VALID_CLIENT_ID);
    }
}
