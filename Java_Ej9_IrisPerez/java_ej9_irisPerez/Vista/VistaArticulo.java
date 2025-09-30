package Vista;
import java.util.Scanner;	
import Modelo.Articulo;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaArticulo {
	Scanner s = new Scanner(System.in);
	
	public HashMap<String, Object> solicitarDatosArticulo() {
		HashMap<String, Object> datosArticulo = new HashMap<>();
		
		// Solicito al usuario los datos del nuevo articulo
    	System.out.println("\nINTRODUCE LOS DATOS DEL ARTÍCULO:");
		
		System.out.print("Nombre: ");
		String nombre = s.nextLine();
		
		System.out.print("Precio unitario: ");
		while (!s.hasNextDouble()) { 
			System.out.print("ERROR. Introduce un precio: "); 
			s.next(); 
		}
		double precio_unitario = s.nextDouble();
		s.nextLine(); // Limpio el buffer
		System.out.print("Stock: ");
		while (!s.hasNextInt()) { 
			System.out.print("ERROR. Introduce un número entero: "); 
			s.next(); 
		}
		int stock = s.nextInt();
		s.nextLine(); // Limpio el buffer

		datosArticulo.put("nombre", nombre);
		datosArticulo.put("precio_unitario", precio_unitario);
		datosArticulo.put("stock", stock);
		
		return datosArticulo;
	}
	
	public void mostrarArticulos(ArrayList<Articulo> listaArticulos) {
		 // Recorro la lista de articulos y muestro todos los datos de cada una de ellas usando printf.
        for (Articulo c : listaArticulos) {
            System.out.printf("ID: %d, Nombre: %s, Precio: %.2f€, Stock: %d\n",
                c.getId_articulo(),
                c.getNombre(),
                c.getPrecio_unitario(),
                c.getStock());
        }
        // (.2f imprime 2 decimales del número decimal)
	}
	
	public int preguntarArticuloAEliminar() {
    	// Solicito al usuario la clave primaria del Articulo a eliminar
    	System.out.print("Introduce el ID del Articulo a eliminar: ");
		while (!s.hasNextInt()) {
		    System.out.print("ERROR. Introduce un número (ID): ");
		    s.next();
		}
    	int id_articulo = s.nextInt();
    	s.nextLine(); // Limpio el buffer
		return id_articulo;
	}
	
	public int preguntarArticuloAModificar() {
    	// Solicito al usuario la clave primaria del Articulo a modificar
    	System.out.print("Introduce el ID del Articulo a modificar: ");
		while (!s.hasNextInt()) {
		    System.out.print("ERROR. Introduce un número (ID): ");
		    s.next();
		}
		int id_articulo = s.nextInt();
	    s.nextLine(); // Limpio el buffer
		return id_articulo;
	}
	
	public void mostrarDatos(Articulo Articulo) {
		System.out.printf("ID: %d, Nombre: %s, Precio: %f, Stock: %d\n",
			Articulo.getId_articulo(),
			Articulo.getNombre(),
			Articulo.getPrecio_unitario(),
			Articulo.getStock());
	}
	
	public int preguntarCampoAModificar() {
		System.out.print("¿Qué campo quieres modificar? (1- Nombre, 2- Precio, 3- Stock, 4- Volver): ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public String solicitarNombre() {
		s.nextLine();
		System.out.print("Introduce el nuevo nombre: ");
		return s.nextLine();
	}
	
	public double solicitarPrecio() {
		System.out.print("Introduce el nuevo precio unitario: ");
	    while (!s.hasNextDouble()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    s.nextLine(); // Limpio el buffer
	    return s.nextDouble();	
	}
	
	public int solicitarStock() {
		System.out.print("Introduce el nuevo stock: ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número entero: ");
	        s.next();
	    }
	    s.nextLine(); // Limpio el buffer
	    return s.nextInt();	
	}
}
