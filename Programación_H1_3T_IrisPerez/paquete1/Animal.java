package paquete1;

public abstract class Animal {
	String numeroChip;
	String nombre;
	int edad;
	String raza;
	boolean adoptado;
	
	public Animal(String numeroChip, String nombre, int edad, String raza, boolean adoptado) {
		this.numeroChip = numeroChip;
		this.nombre = nombre;
		this.edad = edad;
		this.raza = raza;
		this.adoptado = adoptado;
	}
	
	public abstract void mostrar();
}
