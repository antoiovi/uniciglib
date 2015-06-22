/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;
import com.antoiovi.unicig.tubi.Tubo;

import it.iovino.fluidi.Fluido;
import it.iovino.utilita.Mylogger;

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
   Mylogger logger; 
    
    
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
    if(logger!=null)
    	logger.appendMessage(Mylogger.FINEST, String.format("\n\tVARTEMPNONCOAS: " ));
        // Calcolo coefficiente liminare
        // calcola il numero di nusselt (formula 20)
        double psipsi0 = FattAttrRuvido / FattAttrLiscio;
        double a = Math.pow(psipsi0, 0.67);
        double b = Math.pow(NumReynold, 0.75);
        NNusselt = a * 0.0354 * (b - 180);
        double conducterm=FluidoI.CondicTermica(Tfi);
        CoefLiminI =  conducterm* NNusselt / Dh;
        if(logger!=null)
        	logger.appendMessage(Mylogger.FINEST, 
        			String.format("\n\t\t N Nusselt %1.3f  conducibilita termica fluido %1.4f Coefficiente liminare interno %1.3f "
        					,NNusselt,conducterm,CoefLiminI));
        // Calcolo coefficiente globale di scambio termico
        k = (1 / CoefLiminI) + (((1 / CoefLimE) * Dh / Dhe) + RT) * SH;
        k = 1 / k;
        if(logger!=null)
        	logger.appendMessage(Mylogger.FINEST,String.format("\n\t\t Coefficinte globale scambio termico %1.4f  "
        			+ "SH fattore correzione temperatura %1.2f    Dh/Dhe %1.3f ",k,SH,(Dh/Dhe) ));
         //Fattore di raffreddamento formula 24
        double capterm=FluidoI.CapTerm(Tfi);
        KR =( U * k * Lungh )/ (M1 *capterm );
        if(logger!=null)
        	logger.appendMessage(Mylogger.FINEST,
        			String.format("\n\t\tFattore raffreddamento %1.4f Perimetro %1.3f Lunghezza %1.2f Portata massica %1.4f"
        					+ "\n\t\t Capacita termica fluido %f temperatura iniziale %1.3f",
        					KR,U,Lungh,M1,capterm,Tfi));
         // Temperatura uscita caso non coassiale, formula 26
        Tfu = Ta + (Tfi - Ta) * Math.exp(-1 * KR);
    // Temperatura media nel condotto caso non coassiale formula 29
         Tfm=Ta+(Tfi-Ta)*(1-Math.exp(-1 * KR))/KR;
         if(logger!=null)
         logger.appendMessage(Mylogger.FINEST,String.format("\n\t Temperatura media %1.2f Temperatura uscita %1.2f",Tfm,Tfu));
         return;
     }

    public void setLogger(Mylogger logger) {
	this.logger = logger;
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
/**
 * 
 * @return Coefficiente globale di scambio termico
 */
    public double getK() {
        return k;
    }
/**
 * 
 * @return Fattore di raffreddamento
 */
    public double getKR() {
        return KR;
    }

    public double getCoefLiminI() {
        return CoefLiminI;
    }

   
}
