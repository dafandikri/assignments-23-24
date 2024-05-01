package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    // Represents Credit Card Payment with 2% transaction fee
    public final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    public long countTransactionFee(long amount){
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    };

    public long processPayment(long amount){
        return amount + countTransactionFee(amount);
    };
}
