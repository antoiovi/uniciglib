/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

/**
 *
 * @author antoiovi
 */
public class PairMoliFluido {
    public double moli;
        public Fluido fluido;
      public  PairMoliFluido(double mol,Fluido fluid){
            this.moli=mol;
            this.fluido=fluid;
        }  
}
