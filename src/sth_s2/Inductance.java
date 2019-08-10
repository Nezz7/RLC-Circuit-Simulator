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
public class Inductance extends Composant {

    public double inductance;

    public Inductance(int id, int px, int py, String nom, Noeud depart, Noeud arrivee, double inductance) {
        super(id, px, py, nom, depart, arrivee);
        this.inductance = inductance;
    }

    public Inductance(int id, String nom, Noeud depart, Noeud arrivee, double inductance) {
        super(id, nom, depart, arrivee);
        this.inductance = inductance;
    }

    @Override
    public Complex coeffBeta(double in_pulsation) {
        return Complex.creeRec(0, -inductance * in_pulsation);
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
        return "[I " + nom + " : " + inductance + " H] entre noeuds " + depart.getId() + " et " + arrivee.getId();
    }
}
