package Controlador;
import Vista.Menus;
import Vista.VistaCliente;
import java.util.ArrayList;
import java.util.HashMap;
import Modelo.Cliente;

public class ControladorClientes {
    Menus m = new Menus();
    VistaCliente vista = new VistaCliente();
    Cliente cli = new Cliente(0, null, null, null);

    public void submenu() {
    	int opcion = 0;
    	while (opcion != 5) {
    		// Muestro el submenú y recibo la opción seleccionada
        	opcion = m.submenu("CLIENTES");
        	
        	switch (opcion) {
    		case 1:
    			agregarCliente();
    			break;
    		case 2:
    			listarClientes();
    			break;
    		case 3:
    			modificarClientes();
    			break;
    		case 4:
    			eliminarClientes();		
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
    
    public void agregarCliente() {
    	// Solicito los datos del nuevo cliente al usuario
    	HashMap<String, Object> datosCliente = vista.solicitarDatosCliente();

    	// Agrego el cliente a la base de datos
    	cli.agregarCliente(datosCliente);
    }
    
    public void listarClientes() {
    	// Obtengo la lista de clientes de la base de datos
    	ArrayList<Cliente> listaClientes = cli.obtenerClientes();
    	
    	// Muestro la lista obtenida al usuario
    	vista.mostrarClientes(listaClientes);
    }
    
    public void modificarClientes() {
    	// Solicito al usuario el id del cliente que quiera modificar
    	int id_cliente = vista.preguntarClienteAModificar();
    	
    	// Compruebo si existe un cliente con ese id antes de intentar modificarlo
    	if (Cliente.existeCliente(id_cliente)) {
    		
    		// Muestro los datos del cliente a modificar
    		Cliente cliente = cli.obtenerDatos(id_cliente); // Obtengo los datos de la base de datos
    		vista.mostrarDatos(cliente); // Muestro los datos
    		
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
    				campo = "email";
    				nuevoValor = vista.solicitarEmail();
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
    		
    		// Modifico el cliente
    		cli.modificarCliente(id_cliente, campo, nuevoValor);
    	} else {
    		System.out.println("ERROR: No se ha encontrado ningún cliente con ese id.");
    	}
    }
    
    public void eliminarClientes() {
    	// Solicito al usuario el id del cliente que quiera eliminar
    	int id_cliente = vista.preguntarClienteAEliminar();
    	
    	// Compruebo si existe un cliente con ese id antes de intentar eliminarlo
		if (Cliente.existeCliente(id_cliente)) {
			// Elimino el cliente con el id recogido de la base de datos
	    	cli.eliminarCliente(id_cliente);
		} else {
			System.out.println("ERROR: No se ha encontrado ningún cliente con ese id.");
		}
    }
}