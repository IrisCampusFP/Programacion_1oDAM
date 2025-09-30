package paquete1;

public class Pelicula {
	int idPelicula;
	String titulo;
	String director;
	int duracionMinutos;
	String idioma;
	int anoLanzamiento;
	Categoria categoria;
	
	public Pelicula(int idPelicula, String titulo, String director, int duracionMinutos, String idioma, int anoLanzamiento, Categoria categoria) {
		this.idPelicula = idPelicula;
		this.titulo = titulo;
		this.director = director;
		this.duracionMinutos = duracionMinutos;
		this.idioma = idioma;
		this.anoLanzamiento = anoLanzamiento;
		this.categoria = categoria;
	}
	
	public void mostrarDatos() {
		System.out.println("- ID: " + idPelicula);
		System.out.println("- Título: " + titulo);
		System.out.println("- Director: " + director);
		System.out.println("- Duración: " + duracionMinutos + " minutos");
		System.out.println("- Idioma: " + idioma);
		System.out.println("- Año de lanzamiento: " + anoLanzamiento);
		System.out.println("- Categoría: " + categoria);
	}
}
