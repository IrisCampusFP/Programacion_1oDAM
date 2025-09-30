package paquete1;

import java.util.ArrayList;	
import java.util.Scanner;

public class GestionPeliculas {
	// Importo un Scanner para poder solicitar datos al usuario
	Scanner s = new Scanner(System.in);
	// Instancio un objeto de la clase PeliculasDAO para poder acceder a sus métodos
	PeliculasDAO DAO = new PeliculasDAO(); // (Los métodos de esta clase interactúan con la base de datos)
	// ArrayList en el que se almacenan las películas obtenidas de la base de datos
    ArrayList<Pelicula> listaPeliculas = new ArrayList<>(DAO.obtenerPeliculas());

    public void verPeliculas() {
        // Recorro la lista de películas y muestro todos los datos de cada una de ellas usando printf.
        for (Pelicula p : listaPeliculas) {
            System.out.printf("ID: %d, Titulo: %s, Director: %s, Duracion: %d min., Idioma: %s, Año de Lanzamiento: %d, Categoría: %s\n",
                p.idPelicula,
                p.titulo,
                p.director,
                p.duracionMinutos,
                p.idioma,
                p.anoLanzamiento,
                p.categoria.nombre);
        }
    }
    
    public void agregarPelicula() {
    	// Solicito al usuario los datos de la nueva película
    	System.out.println("INTRODUCE LOS DATOS DE LA NUEVA PELÍCULA:");
		
		System.out.print("ID de la película: ");
		int idPelicula = s.nextInt();
		
		/* Si ya existe una pelicula con la misma clave primaria (idPelicula),
    	muestro un mensaje indicándoselo al usuario y le devuelvo al menú. */
		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.idPelicula == idPelicula) {
				System.out.println("ERROR: Ya existe una película con ese ID.");
				return;
			}
		}
		
		s.nextLine();
		System.out.print("Título: ");
		String titulo = s.nextLine();
		
		System.out.print("Director: ");
		String director = s.nextLine();
		
		System.out.print("Duración (en minutos): ");
		int duracionMinutos = s.nextInt();
		
		System.out.print("Idioma: ");
		String idioma = s.next();
		
		System.out.print("Año de lanzamiento: ");
		int anoLanzamiento = s.nextInt();
		
		System.out.print("CATEGORÍA (1- Acción, 2- Comedia, 3- Drama, 4- Terror, 5- Ciencia Ficción): ");
		int idCategoria = s.nextInt();

		Categoria categoria = new Categoria(idCategoria); // Llamo al nuevo constructor de Categoria, que atribuye el nombre correspondiente al id seleccionado
		
		Pelicula pelicula = new Pelicula(idPelicula, titulo, director, duracionMinutos, idioma, anoLanzamiento, categoria);
		
		// Guardo la nueva película en la base de datos
		DAO.agregarPelicula(pelicula);
		
		// Agrego la nueva película a la lista de películas
		listaPeliculas.add(pelicula);
    	
    }
    
    public void eliminarPelicula() {
    	// Solicito al usuario la clave primaria de la película a eliminar
    	System.out.print("Introduce el ID de la película a eliminar: ");
		int id = s.nextInt();
		
		boolean encontrada = false;
		System.out.println();
		
		// Si existe una película con ese ID, la elimino del ArrayList y de la base de datos
		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.idPelicula == id) {
				encontrada = true;
				System.out.println("Película encontrada. ¿Estas segur@ de que quieres eliminarla?");
				System.out.print("1- Sí, eliminar | 2- Cancelar: ");
				int opcion = s.nextInt();
				
				while (opcion != 1 && opcion != 2) {
	                System.out.print("\nOpción no válida, introduce 1 para eliminar la película, 2 para cancelar la operación: ");
	                opcion = s.nextInt();
	            }
				
				if (opcion == 1) {				
					// Elimino la película de la base de datos
					DAO.eliminarPelicula(id);
					// Elimino la película del ArrayList de películas
					listaPeliculas.remove(pelicula);
					
					System.out.println("\nPelícula eliminada correctamente.");
					
	            } else { 
	            	System.out.println("\nOperación cancelada, volviendo al menú...");
	            }
			}
		}
		
		if (!encontrada) { // Si no existe niguna película con el ID introducido, se lo indico al usuario
			System.out.println("No se ha encontrado ninguna película registrada con ese ID.");
		}

    }
    
    public void modificarPelicula() {
    	// Solicito al usuario la clave primaria de la película a modificar
    	System.out.print("Introduce el ID de la película a modificar: ");
		int id = s.nextInt();
		
		boolean encontrada = false;
		System.out.println();
		
		for (Pelicula pelicula : listaPeliculas) {
			if (pelicula.idPelicula == id) {
				encontrada = true;
				// Si existe una película con ese ID, muestro al usuario sus datos actuales.
				System.out.println("Película encontrada. DATOS DE LA PELÍCULA:");
				pelicula.mostrarDatos();
				
				// Pregunto al usuario el campo a modificar
				System.out.println("¿Que campo quieres modificar?");
				System.out.println("(1- ID, 2- Título, 3- Director, 4- Duración, 5- Idioma, 6- Año de lanzamiento, 7- Categoría)");
				int opcion = s.nextInt();
				System.out.println();
				// Guardo en la variable campo el nombre del campo correspondiente a la opción seleccionada
				// Solicito al usuario el nuevo valor del campo
				String campo = "";
				String valorAntiguo = "";
				String nuevoValor = "";
				
				switch (opcion) {
                case 1:
                    campo = "idPelicula";
                    valorAntiguo = String.valueOf(pelicula.idPelicula); 
                    System.out.print("Escribe el nuevo ID: ");
                    nuevoValor = s.next();
                    break;
                case 2:
                    campo = "titulo";
                    valorAntiguo = pelicula.titulo; 
                    s.nextLine();
                    System.out.print("Escribe el nuevo título: ");
                    nuevoValor = s.nextLine();
                    break;
                case 3:
                    campo = "director";
                    valorAntiguo = pelicula.director; 
                    s.nextLine();
                    System.out.print("Escribe el nuevo director: ");
                    nuevoValor = s.nextLine();
                    break;
                case 4:
                    campo = "duracionMinutos";
                    valorAntiguo = String.valueOf(pelicula.duracionMinutos); 
                    System.out.print("Escribe la nueva duración (en minutos): ");
                    nuevoValor = s.next();
                    break;
                case 5:
                    campo = "idioma";
                    valorAntiguo = pelicula.idioma;
                    s.nextLine();
                    System.out.print("Escribe el nuevo idioma: ");
                    nuevoValor = s.nextLine();
                    break;
                case 6:
                    campo = "anoLanzamiento";
                    valorAntiguo = String.valueOf(pelicula.anoLanzamiento);
                    System.out.print("Escribe el nuevo año de lanzamiento: ");
                    nuevoValor = s.next();
                    break;
                default: // Si el usuario introduce un número no válido (que no corresponde a ninguna opción), le saltará un mensaje de error.
					System.out.println("ERROR: Introduce el número correspondiente a la opción que quieras seleccionar.");
				}
				
				// Llamo al método que modifica la película en la base de datos enviándole el campo a modificar y su valor anterior y el nuevo valor
				DAO.modificarPelicula(campo, nuevoValor, valorAntiguo);
				
				// Vuelvo a obtener la lista de películas de la base de datos para actualizar el ArrayList
				DAO.obtenerPeliculas();
			}
		} 
		
		if (!encontrada) { // Si no existe niguna película con el ID introducido, se lo indico al usuario
			System.out.println("No se ha encontrado ninguna película registrada con ese ID.");
		}
    }
}
