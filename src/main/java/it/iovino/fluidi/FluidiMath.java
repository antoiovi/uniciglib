/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

/**
 * CREATA PER REALIZZARE DELLE FUNZIONI STATICHE DA UTILIZZARE PER EFFETTUARE
 * OPERAZIONI SUI FLUIDI : 
 * @author antoiovi
 */
public class FluidiMath {
    /**
     * Costante universale dei gas J/(K kmol)
     */
    public static final double Ru=8314.472;
    /**
     * Crea un nuovo Fluido come somma dei fluidi; 
     *la utilizzo dove vengono utilizzate interfacce Fluido, così non 
     * mi interessa se vengono create miscele...
    **/ 
    
  /* public static Fluido Somma(double m1,Fluido f1,double m2,Fluido f2) {
    Miscela m=new Miscela(m1,f1,m2,f2);
    return m;
   }*/
}
