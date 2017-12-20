package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;

@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private String account;

    @Column
    private Source source;

    public PaymentMethod() {
    }

    public PaymentMethod(String account, Source source) {
        this.account = account;
        this.source = source;
    }

    public String getAccount() {
        return this.account;
    }

    public Source getSource() {
        return this.source;
    }
}
