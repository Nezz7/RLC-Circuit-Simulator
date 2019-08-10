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
public class Resistance extends Composant {

    private double resistance;

    public Resistance(int id, int px, int py, String nom, Noeud entree, Noeud sortie, double resistance) {
        super(id, px, py, nom, entree, sortie);
        this.resistance = resistance;
    }

    public Resistance(int id, String nom, Noeud entree, Noeud sortie, double resistance) {
        super(id, nom, entree, sortie);
        this.resistance = resistance;
    }

    @Override
    public Complex coeffBeta(double in_pulsation) {
        return Complex.creeRec(-resistance, 0);
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
        return "[R " + nom + " : " + resistance + " Ohm] entre noeuds " + depart.getId() + " et " + arrivee.getId();
    }
}
