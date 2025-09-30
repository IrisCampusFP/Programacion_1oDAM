package Controlador;
import Vista.Menus;
import Vista.VistaProveedor;
import java.util.ArrayList;
import java.util.HashMap;
import Modelo.Proveedor;

public class ControladorProveedores {
    Menus m = new Menus();
    VistaProveedor vista = new VistaProveedor();
    Proveedor pro = new Proveedor(0, null, null, null);

    public void submenu() {
    	int opcion = 0;
    	while (opcion != 5) {
    		// Muestro el submenú y recibo la opción seleccionada
        	opcion = m.submenu("PROVEEDORES");
        	
        	switch (opcion) {
    		case 1:
    			agregarProveedor();
    			break;
    		case 2:
    			listarProveedores();
    			break;
    		case 3:
    			modificarProveedores();
    			break;
    		case 4:
    			eliminarProveedores();		
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
    
    public void agregarProveedor() {
    	// Solicito los datos del nuevo proveedor al usuario
    	HashMap<String, Object> datosProveedor = vista.solicitarDatosProveedor();

    	// Agrego el proveedor a la base de datos
    	pro.agregarProveedor(datosProveedor);
    }
    
    public void listarProveedores() {
    	// Obtengo la lista de proveedores de la base de datos
    	ArrayList<Proveedor> listaProveedores = pro.obtenerProveedores();
    	
    	// Muestro la lista obtenida al usuario
    	vista.mostrarProveedores(listaProveedores);
    }
    
    public void modificarProveedores() {
    	// Solicito al usuario el id del proveedor que quiera modificar
    	int id_proveedor = vista.preguntarProveedorAModificar();
    	
    	// Compruebo si existe un proveedor con ese id antes de intentar modificarlo
    	if (Proveedor.existeProveedor(id_proveedor)) {
    		
    		// Muestro los datos del proveedor a modificar
    		Proveedor proveedor = pro.obtenerDatos(id_proveedor); // Obtengo los datos de la base de datos
    		vista.mostrarDatos(proveedor); // Muestro los datos
    		
    		// Solicito al usuario el campo que quiere modificar
    		int opcion = vista.preguntarCampoAModificar();
    		
    		// Solicito el nuevo valor del campo
    		String nuevoValor = null;
    		String campo = null;
    		switch (opcion) {
    			case 1: 
    				campo = "nombre";
    				nuevoValor = vista.solicitarNombre();
    				break;
    			case 2: 
    				campo = "cif";
    				nuevoValor = vista.solicitarCif();
    				break;
    			case 3: 
    				campo = "telefono";
    				nuevoValor = vista.solicitarTelefono();
    				break;
    			case 4:
    				System.out.println("Volviendo al menú...");
    				return;
    			default:
                	System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
                	return;
    		}
    		
    		// Modifico el proveedor
    		pro.modificarProveedor(id_proveedor, campo, nuevoValor);
    	} else {
    		System.out.println("ERROR: No se ha encontrado ningún proveedor con ese id.");
    	}
    }
    
    public void eliminarProveedores() {
    	// Solicito al usuario el id del proveedor que quiera eliminar
    	int id_proveedor = vista.preguntarProveedorAEliminar();
    	
    	// Compruebo si existe un proveedor con ese id antes de intentar eliminarlo
		if (Proveedor.existeProveedor(id_proveedor)) {
			// Elimino el proveedor con el id recogido de la base de datos
	    	pro.eliminarProveedor(id_proveedor);
		} else {
			System.out.println("ERROR: No se ha encontrado ningún proveedor con ese id.");
		}
    }
}