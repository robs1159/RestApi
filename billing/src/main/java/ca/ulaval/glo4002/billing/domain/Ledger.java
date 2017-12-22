package ca.ulaval.glo4002.billing.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private long accountid;

    @Column
    private List<Entrie> entries;

    public Ledger(long accountid, List<Entrie> entries) {
        this.accountid = 0;
        this.entries = new ArrayList<>();
        addEntries(entries);
    }

    public List<Entrie> addEntries(List<Entrie> entries) {
        for (Entrie entrie : entries) {
            this.entries.add(entrie);
        }
        return entries;
    }

    public void setBalanceOnEntries() {
        entries.sort(Comparator.comparing(Entrie::getDate));
        float balance = 0;
        for (Entrie entrie : entries) {
            if (entrie.getTypeTransaction() == TransactionType.INVOICE) {
                balance += entrie.getAmount();
            } else if (entrie.getTypeTransaction() == TransactionType.PAYMENT) {
                balance -= entrie.getAmount();
            }
            entrie.setBalance(balance);
        }
    }

    public List<Entrie> getEntries() {
        return entries;
    }

    public long getAccountid() {
        return accountid;
    }
}