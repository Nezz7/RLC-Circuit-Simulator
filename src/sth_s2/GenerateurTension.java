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
public class GenerateurTension extends Composant {

    public double fem;

    public GenerateurTension(int id, int px, int py, String nom, Noeud depart, Noeud arrivee, double fem) {
        super(id, px, py, nom, depart, arrivee);
        this.fem = fem;
    }

    public GenerateurTension(int id, String nom, Noeud depart, Noeud arrivee, double fem) {
        super(id, nom, depart, arrivee);
        this.fem = fem;
    }

    @Override
    public Complex coeffGamma(double in_pulsation) {
        return new Complex(fem, 0);
    }

    @Override
    public Complex coeffAlpha(double pulsation) {
        return Complex.ZERO;
    }

    @Override
    public Complex coeffBeta(double pulsation) {
        return Complex.ZERO;
    }

    @Override
    public String toString() {
        return "[G " + nom + " : " + fem + " V] entre noeuds " + depart.getId() + " et " + arrivee.getId();
    }
}
