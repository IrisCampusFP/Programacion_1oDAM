package Vista;
import java.util.Scanner;
import Modelo.Venta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaVenta {
	Scanner s = new Scanner(System.in);
	
	public HashMap<String, Object> solicitarDatosVenta() {
		HashMap<String, Object> datosVenta = new HashMap<>();
		
		// Solicito al usuario los datos del nuevo venta
    	System.out.println("\nINTRODUCE LOS DATOS DE LA VENTA:");
		
		System.out.print("Fecha (Año-Mes-Día): ");
		String fecha_venta;
		while (true) {
		    fecha_venta = s.nextLine();
		    try {
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		        formato.setLenient(false); // Esto comprueba que la fecha sea válida
		    	formato.parse(fecha_venta); // Convierto la cadena recibida a formato fecha
		        break;
		    } catch (Exception e) {
		        System.out.print("Fecha inválida. Introduce una fecha válida (formato: yyyy-MM-dd): ");
		    }
		}
		
		System.out.print("ID del Artículo: ");
		while (!s.hasNextInt()) {
		    System.out.print("ERROR. Introduce un número: ");
		    s.next();
		}
		int id_articulo = s.nextInt();
		s.nextLine(); // Limpio el buffer
		
		System.out.print("Cantidad: ");
		while (!s.hasNextInt()) {
		    System.out.print("ERROR. Introduce un número: ");
		    s.next();
		}
		int cantidad = s.nextInt();
		s.nextLine(); // Limpio el buffer

		System.out.print("ID del Cliente: ");
		while (!s.hasNextInt()) {
		    System.out.print("ERROR. Introduce un número: ");
		    s.next();
		}
		int id_cliente = s.nextInt();
		s.nextLine(); // Limpio el buffer
		
		datosVenta.put("fecha_venta", fecha_venta);
		datosVenta.put("cantidad", cantidad);
		datosVenta.put("id_cliente", id_cliente);
		datosVenta.put("id_articulo", id_articulo);

		return datosVenta;
	}
	
	public void mostrarVentas(ArrayList<Venta> listaVentas) {
		// Recorro la lista de ventas y muestro todos los datos de cada una de ellas usando printf.
        for (Venta v : listaVentas) {
            System.out.printf("ID: %d, Fecha: %s, Cantidad: %s",
                v.getId_venta(),
                v.getFecha_venta(),
                v.getCantidad());
            System.out.print(" | CLIENTE -> "  + v.getCliente().mostrarDatos());
    		System.out.print(" | ARTICULO -> " + v.getArticulo().mostrarDatos() + "\n");
        }
	}
	
	public int preguntarVentaAEliminar() {
    	// Solicito al usuario la clave primaria de la venta a eliminar
    	System.out.print("Introduce el ID de la venta a eliminar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
		int id_venta = s.nextInt();
		return id_venta;
	}
	
	public int preguntarVentaAModificar() {
    	// Solicito al usuario la clave primaria de la venta a modificar
    	System.out.print("Introduce el ID de la venta a modificar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
		int id_venta = s.nextInt();
		return id_venta;
	}
	
	public void mostrarDatos(Venta venta) {
		System.out.printf("ID: %d, Fecha: %s, Cantidad: %s",
			venta.getId_venta(),
			venta.getFecha_venta(),
			venta.getCantidad());
		System.out.print(" | CLIENTE -> "  + venta.getCliente().mostrarDatos());
		System.out.print(" | ARTICULO -> " + venta.getArticulo().mostrarDatos() + "\n");
	}
	
	public int preguntarCampoAModificar() {
		System.out.print("¿Qué campo quieres modificar? (1- Fecha, 2- Cantidad, 3- ID Cliente, 4- ID Articulo, 5- Volver): ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public String solicitarFecha() {
		s.nextLine();
		System.out.print("Introduce la nueva fecha de venta (Año-Mes-Día): ");
		String fecha_venta;
		while (true) {
		    fecha_venta = s.nextLine();
		    try {
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		        formato.setLenient(false); // Esto comprueba que la fecha sea válida
		    	formato.parse(fecha_venta); // Convierto la cadena recibida a formato fecha
		        break;
		    } catch (Exception e) {
		        System.out.print("Fecha inválida. Introduce una fecha válida (formato: yyyy-MM-dd): ");
		    }
		}
		return fecha_venta;
	}
	
	public int solicitarCantidad() {
		s.nextLine();
		System.out.print("Introduce la nueva cantidad: ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número entero: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public int solicitarIdCliente() {
		s.nextLine();
		System.out.print("Introduce el nuevo ID de Cliente: ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número entero: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public int solicitarIdArticulo() {
		s.nextLine();
		System.out.print("Introduce el nuevo ID de Artículo: ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número entero: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public void mostrarInformeVenCli(ArrayList<HashMap<String, Object>> informe) {
	    if (informe.isEmpty()) {
	        System.out.println("No hay datos de ventas.");
	        return;
	    }

	    System.out.println("\nINFORMES DE VENTAS POR CLIENTE\n");

	    int clienteActual = -1;
	    double totalVenta = 0;

	    for (HashMap<String, Object> fila : informe) {
	        int id_cliente = (int) fila.get("id_cliente");
	        String nombre = (String) fila.get("cliente");
	        String articulo = (String) fila.get("articulo");
	        int cantidad = (int) fila.get("cantidad");
	        String fecha = (String) fila.get("fecha");
	        double total = (double) fila.get("total");

	        if (id_cliente != clienteActual) {
	            if (clienteActual != -1) {
	                System.out.println("Total de la venta: " + totalVenta + "€\n");
	                totalVenta = 0;
	            }
	            System.out.println("Cliente: " + nombre);
	            clienteActual = id_cliente;
	        }

	        System.out.println("- Artículo: " + articulo + " | Cantidad: " + 
	        cantidad + " | Fecha: " + fecha + " | Total: " + total + "€");

	        totalVenta += total;
	    }

	    System.out.println("Total de la venta: " + totalVenta + "€\n");
	}

}
