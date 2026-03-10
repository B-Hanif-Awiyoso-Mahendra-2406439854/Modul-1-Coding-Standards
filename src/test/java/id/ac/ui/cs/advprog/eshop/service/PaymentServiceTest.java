package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    private PaymentServiceImpl paymentService;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository);

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                List.of(product),
                1708560000L,
                "Safira Sudrajat"
        );
    }

    @Test
    void testAddVoucherPayment() {
        doReturn(new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "ESHOP1234ABC5678")
        )).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "ESHOP1234ABC5678")
        );

        assertEquals(Payment.VOUCHER_CODE, result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddBankTransferPaymentWithInvalidData() {
        doReturn(new Payment(
                "payment-1",
                order,
                Payment.BANK_TRANSFER,
                Map.of("bankName", "", "referenceCode", "REF-1")
        )).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(
                order,
                Payment.BANK_TRANSFER,
                Map.of("bankName", "", "referenceCode", "REF-1")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessUpdatesOrderStatus() {
        Payment payment = new Payment(
                "payment-1",
                order,
                Payment.BANK_TRANSFER,
                Map.of("bankName", "BCA", "referenceCode", "REF-1")
        );
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertSame(payment, result);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejectedUpdatesOrderStatus() {
        Payment payment = new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "INVALID")
        );
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertSame(payment, result);
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentById() {
        Payment payment = new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "ESHOP1234ABC5678")
        );
        doReturn(payment).when(paymentRepository).findById("payment-1");

        Payment result = paymentService.getPayment("payment-1");

        assertEquals("payment-1", result.getId());
        verify(paymentRepository, times(1)).findById("payment-1");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "ESHOP1234ABC5678")
        ));
        payments.add(new Payment(
                "payment-2",
                order,
                Payment.BANK_TRANSFER,
                Map.of("bankName", "BCA", "referenceCode", "REF-1")
        ));
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();

        assertEquals(2, results.size());
        verify(paymentRepository, times(1)).findAll();
    }
}