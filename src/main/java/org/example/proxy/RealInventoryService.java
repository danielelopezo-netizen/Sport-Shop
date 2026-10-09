package org.example.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio real de inventario (Real Subject en el patrón Proxy).
 * Consulta directamente el stock físico de la tienda.
 * NOTA: Esta clase no contiene lógica de verificación ni autenticación de accesos.
 */
public class RealInventoryService implements InventoryService {

    private final Map<String, Integer> stockDatabase;

    public RealInventoryService() {
        this.stockDatabase = new HashMap<>();
        // Stock inicial de productos de la tienda deportiva
        stockDatabase.put("Zapatillas Running Nike", 12);
        stockDatabase.put("Balón de Fútbol Adidas", 20);
        stockDatabase.put("Camiseta Selección Colombia", 8);
        stockDatabase.put("Raqueta de Tenis Wilson", 0); // Sin existencias para simulación
    }

    @Override
    public boolean checkStock(String product) {
        System.out.println("[Real Inventory Service] Conectando a la base de datos de existencias...");
        int count = stockDatabase.getOrDefault(product, 0);

        if (count > 0) {
            System.out.println("[Real Inventory Service] ¡Stock disponible para '" + product + "'! Existencias: " + count + " unidad(es).");
            return true;
        } else {
            System.out.println("[Real Inventory Service] Producto '" + product + "' AGOTADO en inventario.");
            return false;
        }
    }
}
