package controller;

import service.PaymentService;
import view.PaymentView;

public class PaymentController {
    private PaymentService paymentService;
    private PaymentView paymentView;

    public PaymentController() {
        paymentService = new PaymentService();
        paymentView = new PaymentView();
    }

    public void processPayment(double cashTendered, double finalTotal) {
        double changeAmount = paymentService.calculateChange(cashTendered, finalTotal);
        paymentView.displayPaymentDetails(cashTendered, changeAmount);
    }
}