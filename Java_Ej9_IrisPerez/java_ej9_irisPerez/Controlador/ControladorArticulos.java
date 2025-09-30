package Controlador;
import Vista.Menus;	
import Vista.VistaArticulo;
import java.util.ArrayList;
import java.util.HashMap;
import Modelo.Articulo;

public class ControladorArticulos {
    Menus m = new Menus();
    VistaArticulo vista = new VistaArticulo();
    Articulo art = new Articulo(0, null, 0, 0);

    public void submenu() {
    	int opcion = 0;
    	while (opcion != 5) {
    		// Muestro el submenú y recibo la opción seleccionada
        	opcion = m.submenu("ARTÍCULOS");
        	
        	switch (opcion) {
    		case 1:
    			agregarArticulo();
    			break;
    		case 2:
    			listarArticulos();
    			break;
    		case 3:
    			modificarArticulos();
    			break;
    		case 4:
    			eliminarArticulos();		
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
    
    public void agregarArticulo() {
    	// Solicito los datos del nuevo articulo al usuario
    	HashMap<String, Object> datosArticulo = vista.solicitarDatosArticulo();

    	// Agrego el articulo a la base de datos
    	art.agregarArticulo(datosArticulo);
    }
    
    public void listarArticulos() {
    	// Obtengo la lista de articulos de la base de datos
    	ArrayList<Articulo> listaArticulos = art.obtenerArticulos();
    	
    	// Muestro la lista obtenida al usuario
    	vista.mostrarArticulos(listaArticulos);
    }
    
    public void modificarArticulos() {
    	// Solicito al usuario el id del articulo que quiera modificar
    	int id_Articulo = vista.preguntarArticuloAModificar();
    	
    	// Compruebo si existe un articulo con ese id antes de intentar modificarlo
    	if (Articulo.existeArticulo(id_Articulo)) {
    		
    		// Muestro los datos del articulo a modificar
    		Articulo Articulo = art.obtenerDatos(id_Articulo); // Obtengo los datos de la base de datos
    		vista.mostrarDatos(Articulo); // Muestro los datos
    		
    		// Solicito al usuario el campo que quiere modificar
    		int opcion = vista.preguntarCampoAModificar();
    		
    		// Solicito el nuevo valor del campo
    		Object nuevoValor = null;
    		String campo = null;
    		switch (opcion) {
    			case 1: 
    				campo = "nombre";
    				nuevoValor = vista.solicitarNombre();
    				break;
    			case 2: 
    				campo = "precio_unitario";
    				nuevoValor = vista.solicitarPrecio();
    				break;
    			case 3: 
    				campo = "stock";
    				nuevoValor = vista.solicitarStock();
    				break;
    			case 4:
    				System.out.println("Volviendo al menú...");
    				return;
    			default:
                	System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
                	return;
    		}
    		
    		// Modifico el articulo
    		art.modificarArticulo(id_Articulo, campo, nuevoValor);
    	} else {
    		System.out.println("ERROR: No se ha encontrado ningún articulo con ese id.");
    	}
    }
    
    public void eliminarArticulos() {
    	// Solicito al usuario el id del articulo que quiera eliminar
    	int id_Articulo = vista.preguntarArticuloAEliminar();
    	
    	// Compruebo si existe un articulo con ese id antes de intentar eliminarlo
		if (Articulo.existeArticulo(id_Articulo)) {
			// Elimino el articulo con el id recogido de la base de datos
	    	art.eliminarArticulo(id_Articulo);
		} else {
			System.out.println("ERROR: No se ha encontrado ningún articulo con ese id.");
		}
    }
}