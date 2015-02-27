/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

/**
 * Coppia massa fluido 
 * @author antoiovi
 */
public class PairMassaFluido {
        public double massa;
        public Fluido fluido;
      public  PairMassaFluido(double mass,Fluido fluid){
            this.massa=mass;
            this.fluido=fluid;
        } 
}
