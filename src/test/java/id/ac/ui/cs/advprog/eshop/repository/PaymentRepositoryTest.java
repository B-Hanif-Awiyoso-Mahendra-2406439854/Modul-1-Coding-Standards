package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                List.of(product),
                1708560000L,
                "Safira Sudrajat"
        );

        payments = new ArrayList<>();
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
                Map.of("bankName", "BCA", "referenceCode", "INV-2406439854")
        ));
    }

    @Test
    void testSaveCreatePayment() {
        Payment payment = payments.get(0);

        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdatePayment() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment updatedPayment = new Payment(
                payment.getId(),
                payment.getOrder(),
                Payment.BANK_TRANSFER,
                Map.of("bankName", "BNI", "referenceCode", "REF-0001")
        );

        Payment result = paymentRepository.save(updatedPayment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(Payment.BANK_TRANSFER, paymentRepository.findById(payment.getId()).getMethod());
    }

    @Test
    void testFindByIdIfNotFound() {
        assertNull(paymentRepository.findById("missing-id"));
    }

    @Test
    void testFindAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> results = paymentRepository.findAll();

        assertEquals(2, results.size());
    }
}