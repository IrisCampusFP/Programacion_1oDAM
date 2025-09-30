package Vista;
import java.util.Scanner;
import Modelo.Cliente;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaCliente {
	Scanner s = new Scanner(System.in);
	
	public HashMap<String, Object> solicitarDatosCliente() {
		HashMap<String, Object> datosCliente = new HashMap<>();
		
		// Solicito al usuario los datos del nuevo cliente
    	System.out.println("\nINTRODUCE LOS DATOS DEL CLIENTE:");
		
		System.out.print("Nombre: ");
		String nombre = s.nextLine();
		
		System.out.print("Email: ");
		String email = s.nextLine();

		System.out.print("Teléfono: ");
		String telefono = s.nextLine();
		
		datosCliente.put("nombre", nombre);
		datosCliente.put("email", email);
		datosCliente.put("telefono", telefono);
		
		return datosCliente;
	}
	
	public void mostrarClientes(ArrayList<Cliente> listaClientes) {
		 // Recorro la lista de clientes y muestro todos los datos de cada una de ellas usando printf.
        for (Cliente c : listaClientes) {
            System.out.printf("ID: %d, Nombre: %s, Email: %s, Teléfono: %s\n",
                c.getId_cliente(),
                c.getNombre(),
                c.getEmail(),
                c.getTelefono());
        }
	}
	
	public int preguntarClienteAEliminar() {
    	// Solicito al usuario la clave primaria del cliente a eliminar
    	System.out.print("Introduce el ID del cliente a eliminar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. INTRODUCE UN NUMERO: ");
    	    s.next();
    	}
    	int id_cliente = s.nextInt();
    	s.nextLine();
		return id_cliente;
	}
	
	public int preguntarClienteAModificar() {
    	// Solicito al usuario la clave primaria del cliente a modificar
    	System.out.print("Introduce el ID del cliente a modificar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. INTRODUCE UN NUMERO: ");
    	    s.next();
    	}
    	int id_cliente = s.nextInt();
    	s.nextLine();
		return id_cliente;
	}
	
	public void mostrarDatos(Cliente cliente) {
		System.out.printf("ID: %d, Nombre: %s, Email: %s, Teléfono: %s\n",
			cliente.getId_cliente(),
			cliente.getNombre(),
			cliente.getEmail(),
			cliente.getTelefono());
	}
	
	public int preguntarCampoAModificar() {
		System.out.print("¿Qué campo quieres modificar? (1- Nombre, 2- Email, 3- Teléfono, 4- Volver): ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. INTRODUCE UN NUMERO: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public String solicitarNombre() {
		s.nextLine();
		System.out.print("Introduce el nuevo nombre: ");
		return s.nextLine();
	}
	
	public String solicitarEmail() {
		s.nextLine();
		System.out.print("Introduce el nuevo email: ");
		return s.nextLine();
	}
	
	public String solicitarTelefono() {
		s.nextLine();
		System.out.print("Introduce el nuevo teléfono: ");
		return s.nextLine();
	}
}
