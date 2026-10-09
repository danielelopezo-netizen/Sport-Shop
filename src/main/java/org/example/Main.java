package org.example;

import org.example.adapter.ExternalPaymentAdapter;
import org.example.adapter.ExternalPaymentService;
import org.example.decorator.BasicMessage;
import org.example.decorator.CompressionDecorator;
import org.example.decorator.EncryptionDecorator;
import org.example.decorator.LoggingDecorator;
import org.example.decorator.Message;
import org.example.facade.OrderFacade;
import org.example.proxy.InventoryService;
import org.example.proxy.InventoryServiceProxy;
import org.example.proxy.RealInventoryService;
import org.example.service.ShippingService;

/**
 * Clase principal que ejecuta y demuestra la simulación de compra en la tienda deportiva.
 *
 * Implementa y coordina los 4 patrones de diseño estructurales solicitados en el Punto 5:
 * 1. ADAPTER:   Adaptación del servicio externo de pagos a la interfaz del sistema.
 * 2. FACADE:    Unificación y simplificación del proceso de compra completo en una sola interfaz.
 * 3. DECORATOR: Composición dinámica de funcionalidades (logging, compresión, cifrado) al mensaje.
 * 4. PROXY:     Control de acceso e interceptación de permisos antes de consultar el inventario real.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("********************************************************************************");
        System.out.println("          SISTEMA DE COMPRAS - TIENDA DEPORTIVA (SPORT SHOP)                    ");
        System.out.println("               DEMOSTRACIÓN DE PATRONES DE DISEÑO ESTRUCTURALES                 ");
        System.out.println("********************************************************************************\n");

        // --------------------------------------------------------------------------------
        // CASO PRINCIPAL: Compra completa utilizando la Fachada (OrderFacade)
        // El cliente (Main) únicamente interactúa con la interfaz unificada de la fachada.
        // --------------------------------------------------------------------------------
        System.out.println(">>> CASO 1: Ejecución de una compra completa a través de OrderFacade <<<\n");

        OrderFacade tienda = new OrderFacade();
        boolean resultadoCompra = tienda.purchase("Zapatillas Running Nike", 149.99);

        System.out.println("Estado final de la compra: " + (resultadoCompra ? "EXITOSA" : "FALLIDA"));
        System.out.println("\n--------------------------------------------------------------------------------\n");

        // --------------------------------------------------------------------------------
        // CASO 2: Demostración del Patrón PROXY (Acceso Denegado por falta de permisos)
        // --------------------------------------------------------------------------------
        System.out.println(">>> CASO 2: Demostración del Patrón PROXY (Control de Acceso) <<<");
        System.out.println("Intentando consultar inventario con un usuario sin permisos suficientes...\n");

        InventoryService servicioReal = new RealInventoryService();
        InventoryService proxySinPermisos = new InventoryServiceProxy(servicioReal, "INVITADO_NO_AUTORIZADO");

        OrderFacade tiendaSinPermisos = new OrderFacade(
                proxySinPermisos,
                new ExternalPaymentAdapter(new ExternalPaymentService()),
                new ShippingService()
        );

        boolean resultadoSinPermiso = tiendaSinPermisos.purchase("Balón de Fútbol Adidas", 45.00);
        System.out.println("Estado final de la compra: " + (resultadoSinPermiso ? "EXITOSA" : "RECHAZADA (Seguridad)"));
        System.out.println("\n--------------------------------------------------------------------------------\n");

        // --------------------------------------------------------------------------------
        // CASO 3: Demostración del Patrón PROXY (Producto sin existencias en inventario real)
        // --------------------------------------------------------------------------------
        System.out.println(">>> CASO 3: Demostración del Patrón PROXY (Producto Agotado) <<<");
        System.out.println("Intentando comprar un producto con stock 0...\n");

        boolean resultadoAgotado = tienda.purchase("Raqueta de Tenis Wilson", 180.00);
        System.out.println("Estado final de la compra: " + (resultadoAgotado ? "EXITOSA" : "RECHAZADA (Sin Stock)"));
        System.out.println("\n--------------------------------------------------------------------------------\n");

        // --------------------------------------------------------------------------------
        // CASO 4: Demostración del Patrón DECORATOR (Combinación flexible de funcionalidades)
        // Se muestra cómo se pueden combinar Logging, Compression y Encryption dinámicamente.
        // --------------------------------------------------------------------------------
        System.out.println(">>> CASO 4: Demostración del Patrón DECORATOR (Combinación de Envolturas) <<<\n");

        System.out.println("--- 4.1 Notificación básica sin decoradores ---");
        Message mensajeSimple = new BasicMessage("Tu factura #10492 está disponible");
        mensajeSimple.send();

        System.out.println("\n--- 4.2 Notificación solo con Logging ---");
        Message mensajeConLog = new LoggingDecorator(
                new BasicMessage("Tu factura #10492 está disponible")
        );
        mensajeConLog.send();

        System.out.println("\n--- 4.3 Notificación combinada (Compresión + Encriptación + Logging) ---");
        Message mensajeCompleto = new CompressionDecorator(
                new EncryptionDecorator(
                        new LoggingDecorator(
                                new BasicMessage("Tu factura #10492 está disponible")
                        )
                )
        );
        mensajeCompleto.send();

        System.out.println("\n********************************************************************************");
        System.out.println("          TODAS LAS PRUEBAS Y CASOS DE USO EJECUTADOS CON ÉXITO                ");
        System.out.println("********************************************************************************");
    }
}
