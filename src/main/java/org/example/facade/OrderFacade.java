package org.example.facade;

import org.example.adapter.ExternalPaymentAdapter;
import org.example.adapter.ExternalPaymentService;
import org.example.adapter.PaymentProcessor;
import org.example.decorator.BasicMessage;
import org.example.decorator.CompressionDecorator;
import org.example.decorator.LoggingDecorator;
import org.example.decorator.Message;
import org.example.proxy.InventoryService;
import org.example.proxy.InventoryServiceProxy;
import org.example.proxy.RealInventoryService;
import org.example.service.ShippingService;

/**
 * Fachada para el proceso de compra (Patrón Facade).
 * Proporciona una interfaz unificada y simple (OrderFacade) para interactuar con los
 * distintos subsistemas: inventario (Proxy), pasarela de pagos (Adapter),
 * despacho de envíos y notificaciones combinadas (Decorator).
 *
 * El cliente (Main) no necesita conocer los detalles de implementación ni coordinar
 * manualmente cada servicio.
 */
public class OrderFacade {

    private final InventoryService inventoryService;
    private final PaymentProcessor paymentProcessor;
    private final ShippingService shippingService;

    /**
     * Constructor por defecto con la configuración estándar de la tienda deportiva.
     */
    public OrderFacade() {
        this(
                new InventoryServiceProxy(new RealInventoryService(), "AUTHORIZED_PURCHASER"),
                new ExternalPaymentAdapter(new ExternalPaymentService()),
                new ShippingService()
        );
    }

    /**
     * Constructor con inyección de dependencias para máxima flexibilidad o pruebas.
     */
    public OrderFacade(InventoryService inventoryService,
                       PaymentProcessor paymentProcessor,
                       ShippingService shippingService) {
        this.inventoryService = inventoryService;
        this.paymentProcessor = paymentProcessor;
        this.shippingService = shippingService;
    }

    /**
     * Ejecuta una compra completa coordinando de forma transparente todos los subsistemas.
     *
     * @param product Nombre del producto deportivo a comprar.
     * @param amount  Monto total de la compra.
     * @return true si la compra se completó con éxito; false en caso contrario.
     */
    public boolean purchase(String product, double amount) {
        System.out.println("================================================================================");
        System.out.println("               INICIANDO PROCESO DE COMPRA (FACHADA - ORDER FACADE)            ");
        System.out.println("  Producto: " + product + " | Valor: $" + String.format("%.2f", amount));
        System.out.println("================================================================================");

        // 1. Verificación de existencias mediante Proxy (control de acceso + consulta real)
        System.out.println("\n[PASO 1] Consultando disponibilidad de inventario (Patrón Proxy)...");
        boolean stockAvailable = inventoryService.checkStock(product);

        if (!stockAvailable) {
            System.out.println("\n[RESULTADO] Compra cancelada: No se pudo validar existencias o no hay stock.");
            System.out.println("================================================================================\n");
            return false;
        }

        // 2. Procesamiento de pago mediante Adapter (adaptando servicio externo incompatible)
        System.out.println("\n[PASO 2] Procesando transacción financiera (Patrón Adapter)...");
        paymentProcessor.processPayment(amount);

        // 3. Coordinación de despacho y logística
        System.out.println("\n[PASO 3] Coordinando logística de envío...");
        shippingService.createShipment(product);

        // 4. Envío de notificación combinando Logging y Compresión mediante Decorator
        System.out.println("\n[PASO 4] Enviando confirmación al cliente (Patrón Decorator)...");
        String notificationContent = "¡Compra confirmada! Tu orden de '" + product + "' por $"
                + String.format("%.2f", amount) + " está en camino.";

        // Componemos dinámicamente: BasicMessage -> LoggingDecorator -> CompressionDecorator
        Message notification = new CompressionDecorator(
                new LoggingDecorator(
                        new BasicMessage(notificationContent)
                )
        );
        notification.send();

        System.out.println("\n================================================================================");
        System.out.println("               ¡COMPRA FINALIZADA Y PROCESADA CON ÉXITO!                       ");
        System.out.println("================================================================================\n");
        return true;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public PaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }

    public ShippingService getShippingService() {
        return shippingService;
    }
}
