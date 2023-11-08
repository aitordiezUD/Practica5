package clases;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;

public class GestionTwitter {
	private static HashMap<String,UsuarioTwitter> mapaUsuariosId = new HashMap<String,UsuarioTwitter>();
	private static HashMap<String,UsuarioTwitter> mapaUsuariosUsername;
//	Para la creacion del set ordenado debo de implementar comparable en UsuarioTwitter
	private static TreeSet<UsuarioTwitter> setOrdenado;
	
	public static TreeSet<UsuarioTwitter> getSetOrdenado() {
		return setOrdenado;
	}

	public static void setSetOrdenado(TreeSet<UsuarioTwitter> setOrdenado) {
		GestionTwitter.setOrdenado = setOrdenado;
	}

	public static HashMap<String, UsuarioTwitter> getMapaUsuariosId() {
		return mapaUsuariosId;
	}

	public static void setMapaUsuariosId(HashMap<String, UsuarioTwitter> mapaUsuarios) {
		GestionTwitter.mapaUsuariosId = mapaUsuarios;
	}

	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			mapaUsuariosId = new HashMap<String,UsuarioTwitter>();
			String fileName = "data.csv";
			CSV.processCSV( new File( fileName ) );
			System.out.println("Numero de usuarios diferentes: " + mapaUsuariosId.keySet().size()); //No hay ID repetidos porque el tamaño del HashMap es igual al de filas de datos del Excel
			crearMapaUsuariosUsername();
			System.out.println("Numero de usuarios diferentes: " + mapaUsuariosUsername.keySet().size()); //No hay usernames repetidos porque el tamaño del HashMap es igual al de filas de datos del Excel
			imprimirUsuariosConAmigosEnSistema();
			crearSetOrdenado();
			imprimirSetOrdenado();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void crearMapaUsuariosUsername(){
		HashMap<String,UsuarioTwitter> mapa = new HashMap<String,UsuarioTwitter>();
		for (String id : mapaUsuariosId.keySet()) {
			mapa.put(mapaUsuariosId.get(id).getScreenName(), mapaUsuariosId.get(id));
		}
		
		mapaUsuariosUsername = mapa;
	};
	
	public static void imprimirUsuariosConAmigosEnSistema() {
		int cont = 0;
		ArrayList<String> listaAux = new ArrayList<String>(mapaUsuariosUsername.keySet());
		Collections.sort(listaAux);
		for (String username : listaAux) {
			UsuarioTwitter usuario = mapaUsuariosUsername.get(username);
			int usuariosEnSistema = contarAmigosEnSistema(usuario);
			if (usuariosEnSistema > 0) {
				System.out.println("Usuario " + username + " tiene " + (usuario.getFriendsCount()-usuariosEnSistema) + " amigos fuera de nuestro sistema y "
						+ usuariosEnSistema + " dentro.");
				cont++;
			}
		}
		System.out.println("Hay " + cont  + " usuarios con algunos amigos dentro de nuestro sistema");
	}
	
	public static int contarAmigosEnSistema(UsuarioTwitter usuario) {
		int contadorDentro = 0;
		for (String id : usuario.getFriends()) {
			if(!(mapaUsuariosId.get(id)==null)) {
				contadorDentro++;
			}
		};
		return contadorDentro;
	}
	
	public static void crearSetOrdenado() {
		TreeSet<UsuarioTwitter> set = new TreeSet<UsuarioTwitter>();
		for (String id : mapaUsuariosId.keySet()) {
			UsuarioTwitter usuario = mapaUsuariosId.get(id);
			if (contarAmigosEnSistema(usuario)>0) {
				set.add(usuario);
			}
		}
		GestionTwitter.setOrdenado = set;
	}
	
	public static void imprimirSetOrdenado() {
		for (UsuarioTwitter usuario : setOrdenado) {
			System.out.println(usuario.getScreenName() + " - " + contarAmigosEnSistema(usuario) + " amigos.");
		}
	}
	
}
