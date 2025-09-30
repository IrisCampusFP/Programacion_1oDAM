package paquete1;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		GestionPeliculas gp = new GestionPeliculas();
		
		int opcion = 0;
		// Hago un bucle para mostrar el menú hasta que el usuario seleccione salir (fin del programa)
		while (opcion != 5) {
			System.out.println("\nMENÚ:");
			System.out.println("1- Ver películas");
			System.out.println("2- Añadir película");
			System.out.println("3- Eliminar película");
			System.out.println("4- Modificar película");
			System.out.println("5- Salir");
			
			System.out.print("Selecciona una opción: ");
			opcion = s.nextInt(); // Recojo la opción seleccionada por el usuario
			
			System.out.println();
			
			// Realizo la opción correspondiente a la selección del usuario
			switch (opcion) {
				case 1:
					gp.verPeliculas();
					break;
				case 2:
					gp.agregarPelicula();
					break;
				case 3:
					gp.eliminarPelicula();
					break;
				case 4:
					gp.modificarPelicula();
					break;
				case 5: 
					System.out.println("¡Hasta pronto!");
					break;
				default: // Si el usuario introduce un número no válido (que no corresponde a ninguna opción), le saltará un mensaje de error.
					System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
			}
		}
	}

}
