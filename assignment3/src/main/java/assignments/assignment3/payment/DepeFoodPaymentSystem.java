package assignments.assignment3.payment;

public interface DepeFoodPaymentSystem {
    // Parent class for DebitPayment and CreditCardPayment
    public long saldo = 0;
    public long processPayment(long amount);
}