package ca.ulaval.glo4002.payment.application;

import ca.ulaval.glo4002.billing.application.BillApplicationServiceFactory;
import ca.ulaval.glo4002.billing.application.BillPaymentOperation;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.payment.application.assembler.PaymentAssembler;
import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.repositories.CrmRepository;
import ca.ulaval.glo4002.payment.domain.payment.repositories.PaymentRepository;

import javax.inject.Inject;

public class PaymentApplicationService {

    private CrmRepository crmRepository;
    private PaymentRepository paymentRepository;

    @Inject
    public PaymentApplicationService(PaymentRepository paymentRepository, CrmRepository crmRepository) {
        this.paymentRepository = paymentRepository;
        this.crmRepository = crmRepository;
    }

    public Payment createPayment(PaymentDto paymentDto) throws ClientNotFoundException {
        Payment payment = PaymentAssembler.createPaymentFromDto(paymentDto);

        this.crmRepository.findClientById(payment.getClientId());
        this.paymentRepository.insert(payment);

        return payment;
    }

    public PaymentToReturnDto toReturnDto(Payment payment) {
        return PaymentAssembler.toDto(payment);
    }

    public void payBills(Payment payment) {
        BillPaymentOperation billPaymentOperation = BillApplicationServiceFactory.getBillApplicationService();
        billPaymentOperation.payOldestBill(payment.getClientId(), payment.getAmount());
    }
}
