/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

/**
 *
 * @author Nezz
 */
public class Condensateur extends Composant {

    public double capacite;

    public Condensateur(int id, int px, int py, String nom, Noeud depart, Noeud arrivee, double capacite) {
        super(id, px, py, nom, depart, arrivee);
        this.capacite = capacite;
    }

    public Condensateur(int id, String nom, Noeud depart, Noeud arrivee, double capacite) {
        super(id, nom, depart, arrivee);
        this.capacite = capacite;
    }

    @Override
    public Complex coeffBeta(double in_pulsation) {
        return Complex.creeRec(0, 1 / (capacite * in_pulsation));
    }

    @Override
    public Complex coeffAlpha(double pulsation) {
        return Complex.UN;
    }

    @Override
    public Complex coeffGamma(double pulsation) {
        return Complex.ZERO;
    }

    @Override
    public String toString() {
        return "[C " + nom + " : " + capacite + " F] entre noeuds " + depart.getId() + " et " + arrivee.getId();

    }

}
