package org.example.decorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Decorador concreto para registro de logs y auditoría (Patrón Decorator).
 * Agrega comportamiento de bitácora antes y después de enviar el mensaje original.
 */
public class LoggingDecorator extends MessageDecorator {

    public LoggingDecorator(Message wrappee) {
        super(wrappee);
    }

    @Override
    public void send() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[LoggingDecorator] [LOG " + timestamp + "] Registrando intento de envío de notificación...");
        super.send();
        System.out.println("[LoggingDecorator] [LOG " + timestamp + "] Notificación registrada exitosamente en bitácora de auditoría.");
    }
}
