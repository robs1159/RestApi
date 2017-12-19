package ca.ulaval.glo4002.crmInterface.application.repositories;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;

import java.io.FileNotFoundException;

public class ClientNotFoundException extends FileNotFoundException {

    private final ClientId clientId;

    public ClientNotFoundException(ClientId clientId) {
        this.clientId = clientId;
    }

    public ClientId getClientId() {
        return clientId;
    }
}
