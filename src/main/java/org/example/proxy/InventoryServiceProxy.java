package org.example.proxy;

/**
 * Proxy de protección para el servicio de inventario (Patrón Proxy).
 * Verifica los permisos de acceso y autenticación antes de permitir la consulta
 * al servicio real, protegiendo información sensible del inventario sin modificar
 * la implementación original.
 */
public class InventoryServiceProxy implements InventoryService {

    private final InventoryService realInventoryService;
    private String userRole;

    public InventoryServiceProxy(InventoryService realInventoryService, String userRole) {
        this.realInventoryService = realInventoryService;
        this.userRole = userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    @Override
    public boolean checkStock(String product) {
        System.out.println("[Proxy de Inventario] Interceptando consulta de inventario para: '" + product + "'");
        System.out.println("[Proxy de Inventario] Verificando credenciales de acceso para el rol actual: [" + userRole + "]...");

        if (!hasAccess(userRole)) {
            System.out.println("[Proxy de Inventario] >> ACCESO DENEGADO << : El rol [" + userRole
                    + "] no posee autorización para consultar el inventario.");
            return false;
        }

        System.out.println("[Proxy de Inventario] >> ACCESO AUTORIZADO <<. Delegando la consulta al servicio real...");
        return realInventoryService.checkStock(product);
    }

    private boolean hasAccess(String role) {
        // Roles permitidos para consultar inventario
        return role != null && (
                role.equalsIgnoreCase("ADMIN") ||
                role.equalsIgnoreCase("STORE_MANAGER") ||
                role.equalsIgnoreCase("AUTHORIZED_PURCHASER")
        );
    }
}
