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
public class Noeud {

    private double px;
    private double py;
    private int id;
    ArrayList<Composant> composantEntree;
    ArrayList<Composant> composantSortie;

    public Noeud(int id, double px, double py, ArrayList<Composant> ComposantEntree, ArrayList<Composant> ComposantSortie) {
        this.id = id;
        this.px = px;
        this.py = py;
        this.composantEntree = ComposantEntree;
        this.composantSortie = ComposantSortie;
    }

    public Noeud(int id, double px, double py) {
        this.id = id;
        this.px = px;
        this.py = py;
        this.composantEntree = new ArrayList<>();
        this.composantSortie = new ArrayList<>();
    }

    public ArrayList<Composant> getComposantEntree() {
        return composantEntree;
    }

    public ArrayList<Composant> getComposantSortie() {
        return composantSortie;
    }

    public int getId() {
        return id;
    }
    
    public int getPx() {
        return (int)px;
    }

    public int getPy() {
        return (int) py;
    }

    public void ajouterComposantEntree(Composant c) {
        composantEntree.add(c);
    }

    public void ajouterComposantSortie(Composant c) {
        composantSortie.add(c);
    }

    @Override
    public String toString() {

        return "{N " + id + " en ( " + px + " , " + py + " )   : départ de " + composantEntree.size() + " composants ; arrivée de " + composantSortie.size() + " composants }";
    }

}
