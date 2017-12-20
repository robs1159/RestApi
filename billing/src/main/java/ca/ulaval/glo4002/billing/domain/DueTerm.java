package ca.ulaval.glo4002.billing.domain;

public enum DueTerm {
    IMMEDIATE(0),
    DAYS30(30),
    DAYS90(90);

    private final Integer dueTermInDays;

    DueTerm(Integer dueTermInDays) {
        this.dueTermInDays = dueTermInDays;
    }

    public Integer inDays() {
        return this.dueTermInDays;
    }
}
