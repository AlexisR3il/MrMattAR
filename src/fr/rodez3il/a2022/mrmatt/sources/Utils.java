package fr.rodez3il.a2022.mrmatt.sources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import fr.rodez3il.a2022.mrmatt.sources.objets.ObjetPlateau;


/**
 * Classe d'utilitaires pour le projet Mr. Matt
 * 
 * @author proussille
 *
 */
public class Utils {
	
	// Objet scanner partagé par toutes les méthodes statiques de la classe
	private static Scanner scanner;
	
	public static char lireCaractere() {
		char result = 0;
		// Assez laid, mais les exceptions n'ont pas encore été vues.
		if(scanner.hasNext()) {
			result = scanner.next().charAt(0);
		}
		return result;
	}
	
	/**
	 * Lit un fichier, et le renvoie sous forme de chaîne de caractère.
	 * Renvoie "" en cas d'erreur.
	 * 
	 * @param fichier Le fichier à lire.
	 * @return le contenu du fichier sous forme de chaîne de caractères
	 * 
	 * @author proussille
	 */
	public static String lireFichier(String fichier) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fichier));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} catch (IOException e) {
			return "";
		} finally {
			// Fermeture du Reader
			try {
				reader.close();
			} catch (IOException e) {
				// Ne doit pas arriver
				e.printStackTrace();
			}
		}
	}

	/**
	 * Clone le tableau en 2D passé en paramètres.
	 * 
	 * @param src le tableau à cloner
	 * @return un clone du tableau passé en paramètre
	 * 
	 * @author proussille
	 */
	public static ObjetPlateau[][] cloneTableau(ObjetPlateau[][] src) {
		int longueur = src.length;
		ObjetPlateau[][] clone = new ObjetPlateau[longueur][src[0].length];
		for(int i=0; i<src.length; i++)
			  for(int j=0; j<src[i].length; j++)
			    clone[i][j]=src[i][j];
		return clone;
	}

	/**
	 * Attend une certaine durée en millisecondes.
	 * 
	 * @param msec	le temps à attendre
	 */
	public static void attendre(long msec) {
		try {
			TimeUnit.MILLISECONDS.sleep(msec);
		} catch (InterruptedException e) {
			// ne doit pas arriver
			e.printStackTrace();
		}
	}
	
	// Initialisation statique
	static {
		scanner = new Scanner(System.in);
	}
	
}
