package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Cliente {
	private int id_cliente;
	private String nombre;
	private String email;
	private String telefono;
	
	public Cliente(int id_cliente, String nombre, String email, String telefono) {
		this.setId_cliente(id_cliente);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setTelefono(telefono);
	}
	
	/* Creo un segundo constructor que recibe solo el id e instancia el cliente
	 *  correspondiente en la base de datos */
	public Cliente(int id_cliente) {
		this.setId_cliente(id_cliente);
		// Obtengo los datos de la base de datos mediante el id y el método obtenerDatos
		Cliente cliente = obtenerDatos(id_cliente);
		this.setNombre(cliente.getNombre());
		this.setEmail(cliente.getEmail());
		this.setTelefono(cliente.getTelefono());
	}
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	// Método que muestra los datos del cliente
	public String mostrarDatos() {
		return "ID: " + getId_cliente() + ", Nombre: " + getNombre() + ", Email: " + getEmail() + ", Teléfono: " + getTelefono();
	}
		
    
    public void agregarCliente(HashMap<String, Object> datosCliente) {
    	
		try {
            // Consulta SQL para insertar el nuevo cliente en la base de datos
            String query = "INSERT INTO Clientes (nombre, email, telefono) VALUES (?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros del nuevo cliente
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, (String) datosCliente.get("nombre"));
            pstmt.setString(2, (String) datosCliente.get("email"));
            pstmt.setString(3, (String) datosCliente.get("telefono"));

            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Cliente añadido correctamente.");
            } else {
                System.out.println("Error al añadir el cliente a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir el cliente: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Cliente> obtenerClientes() {
    	// Creo el ArrayList listaClientes para almacenar los clientes que se obtengan de la base de datos
    	ArrayList<Cliente> listaClientes = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT * FROM Clientes;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos los datos del cliente (id_cliente, nombre, email, telefono).
            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                // Instancio un objeto cliente con todos los datos obtenidos
                Cliente cliente = new Cliente(id_cliente, nombre, email, telefono);
                listaClientes.add(cliente); // Añado el cliente a la lista
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener los clientes: " + e.getMessage());
        }
        
        return listaClientes; // Devuelvo la lista de clientes con los resultados obtenidos de la base de datos.
    }
    
    
    // Método para comprobar si existe un cliente registrado con la clave primaria recibida
    public static boolean existeCliente(int id_cliente) {
        String query = "SELECT * FROM Clientes WHERE id_cliente = ?";
        try {
        	Connection conexion = Conexion.obtenerConexion();
        	// Preparo la consulta con la query
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establezco el parámetro id_cliente con el id recibido
            pstmt.setInt(1, id_cliente);
            // Ejecuto la sentencia
            ResultSet rs = pstmt.executeQuery();
            // Devuelvo un booleano según el resultado
            return rs.next(); // Si hay resultado, devolverá true (el cliente existe)
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia del cliente en la base de datos: " + e.getMessage());
            return false;
        }
    }

    
    
    public Cliente obtenerDatos(int id_cliente) {
    	// Inicializo la variable cliente en la que instaciaré un objeto con los datos obtenidos
		Cliente cliente = null;
    	try {
            // Consulta SQL para obtener los datos del cliente con ese id
            String query = "SELECT * FROM Clientes WHERE id_cliente = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();

            // Preparo la consulta con el ID del cliente
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_cliente);

            // Ejecuto la consulta
            ResultSet rs = pstmt.executeQuery();

            // Si el cliente existe, creo un objeto Cliente con sus datos
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                cliente = new Cliente(id_cliente, nombre, email, telefono);
            } else {
                System.out.println("No se ha encontrado ningún cliente con ese id.");
            }

            // Cierro recursos
            rs.close();
            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del cliente: " + e.getMessage());
        }

        return cliente; // Devuelvo un objeto cliente con los datos
    }
    
    
    public void modificarCliente(int id_cliente, String campo, String nuevoValor) {
    	try {
            String query = "UPDATE Clientes SET " + campo + " = ? WHERE id_cliente = ?;";

            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(query);

            pstmt.setString(1, nuevoValor);
            pstmt.setInt(2, id_cliente);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente modificado correctamente.");
            } else {
                System.out.println("No se ha podido modificar el cliente.");
            }

            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al modificar el cliente: " + e.getMessage());
        }
    }
    
    
    public void eliminarCliente(int id_cliente) {
		try {
            // Consulta SQL para eliminar el cliente
            String query = "DELETE FROM Clientes WHERE id_cliente = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido del cliente a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_cliente);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar el cliente.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }

    }

}
