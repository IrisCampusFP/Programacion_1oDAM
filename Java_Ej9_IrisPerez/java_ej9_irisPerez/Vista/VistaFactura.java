package Vista;
import java.util.Scanner;
import Modelo.Factura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaFactura {
	Scanner s = new Scanner(System.in);
	
	public HashMap<String, Object> solicitarDatosFactura() {
		HashMap<String, Object> datosFactura = new HashMap<>();
		
		// Solicito al usuario los datos de la nueva factura
    	System.out.println("\nINTRODUCE LOS DATOS DE LA FACTURA:");
    	
		System.out.print("Fecha (Año-Mes-Día): ");
		String fecha;
		while (true) {
		    fecha = s.nextLine();
		    try {
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		        formato.setLenient(false); // Esto comprueba que la fecha sea válida
		    	formato.parse(fecha); // Convierto la cadena recibida a formato fecha
		        break;
		    } catch (Exception e) {
		        System.out.print("Fecha inválida. Introduce una fecha válida (formato: yyyy-MM-dd): ");
		    }
		}
		
		System.out.print("Total: ");
    	while (!s.hasNextDouble()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
    	double total = s.nextDouble();
    	s.nextLine();
    	
		System.out.print("ID del Proveedor: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
		int id_proveedor = s.nextInt();
		s.nextLine();
		
		datosFactura.put("fecha", fecha);
		datosFactura.put("total", total);
		datosFactura.put("id_proveedor", id_proveedor);
		
		return datosFactura;
	}
	
	public void mostrarFacturas(ArrayList<Factura> listaFacturas) {
		 // Recorro la lista de facturas y muestro todos los datos de cada una de ellas usando printf.
        for (Factura f : listaFacturas) {
            System.out.printf("ID: %d, Fecha: %s, Total: %s",
                f.getId_factura(),
                f.getFecha(),
                f.getTotal());
            System.out.print(" | PROVEEDOR -> " + f.getProveedor().mostrarDatos() + "\n");
        }
	}
	
	public int preguntarFacturaAEliminar() {
    	// Solicito al usuario la clave primaria del factura a eliminar
    	System.out.print("Introduce el ID del factura a eliminar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
    	int id_factura = s.nextInt();
    	s.nextLine();
		return id_factura;
	}
	
	public int preguntarFacturaAModificar() {
    	// Solicito al usuario la clave primaria del factura a modificar
    	System.out.print("Introduce el ID del factura a modificar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
    	int id_factura = s.nextInt();
    	s.nextLine();
		return id_factura;
	}
	
	public void mostrarDatos(Factura factura) {
		System.out.printf("ID: %d, Fecha: %s, Total: %s, Proveedor: %s\n",
			factura.getId_factura(),
			factura.getFecha(),
			factura.getTotal(),
			factura.getProveedor());
	}
	
	public int preguntarCampoAModificar() {
		System.out.print("¿Qué campo quieres modificar? (1- Fecha, 2- Total, 3- Proveedor, 4- Volver): ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    return  s.nextInt();	
	}
	
	public String solicitarFecha() {
		System.out.print("Introduce la nueva fecha: ");
		String fecha;
		while (true) {
		    fecha = s.nextLine();
		    try {
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		        formato.setLenient(false); // Esto comprueba que la fecha sea válida
		    	formato.parse(fecha); // Convierto la cadena recibida a formato fecha
		        break;
		    } catch (Exception e) {
		        System.out.print("Fecha inválida. Introduce una fecha válida (formato: yyyy-MM-dd): ");
		    }
		}
		return fecha;
	}
	
	public double solicitarTotal() {
		System.out.print("Introduce el nuevo total: ");
	    while (!s.hasNextDouble()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    s.nextLine();
	    return s.nextDouble();	
	}
	
	public int solicitarIdProveedor() {
		System.out.print("Introduce el nuevo ID de Proveedor: ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número entero: ");
	        s.next();
	    }
	    s.nextLine();
	    return s.nextInt();	
	}
}
