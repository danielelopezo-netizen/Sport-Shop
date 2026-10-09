package org.example.decorator;

/**
 * Implementación base de un mensaje (Concrete Component en el patrón Decorator).
 * Envía el mensaje básico sin funcionalidades adicionales.
 */
public class BasicMessage implements Message {

    private final String content;

    public BasicMessage() {
        this("Notificación de compra realizada con éxito");
    }

    public BasicMessage(String content) {
        this.content = content;
    }

    @Override
    public void send() {
        System.out.println("[BasicMessage] Enviando mensaje: \"" + content + "\"");
    }

    public String getContent() {
        return content;
    }
}
