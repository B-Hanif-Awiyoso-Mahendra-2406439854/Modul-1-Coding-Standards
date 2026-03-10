package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {

    private Order order;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                List.of(product),
                1708560000L,
                "Safira Sudrajat",
                OrderStatus.WAITING_PAYMENT.getValue()
        );
    }

    @Test
    void testCreateVoucherPaymentWithValidCode() {
        Payment payment = new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "ESHOP1234ABC5678")
        );

        assertEquals("payment-1", payment.getId());
        assertEquals(Payment.VOUCHER_CODE, payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals(order, payment.getOrder());
    }

    @Test
    void testCreateVoucherPaymentWithInvalidCode() {
        Payment payment = new Payment(
                "payment-1",
                order,
                Payment.VOUCHER_CODE,
                Map.of("voucherCode", "INVALID")
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentWithCompleteData() {
        Payment payment = new Payment(
                "payment-2",
                order,
                Payment.BANK_TRANSFER,
                Map.of(
                        "bankName", "BCA",
                        "referenceCode", "INV-2406439854"
                )
        );

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("INV-2406439854", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testCreateBankTransferPaymentWithEmptyData() {
        Payment payment = new Payment(
                "payment-2",
                order,
                Payment.BANK_TRANSFER,
                Map.of(
                        "bankName", "",
                        "referenceCode", "INV-2406439854"
                )
        );

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusWithInvalidValue() {
        Payment payment = new Payment(
                "payment-2",
                order,
                Payment.BANK_TRANSFER,
                Map.of(
                        "bankName", "BCA",
                        "referenceCode", "INV-2406439854"
                )
        );

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("PENDING"));
    }
}