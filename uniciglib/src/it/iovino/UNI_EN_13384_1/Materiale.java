/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.UNI_EN_13384_1;

/**
 *
 * @author antoiovi
 */


public enum Materiale {

Alluminio                (    2.0,    0.88,  160.0),
Acciaio                  (7800.0,     0.45,   50.0),
Inox                     (7900.0,     0.45,   17.0 ),
CalcestruzzoLeggero_1   (2400.0 ,     1.0,     1.72),
CalcestruzzoLeggero_2   (2400.0 ,     1.0,     1.72),
CalcestruzzoLeggero_3   (2400.0 ,     1.0,     1.72),
CalcestruzzoPesante     (2400.0 ,     1.0,     1.72),
Gesso                   (1800.0 ,     1.0,     0.93),
TerracottaCeramica       (2000.0,     0.45,   17.0),
Fibra                   (  100.0,     0.45,   17.0),
Vetro                    (2200.0,     0.45,   17.0),
PVDF                     (1800.0,     0.45,   17.0),
PP                       (900.0,      0.45,   17.0);

private final double CondTerm;
private final double MassaVolum;
private final double CapTerm;




Materiale(double CondTerm,double MassaVolum,double CapTerm){
    this.CondTerm=CondTerm;
    this.MassaVolum=MassaVolum;
    this.CapTerm=CapTerm;
}
double ConducibiltaTermica(){
    return this.CondTerm;}
double MassaVolumica(){
    return this.CondTerm;}
double CapacitaTermica(){
    return this.MassaVolumica();}

}
