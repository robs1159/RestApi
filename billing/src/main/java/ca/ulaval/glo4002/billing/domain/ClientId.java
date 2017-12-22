package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;

@Entity
public class ClientId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private long clientId;

    public ClientId() {
    }

    public ClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return Long.toString(this.clientId);
    }
}
