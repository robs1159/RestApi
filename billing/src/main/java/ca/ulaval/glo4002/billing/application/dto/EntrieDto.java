package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.OperationType;
import ca.ulaval.glo4002.billing.domain.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class EntrieDto {

    @JsonProperty
    public Instant date;

    @JsonProperty
    public TransactionType typeTransaction;

    @JsonProperty
    public long clientId;

    @JsonProperty
    public OperationType typeOperation;

    @JsonProperty
    public float amount;

    @JsonProperty
    public float balance;
}