package Controlador;
import Vista.Menus;
import Vista.VistaVenta;
import java.util.ArrayList;
import java.util.HashMap;

import Modelo.Articulo;
import Modelo.Cliente;
import Modelo.Proveedor;
import Modelo.Venta;

public class ControladorVentas {
    Menus m = new Menus();
    VistaVenta vista = new VistaVenta();
    Venta ven = new Venta(0, null, 0, null, null);
    

    public void submenu() {
    	int opcion = 0;
    	while (opcion != 5) {
    		// Muestro el submenú y recibo la opción seleccionada
        	opcion = m.submenu("VENTAS");
        	
        	switch (opcion) {
    		case 1:
    			agregarVenta();
    			break;
    		case 2:
    			listarVentas();
    			break;
    		case 3:
    			modificarVentas();
    			break;
    		case 4:
    			eliminarVentas();		
    			break;
    		case 5:
    			System.out.println("Volviendo al menú principal...\n");	
    			break;
    		default:
    			// Si el usuario introduce un número no válido (que no corresponde a ninguna opción), le saltará un mensaje de error.
                System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
                return;
        	}
    	}
    }
    
    public void agregarVenta() {
    	// Solicito los datos de la nueva venta al usuario
    	HashMap<String, Object> datosVenta = vista.solicitarDatosVenta();

		// Compruebo que exista un cliente con el id introducido en la base de datos
    	int id_cliente = (int) datosVenta.get("id_cliente");
		if (!Proveedor.existeProveedor(id_cliente)) {
			System.out.println("ERROR: No existe ningún cliente registrado con el id introducido.");
			return;
		}
		// Compruebo que exista un artículo con el id introducido en la base de datos
    	int id_articulo = (int) datosVenta.get("id_articulo");
		if (!Proveedor.existeProveedor(id_articulo)) {
			System.out.println("ERROR: No existe ningún artículo registrado con el id introducido.");
			return;
		}
    	
    	// Agrego la venta a la base de datos
    	ven.agregarVenta(datosVenta);
    }
    
    public void listarVentas() {
    	// Obtengo la lista de ventas de la base de datos
    	ArrayList<Venta> listaVentas = ven.obtenerVentas();
    	
    	// Muestro la lista obtenida al usuario
    	vista.mostrarVentas(listaVentas);
    }
    
    public void modificarVentas() {
    	// Solicito al usuario el id de la venta que quiera modificar
    	int id_venta = vista.preguntarVentaAModificar();
    	
    	// Compruebo si existe un venta con ese id antes de intentar modificarlo
    	if (Venta.existeVenta(id_venta)) {
    		
    		// Muestro los datos de la venta a modificar
    		Venta venta = ven.obtenerDatos(id_venta); // Obtengo los datos de la base de datos
    		vista.mostrarDatos(venta); // Muestro los datos
    		
    		// Solicito al usuario el campo que quiere modificar
    		int opcion = vista.preguntarCampoAModificar();
    		
    		// Solicito el nuevo valor del campo
    		Object nuevoValor = null;
    		String campo = null;
    		switch (opcion) {
    			case 1: 
    				campo = "fecha_venta";
    				nuevoValor = vista.solicitarFecha();
    				break;
    			case 2: 
    				campo = "cantidad";
    				nuevoValor = vista.solicitarCantidad();
    				break;
    			case 3: 
    				campo = "id_cliente";
    				int id_cliente = vista.solicitarIdCliente();
    				if (Cliente.existeCliente(id_cliente)) { // Compruebo si existe 
    					nuevoValor = id_cliente;
    					break;
    				} else {
    					System.out.println("ERROR: No existe ningún cliente registrado con ese ID.");
    					return;
    				}
    			case 4: 
    				campo = "id_articulo";
    				int id_articulo = vista.solicitarIdArticulo();
    				if (Articulo.existeArticulo(id_articulo)) { // Compruebo si existe 
    					nuevoValor = id_articulo;
    					break;
    				} else {
    					System.out.println("ERROR: No existe ningún artículo registrado con ese ID.");
    					return;
    				}
    			case 5:
    				System.out.println("Volviendo al menú...");
    				return;
    			default:
                	System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
    		}
    		
    		// Modifico la venta
    		ven.modificarVenta(id_venta, campo, nuevoValor);
    	} else {
    		System.out.println("ERROR: No se ha encontrado ninguna venta con ese id.");
    	}
    }
    
    public void eliminarVentas() {
    	// Solicito al usuario el id de la venta que quiera eliminar
    	int id_venta = vista.preguntarVentaAEliminar();
    	
    	// Compruebo si existe una venta con ese id antes de intentar eliminarla
		if (Venta.existeVenta(id_venta)) {
			// Elimino la venta con el id recogido de la base de datos
	    	ven.eliminarVenta(id_venta);
		} else {
			System.out.println("ERROR: No se ha encontrado ninguna venta con ese id.");
		}
    }
    
    public void generarInforme() {
    	ArrayList<HashMap<String, Object>> datosInforme = ven.obtenerInformeVenCli();
        vista.mostrarInformeVenCli(datosInforme);
    }

}