package paquete1;
import java.util.Scanner;	
import java.util.HashSet;

public class GestionarAnimales {
	public static Scanner s = new Scanner(System.in);
	
	// Creo una lista de animales registrados con un HashSet para asegurarme de que no se repiten números de chip
	public static HashSet<Animal> listaAnimales = new HashSet<>();
	
	// Creo una lista para almacenar los datos de todas las adopciones
		public static HashSet<Adopcion> listaAdopciones = new HashSet<>();
	
	public static void menu() {
		while (true) {
			GestionarAnimales gest = new GestionarAnimales();
			
			System.out.println();
			System.out.println("MENÚ:");
			System.out.println("1- Dar de alta animal");
			System.out.println("2- Listar animales");
			System.out.println("3- Buscar animal");
			System.out.println("4- Realizar adopción");
			System.out.println("5- Dar de baja");
			System.out.println("6- Mostrar estadísticas de gatos");
			System.out.println("7- Salir");
			
			System.out.print("\nSelecciona una opción: ");
			int respuesta = s.nextInt();
			
			switch (respuesta) {
	        case 1:
	            gest.agregarAnimal();
	            break;
	        case 2:
	            gest.listarAnimales();
	            break;
	        case 3:
	            gest.buscarAnimal();
	            break;
	        case 4:
	            gest.realizarAdopcion();
	            break;
	        case 5:
	            gest.darDeBaja();
	            break;
	        case 6:
	            gest.mostrarEstadisticasGatos();
	            break;
	        case 7:
	        	System.out.println("¡Hasta pronto!");
	            System.exit(0);; // Termino la ejecución (el 0 indica que el programa se ha finalizado correctamente y no por un error)
	        default:
				System.out.println("Error, introduce una opción válida");
			}
		}
	} 
	
	
	public void agregarAnimal() {
		System.out.print("¿Qué animal quieres agregar? (1- Perro, 2- Gato): ");
		int seleccionAnimal = s.nextInt();
		
		if (seleccionAnimal != 1 && seleccionAnimal != 2) {
			System.out.println("Error, introduce el número correspondiente al animal que quieras agregar.");	
		}
		
		System.out.print("Número de chip: "); 
		String numeroChip = s.next();
		
		// Controlo que no se repitan numeros de chip (Si el numero de chip ya existe, muestro un error al usuario y salgo del método)
		for (Animal animal : listaAnimales) {
			if (animal.numeroChip.equals(numeroChip)) {
				System.out.println("Error, ya existe un animal registrado con ese número de chip.");
			}
		}
		
		System.out.print("Nombre: ");
		String nombre = s.next();
		
		System.out.print("Edad: ");
		int edad = s.nextInt();
		
		System.out.print("Raza: ");
		String raza = s.next();
		
		System.out.print("¿Es adoptado? (true/false): ");
		boolean adoptado = s.nextBoolean();
		
		if (seleccionAnimal == 1) {
			agregarPerro(numeroChip, nombre, edad, raza, adoptado);
		} else if (seleccionAnimal == 2) {
			agregarGato(numeroChip, nombre, edad, raza, adoptado);
		}
	}
	
	public void agregarPerro(String numeroChip, String nombre, int edad, String raza, boolean adoptado) {
		
		System.out.print("Tamaño (pequeño, mediano o grande): "); 
		String tamaño = s.next();
		
		Perro perro = new Perro(numeroChip, nombre, edad, raza, adoptado, tamaño);
		
		// Agrego el nuevo perro a la lista de animales registrados
		listaAnimales.add(perro);
		
		System.out.println("Perro agregado correctamente. :)");
		
	}
	
	public static void agregarGato(String numeroChip, String nombre, int edad, String raza, boolean adoptado) {
		
		System.out.print("Test de leucemia (true/false): "); 
		boolean testLeucemia = s.nextBoolean();
		
		Gato gato = new Gato(numeroChip, nombre, edad, raza, adoptado, testLeucemia);

		// Agrego el nuevo gato a la lista de animales registrados		
		listaAnimales.add(gato);
		
		System.out.println("Gato agregado correctamente. :)");
	
	}
	
	
	public void listarAnimales() {
		System.out.println("LISTA DE ANIMALES:\n");
		
		for (Animal animal : listaAnimales) {
			animal.mostrar();
			System.out.println();
		}
	}
	
	
	// Método para buscar animales por número de chip y mostrar sus datos
	public void buscarAnimal() {
		System.out.print("Introduce el número de chip del animal que quieras buscar: ");
		String numeroChip = s.next();
		
		boolean encontrado = false;
		System.out.println();
		
		for (Animal animal : listaAnimales) {
			if (animal.numeroChip.equals(numeroChip)) {
				animal.mostrar();
				encontrado = true;
			}
		}
		
		if (!encontrado) {
			System.out.println("No se ha encontrado ningún animal registrado con ese número de chip.");
		}
		
	}
	
	
	public void realizarAdopcion() {
		System.out.print("Introduce el número de chip del animal a adoptar: ");;
		String numeroChip = s.next();
		
		boolean encontrado = false;
		System.out.println();
		
		for (Animal animal : listaAnimales) {
			if (animal.numeroChip.equals(numeroChip)) {
				encontrado = true;
				
				if (animal.adoptado == true) {
					System.out.println("Este animal ya ha sido adoptado.");
					break;
				}
				
				System.out.println("Estos son los datos del animal:");
				animal.mostrar();
				
				System.out.println("\nIntroduce tus datos para poder confirmar la adopción:\n");
				
				System.out.print("Nombre: ");
				String nombre = s.next();
				
				System.out.print("DNI: ");
				String DNI = s.next();
				
				System.out.print("\n1- Confirmar adopción, 2- Cancelar: ");
				int opcion = s.nextInt();
				
				while (opcion != 1 && opcion != 2) {
	                System.out.print("\nOpción no válida, introduce el número 1 para confirmar, 2 para cancelar: ");
	                opcion = s.nextInt();
	            }
				
				if (opcion == 1) {
					Adopcion adopcion = new Adopcion(nombre, DNI, animal);
					listaAdopciones.add(adopcion);
					animal.adoptado = true;
	                System.out.println("Adopción confirmada.");
	                break;
	            } else {
	                System.out.println("Adopción cancelada.");
	                break;
	            }

			}
			
		}
		
		if (!encontrado) {
			System.out.println("No se ha encontrado ningún animal registrado con ese número de chip.");
		}
	}
	
	
	public void darDeBaja() {
		System.out.print("Introduce el número de chip del animal que quieras dar de baja: ");
		String numeroChip = s.next();
		
		boolean encontrado = false;
		System.out.println();
		
		for (Animal animal : listaAnimales) {
			if (animal.numeroChip.equals(numeroChip)) {
				encontrado = true;
				System.out.println("DATOS DEL ANIMAL:");
				animal.mostrar();
				System.out.println("¿Estas seguro de que quieres eliminar el animal? (Se borrarán sus datos)");
				System.out.print("1- Confirmar, 2- Cancelar: ");
				int opcion = s.nextInt();
				
				while (opcion != 1 && opcion != 2) {
	                System.out.print("\nOpción no válida, introduce el número 1 para confirmar, 2 para cancelar: ");
	                opcion = s.nextInt();
	            }
				
				if (opcion == 1) {				
					
					listaAnimales.remove(animal);
	
					for (Adopcion adopcion : listaAdopciones)
						if (adopcion.animal.numeroChip.equals(numeroChip)) {
							listaAdopciones.remove(adopcion);
						}
					System.out.println("\nAnimal dado de baja correctamente.");
					
	            } else {
	            	System.out.println("\nVolviendo al menú...\n");
	            }

			}
		}
		
		if (!encontrado) {
			System.out.println("No se ha encontrado ningún animal registrado con ese número de chip.");
		}
	}
	
	
	public void mostrarEstadisticasGatos() {
	    int numeroTotalGatos = 0;
	    int gatosTestLeucemia = 0;
	    
	    // Recorro la lista de animales
	    for (Animal animal : listaAnimales) {
	    	// Si es un gato, sumo 1 al número total de gatos
	        if (animal instanceof Gato) {  
	            numeroTotalGatos++; 
	            // Convertimos el objeto de clase Animal a clase Gato para poder comprobar si tiene hecho un test de leucemia
	            Gato gato = (Gato) animal;
	            // Si el gato tiene hecho el test de leucemia (true), sumo 1
	            if (gato.testLeucemia) {  
	                gatosTestLeucemia++;
	            }
	        }
	    }
	    
	    // Muestro las estadísticas
	    System.out.println("Número total de gatos: " + numeroTotalGatos);
	    System.out.println("Número de gatos con test de leucemia: " + gatosTestLeucemia);
	}
	
	
}
