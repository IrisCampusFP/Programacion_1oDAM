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
}
