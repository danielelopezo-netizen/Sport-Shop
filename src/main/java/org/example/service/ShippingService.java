package org.example.service;

/**
 * Servicio de envíos y logística de la tienda deportiva.
 */
public class ShippingService {

    public void createShipment(String product) {
        String trackingId = "ENV-SPORT-" + (System.currentTimeMillis() % 1000000);
        System.out.println("[ShippingService] Despacho preparado para: '" + product + "'.");
        System.out.println("[ShippingService] Número de guía de transporte asignado: " + trackingId);
    }
}
