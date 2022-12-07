package fr.rodez3il.a2022.mrmatt.sources.objets;
import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public class Rocher extends ObjetPlateau {

    ///Fonctions

    /**
     * Permet de renvoyer le caractère correspondant à l'objet.
     */
    public char afficher() {
        return '*';
    }
    /**
     * Renvoie si l'objet est vide.
     */
    @Override
    public boolean estVide() {
        return false;
    }
    /**
     * renvoie si l'objet est marchable.
     */
    @Override
    public boolean estMarchable() {
        return false;
    }
    /**
     * renvoie si l'objet est poussable.
     */
    @Override
    public boolean estPoussable() {
        return true;
    }
    /**
     * renvoie si l'objet est glissant.
     */
    @Override
    public boolean estGlissant() {
        return true;
    }
    /**
     * implémente le aptron Visiteur pour calculer l'etat suivant du niveau en cours.
     */
    @Override
    public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {

    }

}