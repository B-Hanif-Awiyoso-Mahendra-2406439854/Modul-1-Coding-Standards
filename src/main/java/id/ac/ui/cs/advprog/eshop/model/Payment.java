package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Payment {
    public static final String VOUCHER_CODE = "Voucher Code";
    public static final String BANK_TRANSFER = "Bank Transfer";

    private final String id;
    private final Order order;
    private final String method;
    private final Map<String, String> paymentData;
    private String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData == null ? new HashMap<>() : new HashMap<>(paymentData);
        this.status = determineInitialStatus();
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    private String determineInitialStatus() {
        if (VOUCHER_CODE.equals(method)) {
            return isValidVoucherCode(paymentData.get("voucherCode"))
                    ? PaymentStatus.SUCCESS.getValue()
                    : PaymentStatus.REJECTED.getValue();
        }

        if (BANK_TRANSFER.equals(method)) {
            return hasValue(paymentData.get("bankName")) && hasValue(paymentData.get("referenceCode"))
                    ? PaymentStatus.SUCCESS.getValue()
                    : PaymentStatus.REJECTED.getValue();
        }

        throw new IllegalArgumentException();
    }

    private boolean hasValue(String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        long digitCount = voucherCode.chars()
                .filter(Character::isDigit)
                .count();
        return digitCount == 8;
    }
}