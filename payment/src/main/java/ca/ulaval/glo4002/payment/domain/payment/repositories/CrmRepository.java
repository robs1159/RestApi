package ca.ulaval.glo4002.payment.domain.payment.repositories;

import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;

public interface CrmRepository {

    ClientDto findClientById(ClientId clientId) throws ClientNotFoundException;

}
