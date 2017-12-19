package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;

import java.math.BigDecimal;

public interface BillPaymentOperation {
    void payOldestBill(ClientId clientId, BigDecimal amount);
}


