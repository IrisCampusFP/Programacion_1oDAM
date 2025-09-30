package Vista;
import java.util.Scanner;
import Modelo.Proveedor;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaProveedor {
	Scanner s = new Scanner(System.in);
	
	public HashMap<String, Object> solicitarDatosProveedor() {
		HashMap<String, Object> datosProveedor = new HashMap<>();
		
		// Solicito al usuario los datos del nuevo proveedor
    	System.out.println("\nINTRODUCE LOS DATOS DEL PROVEEDOR:");
		
		System.out.print("Nombre: ");
		String nombre = s.nextLine();
		
		System.out.print("Cif: ");
		String cif = s.nextLine();

		System.out.print("Teléfono: ");
		String telefono = s.nextLine();
		
		datosProveedor.put("nombre", nombre);
		datosProveedor.put("cif", cif);
		datosProveedor.put("telefono", telefono);
		
		return datosProveedor;
	}
	
	public void mostrarProveedores(ArrayList<Proveedor> listaProveedores) {
		 // Recorro la lista de proveedores y muestro todos los datos de cada una de ellas usando printf.
        for (Proveedor c : listaProveedores) {
            System.out.printf("ID: %d, Nombre: %s, Cif: %s, Teléfono: %s\n",
                c.getId_proveedor(),
                c.getNombre(),
                c.getCif(),
                c.getTelefono());
        }
	}
	
	public int preguntarProveedorAEliminar() {
    	// Solicito al usuario la clave primaria del proveedor a eliminar
    	System.out.print("Introduce el ID del proveedor a eliminar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
		int id_proveedor = s.nextInt();
		return id_proveedor;
	}
	
	public int preguntarProveedorAModificar() {
    	// Solicito al usuario la clave primaria del proveedor a modificar
    	System.out.print("Introduce el ID del proveedor a modificar: ");
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número: ");
    	    s.next();
    	}
		int id_proveedor = s.nextInt();
		return id_proveedor;
	}
	
	public void mostrarDatos(Proveedor proveedor) {
		System.out.printf("ID: %d, Nombre: %s, Cif: %s, Teléfono: %s\n",
			proveedor.getId_proveedor(),
			proveedor.getNombre(),
			proveedor.getCif(),
			proveedor.getTelefono());
	}
	
	public int preguntarCampoAModificar() {
		System.out.print("¿Qué campo quieres modificar? (1- Nombre, 2- Cif, 3- Teléfono, 4- Volver): ");
	    while (!s.hasNextInt()) {
	        System.out.print("ERROR. Introduce un número: ");
	        s.next();
	    }
	    return s.nextInt();	
	}
	
	public String solicitarNombre() {
		s.nextLine();
		System.out.print("Introduce el nuevo nombre: ");
		return s.nextLine();
	}
	
	public String solicitarCif() {
		s.nextLine();
		System.out.print("Introduce el nuevo cif: ");
		return s.nextLine();
	}
	
	public String solicitarTelefono() {
		s.nextLine();
		System.out.print("Introduce el nuevo teléfono: ");
		return s.nextLine();
	}
}
