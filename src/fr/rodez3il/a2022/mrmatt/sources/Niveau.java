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

	/**
	 * Constructeur public : crée un niveau depuis un fichier.
	 * @param chemin
	 * @author AlexisR
	 */
	public Niveau(String chemin) {
		this.chargerNiveau(chemin);
	}

	/** Methode chargerNiveau appelée par le constructeur */
	private void chargerNiveau(String chemin) {
		String map = Utils.lireFichier(chemin); /** Fonction lire fichier qui renvoie le contenu d'un fichier sous forme de chaine de caractere complète. */
		String[] ligneMap = map.split("\n");
		nbColonnes = Integer.valueOf(ligneMap[0].trim()); /** variable nbColonnes qui stock le nombre de colonne du niveau */
		nbLignes = Integer.valueOf(ligneMap[1].trim()); /** variable nbLignes qui stock le nombre de ligne du niveau */
		System.out.println(nbColonnes);
		plateau = new ObjetPlateau[nbColonnes][nbLignes];
		String[] tableauFinal = new String[nbLignes];

		/** Permet d'enlever la ligne 1 et 2 du niveau */
		int compteur =0, n=0;
		for(String charactGame : ligneMap){
			if (compteur>1 && !charactGame.startsWith(" ")){
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

	/** patron visiteur Rocher */
	public void etatSuivantVisiteur(Rocher r, int x, int y) {
		if (r.getEtatRocher() == EtatRocher.Chute) {
			if (x + 1 == this.plateau[0].length - 1) {
				r.setEtatRocher(EtatRocher.Immobile);
			}
			else if (this.plateau[x+1][y].estVide()) {
				this.echanger(x, y, x+1, y);
			}
			else if (this.joueurX == x+1 && this.joueurY == y) {
				this.perdu = true;
			}
			else if (this.plateau[x+1][y].estGlissant()) {
				if (this.plateau[x][y-1].estVide() && this.plateau[x+1][y-1].estVide()) {
					this.echanger(x, y, x+1, y -1);
				}
				else if (this.plateau[x][y+1].estVide() && this.plateau[x+1][y+1].estVide()) {
					this.echanger(x, y, x+1, y+1);
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
			if(x+1 < this.plateau.length && this.plateau[x+1][y].estVide()) {
				r.setEtatRocher(EtatRocher.Chute);
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
	 * Calcule l'état suivant du niveau.
	 * ........
	 * @author
	 */
	public void etatSuivant() {
		for (int numeroLigne = 2; numeroLigne < plateau.length; ++numeroLigne) {
			for (int numeroColonne = 0; numeroColonne < plateau.length; ++numeroColonne) {
				this.plateau[numeroLigne][numeroColonne].visiterPlateauCalculEtatSuivant(this, numeroLigne, numeroColonne);
			}
		}
	}

	// Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

	/** A faire */
	public boolean enCours() {
		return false;
	}

	// Joue la commande C passée en paramètres
	public boolean jouer(Commande c) {
		switch(c) {
			case HAUT:
				if(deplacementPossible(this.joueurX - 1, this.joueurY)) {
					this.deplacer(this.joueurX - 1, this.joueurY);
					this.compteurDeplacement++;
				}
				break;
			case BAS:
				if(deplacementPossible(this.joueurX + 1, this.joueurY)) {
					this.deplacer(this.joueurX + 1, this.joueurY);
					this.compteurDeplacement++;
				}
				break;
			case GAUCHE:
				if(deplacementPossible(this.joueurX, this.joueurY - 1)) {
					this.deplacer(this.joueurX, this.joueurY - 1);
					this.compteurDeplacement++;
				}
				break;
			case DROITE:
				if(deplacementPossible(this.joueurX, this.joueurY + 1)) {
					this.deplacer(this.joueurX, this.joueurY + 1);
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
		return false;
	}

	private void deplacer(int deltaX, int deltaY) {
		if (deltaY == 0 && plateau[joueurY][joueurX + deltaX].estPoussable()) {
			this.echanger(joueurX + deltaX, joueurY, joueurX + (2 * deltaX), joueurY);
			this.echanger(joueurX, joueurY, joueurX + deltaX, joueurY);
		} else {
			this.echanger(joueurX, joueurY, joueurX + deltaX, joueurY + deltaY);
			plateau[joueurY][joueurX] = new Vide();
		}
		joueurX += deltaX;
		joueurY += deltaY;
	}

	/** A faire */
	public boolean deplacementPossible(int joueurX, int joueurY) {
		return false;
	}
}