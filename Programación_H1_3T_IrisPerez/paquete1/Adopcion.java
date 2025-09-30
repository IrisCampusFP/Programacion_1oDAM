package paquete1;

public class Adopcion {
	String adoptante;
	String DNIadoptante;
	Animal animal;
	
	public Adopcion(String adoptante, String DNIadoptante, Animal animal) {
		this.adoptante = adoptante;
		this.DNIadoptante = DNIadoptante;
		this.animal = animal;
	}
	
	public void mostrarDatosAdopcion() {
		System.out.println("Nombre del adoptante: " + adoptante);
		System.out.println("DNI del adoptante: " + DNIadoptante);
		System.out.println("DATOS DEL ANIMAL:");
		animal.mostrar();
	}
}
