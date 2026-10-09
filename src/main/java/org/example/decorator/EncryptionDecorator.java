package org.example.decorator;

/**
 * Decorador concreto para cifrado de datos (Patrón Decorator).
 * Agrega cifrado de seguridad al contenido del mensaje antes de enviarlo.
 */
public class EncryptionDecorator extends MessageDecorator {

    public EncryptionDecorator(Message wrappee) {
        super(wrappee);
    }

    @Override
    public void send() {
        System.out.println("[EncryptionDecorator] [ENCRIPTACIÓN] Cifrando contenido del mensaje mediante AES-256...");
        super.send();
        System.out.println("[EncryptionDecorator] [ENCRIPTACIÓN] Mensaje transmitido bajo canal cifrado seguro.");
    }
}
