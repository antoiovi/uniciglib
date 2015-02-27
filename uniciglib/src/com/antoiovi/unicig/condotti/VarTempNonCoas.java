/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;
import com.antoiovi.unicig.tubi.Tubo;
import it.iovino.fluidi.Fluido;

/**
 *
 * @author antoiovi
 */
public class VarTempNonCoas  {
 
    
    protected double SH = 0.5;// Fattore correzione termperatura non costante
    
    protected double NNusselt;
    protected double CoefLiminI;
    protected double k;//Coefficiente globale scambio termico
    protected double KR;// Fattore raffreddamento 
    protected double Tfu;
    protected double Tfm;
    
    
    
/**
 * Vengono calcolati:
 *    Il numero di Nusselt
 *     Coefficente liminare interno
 *      Coefficente globale di scambio termico
 *     Fattore di raffreddamento
 *      Temperatura uscita
 *      Temperatura media
 * @param tubo
 * @param Portmassica
 * @param fluidoi
 * @param coeflime
 * @param velflu
 * @param Tamb
 * @param Tinput 
 */
    public void Calcola(Tubo tubo,double Portmassica,Fluido fluidoi,
            double coeflime, VelocitaFluido velflu ,double Tamb,double Tinput)
    
    {
    double Lungh=tubo.Lung();
    double  RT=tubo.Resterm();// Resistenza termica parete
    double Dh=tubo.Dh();//Diametro idraulico
    double Dhe=tubo.Dhe();//Diametro idraulico esterno
    double U=tubo.Per_int();// Perimetro interno
    
    double M1=Portmassica;
    Fluido FluidoI=fluidoi;
    double CoefLimE=coeflime;
   double NumReynold=velflu.getNre();
    double FattAttrRuvido =velflu.getFattAttrRuvido();
    double FattAttrLiscio=velflu.getFattAttrLiscio();
    double Ta=Tamb;
    double Tfi=Tinput;
        // Calcolo coefficiente liminare
        // calcola il numero di nusselt (formula 20)
        double psipsi0 = FattAttrRuvido / FattAttrLiscio;
        double a = Math.pow(psipsi0, 0.67);
        double b = Math.pow(NumReynold, 0.75);
        NNusselt = a * 0.0354 * (b - 180);
        CoefLiminI = FluidoI.CondicTermica(Tfi) * NNusselt / Dh;
        // Calcolo coefficiente globale di scambio termico
        k = (1 / CoefLiminI) + (((1 / CoefLimE) * Dh / Dhe) + RT) * SH;
        k = 1 / k;
         //Fattore di raffreddamento formula 24
        KR =( U * k * Lungh )/ (M1 * FluidoI.CapTerm(Tfi));
         // Temperatura uscita caso non coassiale, formula 26
        Tfu = Ta + (Tfi - Ta) * Math.exp(-1 * KR);
    // Temperatura media nel condotto caso non coassiale formula 29
         Tfm=Ta+(Tfi-Ta)*(1-Math.exp(-1 * KR))/KR;
     }

    public double getTfu() {
        return Tfu;
    }

    public double getTfm() {
        return Tfm;
    }

    public double getNNusselt() {
        return NNusselt;
    }

    public double getK() {
        return k;
    }

    public double getKR() {
        return KR;
    }

    public double getCoefLiminI() {
        return CoefLiminI;
    }

   
}
