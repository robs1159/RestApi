package ca.ulaval.glo4002.billing.domain;

public enum TransactionType {
    INVOICE,
    PAYMENT,
    INVOICE_CANCELLED,
    INVOICE_REJECTED,
    REBATE;
}
