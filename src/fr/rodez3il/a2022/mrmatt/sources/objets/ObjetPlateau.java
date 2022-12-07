package fr.rodez3il.a2022.mrmatt.sources.objets;

import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public abstract class ObjetPlateau {
	/**
	 * Fabrique des objets
	 * @param chr le symbole à produire
	 * @return la classe ObjetPlateau correspondante
	 */
	public static ObjetPlateau depuisCaractere(char chr) {
		ObjetPlateau nouveau = null;
		switch(chr) {
		case '-':
			nouveau = new Herbe();
			break;
		case '+':
			nouveau = new Pomme();
			break;
		case '*':
			nouveau = new Rocher();
			break;
		case ' ':
			nouveau = new Vide();
			break;
		case '#':
			nouveau = new Mur();
			break;
		case 'H':
			nouveau = new Joueur();
			break;
		}
		return nouveau;
	}
	/// Autres fonctions à réaliser ici...

	public boolean estVide() {
		return false;
	}

	public boolean estMarchable() {
		return false;
	}

	public boolean estPoussable() {
		return false;
	}

	public boolean estGlissant() {
		return false;
	}

	public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {
	}

	public abstract char afficher();

}

