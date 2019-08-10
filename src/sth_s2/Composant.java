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
public abstract class Composant {

    private int px, py;
    String nom;
    Noeud depart;
    Noeud arrivee;
    int id;

    public Composant(int id, int px, int py, String nom, Noeud depart, Noeud arrivee) {
        this.id = id;
        this.px = px;
        this.py = py;
        this.nom = nom;
        this.depart = depart;
        this.arrivee = arrivee;
        depart.ajouterComposantEntree(this);
        arrivee.ajouterComposantSortie(this);
    }

    public Composant(int id, String nom, Noeud depart, Noeud arrivee) {
        this.id = id;
        this.nom = nom;
        this.depart = depart;
        this.arrivee = arrivee;
        depart.ajouterComposantEntree(this);
        arrivee.ajouterComposantSortie(this);
    }

    public abstract Complex coeffAlpha(double pulsation);

    public abstract Complex coeffBeta(double pulsation);

    public abstract Complex coeffGamma(double pulsation);

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Noeud getDepart() {
        return depart;
    }

    public void setDepart(Noeud depart) {
        this.depart = depart;
    }

    public Noeud getArrivee() {
        return arrivee;
    }

    public void setArrivee(Noeud arrivee) {
        this.arrivee = arrivee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
