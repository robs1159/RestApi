package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.PaymentAssembler;
import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.PaymentRepository;

import javax.inject.Inject;

public class PaymentApplicationService {

    private PaymentRepository paymentRepository;
    private PaymentAssembler paymentAssembler;
    private ClientRepository clientRepository;

    @Inject
    public PaymentApplicationService(PaymentRepository paymentRepository, ClientRepository clientRepository, PaymentAssembler paymentAssembler) {
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
        this.paymentAssembler = paymentAssembler;
    }

    public PaymentToReturnDto createPayment(PaymentDto paymentDto) throws ClientNotFoundException {
        Payment payment = paymentAssembler.createPaymentFromDto(paymentDto);

        payBills(payment);
        PaymentToReturnDto paymentToReturnDto = paymentAssembler.toDto(payment);

        this.clientRepository.getClient(payment.getClientId());
        this.paymentRepository.insert(payment);

        return paymentToReturnDto;
    }

    public void payBills(Payment payment) {
        BillApplicationService billApplicationService = (new BillApplicationServiceFactory()).getBillApplicationService();
        billApplicationService.payOldestBill(payment.getClientId(), payment.getAmount());
    }
}
