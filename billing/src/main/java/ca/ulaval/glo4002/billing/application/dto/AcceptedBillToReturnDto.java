package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AcceptedBillToReturnDto {

    @JsonProperty
    public Long id;

    @JsonProperty
    public Instant effectiveDate;

    @JsonProperty
    public Instant expectedPayment;

    @JsonProperty
    public DueTerm dueTerm;

    @JsonProperty
    public String url;
}