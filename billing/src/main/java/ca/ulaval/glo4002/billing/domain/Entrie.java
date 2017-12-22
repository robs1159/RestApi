package ca.ulaval.glo4002.billing.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

public class Entrie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private Instant date;

    @Column
    private TransactionType typeTransaction;

    @Column
    private ClientId clientId;

    @Column
    private OperationType typeOperation;

    @Column
    private float amount;

    @Column
    private float balance;

    public Entrie(Instant date, TransactionType typeTransaction, ClientId clientId, OperationType typeOperation, float amount) {
        this.date = date;
        this.typeTransaction = typeTransaction;
        this.clientId = clientId;
        this.typeOperation = typeOperation;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Instant getDate() {
        return date;
    }

    public TransactionType getTypeTransaction() {
        return typeTransaction;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public OperationType getTypeOperation() {
        return typeOperation;
    }

    public float getBalance() {
        return balance;
    }
}