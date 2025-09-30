package paquete1;

public class Perro extends Animal {
	String tamaño;

	public Perro(String numeroChip, String nombre, int edad, String raza, boolean adoptado, String tamaño) {
		super(numeroChip, nombre, edad, raza, adoptado);
		this.tamaño = tamaño;
	}
	
	@Override
	public void mostrar() {
		System.out.println("DATOS DEL PERRO");
		System.out.println("Número de chip: " + numeroChip);
		System.out.println("Nombre: " + nombre);
		System.out.println("Edad: " + edad);
		System.out.println("Raza: " + raza);
		System.out.println("¿Adoptado?: " + adoptado);
		System.out.println("Test de leucemia: " + tamaño);
	}

}
