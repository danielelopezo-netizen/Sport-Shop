package org.example.decorator;

/**
 * Decorador base abstracto (Base Decorator en el patrón Decorator).
 * Mantiene una referencia a un objeto Message y delega la ejecución del método send().
 */
public abstract class MessageDecorator implements Message {

    protected final Message wrappee;

    public MessageDecorator(Message wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void send() {
        wrappee.send();
    }
}
