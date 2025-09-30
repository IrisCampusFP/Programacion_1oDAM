package Vista;
import java.util.Scanner;

public class Menus {
	Scanner s = new Scanner(System.in);
	
	public int menuPrincipal() {
		System.out.println("\nMENÚ PRINCIPAL:");
		System.out.println("1- Gestión de Clientes");
		System.out.println("2- Gestión de Proveedores");
		System.out.println("3- Gestión de Artículos");
		System.out.println("4- Gestión de Facturas Recibidas");
		System.out.println("5- Gestión de Ventas");
		System.out.println("6- Informes de Ventas por Cliente");
		System.out.println("7- Salir");
		
		System.out.print("Selecciona una opción: ");
		int opcion;
		while (!s.hasNextInt()) { // Controlo la excepción en la que el usuario no introduce un numero
		    System.out.print("ERROR. Introduce un número: ");
			s.next();
		}
		opcion = s.nextInt();
		return opcion;
	}
	
	public int submenu(String tabla) {
		System.out.println("\nMENÚ " + tabla + ":");
		System.out.println("1- Crear nuevos registros");
		System.out.println("2- Listar registros existentes");
		System.out.println("3- Modificar registros");
		System.out.println("4- Eliminar registros");
		System.out.println("5- Volver");
		
		System.out.print("Selecciona una opción: ");
		int opcion;
		while (!s.hasNextInt()) { // Controlo la excepción en la que el usuario no introduce un numero
		    System.out.print("ERROR. Introduce un número: ");
			s.next();
		}
		opcion = s.nextInt();
		return opcion;
	}
}
