/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.Tubo;
import it.iovino.fluidi.Fluido;
import it.iovino.impter.MoodyDiagram;

/**
 * Incapsula le seguenti propietà : 
 *  Fattore Liminare Esterno
 *  Fattore di Attrito Liscio e Ruvido
 *  Temperatura media
 * 
 * @author antoiovi
 */
public class VelocitaFluido {
    
    protected double FattoreLiminEst;
    protected double FattAttrLiscio;
    protected double FattAttrRuvido;
    protected double MassaVolumicaMedia;
    protected double VelocitaMedia;
    protected double Nre;//Numero di reynold
    /**
     * Vengono calcolati : 
     *  MassaVolumicaMedia
     *  VelocitaMedia
     *  NumerodiReynold
     *  Fattore di attrito liscio
     *  Fattore di attrito ruvido
     */
    
    /**
     * Dati in ingresso la Pressione Atmnosferica, la portata massica, 
     * la temperatura media iniziale /n, i dati del fluido e del tubo
     * 
     *  Vengono calcolati : 
         MassaVolumicaMedia
        VelocitaMedia
        NumerodiReynold
        Fattore di attrito liscio
        Fattore di attrito ruvido
     * @param patm
     */
    public void Calcola(double Patm,double PortMassica,double Tm,Fluido FluidoInt,Tubo t ){

    double    Area=t.Area_int();
    double    Lungh=t.Lung();
    double    rugosita=t.Rug();
    double    Dh=t.Dh();
      // Massa volumica media formula 13
      MassaVolumicaMedia= Patm/(Tm*FluidoInt.CostElasticita());  
      // Velocità media formula 14
      VelocitaMedia= PortMassica/(MassaVolumicaMedia*Area);
// Calcolo fattori di attrito liscio e ruvido Formula 15
     // Formula 16 Numero di Raynold 
       //Nre= MassaVolumicaMedia*Dh*VelocitaMedia;
        double viscCin=FluidoInt.ViscCin(Patm,Tm);
        Nre= Dh*VelocitaMedia/viscCin;
       //double viscdin=FluidoInt.ViscCin(TempMedia)*MassaVolumicaMedia;
       //Nre=Nre/viscdin;
       //System.out.println("Nre= "+Nre+"    visccin= "+viscCin);
        if(Nre<3000){
               FattAttrLiscio=64/Nre;
               FattAttrRuvido=FattAttrLiscio;//verificare per nre<3000
           }else{
                double scabrRel=rugosita/Dh;
                it.iovino.impter.MoodyDiagram moodydiagr=new MoodyDiagram(Nre,scabrRel);
                
                FattAttrRuvido=moodydiagr.zbrent();
                moodydiagr.setEsd(0);// fattore attrito liscio con scabrezza=0
                FattAttrLiscio=moodydiagr.zbrent();
           }
   }

    public double getFattAttrLiscio() {
        return FattAttrLiscio;
    }

    public double getFattAttrRuvido() {
        return FattAttrRuvido;
    }

    public double getMassaVolumicaMedia() {
        return MassaVolumicaMedia;
    }

    public double getVelocitaMedia() {
        return VelocitaMedia;
    }

    public double getNre() {
        return Nre;
    }

}
