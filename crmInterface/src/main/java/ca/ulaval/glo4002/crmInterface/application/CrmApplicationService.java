package ca.ulaval.glo4002.crmInterface.application;

import ca.ulaval.glo4002.crmInterface.application.dto.ClientDto;
import ca.ulaval.glo4002.crmInterface.application.dto.ProductDto;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.crm.CrmClientRepository;
import ca.ulaval.glo4002.crmInterface.application.repositories.crm.CrmProductRepository;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.crmInterface.domain.repositories.ProductRepository;

public class CrmApplicationService {

    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    public CrmApplicationService() {
        clientRepository = new CrmClientRepository();
        productRepository = new CrmProductRepository();
    }

    public CrmApplicationService(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public ProductDto findProductById(ProductId productId) throws ProductNotFoundException {
        return productRepository.getProduct(productId);
    }

    public ClientDto findClientById(ClientId clientId) throws ClientNotFoundException {
        return clientRepository.getClient(clientId);
    }

    public DueTerm getDefaultTermForClient(ClientId clientId) throws ClientNotFoundException {
        return clientRepository.getDefaultDueTerm(clientId);
    }

    public boolean isClientExist(ClientId clientId) {

        try {
            clientRepository.getClient(clientId);
        } catch (ClientNotFoundException e) {
            return false;
        }
        return true;
    }
}
