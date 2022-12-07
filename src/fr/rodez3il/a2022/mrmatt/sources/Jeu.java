package fr.rodez3il.a2022.mrmatt.sources;

import java.io.IOException;

public class Jeu {

	/**
	 * Fonction qui lit un caractère au clavier et le transforme en une commande
	 * interprétable (haut, gauche, bas, droite, annuler ou quitter).
	 * 
	 * @return la commande finale
	 * 
	 * @author proussille
	 */
	public static Commande lireCommande() {
		Commande valeur = Commande.ERREUR;
		char chr = Utils.lireCaractere();
		switch (chr) {
		case '8':
			valeur = Commande.HAUT;
			break;
		case '4':
			valeur = Commande.GAUCHE;
			break;
		case '6':
			valeur = Commande.DROITE;
			break;
		case '2':
			valeur = Commande.BAS;
			break;
		case 'a':
			valeur = Commande.ANNULER;
			break;
		case 'q':
			valeur = Commande.QUITTER;
			break;
		default:
			System.err.println("Veuillez entrer une commande valide !");
		}
		return valeur;
	}

	/**
	 * Point d'entrée principal du programme.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// Le chemin du plateau à charger s'il n'y a aucun argument
		String chemin = "niveaux/StartHere/1-french-garden.txt";
		// vous pouvez changer ici le nom de fichier si vous le souhaitez.
		if (args.length > 0) {
			chemin = args[0];
		}
		// Création du plateau depuis le chemin
		Niveau plateau = new Niveau(chemin);
		System.out.println("Bienvenue dans Mr Matt version ASCII !");
		System.out.println("Commandes :");
		System.out.println("- 8, 4, 6, 2 : pour vous déplacer dans la direction souhaitée");
		System.out.println("- q : pour quitter");
		System.out.println("- 0 : pour annuler le dernier déplacement");
		// Boucle d'attente de jeu
		while (plateau.enCours()) {
			// Affiche le plateau et récupère la commande du joueur
			plateau.afficher();
			System.out.println("Entrez une commande :");
			Commande c = lireCommande();
			if (plateau.jouer(c)) {
				// Cette condition ne s'exécute que
				// si la commande du joueur modifie
				// l'état du plateau.
				plateau.etatSuivant();
				while (plateau.estIntermediaire()) {
					// Tant qu'il y a un état intermédiaire,
					// on fait la mise à jour nécessaire.
					plateau.afficher();
					// Petite attente pour animation
					Utils.attendre(500);
					plateau.etatSuivant();
				}
			}
		}
		plateau.afficher();
		plateau.afficherEtatFinal();
	}

}
