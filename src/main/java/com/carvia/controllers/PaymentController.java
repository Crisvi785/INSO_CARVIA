package com.carvia.controllers;

import com.carvia.models.vo.VehicleVo;
import com.carvia.models.vto.VehicleAdVto;
import com.stripe.Stripe;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.cdimascio.dotenv.Dotenv;

import java.awt.Desktop;
import java.net.URI;


public class PaymentController {

    public void realizarCompra(VehicleAdVto vehicle) {
        

        Dotenv dotenv = Dotenv.load();
        Stripe.apiKey = dotenv.get("STRIPE_KEY");

        try {
            // Calcular el precio basado en el vehículo
            long amount = calculatePriceBasedOnVehicle(vehicle);

                
            
                    // Crear una sesión de pago
                    SessionCreateParams params = SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT) // Modo de pago
                            .setSuccessUrl("https://tu-dominio.com/success?session_id={CHECKOUT_SESSION_ID}") // URL de éxito
                            .setCancelUrl("https://tu-dominio.com/cancel") // URL de cancelación
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("eur")
                                                            .setUnitAmount(amount) // Precio en centavos
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName(vehicle.getMarca() + " " + vehicle.getModelo()) // Nombre del producto
                                                                            .build()
                                                            )
                                                            .build()
                                            )
                                            .build()
                            )
                            .build();
            
                    // Crear la sesión en Stripe
                    Session session = Session.create(params);
            
                    // Obtener la URL para redirigir al usuario
                    String paymentUrl = session.getUrl();
                    System.out.println("Redirigiendo a URL de pago: " + paymentUrl);
            
                    // Abrir la URL en el navegador predeterminado
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(paymentUrl));
                    } else {
                        System.out.println("Navegador no soportado. Abre la siguiente URL manualmente: " + paymentUrl);
                    }
            
                } catch (StripeException e) {
                    System.err.println("Error al crear la sesión de pago: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error al abrir la URL de pago: " + e.getMessage());
                }
            }
            
    

     
    private long calculatePriceBasedOnVehicle(VehicleAdVto vehicle) {
        // Lógica para calcular el precio en base al vehículo
        return Math.round(vehicle.getPrecio() * 100.00);
    }


}
