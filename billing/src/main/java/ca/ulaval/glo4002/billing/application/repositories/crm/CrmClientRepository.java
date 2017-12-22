package ca.ulaval.glo4002.billing.application.repositories.crm;

import ca.ulaval.glo4002.billing.application.dto.ClientDto;
import ca.ulaval.glo4002.billing.application.repositories.HttpRequester;
import ca.ulaval.glo4002.billing.domain.Client;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;

import java.io.FileNotFoundException;

public class CrmClientRepository implements ClientRepository {

    private HttpRequester httpRequester;

    public CrmClientRepository() {
        this.httpRequester = new HttpRequester();
    }

    public CrmClientRepository(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }

    public Client getClient(ClientId clientId) throws ClientNotFoundException {
        try {
            ClientDto clientDto = httpRequester.getDtoFromUrl(CLIENTS_URL + clientId.getClientId(), ClientDto.class);
            return new Client(clientId, clientDto.defaultTerm);
        } catch (FileNotFoundException e) {
            throw new ClientNotFoundException(clientId);
        }
    }

    public DueTerm getDefaultDueTerm(ClientId clientId) throws ClientNotFoundException {
        return getClient(clientId).getDefaultTerm();
    }
}
