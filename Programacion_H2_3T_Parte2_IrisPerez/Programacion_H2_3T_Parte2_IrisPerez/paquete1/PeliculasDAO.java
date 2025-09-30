package paquete1;

import java.sql.*;		
import java.util.ArrayList;

// CLASE ENCARGADA DE LA INTERACCIÓN CON LA BASE DE DATOS

public class PeliculasDAO {

    public ArrayList<Pelicula> obtenerPeliculas() {
    	// Creo el ArrayList listaPeliculas para almacenar las peliculas que se obtengan de la base de datos
    	ArrayList<Pelicula> listaPeliculas = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT p.idPelicula, p.titulo, p.director, p.duracionMinutos, "
					+ "p.idioma, p.anoLanzamiento, c.nombre AS categoria, c.idCategoria "
					+ "FROM peliculas p "
					+ "JOIN categorias c ON p.idCategoria = c.idCategoria;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos sus datos (idPelicula, titulo, director...).
            while (rs.next()) {
                int idPelicula = rs.getInt("idPelicula");
                String titulo = rs.getString("titulo");
                String director = rs.getString("director");
                int duracion = rs.getInt("duracionMinutos");
                String idioma = rs.getString("idioma");
                int anoLanzamiento = rs.getInt("anoLanzamiento");
                
                int idCategoria = rs.getInt("idCategoria");
                String nombreCategoria = rs.getString("categoria");

                // Instancio un objeto categoria con los datos de la categoría de la pelicula
                Categoria categoria = new Categoria(idCategoria, nombreCategoria);
                // Instancio un objeto pelicula con todos los datos obtenidos (incluyendo su categoria)
                Pelicula pelicula = new Pelicula(idPelicula, titulo, director, duracion, idioma, anoLanzamiento, categoria);
                listaPeliculas.add(pelicula); // Añado la pelicula a la lista de películas
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener las películas: " + e.getMessage());
        }
        
        return listaPeliculas; // Devuelvo la lista de películas con los resultados obtenidos de la base de datos almacenados en ella.
    }

    
    public void agregarPelicula(Pelicula pelicula) {
    	try {
            // Consulta SQL para insertar la nueva película en la base de datos
            String query = "INSERT INTO peliculas (idPelicula, titulo, director, duracionMinutos, idioma, anoLanzamiento, idCategoria) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros de la nueva película
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, pelicula.idPelicula);
            pstmt.setString(2, pelicula.titulo);
            pstmt.setString(3, pelicula.director);
            pstmt.setInt(4, pelicula.duracionMinutos);
            pstmt.setString(5, pelicula.idioma);
            pstmt.setInt(6, pelicula.anoLanzamiento);
            pstmt.setInt(7, pelicula.categoria.idCategoria);
            
            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Película añadida correctamente.");
            } else {
                System.out.println("Error al añadir la película a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir la película: " + e.getMessage());
        }
    }
    
    public void eliminarPelicula(int idPelicula) {
    	try {
            // Consulta SQL para eliminar la película ID
            String query = "DELETE FROM peliculas WHERE idPelicula = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido de la película a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, idPelicula);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Película eliminada correctamente.");
            } else {
                System.out.println("Error al eliminar la película.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la película: " + e.getMessage());
        }
    }
    
    public void modificarPelicula(String campo, String nuevoValor, String valorAntiguo) {
    	try {
        	String query = "UPDATE peliculas SET " + campo + " = ? WHERE " + campo + " = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el nuevo valor como parámetro
            PreparedStatement pstmt = conexion.prepareStatement(query);
            
            // Hago setInt si el parámetro es un número y setString si es cadena de texto
            if (campo.equals("idPelicula") || campo.equals("duracionMinutos") || campo.equals("anoLanzamiento") || campo.equals("idCategoria")) {
                int valorInt = Integer.parseInt(nuevoValor);
                pstmt.setInt(1, valorInt);
                pstmt.setInt(2, Integer.parseInt(valorAntiguo));
            } else {
                pstmt.setString(1, nuevoValor);
                pstmt.setString(2, valorAntiguo);
            }
                        
            // Ejecutamos la consulta en la base de datos
            int rowsAffected = pstmt.executeUpdate();

            // Compruebo si ha habido filas afectadas, mostrando al usuario el mensaje correspondiente
            if (rowsAffected > 0) {
                System.out.println("Película modificada correctamente.");
            } else {
                System.out.println("Error al modificar la película. No se encontró una película con ese ID.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al modificar la película: " + e.getMessage());
        }
    }
}
