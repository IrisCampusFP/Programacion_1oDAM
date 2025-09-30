package Controlador;
import Vista.Menus;
import Vista.VistaFactura;
import java.util.ArrayList;
import java.util.HashMap;
import Modelo.Factura;
import Modelo.Proveedor;

public class ControladorFacturas {
    Menus m = new Menus();
    VistaFactura vista = new VistaFactura();
    Factura fact = new Factura(0, null, 0, null);

    public void submenu() {
    	int opcion = 0;
    	while (opcion != 5) {
    		// Muestro el submenú y recibo la opción seleccionada
        	opcion = m.submenu("FACTURAS");
        	
        	switch (opcion) {
    		case 1:
    			agregarFactura();
    			break;
    		case 2:
    			listarFacturas();
    			break;
    		case 3:
    			modificarFacturas();
    			break;
    		case 4:
    			eliminarFacturas();		
    			break;
    		case 5:
    			System.out.println("Volviendo al menú principal...\n");		
    			break;
    		default:
    			// Si el usuario introduce un número no válido (que no corresponde a ninguna opción), le saltará un mensaje de error.
                System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
        	}
    	}
    }
    
    public void agregarFactura() {
    	// Solicito los datos de la nueva factura al usuario
    	HashMap<String, Object> datosFactura = vista.solicitarDatosFactura();
    	
		// Compruebo que exista un proveedor con el id introducido en la base de datos
    	int id_proveedor = (int) datosFactura.get("id_proveedor");
		if (!Proveedor.existeProveedor(id_proveedor)) {
			System.out.println("ERROR: No existe ningún proveedor registrado con ese id.");
			return;
		}
    	
    	// Agrego la factura a la base de datos
    	fact.agregarFactura(datosFactura);
    }
    
    public void listarFacturas() {
    	// Obtengo la lista de facturas de la base de datos
    	ArrayList<Factura> listaFacturas = fact.obtenerFacturas();
    	
    	// Muestro la lista obtenida al usuario
    	vista.mostrarFacturas(listaFacturas);
    }
    
    public void modificarFacturas() {
    	// Solicito al usuario el id de la factura que quiera modificar
    	int id_factura = vista.preguntarFacturaAModificar();
    	
    	// Compruebo si existe una factura con ese id antes de intentar modificarlo
    	if (Factura.existeFactura(id_factura)) {
    		
    		// Muestro los datos de la factura a modificar
    		Factura factura = fact.obtenerDatos(id_factura); // Obtengo los datos de la base de datos
    		vista.mostrarDatos(factura); // Muestro los datos
    		
    		// Solicito al usuario el campo que quiere modificar
    		int opcion = vista.preguntarCampoAModificar();
    		
    		// Solicito el nuevo valor del campo
    		Object nuevoValor = null;
    		String campo = null;
    		switch (opcion) {
    			case 1: 
    				campo = "fecha";
    				nuevoValor = vista.solicitarFecha();
    				break;
    			case 2: 
    				campo = "total";
    				nuevoValor = vista.solicitarTotal();
    				break;
    			case 3: 
    				campo = "id_proveedor";
    				int id_proveedor = vista.solicitarIdProveedor();
    				if (Proveedor.existeProveedor(id_proveedor)) { // Compruebo si existe 
    					nuevoValor = id_proveedor;
    					break;
    				} else {
    					System.out.println("ERROR: No existe ningún proveedor registrado con ese ID.");
    					return;
    				}
    			case 4:
    				System.out.println("Volviendo al menú...");
    				return;
    			default:
                	System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
                	return;
    		}
    		
    		// Modifico la factura
    		fact.modificarFactura(id_factura, campo, nuevoValor);
    	} else {
    		System.out.println("ERROR: No se ha encontrado ninguna factura con ese id.");
    	}
    }
    
    public void eliminarFacturas() {
    	// Solicito al usuario el id de la factura que quiera eliminar
    	int id_factura = vista.preguntarFacturaAEliminar();
    	
    	// Compruebo si existe una factura con ese id antes de intentar eliminarlo
		if (Factura.existeFactura(id_factura)) {
			// Elimino la factura con el id recogido de la base de datos
	    	fact.eliminarFactura(id_factura);
		} else {
			System.out.println("ERROR: No se ha encontrado ninguna factura con ese id.");
		}
    }
}