package org.example.adapter;

/**
 * Servicio de pago provisto por una biblioteca externa de terceros.
 * ATENCIÓN: Esta clase simula una librería externa y no puede ser modificada directamente.
 * Cuenta con el método makeTransaction(double value) en lugar de processPayment(double amount).
 */
public class ExternalPaymentService {

    public void makeTransaction(double value) {
        System.out.println("[Servicio Externo de Pagos] Transacción bancaria procesada exitosamente por un valor de: $"
                + String.format("%.2f", value));
    }
}
