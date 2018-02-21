/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.UNI_EN_13384_1;

/**
 *
 * @author antoiovi
 */
public enum Materiali {
    ALLUMINIO (1),
    ACCIAIO(2),
    INOX (3),
    CLINKER(4),
    MATTONI(5),
    SILICATO(6);


    private final int indice;
    Materiali(int indice){
    this.indice = indice;
    }
int indice(){return this.indice;}

}
