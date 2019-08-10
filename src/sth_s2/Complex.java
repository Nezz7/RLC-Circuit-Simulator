/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nezz
 */
public class Complex {

    public double mod;
    public double arg;

    Complex(double mod, double arg) {
        this.mod = mod;
        this.arg = arg;
    }

    public static Complex creePol(double module, double argument) {
        return new Complex(module, argument);
    }

    public static Complex creeRec(double partReelle, double partImaginaire) {
        double mod = Math.sqrt(partReelle * partReelle + partImaginaire * partImaginaire);
        double arg = Math.atan2(partImaginaire, partReelle);
        return new Complex(mod, arg);
    }

//============================= attributs statiques
// on a parfois besoin de définir des valeurs qui sont communes à tous les
// objet d'une classe
// on parle alors d'attribut statique, et on les définis avec le mot clé "static"
// attention ce mot clé change complètement la signification : la valeur d'un
// attribut statique est associée à la classe et non à un objet particulier. On 
// parlerait de "variable globale" dans d'autres langages. Il faut autant que 
// possible limiter l'utilisation de ces attributs statique (sauf si leur valeur
// est en fait une constante comme ci-dessous)
// le mot clé "final" indique que la valeur ne peut pas être modifiée (c'est
// une constante)
// convention de nom : les constantes sont écrites tout en majuscule
// il n'est pas rare de définir comme constantes des objets particuliers
    public static final Complex ZERO = creeRec(0, 0);
    public static final Complex UN = creeRec(1, 0);

//
//  - lors de l'appel, ce paramètre n'apparait pas dans la liste des
//    paramètres, mais devant le nom de la méthode (séparé par un ".")
//    Ainsi si l'on veut faire la somme de z2 et z3, et placer le résultat dans
//    z1, l'écriture fonctionnelle serait :
//         z1 = add(z2,z3)   (écriture fonctionnelle, pas du Java)
//    En java il faut écrire :
//         z1 = z2.add(z3)
//    Cette écriture est un peu déroutante au début, mais on s'y habitue vite.
//
    public double module() {

        return this.mod;
    }

    public double argument() {
        return this.arg;
    }

    public double partieReelle() {
        return this.mod * Math.cos(this.arg);
    }

    public double partieImaginaire() {
        return this.mod * Math.sin(this.arg);
    }

    public Complex plus(Complex arg2) {
        double pa = this.partieReelle() + arg2.partieReelle();
        double pi = this.partieImaginaire() + arg2.partieImaginaire();
        return creeRec(pa, pi);
    }

    public Complex opp() {
        return creeRec(-this.partieReelle(), -this.partieImaginaire());
    }

    /**
     * calcule la différence de deux complexes et retourne un nouveau complexe.
     * ni this ni arg2 ne sont modifiés par l'appel de sub
     *
     * @param arg2 le complexe à soustraire à this
     * @return la différence de this et arg2.
     */
    public Complex moins(Complex arg2) {
        return this.plus(arg2.opp());
    }

    public Complex mult(Complex arg2) {
        double real = partieReelle() * arg2.partieReelle() + partieImaginaire() * arg2.partieImaginaire();
        double img = partieReelle() * arg2.partieImaginaire() + partieImaginaire() * arg2.partieReelle();
        return creeRec(real, img);

    }

// pour l'inverse, il faut tester si le complexe n'est pas nul. On fourni donc
// une méthode pour tester si un complexe est nul, et tant qu'à faire, une
// méthode pour tester si un complexe est égal à un
    /**
     *
     * @return true ssi le complexe est nul (ssi son module est nul)
     */
    public boolean estNul() {
        return this.module() == 0;
    }

    /**
     *
     * @return true ssi le complexe vaut 1 (= 1 + i 0 )
     */
    public boolean estUn() {
        return this.partieReelle() == 1 && this.partieImaginaire() == 0;
    }

    /**
     * calcule l'inverse d'un complexe et retourne un nouveau complexe. sort du
     * programme si tentative de division par zéro this n'est sont modifiés par
     * l'appel de inv
     *
     * @return l'inverse de this
     */
    public Complex inv() throws Exception {
        if (this.estNul()) {
            // la signification et la syntaxe de gestion des erreurs sera
            // explicitée plus tard dans le cours
            throw new Exception();
        }
        return creePol(1 / this.module(), -this.argument());
    }

    public Complex div(Complex arg2) throws Exception {
        return this.mult(arg2.inv());
    }

    public Complex puiss(double p) {
        return creePol(Math.pow(this.module(), p), p * this.argument());
    }

    public String toString() {
        if (Math.abs(this.partieImaginaire()) < 1e-6) {
            return this.partieReelle() + "";
        }
        if (Math.abs(this.partieReelle()) < 1e-6) {
            return "j" + this.partieImaginaire();
        }
        return this.partieReelle() + " +j " + this.partieImaginaire();
    }

    public static Complex[] eqSecondDegre(Complex a, Complex b, Complex c) throws Exception {
        Complex delta = b.mult(b).moins(creeRec(4, 0).mult(a).mult(c));
        Complex racDelta = delta.puiss(0.5);
        Complex[] res = new Complex[2];
        try {
            res[0] = b.opp().moins(racDelta).div(creeRec(2, 0).mult(a));
        } catch (Exception ex) {
            Logger.getLogger(Complex.class.getName()).log(Level.SEVERE, null, ex);
        }
        res[1] = b.opp().plus(racDelta).div(creeRec(2, 0).mult(a));
        return res;
    }

    public Complex getNul() {
        return ZERO;
    }

    public Complex getUn() {
        return UN;

    }
}
