package ca.ulaval.glo4002.crmInterface.application.repositories.crm;

import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.HttpRequester;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ClientRepository;

import java.io.FileNotFoundException;

public class CrmClientRepository implements ClientRepository {

    private static final String CLIENTS_URL = "http://localhost:8080/clients/";

    private HttpRequester httpRequester;

    public CrmClientRepository() {
        this.httpRequester = new HttpRequester();
    }

    public CrmClientRepository(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }

    public ClientDto getClient(ClientId clientId) throws ClientNotFoundException {
        try {
            return httpRequester.getDtoFromUrl(CLIENTS_URL + clientId.getClientId(), ClientDto.class);
        } catch (FileNotFoundException e) {
            throw new ClientNotFoundException(clientId);
        }
    }

    @Override
    public DueTerm getDefaultDueTerm(ClientId clientId) throws ClientNotFoundException {
        ClientDto clientDto = this.getClient(clientId);
        return clientDto.defaultTerm;
    }
}
