/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

import java.util.*;

/**
 *
 * @author Nezz
 */
public class MatriceRectangulaire {

    private Complex[][] mat;
    private Complex[] sol;

    public Complex[] getSol() {
        return sol;
    }
    private int n;

    public MatriceRectangulaire(int n) {
        mat = new Complex[n][n + 1];
        this.n = n;
    }

    public MatriceRectangulaire(Complex[][] mat) {
        this.mat = mat;
        this.n = mat.length;
    }

    public Complex[][] getMat() {
        return mat;
    }

    public int getN() {
        return n;
    }

    public void ajouterLigne(int i, Complex[] ligne) {
        System.arraycopy(ligne, 0, mat[i], 0, n);
    }

    public void ajouterColonne(int j, Complex[] colonne) {
        for (int i = 0; i < n; i++) {
            mat[i][j] = colonne[i];
        }
    }

    void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    void swap_row(int i, int j) {
        for (int k = 0; k <= n; k++) {
            Complex temp = mat[i][k];
            mat[i][k] = mat[j][k];
            mat[j][k] = temp;
        }
    }

    int forwardElim() throws Exception {
        for (int k = 0; k < n; k++) {
            // Initialise la valeur maximale et l'index pour le pivot
            int i_max = k;

            Complex v_max = mat[i_max][k];
            // Trouver une plus grande amplitude pour le pivot éventuel 
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(mat[i][k].partieReelle()) > v_max.partieReelle()) {
                    v_max = mat[i][k];
                    i_max = i;
                    //                 == de double parce que ça marche pas pas efficace 
                } else if (Math.abs(mat[i][k].partieReelle() - v_max.partieReelle()) < 1e-6) {
                    if (Math.abs(mat[i][k].partieImaginaire()) > v_max.partieImaginaire()) {
                        v_max = mat[i][k];
                        i_max = i;
                    }
                }
            }

            //  Si un élément de la diagonale principale est égal à zéro, 
            // indique que la matrice est singulière et 
            // conduira ultérieurement à une division par zéro.
            if (mat[k][i_max].estNul()) {
                return k;
            }
            // Swap the greatest value row with current row 

            if (i_max != k) {
                swap_row(k, i_max);
            }
            for (int i = k + 1; i < n; i++) {
                // facteur f pour définir la kième élément de la rangée actuelle sur 0,
                // et kème colonne restant ensuite à 0
                Complex f = mat[i][k].div(mat[k][k]);
                //soustraire le cinquième multiple de la kième correspondante element de ligne
                for (int j = k + 1; j <= n; j++) {
                    mat[i][j] = mat[i][j].moins(mat[k][j].mult(f));
                }
                // remplissage de la matrice triangulaire inférieure avec des zéros
                mat[i][k] = Complex.ZERO;

            }

        }

        return -1;
    }
// function to calculate the values of the unknowns 

    void backSub() throws Exception {
        // Un tableau pour stocker la solution
        Complex[] x = new Complex[n];
        //Commencez à calculer à partir de la dernière équation jusqu'au premier
        for (int i = n - 1; i >= 0; i--) {
            x[i] = mat[i][n];
            // Initialise j sur i + 1 puisque la matrice est supérieure
            // triangulaire
            for (int j = i + 1; j < n; j++) {
                // soustrayez toutes les valeurs de lhs
                //sauf le coefficient de la variable
                //dont la valeur est calculée * /
                x[i] = x[i].moins(mat[i][j].mult(x[j]));
            }
            x[i] = x[i].div(mat[i][i]);
        }
        this.sol = x;
        System.out.println("Les Solutions : ");
        for (int i = 0; i < n; i++) {
            System.out.println(x[i] + " ");
        }
        System.out.println();

    }

    void resoudreSystem() throws Exception {

        int singular_flag = forwardElim();
        if (singular_flag != -1) {
            System.out.println("Matrice singulière.");
            if (mat[singular_flag][n].module() >= 0) {
                System.out.println("Système incohérent.");
            } else {
                System.out.println("Peut avoir une infinité de solutions.");
            }
            return;
        }

        backSub();
    }

    public static void main(String[] args) throws Exception {
        //
        Complex[][] a = new Complex[3][4];
        a[0][0] = Complex.ZERO;
        a[0][1] = Complex.UN;
        a[0][2] = Complex.UN;
        a[0][3] = Complex.creeRec(4, 0);
        a[1][0] = Complex.creeRec(2, 0);
        a[1][1] = Complex.creeRec(4, 0);
        a[1][2] = Complex.creeRec(-2, 0);
        a[1][3] = Complex.creeRec(2, 0);
        a[2][0] = Complex.creeRec(0, 0);
        a[2][1] = Complex.creeRec(3, 0);
        a[2][2] = Complex.creeRec(15, 0);
        a[2][3] = Complex.creeRec(36, 0);
        MatriceRectangulaire mat = new MatriceRectangulaire(a);
        System.out.println(mat.getN());
        mat.resoudreSystem();

    }

}
