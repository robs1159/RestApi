package ca.ulaval.glo4002.billing.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Embedded
    private ClientId clientId;

    @Column
    private DueTerm defaultTerm;

    public Client(ClientId clientId, DueTerm defaultTerm) {
        this.clientId = clientId;
        this.defaultTerm = defaultTerm;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public DueTerm getDefaultTerm() {
        return defaultTerm;
    }
}
