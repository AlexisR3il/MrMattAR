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

	private int nbColonnes;

	private int nbLignes;

	private boolean caTombe = false;

	/**
	 * Constructeur public : crée un niveau depuis un fichier.
	 * @param chemin
	 * @author AlexisR
	 */
	public Niveau(String chemin) {
		this.chargerNiveau(chemin);
	}

	/**
	 * Methode chargerNiveau appelée par le constructeur
	 * @param chemin
	 * @author AlexisR
	 */
	private void chargerNiveau(String chemin) {
		String map = Utils.lireFichier(chemin); /** Fonction lire fichier qui renvoie le contenu d'un fichier sous forme de chaine de caractere complète. */
		String[] ligneMap = map.split("\n");
		nbColonnes = Integer.valueOf(ligneMap[0].trim()); /** variable nbColonnes qui stock le nombre de colonne du niveau */
		nbLignes = Integer.valueOf(ligneMap[1].trim()); /** variable nbLignes qui stock le nombre de ligne du niveau */
		System.out.println(nbColonnes + " " + nbLignes);
		plateau = new ObjetPlateau[nbColonnes][nbLignes];
		String[] tableauFinal = new String[nbLignes];

		/** Permet d'enlever la ligne 1 et 2 du niveau */
		int compteur = 0, n = 0;
		for(String charactGame : ligneMap){
			if (compteur > 1 && !charactGame.startsWith(" ")){
				tableauFinal[n]=charactGame;
				n++;
			}
			compteur++;
		}

		for(int numeroLigne = 0; numeroLigne < nbLignes; ++numeroLigne) { /** On parcourt la map et on crée un objet pour chaque élément rencontré */
			for(int numeroColonne = 0; numeroColonne < nbColonnes; ++numeroColonne) {
				ObjetPlateau objet = ObjetPlateau.depuisCaractere(tableauFinal[numeroLigne].charAt(numeroColonne));
				plateau[numeroColonne][numeroLigne] = objet;

				if (objet.afficher() == 'H') { /** On rentre les coordonnées du joueur dans les variables joueurX et joueur Y */
					joueurX = numeroColonne;
					joueurY = numeroLigne;
				}

				if (objet.afficher() == '+') { /** Incrementation du compteur de pommes */
					++nbPommes;
				}
			}
		}
	}


	/**
	 * Méthode echanger qui permet d'échanger l'objet en position (sourceX, sourceY)
	 * avec celui en position (destinationX, destinationY).
	 * @param sourceX
	 * @param sourceY
	 * @param destinationX
	 * @param destinationY
	 * @author AlexisR
	 */
	private void echanger(int sourceX, int sourceY, int destinationX, int destinationY) {
		ObjetPlateau unObjetPlateau = plateau[sourceX][sourceY];
		plateau[sourceX][sourceY] = plateau[destinationX][destinationY];
		plateau[destinationX][destinationY] = unObjetPlateau;
	}

	/**
	 * Produit une sortie du niveau sur la sortie standard.
	 * Affiche le niveau sous forme ASCII, mais aussi le nombre de déplacements totaux du joueur
	 * et le nombre de pommes restantes à collecter.
	 * @author AlexisR
	 */
	public void afficher() {
		for(int numeroLigne = 0; numeroLigne < nbLignes; ++numeroLigne) { /** On parcourt la map et on crée un objet pour chaque élément rencontré */
			for(int numeroColonne = 0; numeroColonne < nbColonnes; ++numeroColonne) {
				System.out.print(plateau[numeroColonne][numeroLigne].afficher());
			}
			System.out.print("\n");
		}
		System.out.println("Vous avez effectué " + compteurDeplacement + " déplacements" + "\n" + "Il reste " + nbPommes + " Pommes");

	}

	/**
	 * patron visiteur Rocher, permet d'utiliser les methodes set et get etatRocher de la classe Rocher
	 * en fonction des changements au sein du niveau, et par la même occasion savoir si un joueur se
	 * fait écraser par un rocher.
	 * @param r
	 * @param x
	 * @param y
	 * @author AlexisR
	 */
	public void etatSuivantVisiteur(Rocher r, int x, int y) {
		if (r.getEtatRocher() == EtatRocher.Chute) {
			caTombe = true;
			if (x + 1 == this.plateau[0].length - 1) {
				r.setEtatRocher(EtatRocher.Immobile);
			}
			else if (this.plateau[x + 1][y].estVide()) {
				this.echanger(x, y, x + 1, y);
			}
			else if (this.joueurX == x + 1 && this.joueurY == y) {
				this.perdu = true;
			}
			else if (this.plateau[x + 1][y].estGlissant()) {
				if (this.plateau[x][y - 1].estVide() && this.plateau[x + 1][y - 1].estVide()) {
					this.echanger(x, y, x + 1, y - 1);
				}
				else if (this.plateau[x][y+ 1].estVide() && this.plateau[x + 1][y + 1].estVide()) {
					this.echanger(x, y, x + 1, y + 1);
				}
				else {
					r.setEtatRocher(EtatRocher.Immobile);
				}
			}
			else {
				r.setEtatRocher(EtatRocher.Immobile);
			}
		}
		else if(r.getEtatRocher() == EtatRocher.Immobile) {
			if(x + 1 < this.nbLignes && this.plateau[x + 1][y].estVide()) {
				r.setEtatRocher(EtatRocher.Chute);
				caTombe = true;
			}
		}
		else if (r.getEtatRocher() == EtatRocher.Chute) {
			/*this.intermediaire = true;*/
		}
	}

	public void etatSuivantVisiteur(Pomme r, int x, int y) {
		++nbPommes;
	}

	/**
	 * Calcule l'état suivant du niveau en parcourant toute les cases de ce dernier.
	 * @AlexisR
	 */
	public void etatSuivant() {
		caTombe = false;
		for (int numeroLigne = 0; numeroLigne < nbLignes-1; ++numeroLigne) {
			for (int numeroColonne = 0; numeroColonne < nbColonnes-1; ++numeroColonne) {
				this.plateau[numeroColonne][numeroLigne].visiterPlateauCalculEtatSuivant(this, numeroColonne, numeroLigne);
			}
		}
	}

	// Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

	/**
	 * Méthode permettant de savoir si la partie continue ou quand elle s'arrête.
	 * @author AlexisR
	 */
	public boolean enCours() {
		if(this.perdu) {
			return false;
		}
		else if(this.nbPommes == 0) {
			return false;
		}
		return true;
	}

	/**Methode jouer qui utilise l'énumération Commande pour savoir quelle action le
	 * joueur veut effectuer. On utilise la méthode deplacementPossible pour savoir si
	 * la commandre saisie par le joueur peut se réaliser, puis la méthode déplacer pour
	 * réaliser l'action demander si c'est une action de déplacement.
	 * @param c
	 * @author AlexisR
	 */
	public boolean jouer(Commande c) {
		switch(c) {
			case HAUT:
				if(deplacementPossible(this.joueurX, this.joueurY - 1)) {
					this.deplacer(this.joueurX, this.joueurY - 1);
					this.compteurDeplacement++;
				}
				break;
			case BAS:
				if(deplacementPossible(this.joueurX, this.joueurY + 1)) {
					this.deplacer(this.joueurX, this.joueurY + 1);
					this.compteurDeplacement++;
				}
				break;
			case GAUCHE:
				if(deplacementPossible(this.joueurX - 1, this.joueurY)) {
					this.deplacer(this.joueurX - 1, this.joueurY);
					this.compteurDeplacement++;
				}
				break;
			case DROITE:
				if(deplacementPossible(this.joueurX + 1, this.joueurY)) {
					this.deplacer(this.joueurX + 1, this.joueurY);
					this.compteurDeplacement++;
				}
				break;
			case QUITTER:
				this.perdu = true;
				break;

			case ANNULER:
				// A faire
				break;
		}

		return true;
	}

	/**
	 * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
	 * @author AlexisR
	 */
	public void afficherEtatFinal() {
		if (this.gagne) {
			System.out.println("Bravo c'est Gagné !");
		} else if (this.perdu) {
			System.out.println("Dommage c'est Perdu !");
		}
	}

	/**
	 * A faire
	 */
	public boolean estIntermediaire() {
		/*if (caTombe){
			return true;
		}*/
		return false;
	}

	/**
	 * Methode deplacer qui va permettre au joueur de changer sa position avec celle qu'il désire
	 * (une case au dessus, en dessous, à droite ou à gauche). Cette méthode inclue aussi la possibilité
	 * de pousser un rocher à condition que c'est bien un déplacement horizontal et si l'environnement le permet
	 * @param deltaX
	 * @param deltaY
	 * @author AlexisR
	 */
	private void deplacer(int deltaX, int deltaY) {
			this.echanger(joueurX, joueurY, deltaX, deltaY);
			plateau[joueurX][joueurY] = new Vide();
		joueurX = deltaX;
		joueurY = deltaY;
	}

	/**
	 * Methode pour savoir si la case où veut aller le joueur est bien dans le plateau
	 * et est marchable.
	 * @param joueurX
	 * @param joueurY
	 * @author AlexisR
	 */
	public boolean deplacementPossible(int joueurX, int joueurY) {
		if (joueurX >= 0 && joueurY >= 0 && joueurX < nbLignes - 1 && joueurY < nbColonnes - 1) {
			if (plateau[joueurX][joueurY].estMarchable()) {
				return true;
			}
		}
		return false;
	}
}