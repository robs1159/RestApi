package ca.ulaval.glo4002.billing.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.List;

public class LedgerDto {

    @JsonProperty
    public long accountid;

    @JsonProperty
    @Valid
    public List<EntrieDto> entries;
}