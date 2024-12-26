package com.carvia.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.github.cdimascio.dotenv.Dotenv;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class PaymentController {

    @FXML
    private Button payButton;

    // Cargar la clave de Stripe desde el archivo .env
    static {
        Dotenv dotenv = Dotenv.load();
        Stripe.apiKey = dotenv.get("STRIPE_KEY");
    }

    @FXML
    public void initialize() {
        payButton.setOnAction(event -> {
            try {
                handlePayment(event);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handlePayment(ActionEvent event) throws StripeException {
        PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                .setAmount(2000L) // Monto en centavos (2000 centavos = 20.00 USD)
                .setCurrency("usd")
                .addPaymentMethodType("card") // Añade este método de pago
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Aquí puedes manejar la respuesta del paymentIntent
        System.out.println("Payment Intent creado: " + paymentIntent.getId());
    }
}
