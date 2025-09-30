package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Proveedor {
	private int id_proveedor;
	private String nombre;
	private String cif;
	private String telefono;
	
	public Proveedor(int id_proveedor, String nombre, String cif, String telefono) {
		this.setId_proveedor(id_proveedor);
		this.setNombre(nombre);
		this.setCif(cif);
		this.setTelefono(telefono);
	}
	
	/* Creo un segundo constructor que recibe solo el id e instancia el proveedor
	 *  correspondiente en la base de datos */
	public Proveedor(int id_proveedor) {
		this.setId_proveedor(id_proveedor);
		// Obtengo los datos de la base de datos mediante el id y el método obtenerDatos
		Proveedor proveedor = obtenerDatos(id_proveedor);
		this.setNombre(proveedor.getNombre());
		this.setCif(proveedor.getCif());
		this.setTelefono(proveedor.getTelefono());
	}
	
	// Creo el método toString para poder mostrar los datos del proveedor en consola
	public String toString() {
	    return "ID: " + id_proveedor + ", Nombre: " + nombre + ", CIF: " + cif + ", Telefono: " + telefono;
	}

	
	public int getId_proveedor() {
		return id_proveedor;
	}


	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	// Método que muestra los datos del proveedor
	public String mostrarDatos() {
		return "ID: " + getId_proveedor() + ", Nombre: " + getNombre() + ", CIF: " + getCif() + ", Teléfono: " + getTelefono();
	}
	
	
    public void agregarProveedor(HashMap<String, Object> datosProveedor) {
    	
		try {
            // Consulta SQL para insertar el nuevo proveedor en la base de datos
            String query = "INSERT INTO proveedores (nombre, cif, telefono) VALUES (?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros del nueva proveedor
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, (String) datosProveedor.get("nombre"));
            pstmt.setString(2, (String) datosProveedor.get("cif"));
            pstmt.setString(3, (String) datosProveedor.get("telefono"));

            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Proveedor añadido correctamente.");
            } else {
                System.out.println("Error al añadir el proveedor a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir el proveedor: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Proveedor> obtenerProveedores() {
    	// Creo el ArrayList listaproveedores para almacenar los proveedores que se obtengan de la base de datos
    	ArrayList<Proveedor> listaproveedores = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT * FROM proveedores;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos los datos del proveedor (id_proveedor, nombre, cif, telefono).
            while (rs.next()) {
                int id_proveedor = rs.getInt("id_proveedor");
                String nombre = rs.getString("nombre");
                String cif = rs.getString("cif");
                String telefono = rs.getString("telefono");

                // Instancio un objeto proveedor con todos los datos obtenidos
                Proveedor proveedor = new Proveedor(id_proveedor, nombre, cif, telefono);
                listaproveedores.add(proveedor); // Añado el proveedor a la lista
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener los proveedores: " + e.getMessage());
        }
        
        return listaproveedores; // Devuelvo la lista de proveedores con los resultados obtenidos de la base de datos.
    }
    
    
    // Método para comprobar si existe un proveedor registrado con la clave primaria recibida
    public static boolean existeProveedor(int id_proveedor) {
        String query = "SELECT * FROM Proveedores WHERE id_proveedor = ?";
        try {
        	Connection conexion = Conexion.obtenerConexion();
        	// Preparo la consulta con la query
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establezco el parámetro id_proveedor con el id recibido
            pstmt.setInt(1, id_proveedor);
            // Ejecuto la sentencia
            ResultSet rs = pstmt.executeQuery();
            // Devuelvo un booleano según el resultado
            return rs.next(); // Si hay resultado, devolverá true (el proveedor existe)
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia del proveedor en la base de datos: " + e.getMessage());
            return false;
        }
    }
    
    
    public Proveedor obtenerDatos(int id_proveedor) {
    	// Inicializo la variable proveedor en la que instaciaré un objeto con los datos obtenidos
		Proveedor proveedor = null;
    	try {
            // Consulta SQL para obtener los datos del proveedor con ese id
            String query = "SELECT * FROM proveedores WHERE id_proveedor = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();

            // Preparo la consulta con el ID del proveedor
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_proveedor);

            // Ejecuto la consulta
            ResultSet rs = pstmt.executeQuery();

            // Si el proveedor existe, creo un objeto Proveedor con sus datos
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String cif = rs.getString("cif");
                String telefono = rs.getString("telefono");

                proveedor = new Proveedor(id_proveedor, nombre, cif, telefono);
            } else {
                System.out.println("No se ha encontrado ningún proveedor con ese id.");
            }

            // Cierro recursos
            rs.close();
            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del proveedor: " + e.getMessage());
        }

        return proveedor; // Devuelvo un objeto proveedor con los datos
    }
    
    
    public void modificarProveedor(int id_proveedor, String campo, String nuevoValor) {
    	try {
            String query = "UPDATE proveedores SET " + campo + " = ? WHERE id_proveedor = ?;";

            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(query);

            pstmt.setString(1, nuevoValor);
            pstmt.setInt(2, id_proveedor);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Proveedor modificado correctamente.");
            } else {
                System.out.println("No se ha podido modificar el proveedor.");
            }

            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al modificar el proveedor: " + e.getMessage());
        }
    }
    
    
    public void eliminarProveedor(int id_proveedor) {
		try {
            // Consulta SQL para eliminar el proveedor
            String query = "DELETE FROM proveedores WHERE id_proveedor = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido del proveedor a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_proveedor);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Proveedor eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el proveedor.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el proveedor: " + e.getMessage());
        }

    }

}
