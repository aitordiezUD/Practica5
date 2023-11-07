package clases;

import java.io.File;
import java.util.HashMap;

public class GestionTwitter {
	private static HashMap<String,UsuarioTwitter> mapaUsuarios = new HashMap<String,UsuarioTwitter>();
	
	public static HashMap<String, UsuarioTwitter> getMapaUsuarios() {
		return mapaUsuarios;
	}

	public static void setMapaUsuarios(HashMap<String, UsuarioTwitter> mapaUsuarios) {
		GestionTwitter.mapaUsuarios = mapaUsuarios;
	}

	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			mapaUsuarios = new HashMap<String,UsuarioTwitter>();
			String fileName = "data3.csv";
			CSV.processCSV( new File( fileName ) );
			System.out.println(mapaUsuarios);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
