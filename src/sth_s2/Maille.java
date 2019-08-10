/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

import java.util.ArrayList;

/**
 *
 * @author Nezz
 */
public class Maille {

    private ArrayList<Composant> chemin;

    public Maille(ArrayList<Composant> chemin) {
        this.chemin = (ArrayList<Composant>) chemin.clone();
    }

    public Maille() {
    }

    public ArrayList<Composant> getChemin() {
        return chemin;
    }

    public void ajouterComposant(Composant c) {
        chemin.add(c);
    }

    @Override
    public String toString() {
        String out = "[Chemin ";
        for (Composant c : chemin) {
            out += c.toString() + " ";
        }
        out += "]";
        return out;

    }

}
