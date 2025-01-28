package service;

public class PaymentService {
    public double calculateChange(double cashTendered, double finalTotal) {
        return cashTendered - finalTotal;
    }
}