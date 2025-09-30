package Modelo;

import java.sql.Connection;	
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Articulo {
	private int id_articulo;
	private String nombre;
	private double precio_unitario;
	private int stock;
	
	public Articulo(int id_articulo, String nombre, double precio_unitario, int stock) {
		this.setId_articulo(id_articulo);
		this.setNombre(nombre);
		this.setPrecio_unitario(precio_unitario);
		this.setStock(stock);
	}
	
	/* Creo un segundo constructor que recibe solo el id e instancia el artículo
	 *  correspondiente en la base de datos */
	public Articulo(int id_articulo) {
		this.setId_articulo(id_articulo);
		// Obtengo los datos de la base de datos mediante el id y el método obtenerDatos
		Articulo articulo = obtenerDatos(id_articulo);
		this.setNombre(articulo.getNombre());
		this.setPrecio_unitario(articulo.getPrecio_unitario());
		this.setStock(articulo.getStock());
	}
	
	public int getId_articulo() {
		return id_articulo;
	}

	public void setId_articulo(int id_articulo) {
		this.id_articulo = id_articulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(double precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	// Método que muestra los datos del cliente
	public String mostrarDatos() {
		return "ID: " + getId_articulo() + ", Nombre: " + getNombre() + ", Precio: " + getPrecio_unitario() + "€, Stock: " + getStock();
	}
	
	
	public void agregarArticulo(HashMap<String, Object> datosArticulo) {
    	
		try {
            // Consulta SQL para insertar el nuevo Articulo en la base de datos
            String query = "INSERT INTO Articulos (nombre, precio_unitario, stock) VALUES (?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros de la nueva Articulo
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, (String) datosArticulo.get("nombre"));
            pstmt.setDouble(2, (double) datosArticulo.get("precio_unitario"));
            pstmt.setInt(3, (int) datosArticulo.get("stock"));

            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Articulo añadido correctamente.");
            } else {
                System.out.println("Error al añadir la Articulo a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir la Articulo: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Articulo> obtenerArticulos() {
    	// Creo el ArrayList listaArticulos para almacenar los articulos que se obtengan de la base de datos
    	ArrayList<Articulo> listaArticulos = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT * FROM Articulos;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos los datos del articulo (id_articulo, nombre, precio_unitario, stock).
            while (rs.next()) {
                int id_articulo = rs.getInt("id_articulo");
                String nombre = rs.getString("nombre");
                double precio_unitario = rs.getDouble("precio_unitario");
                int stock = rs.getInt("stock");

                // Instancio un objeto articulo con todos los datos obtenidos
                Articulo articulo = new Articulo(id_articulo, nombre, precio_unitario, stock);
                listaArticulos.add(articulo); // Añado el Articulo a la lista
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener los articulos: " + e.getMessage());
        }
        
        return listaArticulos; // Devuelvo la lista de articulos con los resultados obtenidos de la base de datos.
    }
    
    
    // Método para comprobar si existe un articulo registrado con la clave primaria recibida
    public static boolean existeArticulo(int id_articulo) {
        String query = "SELECT * FROM Articulos WHERE id_articulo = ?";
        try {
        	Connection conexion = Conexion.obtenerConexion();
        	// Preparo la consulta con la query
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establezco el parámetro id_articulo con el id recibido
            pstmt.setInt(1, id_articulo);
            // Ejecuto la sentencia
            ResultSet rs = pstmt.executeQuery();
            // Devuelvo un booleano según el resultado
            return rs.next(); // Si hay resultado, devolverá true (el articulo existe)
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia del articulo en la base de datos: " + e.getMessage());
            return false;
        }
    }
    
    
    public Articulo obtenerDatos(int id_articulo) {
    	// Inicializo la variable articulo en la que instaciaré un objeto con los datos obtenidos
		Articulo articulo = null;
    	try {
            // Consulta SQL para obtener los datos del articulo con ese id
            String query = "SELECT * FROM Articulos WHERE id_articulo = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();

            // Preparo la consulta con el ID del articulo
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_articulo);

            // Ejecuto la consulta
            ResultSet rs = pstmt.executeQuery();

            // Si el articulo existe, creo un objeto articulo con sus datos
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio_unitario = rs.getDouble("precio_unitario");
                int stock = rs.getInt("stock");

                articulo = new Articulo(id_articulo, nombre, precio_unitario, stock);
            } else {
                System.out.println("No se ha encontrado ningún articulo con ese id.");
            }

            // Cierro recursos
            rs.close();
            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del articulo: " + e.getMessage());
        }

        return articulo; // Devuelvo un objeto articulo con los datos
    }
    
    
    public void modificarArticulo(int id_articulo, String campo, Object nuevoValor) {
    	try {
            String query = "UPDATE Articulos SET " + campo + " = ? WHERE id_articulo = ?;";

            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(query);

            pstmt.setObject(1, nuevoValor);
            pstmt.setInt(2, id_articulo);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Articulo modificado correctamente.");
            } else {
                System.out.println("No se ha podido modificar el articulo.");
            }

            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al modificar el articulo: " + e.getMessage());
        }
    }
    
    
    public void eliminarArticulo(int id_articulo) {
		try {
            // Consulta SQL para eliminar el articulo
            String query = "DELETE FROM Articulos WHERE id_articulo = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido del artículo a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_articulo);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Articulo eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el articulo.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el articulo: " + e.getMessage());
        }

    }

}
