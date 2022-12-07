package fr.rodez3il.a2022.mrmatt.sources;

/**
 * Jeton pour les commandes possibles.
 * @author proussille
 *
 */
public enum Commande {
	// Commandes valides
	HAUT, // 8
	GAUCHE, // 4
	BAS, // 2
	DROITE, // 6
	ANNULER, // a
	QUITTER, // q
	// Erreur de lecture
	ERREUR;
}
