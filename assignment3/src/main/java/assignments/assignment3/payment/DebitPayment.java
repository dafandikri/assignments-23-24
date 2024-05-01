package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    // Represents Debit Payment
    public final double MINIMUM_TOTAL_PRICE = 50000.0;

    public long processPayment(long amount){
        if(amount < MINIMUM_TOTAL_PRICE){
            return 0;
        }else{
            return amount;
        }
    };
}
