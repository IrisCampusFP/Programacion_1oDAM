package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Venta {
	private int id_venta;
	private String fecha_venta;
	private int cantidad;
	private Cliente cliente;
	private Articulo articulo;

	
	public Venta(int id_venta, String fecha_venta, int cantidad, Cliente cliente, Articulo articulo) {
		this.setId_venta(id_venta);
		this.setFecha_venta(fecha_venta);
		this.setCantidad(cantidad);
		this.setCliente(cliente);
		this.setArticulo(articulo);
	}
	
	public int getId_venta() {
		return id_venta;
	}

	public void setId_venta(int id_venta) {
		this.id_venta = id_venta;
	}

	public String getFecha_venta() {
		return fecha_venta;
	}

	public void setFecha_venta(String fecha_venta) {
		this.fecha_venta = fecha_venta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	
	
	public void agregarVenta(HashMap<String, Object> datosVenta) {
    	
		try {
            // Consulta SQL para insertar el nuevo venta en la base de datos
            String query = "INSERT INTO Ventas (fecha_venta, cantidad, id_cliente, id_articulo) VALUES (?, ?, ?, ?);";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con los parámetros de la nueva venta
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, (String) datosVenta.get("fecha_venta"));
            pstmt.setInt(2, (int) datosVenta.get("cantidad"));
            pstmt.setInt(3, (int) datosVenta.get("id_cliente"));
            pstmt.setInt(4, (int) datosVenta.get("id_articulo"));

            // Ejecuto la consulta en la base de datos, guardando en una variable las filas afectadas
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario si la consulta se ha ejecutado correctamente o ha habido un error)
            if (rowsAffected > 0) {
                System.out.println("Venta añadida correctamente.");
            } else {
                System.out.println("Error al añadir la venta a la base de datos.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al añadir la venta: " + e.getMessage());
        }
    }
    
    
    public ArrayList<Venta> obtenerVentas() {
    	// Creo el ArrayList listaVentas para almacenar los ventas que se obtengan de la base de datos
    	ArrayList<Venta> listaVentas = new ArrayList<>();

        try {
        	// Elaboro la consulta a la base de datos y la guardo en la variable query.
        	String query = "SELECT * FROM Ventas;";
        	
            Connection conexion = Conexion.obtenerConexion(); // Obtengo la conexión con la base de datos llamando a la clase Conexion.
            Statement stmt = conexion.createStatement(); // Creo el Statement para poder hacer la consulta.
            ResultSet rs = stmt.executeQuery(query); // Ejecuto la consulta (llamando a la variable query).

            // Para cada resultado de la consulta, obtengo todos los datos de la venta (id_venta, fecha_venta, cantidad, id_cliente).
            while (rs.next()) {
                int id_venta = rs.getInt("id_venta");
                String fecha_venta = rs.getString("fecha_venta");
                int cantidad = rs.getInt("cantidad");
                int id_cliente = rs.getInt("id_cliente");
                int id_articulo = rs.getInt("id_articulo");

                /* Instancio un objeto cliente con el cliente de la base de datos 
                 * que tenga un id correspondiente al que se introduce. */
                Cliente cliente = new Cliente(id_cliente);
                // Lo mismo para el artículo
                Articulo articulo = new Articulo(id_articulo);
                // Instancio un objeto venta con todos los datos obtenidos
                Venta venta = new Venta(id_venta, fecha_venta, cantidad, cliente, articulo);
                listaVentas.add(venta); // Añado la venta a la lista
            }
            
            // Cierro el ResultSet, el Statement y la Conexión.
            rs.close();
            stmt.close();
            conexion.close();
            
        // Controlo posibles errores SQL al ejecutar la consulta
        } catch (SQLException e) {
            System.out.println("Error al obtener los ventas: " + e.getMessage());
        }
        
        return listaVentas; // Devuelvo la lista de ventas con los resultados obtenidos de la base de datos.
    }
    
    
    // Método para comprobar si existe un venta registrado con la clave primaria recibida
    public static boolean existeVenta(int id_venta) {
        String query = "SELECT * FROM Ventas WHERE id_venta = ?";
        try {
        	Connection conexion = Conexion.obtenerConexion();
        	// Preparo la consulta con la query
            PreparedStatement pstmt = conexion.prepareStatement(query);
            // Establezco el parámetro id_venta con el id recibido
            pstmt.setInt(1, id_venta);
            // Ejecuto la sentencia
            ResultSet rs = pstmt.executeQuery();
            // Devuelvo un booleano según el resultado
            return rs.next(); // Si hay resultado, devolverá true (la venta existe)
        } catch (SQLException e) {
            System.out.println("Error al comprobar la existencia de la venta en la base de datos: " + e.getMessage());
            return false;
        }
    }
    
    
    public Venta obtenerDatos(int id_venta) {
    	// Inicializo la variable venta en la que instaciaré un objeto con los datos obtenidos
		Venta venta = null;
    	try {
            // Consulta SQL para obtener los datos de la venta con ese id
            String query = "SELECT * FROM Ventas WHERE id_venta = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();

            // Preparo la consulta con el ID de la venta
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_venta);

            // Ejecuto la consulta
            ResultSet rs = pstmt.executeQuery();

            // Si la venta existe, creo un objeto Venta con sus datos
            if (rs.next()) {
                String fecha_venta = rs.getString("fecha_venta");
                int cantidad = rs.getInt("cantidad");
                int id_cliente = rs.getInt("id_cliente");
                int id_articulo = rs.getInt("id_articulo");
                Cliente cliente = new Cliente(id_cliente);
                Articulo articulo = new Articulo(id_articulo);
                venta = new Venta(id_venta, fecha_venta, cantidad, cliente, articulo);
            } else {
                System.out.println("No se ha encontrado ningún venta con ese id.");
            }

            // Cierro recursos
            rs.close();
            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la venta: " + e.getMessage());
        }

        return venta; // Devuelvo un objeto venta con los datos
    }
    
    
    public void modificarVenta(int id_venta, String campo, Object nuevoValor) {
    	try {
            String query = "UPDATE Ventas SET " + campo + " = ? WHERE id_venta = ?;";

            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(query);

            pstmt.setObject(1, nuevoValor);
            pstmt.setInt(2, id_venta);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Venta modificada correctamente.");
            } else {
                System.out.println("No se ha podido modificar la venta.");
            }

            pstmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al modificar la venta: " + e.getMessage());
        }
    }
    
    
    public void eliminarVenta(int id_venta) {
		try {
            // Consulta SQL para eliminar la venta
            String query = "DELETE FROM Ventas WHERE id_venta = ?;";

            // Establezco la conexión con la base de datos
            Connection conexion = Conexion.obtenerConexion();
            
            // Preparo la consulta con el ID introducido de la venta a eliminar
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id_venta);
            
            // Ejecuto la consulta
            int rowsAffected = pstmt.executeUpdate();
            
            // Si hay filas afectadas, la consulta se ha ejecutado correctamente
            // (Indico al usuario el mensaje correspondiente)
          	if (rowsAffected > 0) {
                System.out.println("Venta eliminada correctamente.");
            } else {
                System.out.println("Error al eliminar la venta.");
            }

            // Cierro el PreparedStatement y la conexión
            pstmt.close();
            conexion.close();
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
        }
    }
    
    
    public ArrayList<HashMap<String, Object>> obtenerInformeVenCli() {
        ArrayList<HashMap<String, Object>> informe = new ArrayList<>();

        try {
            String query = """
            SELECT c.id_cliente, c.nombre AS cliente,
                   a.nombre AS articulo,
                   v.cantidad, v.fecha_venta,
                   (v.cantidad * a.precio_unitario) AS total
            FROM Ventas v
            JOIN Clientes c ON v.id_cliente = c.id_cliente
            JOIN Articulos a ON v.id_articulo = a.id_articulo
            ORDER BY c.id_cliente, v.fecha_venta;
            """;

            Connection conexion = Conexion.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                HashMap<String, Object> fila = new HashMap<>();
                fila.put("id_cliente", rs.getInt("id_cliente"));
                fila.put("cliente", rs.getString("cliente"));
                fila.put("articulo", rs.getString("articulo"));
                fila.put("cantidad", rs.getInt("cantidad"));
                fila.put("fecha", rs.getString("fecha_venta"));
                fila.put("total", rs.getDouble("total"));
                informe.add(fila);
            }

            rs.close();
            stmt.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener el informe: " + e.getMessage());
        }

        return informe;
    }
   
}
