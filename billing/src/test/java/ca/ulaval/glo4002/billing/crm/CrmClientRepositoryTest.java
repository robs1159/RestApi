package ca.ulaval.glo4002.billing.crm;

import ca.ulaval.glo4002.billing.application.dto.ClientDto;
import ca.ulaval.glo4002.billing.application.repositories.HttpRequester;
import ca.ulaval.glo4002.billing.application.repositories.crm.CrmClientRepository;
import ca.ulaval.glo4002.billing.domain.Client;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

public class CrmClientRepositoryTest {

    private static final ClientId VALID_CLIENT_ID = new ClientId(123);
    private static final ClientId INVALID_CLIENT_ID = new ClientId(456);

    private HttpRequester httpRequester;
    private CrmClientRepository crmClientRepository;

    private ClientDto clientDto;

    @Before
    public void setup() throws Exception {
        httpRequester = mock(HttpRequester.class);

        crmClientRepository = new CrmClientRepository(httpRequester);

        clientDto = new ClientDto();

        when(httpRequester.getDtoFromUrl(contains(VALID_CLIENT_ID.toString()), any())).thenReturn(clientDto);
        doThrow(new FileNotFoundException()).when(httpRequester).getDtoFromUrl(contains(INVALID_CLIENT_ID.toString()), any());
    }

    @Test
    public void givenAValidClientId_whenGettingTheClient_thenReturnsAClient() throws Exception {
        Client client = crmClientRepository.getClient(VALID_CLIENT_ID);

        assertNotNull(client);
    }

    @Test(expected = ClientNotFoundException.class)
    public void givenAnInvalidClientId_WhenGettingTheClient_thenThrowsAnException() throws Exception {
        crmClientRepository.getClient(INVALID_CLIENT_ID);
    }

    @Test
    public void givenAValidClientId_whenGettingTheDefaultDueTerm_thenReturnsTheDefaultDueTerm() throws Exception {
        clientDto.defaultTerm = DueTerm.DAYS90;

        DueTerm dueTerm = crmClientRepository.getDefaultDueTerm(VALID_CLIENT_ID);

        assertSame(dueTerm, clientDto.defaultTerm);
    }

    @Test(expected = ClientNotFoundException.class)
    public void givenAnInvalidClientId_whenGettingTheDefaultDueTerm_thenThrowsAnException() throws Exception {
        crmClientRepository.getClient(INVALID_CLIENT_ID);
    }
}