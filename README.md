# Tienda Deportiva (Sport Shop) - Patrones de Diseño Estructurales

Proyecto Java desarrollado para simular un proceso de compra en una tienda deportiva aplicando los **Patrones de Diseño Estructurales** según los requisitos, condiciones y especificaciones del examen/quiz.

---

## 🎯 Cumplimiento de Requisitos (Punto 5)

| Requisito | Patrón de Diseño | Descripción de la Solución |
| :--- | :---: | :--- |
| **1. Pagos** | **Adapter** | La tienda utiliza un servicio externo (`ExternalPaymentService`) con el método incompatible `makeTransaction(value)`. Mediante `ExternalPaymentAdapter` se adapta a la interfaz interna `PaymentProcessor` (`processPayment(amount)`) sin modificar la librería externa. |
| **2. Compra** | **Facade** | Todo el flujo de compra se ejecuta mediante una única interfaz simplificada (`OrderFacade.purchase(product, amount)`). El cliente (`Main`) no necesita conocer ni coordinar directamente el inventario, pago, despacho ni notificaciones. |
| **3. Notificaciones** | **Decorator** | Se permite agregar y combinar dinámicamente funcionalidades como `LoggingDecorator`, `CompressionDecorator` y `EncryptionDecorator` sobre la notificación original (`BasicMessage`), sin alterar el componente base. |
| **4. Inventario** | **Proxy (Protection)** | Antes de consultar el stock real en `RealInventoryService`, la clase `InventoryServiceProxy` intercepta la llamada y valida los permisos de acceso del usuario/rol, protegiendo el servicio real sin modificar su implementación. |

---

## 📁 Estructura del Proyecto

El código fuente está organizado en paquetes por responsabilidad dentro de `src/main/java/org/example/`:

```
org.example/
├── adapter/
│   ├── PaymentProcessor.java        # Interfaz esperada por el sistema
│   ├── ExternalPaymentService.java  # Biblioteca externa no modificable
│   └── ExternalPaymentAdapter.java  # Adaptador de integración
├── proxy/
│   ├── InventoryService.java        # Interfaz común de inventario
│   ├── RealInventoryService.java    # Servicio real con la lógica y base de datos
│   └── InventoryServiceProxy.java   # Proxy de control y validación de acceso
├── decorator/
│   ├── Message.java                 # Interfaz base para el envío de mensajes
│   ├── BasicMessage.java            # Implementación base del mensaje
│   ├── MessageDecorator.java        # Decorador base abstracto
│   ├── LoggingDecorator.java        # Decorador concreto: registro y auditoría
│   ├── CompressionDecorator.java    # Decorador concreto: compresión de datos
│   └── EncryptionDecorator.java     # Decorador concreto: cifrado de seguridad
├── service/
│   └── ShippingService.java         # Servicio de gestión de envíos y guías
├── facade/
│   └── OrderFacade.java             # Fachada principal que coordina todo el proceso
└── Main.java                        # Punto de entrada que ejecuta los casos de prueba
```

---

## 🚀 Cómo Ejecutar el Proyecto

### Opción 1: Con Maven Wrapper (Recomendado desde Terminal)
En la raíz del proyecto, ejecutar:

```bash
# En Windows (PowerShell o CMD)
.\mvnw.cmd clean compile exec:java

# En Linux / macOS
./mvn clean compile exec:java
```

### Opción 2: Desde cualquier IDE (IntelliJ IDEA, Eclipse, VS Code)
1. Abrir la carpeta del proyecto en su IDE favorito.
2. Navegar hasta `src/main/java/org/example/Main.java`.
3. Ejecutar el método `main` haciendo clic derecho -> **Run 'Main.main()'**.

---

## 🧪 Casos de Prueba Demostrados en `Main.java`

Al ejecutar el programa, se despliegan automáticamente los siguientes escenarios:

1. **Caso 1 - Compra Exitosa (Flujo Completo con Facade):**
   - El Proxy valida y autoriza el acceso al inventario.
   - El servicio real confirma que hay unidades disponibles.
   - El Adapter procesa el pago de forma transparente con el servicio externo.
   - El servicio de envío genera la guía de transporte.
   - El Decorator aplica compresión y logging al mensaje de confirmación.

2. **Caso 2 - Control de Acceso (Proxy Protection):**
   - Se simula una consulta de un usuario no autorizado (`INVITADO_NO_AUTORIZADO`).
   - El Proxy bloquea el acceso antes de tocar el servicio real y cancela la transacción.

3. **Caso 3 - Validación de Stock (Proxy + Servicio Real):**
   - Se solicita un producto agotado (stock = 0).
   - El servicio cancela la compra por falta de existencias.

4. **Caso 4 - Combinación Flexible de Decoradores (Decorator):**
   - Mensaje base sin decoradores.
   - Mensaje con Logging.
   - Mensaje combinando Compresión + Encriptación + Logging en cadena.

---

## 📝 Respuestas Justificadas a las Preguntas Teóricas del Quiz

### Pregunta 1 (Identificación)
- **a. ¿Qué patrón de diseño utilizaría?**
  **Patrón Decorator (Decorador)**.
- **b. ¿Por qué este patrón es más apropiado que crear una clase diferente para cada combinación?**
  Porque evita la **explosión de subclases** (combinatoria exponencial de herencia múltiple). Permite agregar responsabilidades a los objetos de forma dinámica en tiempo de ejecución mediante composición, cumpliendo con el principio de responsabilidad única (SRP) y el principio Abierto/Cerrado (OCP).

### Pregunta 2 (Identificación)
- **a. ¿Qué patrón de diseño utilizaría?**
  **Patrón Facade (Fachada)**.
- **b. Explique en 2 o 3 líneas por qué considera que este patrón es apropiado:**
  Proporciona una interfaz unificada, simple y de alto nivel (`OrderController` / `OrderFacade`) que desacopla al cliente de los múltiples subsistemas complejos (inventario, pago, despacho, notificaciones), ocultando los detalles de implementación interna.

### Pregunta 3 (Identificación)
- **a. ¿Qué patrón de diseño estructural utilizaría?**
  **Patrón Proxy (Específicamente Protection Proxy / Proxy de Protección)**.
- **b. Explique brevemente por qué es adecuado para esta situación:**
  Actúa como un intermediario que implementa la misma interfaz que el servicio real. Esto permite interceptar cada consulta para validar los permisos o credenciales del usuario antes de delegar la llamada, protegiendo el servicio original sin necesidad de modificar su código fuente.

### Pregunta 4 (Identificación)
- **a. ¿Qué patrón de diseño utilizaría?**
  **Patrón Adapter (Adaptador)**.
- **b. Explique cuál es el problema que debe resolver el patrón:**
  Resuelve la **incompatibilidad entre interfaces**. El sistema espera interactuar con `PaymentProcessor` (`processPayment(amount)`), mientras que la biblioteca externa de terceros expone `ExternalPaymentService` (`makeTransaction(value)`), la cual no puede ser modificada. El adaptador traduce las peticiones entre ambas interfaces.

---

## 📤 Instrucciones para Subir a GitHub

Para cumplir con la entrega del enlace del repositorio:

1. Crear un repositorio vacío en su cuenta de GitHub (ejemplo: `Sport-Shop`).
2. Vincular el repositorio remoto y subir los cambios ejecutando en la terminal:
   ```bash
   git add .
   git commit -m "Implementación de patrones estructurales: Adapter, Facade, Decorator y Proxy"
   git branch -M main
   git remote add origin https://github.com/TU_USUARIO/Sport-Shop.git
   git push -u origin main
   ```
3. Copiar el enlace de su repositorio generado (ej. `https://github.com/TU_USUARIO/Sport-Shop`) y entregarlo.
