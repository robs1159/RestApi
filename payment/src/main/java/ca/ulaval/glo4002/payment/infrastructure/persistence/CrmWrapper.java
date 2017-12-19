package ca.ulaval.glo4002.payment.infrastructure.persistence;

import ca.ulaval.glo4002.crmInterface.application.CrmApplicationService;
import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.payment.domain.payment.repositories.CrmRepository;

public class CrmWrapper implements CrmRepository {

    private CrmApplicationService crmApplicationService;

    public CrmWrapper() {
        crmApplicationService = new CrmApplicationService();
    }

    @Override
    public ClientDto findClientById(ClientId clientId) throws ClientNotFoundException {
        return crmApplicationService.findClientById(clientId);
    }
}
