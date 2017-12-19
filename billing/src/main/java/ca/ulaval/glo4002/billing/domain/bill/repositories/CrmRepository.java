package ca.ulaval.glo4002.billing.domain.bill.repositories;

import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;

public interface CrmRepository {

    ProductDto findProductById(ProductId ProductId) throws ProductNotFoundException;

    DueTerm getDefaultTermForClient(ClientId clientId) throws ClientNotFoundException;

    Boolean isClientExist(ClientId clientId);
}
