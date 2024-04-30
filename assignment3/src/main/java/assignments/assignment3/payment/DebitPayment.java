package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    //TODO implementasikan class di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
    public final double MINIMUM_TOTAL_PRICE = 50000.0;

    public long processPayment(long amount){
        if(amount < MINIMUM_TOTAL_PRICE){
            return 0;
        }else{
            return amount;
        }
    };
}
