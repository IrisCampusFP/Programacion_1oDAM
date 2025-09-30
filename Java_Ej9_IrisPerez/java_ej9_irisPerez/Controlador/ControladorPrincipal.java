package Controlador;
import Vista.Menus;	
public class ControladorPrincipal {
	Menus m = new Menus();
    ControladorClientes cli = new ControladorClientes();
    ControladorProveedores pro = new ControladorProveedores();
    ControladorArticulos art = new ControladorArticulos();
    ControladorFacturas fact = new ControladorFacturas();
    ControladorVentas ven = new ControladorVentas();
    
    public void menuPrincipal() {
    	int opcion = 0;
        
        // Mientras no se seleccione la opción salir
        while (opcion != 7) {
            // Muestro el menú principal y recibo la opción seleccionada
            opcion = m.menuPrincipal();
            
            // Continúo el programa en el controlador correspondiente a la opción seleccionada
            switch (opcion) {
                case 1: // Gestión de Clientes
                    cli.submenu();
                    break;
                case 2: // Gestión de Proveedores
                    pro.submenu();
                    break;
                case 3: // Gestión de Artículos
                	art.submenu();
                    break;
                case 4: // Gestión de Facturas Recibidas
                	fact.submenu();
                    break;
                case 5: // Gestión de Ventas
                	ven.submenu();
                    break;
                case 6: // Informes de Ventas por Cliente
                	ven.generarInforme();
                    break;
                case 7:
                    System.out.println("¡Hasta pronto!");
                    break;
                default: 
                    // Si el usuario introduce un número no válido (que no corresponde a ninguna opción), le saltará un mensaje de error.
                    System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
            }
        }
    }
    
}
