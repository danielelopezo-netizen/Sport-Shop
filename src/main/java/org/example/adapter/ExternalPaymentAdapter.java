package org.example.adapter;

/**
 * Adaptador de pagos (Patrón Adapter).
 * Permite que el sistema utilice ExternalPaymentService a través de la interfaz interna PaymentProcessor,
 * traduciendo la llamada 'processPayment(amount)' a 'makeTransaction(value)'.
 */
public class ExternalPaymentAdapter implements PaymentProcessor {

    private final ExternalPaymentService externalPaymentService;

    public ExternalPaymentAdapter(ExternalPaymentService externalPaymentService) {
        this.externalPaymentService = externalPaymentService;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("[Adapter] Adaptando llamada del sistema interno 'processPayment(" + amount
                + ")' hacia el servicio externo 'makeTransaction'...");
        this.externalPaymentService.makeTransaction(amount);
    }
}
