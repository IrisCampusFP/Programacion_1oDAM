package paquete1;

public class Gato extends Animal {
	boolean testLeucemia;

	public Gato(String numeroChip, String nombre, int edad, String raza, boolean adoptado, boolean testLeucemia) {
		super(numeroChip, nombre, edad, raza, adoptado);
		this.testLeucemia = testLeucemia;
	}

	@Override
	public void mostrar() {
		System.out.println("DATOS DEL GATO");
		System.out.println("Número de chip: " + numeroChip);
		System.out.println("Nombre: " + nombre);
		System.out.println("Edad: " + edad);
		System.out.println("Raza: " + raza);
		System.out.println("¿Adoptado?: " + adoptado);
		System.out.println("Test de leucemia: " + testLeucemia);
	}

}
