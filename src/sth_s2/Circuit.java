/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

import java.util.ArrayList;
import java.util.Arrays;
import fr.insa.beuvron.cours.m2.circuitRLC.v6.circuit.CalBaseMaille;

/**
 *
 * @author Nezz
 */
public class Circuit {

    ArrayList<Composant> CompoCircuit;
    ArrayList<Noeud> NoeudCircuit;

    public Circuit() {
        CompoCircuit = new ArrayList<>();
        NoeudCircuit = new ArrayList<>();

    }

    public Circuit(ArrayList<Composant> CompoCircuit, ArrayList<Noeud> NoeudCircuit) {
        this.CompoCircuit = CompoCircuit;
        this.NoeudCircuit = NoeudCircuit;
    }

    public void ajouterCompCircuit(Composant c) {
        CompoCircuit.add(c);

    }

    public void ajouterNoeudCircuit(Noeud c) {
        NoeudCircuit.add(c);
    }

    public ArrayList<Composant> getCompoCircuit() {
        return CompoCircuit;
    }

    public ArrayList<Noeud> getNoeudCircuit() {
        return NoeudCircuit;
    }

    /*redondant*/
    public int[][] calculBaseDeMaille() {
        Noeud n1 = NoeudCircuit.get(0);
        ArrayList<Noeud> ndv = new ArrayList<>();
        ArrayList<Composant> cdv = new ArrayList<>();
        ArrayList<Maille> res = new ArrayList<>();
        //dessous loi des mailles
        collectMailles(n1, n1, ndv, cdv, res);
        int[][] mailles = matriceDeMaille(res,CompoCircuit.size());
        return CalBaseMaille.vecteursIndependants(mailles);
    }

    public Complex[][] systemeAssocie(double pulsation) {
        int n = CompoCircuit.size();
        Complex[][] mat = new Complex[2 * n][2 * n + 1];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n + 1; j++) {
                mat[i][j] = Complex.ZERO;
            }
        }
        int ind = 0;
        for (Composant c : CompoCircuit) {
            System.out.println(c.getNom());
            int id = c.getId() - 1;
            if (c.getClass().getName().equals("sth_s2.GenerateurTension")) {
                mat[ind][id] = Complex.UN;
                mat[ind][2 * n] = c.coeffGamma(pulsation);
            } else {
                mat[ind][id] = Complex.UN;
                mat[ind][id + n] = c.coeffBeta(pulsation);
            }
            ind++;
        }
        int it = ind;
        System.out.println("Loi des Noeud :");
        //entree +1 sortie -1
        for (Noeud cn : NoeudCircuit) {
            if (it + NoeudCircuit.size() - 1 == ind) {
                break;
            }
            for (Composant c : cn.getComposantEntree()) {
                int id = c.getId() - 1;
                System.out.print("+I" + id + " ");
                mat[ind][id + n] = Complex.UN;
            }
            for (Composant c : cn.getComposantSortie()) {
                int id = c.getId() - 1;
                System.out.print("-I" + id + " ");
                mat[ind][id + n] = Complex.creeRec(-1, 0);
            }
            System.out.println();
            ind++;
        }
//loi de maille + independant INJECTER MATRICE
        int[][] m = calculBaseDeMaille();

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                mat[ind][j] = Complex.creeRec(m[i][j], 0);
            }
            ind++;
        }
        for (int j = 0; j < 2 * n; j++) {
            System.out.printf("%10d | ", j + 1);
        }
        System.out.println();
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n + 1; j++) {
                System.out.printf("%10s | ", mat[i][j]);
            }
            //verification 
            System.out.println();
        }
        return mat;
    }
//resoud systeme
    public MatriceRectangulaire calculs(double pulsation) throws Exception {
        MatriceRectangulaire mat = new MatriceRectangulaire(systemeAssocie(pulsation));
        mat.resoudreSystem();
        return mat;
    }
//verification appartenance
    public boolean apartientComposant(Composant c, ArrayList<Composant> list) {
        for (Composant cur : list) {
            if (cur.getId() == c.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean apartientNoeud(Noeud n, ArrayList<Noeud> list) {
        for (Noeud cur : list) {
            if (cur.getId() == n.getId()) {
                return true;
            }
        }
        return false;
    }
//loi des mailles //ndv noeud deja vu //composant deja vu cdv
    public void collectMailles(Noeud nc, Noeud depart, ArrayList<Noeud> ndv, ArrayList<Composant> cdv, ArrayList<Maille> res) {
        ArrayList<Composant> mycdv;
        ArrayList<Noeud> myndv = (ArrayList<Noeud>) ndv.clone();
        myndv.add(nc);
        for (Composant c : nc.getComposantEntree()) {
            if (!apartientComposant(c, cdv)) {
                mycdv = (ArrayList<Composant>) cdv.clone();
                mycdv.add(c);
                Noeud na = c.getArrivee();
                if (na.getId() == depart.getId()) {
                    res.add(new Maille(mycdv)); //creation maille prouver existance 
                } else if (!apartientNoeud(na, myndv)) {
                    collectMailles(na, depart, myndv, mycdv, res);
                }
            }
        }

        for (Composant c : nc.getComposantSortie()) {

            if (!apartientComposant(c, cdv)) {
                mycdv = (ArrayList<Composant>) cdv.clone();
                mycdv.add(c);
                Noeud na = c.getDepart();
                if (na.getId() == depart.getId()) {
                    res.add(new Maille(mycdv));
                } else if (!apartientNoeud(na, ndv)) {
                    collectMailles(na, depart, myndv, mycdv, res);
                }
            }
        }

    }
// maille creeer dans collect maille injection matrice 
    public int[][] matriceDeMaille(ArrayList<Maille> res, int nombreDeComposant) {
        int[][] mat = new int[res.size()][nombreDeComposant];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < nombreDeComposant; j++) {
                mat[i][j] = 0;
            }
        }
        int ind = 0;
        for (Maille m : res) {
            int cur = 1;
            for (Composant c : m.getChemin()) {
                if (c.getDepart().getId() == cur) {
                    mat[ind][c.getId() - 1] = 1;
                    cur = c.getArrivee().getId();
                } else {
                    mat[ind][c.getId() - 1] = -1;
                    cur = c.getDepart().getId();
                }
            }
            ind++;
        }

        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < nombreDeComposant; j++) {
                System.out.printf(" %2d |", mat[i][j]);
            }
            System.out.println();
        }
        return mat;

    }

    public String toString() {
        String out = "Circuit {\nnoeuds :\n";
        for (Noeud n : NoeudCircuit) {
            out += n.toString() + "\n";
        }
        out += "Composants :\n";
        for (Composant c : CompoCircuit) {
            out += c.toString() + "\n";
        }
        out += "}";
        return out;
    }
//exemple pdf
    public static void main(String[] Args) throws Exception {
        Circuit cir = new Circuit();
        Noeud n1 = new Noeud(1, 0.0, 0.0);
        Noeud n2 = new Noeud(2, 0.0, 1.0);
        Noeud n3 = new Noeud(3, 1.0, 1.0);
        Noeud n4 = new Noeud(4, 1.0, 0.0);
        GenerateurTension g = new GenerateurTension(1, "G", n1, n2, 10);
        Resistance r1 = new Resistance(2, "R1", n2, n3, 200);
        Resistance r2 = new Resistance(5, "R2", n4, n1, 400);
        Condensateur c = new Condensateur(3, "C1", n3, n1, 1e-4);
        Inductance i = new Inductance(4, "L1", n3, n4, 0.01);
        cir.ajouterCompCircuit(g);
        cir.ajouterCompCircuit(r1);
        cir.ajouterCompCircuit(c);
        cir.ajouterCompCircuit(i);
        cir.ajouterCompCircuit(r2);

        cir.ajouterNoeudCircuit(n1);
        cir.ajouterNoeudCircuit(n2);
        cir.ajouterNoeudCircuit(n3);
        cir.ajouterNoeudCircuit(n4);
        System.out.println(cir);
        ArrayList<Noeud> ndv = new ArrayList<>();
        ArrayList<Composant> cdv = new ArrayList<>();
        ArrayList<Maille> res = new ArrayList<>();
        cir.collectMailles(n1, n1, ndv, cdv, res);

        for (Maille m : res) {
            System.out.println(m);
        }
        System.out.println("Matrice de Mailles : ");
        int[][] mailles = cir.matriceDeMaille(res, 5);
        int[][] independants = CalBaseMaille.vecteursIndependants(mailles);
        System.out.println("Bsse de Mailles : \n" + Arrays.deepToString(independants));
        MatriceRectangulaire mr = cir.calculs(100);
    }

}
