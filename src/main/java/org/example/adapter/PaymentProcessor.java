package org.example.adapter;

/**
 * Interfaz interna que espera el sistema de la tienda deportiva para procesar pagos.
 */
public interface PaymentProcessor {
    void processPayment(double amount);
}
