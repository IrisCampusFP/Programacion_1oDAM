package paquete1;

public class Categoria {
	int idCategoria;
	String nombre;
	
	public Categoria(int idCategoria, String nombre) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
	}
	
	/* Creo un constructor que, recibiendo el id de la categoría
	 guarda automáticamente el nombre correspondiente. */
	public Categoria(int idCategoria) {
		this.idCategoria = idCategoria; // Guardo el id
		switch (idCategoria) { // Guardo el nombre correspondiente (según las categorías guardadas en la base de datos)
			case 1:
				this.nombre = "Acción";
				break;
			case 2:
				this.nombre = "Comedia";
				break;
			case 3:
				this.nombre = "Drama";
				break;
			case 4:
				this.nombre = "Terror";
				break;
			case 5:
				this.nombre = "Ciencia Ficción";
				break;
		}
	}
}
