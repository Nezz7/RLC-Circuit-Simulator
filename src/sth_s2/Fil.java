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
public class Fil extends Composant {

    public Fil(int id, int px, int py, String nom, Noeud depart, Noeud arrivee) {
        super(id, px, py, nom, depart, arrivee);
    }

    @Override
    public Complex coeffAlpha(double pulsation) {
        return Complex.UN;
    }

    @Override
    public Complex coeffBeta(double pulsation) {
        return Complex.ZERO;
    }

    @Override
    public Complex coeffGamma(double pulsation) {
        return Complex.ZERO;
    }

}
