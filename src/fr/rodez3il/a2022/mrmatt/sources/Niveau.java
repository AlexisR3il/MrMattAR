package fr.rodez3il.a2022.mrmatt.sources;
import fr.rodez3il.a2022.mrmatt.sources.objets.*;
import fr.rodez3il.a2022.mrmatt.sources.objets.ObjetPlateau;

public class Niveau {

	// Les objets sur le plateau du niveau
	private ObjetPlateau[][] plateau;
	// Position du joueur
	private int joueurX;
	private int joueurY;
	private boolean perdu;
	private boolean gagne;
	private int nbPommes;
	private int compteurDeplacement;


	/**
	 * Constructeur public : crée un niveau depuis un fichier.
	 * @param chemin .....
	 * @author .............
	 */
	public Niveau(String chemin) {
		String map = Utils.lireFichier(chemin);
		String[] ligneMap = map.split("\n");
		int nbColonnes = Integer.valueOf(ligneMap[0]);
		int nbLignes = Integer.valueOf(ligneMap[1]);
		plateau = new ObjetPlateau[nbColonnes][nbLignes];

		for(int numeroLigne = 2; numeroLigne < ligneMap.length; ++numeroLigne) {
			for(int numeroColonne = 0; numeroColonne < ligneMap.length; ++numeroLigne) {
				ObjetPlateau objet = ObjetPlateau.depuisCaractere(ligneMap[numeroLigne].charAt(numeroColonne));
				plateau[numeroLigne][numeroColonne] = objet;

				if (objet.afficher() == 'H') {
					joueurX = numeroColonne;
					joueurY = numeroLigne;
				}

				if (objet.afficher() == '+') {
					++nbPommes;
				}

				plateau[numeroColonne++][numeroLigne] = objet;
			}
		}
	}

	/**
	 * Méthode echanger qui permet d'échanger l'objet en position (sourceX, sourceY)
	 * avec celui en position (destinationX, destinationY).
	 */
	private void echanger(int sourceX, int sourceY, int destinationX, int destinationY) {
		ObjetPlateau unObjetPlateau = plateau[sourceX][sourceY];
		plateau[sourceX][sourceY] = plateau[destinationX][destinationY];
		plateau[destinationX][destinationY] = unObjetPlateau;
	}

	/**
	 * Produit une sortie du niveau sur la sortie standard.
	 * ................
	 */
	public void afficher() {
		for(int numeroLigne = 2; numeroLigne < plateau.length; ++numeroLigne) {
			for (int numeroColonne = 0; numeroColonne < plateau.length; ++numeroColonne) {
				System.out.print(plateau[numeroColonne][numeroLigne].afficher());
			}
			System.out.print("\n");
		}
		System.out.println("Vous avez effectué " + compteurDeplacement + "déplacements");
		System.out.println("Il reste " + nbPommes + " Pommes");

	}

	// TODO : patron visiteur du Rocher...
	public void etatSuivantVisiteur(Rocher r, int x, int y) {
	}

	public void etatSuivantVisiteur(Pomme r, int x, int y) {
		++nbPommes;
	}

	/**
	 * Calcule l'état suivant du niveau.
	 * ........
	 * @author
	 */
	public void etatSuivant() {
		// TODO
	}


	// Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

	public boolean enCours() {
		return false;
	}

	// Joue la commande C passée en paramètres
	public boolean jouer(Commande c) {
		return false;
	}

	/**
	 * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
	 */
	public void afficherEtatFinal() {
		if (gagne) {
			System.out.println("C'est Gagné !");
		} else if (perdu) {
			System.out.println("C'est Perdu !");
		}
	}

	/**
	 */
	public boolean estIntermediaire() {
		return false;
	}

}