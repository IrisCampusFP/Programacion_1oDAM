package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Factura {
	private int id_factura;
	private Proveedor proveedor;
	private String fecha;
	private double total;
	
	public Factura(int id_factura, String fecha, double total, Proveedor proveedor) {
		this.setId_factura(id_factura);
		this.setFecha(fecha);
		this.setTotal(total);
		this.setProveedor(proveedor);
	}
	
	public int getId_factura() {
		return id_factura;
	}


	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public Proveedor getProveedor() {
		return proveedor;
	}


	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	
	public void agregarFactura(HashMap<String, Object> datosFactura) {
    	
		try {
            // Consulta SQL para insertar la nueva factura en la base de datos
            String query = "INSERT INTO Facturas_Recibidas (fecha, total, id_proveedor) VALUES (?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros de la nueva factura
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, (String) datosFactura.get("fecha"));
            pstmt.setDouble(2, (double) datosFactura.get("total"));
            pstmt.setInt(3, (int) datosFactura.get("id_proveedor"));

            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Factura añadida correctamente.");
            } else {
                System.out.println("Error al añadir la factura a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir la factura: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Factura> obtenerFacturas() {
    	// Creo el ArrayList listaFacturas para almacenar las facturas que se obtengan de la base de datos
    	ArrayList<Factura> listaFacturas = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT * FROM Facturas_Recibidas;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos los datos de la factura (id_factura, fecha, total, id_proveedor).
            while (rs.next()) {
                int id_factura = rs.getInt("id_factura");
                String fecha = rs.getString("fecha");
                double total = rs.getDouble("total");
                int id_proveedor = rs.getInt("id_proveedor");

                /* Instancio un objeto proveedor con el proveedor de la base de datos 
                 * que tenga un id correspondiente al que se introduce. */
                Proveedor proveedor = new Proveedor(id_proveedor);
                // Instancio un objeto factura con todos los datos obtenidos
                Factura factura = new Factura(id_factura, fecha, total, proveedor);
                listaFacturas.add(factura); // Añado la factura a la lista
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener las facturas: " + e.getMessage());
        }
        
        return listaFacturas; // Devuelvo la lista de facturas con los resultados obtenidos de la base de datos.
    }
    
    
    // Método para comprobar si existe una factura registrada con la clave primaria recibida
    public static boolean existeFactura(int id_factura) {
        String query = "SELECT * FROM Facturas_Recibidas WHERE id_factura = ?";
        try {
        	Connection conexion = Conexion.obtenerConexion();
        	// Preparo la consulta con la query
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establezco el parámetro id_factura con el id recibido
            pstmt.setInt(1, id_factura);
            // Ejecuto la sentencia
            ResultSet rs = pstmt.executeQuery();
            // Devuelvo un booleano según el resultado
            return rs.next(); // Si hay resultado, devolverá true (la factura existe)
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia de la factura en la base de datos: " + e.getMessage());
            return false;
        }
    }
    
    
    public Factura obtenerDatos(int id_factura) {
    	// Inicializo la variable factura en la que instaciaré un objeto con los datos obtenidos
		Factura factura = null;
    	try {
            // Consulta SQL para obtener los datos de la factura con ese id
            String query = "SELECT * FROM Facturas_Recibidas WHERE id_factura = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();

            // Preparo la consulta con el ID de la factura
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_factura);

            // Ejecuto la consulta
            ResultSet rs = pstmt.executeQuery();

            // Si la factura existe, creo un objeto Factura con sus datos
            if (rs.next()) {
                String fecha = rs.getString("fecha");
                double total = rs.getDouble("total");
                int id_proveedor = rs.getInt("id_proveedor");
                Proveedor proveedor = new Proveedor(id_proveedor);
                factura = new Factura(id_factura, fecha, total, proveedor);
            } else {
                System.out.println("No se ha encontrado ninguna factura con ese id.");
            }

            // Cierro recursos
            rs.close();
            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la factura: " + e.getMessage());
        }

        return factura; // Devuelvo un objeto factura con los datos
    }
    
    
    public void modificarFactura(int id_factura, String campo, Object nuevoValor) {
    	try {
            String query = "UPDATE Facturas_Recibidas SET " + campo + " = ? WHERE id_factura = ?;";

            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(query);

            pstmt.setObject(1, nuevoValor);
            pstmt.setInt(2, id_factura);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Factura modificada correctamente.");
            } else {
                System.out.println("No se ha podido modificar la factura.");
            }

            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al modificar la factura: " + e.getMessage());
        }
    }
    
    
    public void eliminarFactura(int id_factura) {
		try {
            // Consulta SQL para eliminar la factura
            String query = "DELETE FROM Facturas_Recibidas WHERE id_factura = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido de la factura a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_factura);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Factura eliminada correctamente.");
            } else {
                System.out.println("Error al eliminar la factura.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la factura: " + e.getMessage());
        }

    }
    
}
