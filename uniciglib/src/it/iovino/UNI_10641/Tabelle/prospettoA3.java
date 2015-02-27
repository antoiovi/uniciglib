/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.iovino.UNI_10641.Tabelle;
import it.iovino.utilita.interPolar;

/**
 *
 * @author antoiovi
 */
public class prospettoA3 {
/**
     * prima colonna tabella : rapporo b/D0
     */
public static final double[] b_D01={  0.13d,0.26d,  0.38d,  0.48d, 0.62d, 0.70d};
public static final double[] b_D02={ 0.26d,  0.38d,  0.48d, 0.62d, 0.70d};
public static final double[] b_D03={ 0.38d,   0.48d, 0.62d, 0.70d};

/**
 * Le stette colonne della tabella
 */
public static final double[] A02={   85d, 85d, 79d, 75d, 61d,63d};
public static final double[] A03={   42d, 38d, 36d, 33d,30d};
public static final double[] A04={   23d, 23d, 22d, 20d,18d};
public static final double[] A05={   16d, 16d, 15d, 14d,13d};
public static final double[] A06={   12d, 12d, 11d, 10d,9.4d};
public static final double[] A07={  9.3d,8.8d,8.0d,7.4d};
public static final double[] A08={  6.4d,6.9d,6.5d,6.0d};
    /**
 * Restituisce il coefficente di perdita localizzata per l'apertura di 
 *  compensazione in base al prospetto A.3
 * @param A1 Area apertura compensazione
 * @param A0 Area interna condotto fumi
 * @param D0 Diametro interno condotto fumi
 * @param b lato orizzontale apertura compensazione 
 * @return 
 */
public static double getCsiApComp(double A1,double A0,double D0,double b){
    double b_D0_=b/D0;
    double A1_A0=A1/A0;
    A1_A0=A1_A0+0.05;
    System.out.println("Function "); 
    System.out.println("b= "+b); 
        System.out.println("A1= "+A1);
        System.out.println("A0= "+A0);
        System.out.println("A1/A0= "+A1/A0);
        System.out.println("b/D0 = "+b/D0);
    if(A1_A0<0.3){
        return interPolar.interPolarLin(b_D0_,b_D01,A02);
    }else if(A1_A0<0.4){
        return interPolar.interPolarLin(b_D0_,b_D02,A03);        
    }else if(A1_A0<0.5){
        return interPolar.interPolarLin(b_D0_,b_D02,A04);        
    }else if(A1_A0<0.6){
        return interPolar.interPolarLin(b_D0_,b_D02,A05);        
    }else if(A1_A0<0.7){
        return interPolar.interPolarLin(b_D0_,b_D02,A06);        
    }else if(A1_A0<0.8){
        return interPolar.interPolarLin(b_D0_,b_D03,A07);        
    }else{
        return interPolar.interPolarLin(b_D0_,b_D03,A08);        
    }
    
    }
}
