package ca.ulaval.glo4002.billing.domain.repositories;

import ca.ulaval.glo4002.billing.domain.Client;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;

public interface ClientRepository {

    Client getClient(ClientId clientId) throws ClientNotFoundException;

    DueTerm getDefaultDueTerm(ClientId clientId) throws ClientNotFoundException;
}
