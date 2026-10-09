package org.example.decorator;

/**
 * Decorador concreto para compresión de datos (Patrón Decorator).
 * Agrega comportamiento de compresión de carga útil (payload) antes y después de enviar el mensaje original.
 */
public class CompressionDecorator extends MessageDecorator {

    public CompressionDecorator(Message wrappee) {
        super(wrappee);
    }

    @Override
    public void send() {
        System.out.println("[CompressionDecorator] [COMPRESIÓN] Comprimiendo payload del mensaje con algoritmo GZIP/Deflate...");
        super.send();
        System.out.println("[CompressionDecorator] [COMPRESIÓN] Paquete comprimido enviado con reducción de tamaño (-65%).");
    }
}
