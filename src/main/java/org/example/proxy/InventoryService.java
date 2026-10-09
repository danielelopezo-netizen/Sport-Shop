package org.example.proxy;

/**
 * Interfaz común para el servicio de inventario (utilizada tanto por el servicio real como por el Proxy).
 */
public interface InventoryService {
    boolean checkStock(String product);
}
