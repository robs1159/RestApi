package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.bill.repositories.CrmRepository;
import ca.ulaval.glo4002.crmInterface.application.CrmApplicationService;
import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;

public class CrmWrapper implements CrmRepository {

    private CrmApplicationService crmApplicationService;

    public CrmWrapper() {
        crmApplicationService = new CrmApplicationService();
    }

    @Override
    public ProductDto findProductById(ProductId productId) throws ProductNotFoundException {
        return crmApplicationService.findProductById(productId);
    }

    @Override
    public DueTerm getDefaultTermForClient(ClientId clientId) throws ClientNotFoundException {
        return crmApplicationService.getDefaultTermForClient(clientId);
    }

    @Override
    public Boolean isClientExist(ClientId clientId) {
        return crmApplicationService.isClientExist(clientId);
    }

}
