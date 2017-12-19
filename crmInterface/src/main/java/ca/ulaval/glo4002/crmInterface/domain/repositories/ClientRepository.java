package ca.ulaval.glo4002.crmInterface.domain.repositories;

import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;

public interface ClientRepository {

    ClientDto getClient(ClientId clientId) throws ClientNotFoundException;

    DueTerm getDefaultDueTerm(ClientId clientId) throws ClientNotFoundException;
}
