package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    @JsonProperty
    public DueTerm defaultTerm;

    @JsonProperty
    public ClientId clientId;
}
