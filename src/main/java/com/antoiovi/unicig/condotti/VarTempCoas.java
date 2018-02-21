/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;

import com.antoiovi.unicig.tubi.TuboCoass;
import com.antoiovi.unicig.tubi.Tubo;
import it.iovino.fluidi.Fluido;
import it.iovino.impter.MoodyDiagram;

/**
 *
 * @author antoiovi
 */
public class VarTempCoas {

    protected double Nu_i;
    protected double Nu_e;
    protected double CoefLiminI_i;
    protected double CoefLiminI_e;
    protected double CoefLiminE_1;
    protected double CoefLiminE_2;

    protected double k12;//Coefficiente globale scambio termico per cond coassiali

    protected double KR12;// Fattore raffreddamento per tubi coassiali

    protected double SH = 0.5;// Fattore correzione termperatura non costante

    // Pedice i fluido INTERNO
    // Pedice e fluido ESTERNO
    protected double Tuscita_i;
    protected double Tuscita_e;
    protected double Tmedia_i;//Deve essere inizzializzato con una frazione ragionevole
    protected double Tmedia_e;

    public void Calcola(Fluido fluido_1, Fluido fluido_2, Tubo tubo_i, VelocitaFluido velflu_1,
            VelocitaFluido velflu_2, double massa1, double massa2, double Tinput_i, double Tinput_e) {
        
        double M_i = massa1;
        double M_e = massa2;
        Fluido fluido_i = fluido_1;
        double lungh = tubo_i.Lung();
        double Dhi_i = tubo_i.Dh();
        double Dhe_i = tubo_i.Dhe();
        double NRey_i = velflu_1.getNre();
        double FattRuv_i = velflu_1.getFattAttrRuvido();
        double FattLisc_i = velflu_1.getFattAttrLiscio();

        Fluido fluido_e = fluido_2;
        //double Dhi_e = tubo_e.Dh();
        //double Dhe_e = tubo_e.Dhe();
        // Il numero di Reynold_e è stato calcolato considernado il 
        // perimetro della ciambella per il calco di Dh
        double NRey_e = velflu_2.getNre();
        double FattRuv_e = velflu_2.getFattAttrRuvido();
        double FattLisc_e = velflu_2.getFattAttrLiscio();

        // Calcolo coefficiente liminare
        // calcola il numero di nusselt (formula 20) interno
        double psipsi0 = FattRuv_i / FattLisc_i;
        double a = Math.pow(psipsi0, 0.67);
        double b = Math.pow(NRey_i, 0.75);
        Nu_i = a * 0.0354 * (b - 180);
        // calcola il numero di nusselt (formula 20) esterno
        psipsi0 = FattRuv_e / FattLisc_e;
        a = Math.pow(psipsi0, 0.67);
        b = Math.pow(NRey_e, 0.75);
        Nu_e = a * 0.0354 * (b - 180);
        do {
            // CoefficienteLiminareInterno condotto interno
            CoefLiminI_i = fluido_i.CondicTermica(Tinput_i) * Nu_i / Dhi_i;
            
            // CoefficienteLiminareInterno condotto esterno
            // Per il calcolo del diametro idraulico bisogna considerare il perimetro
            // interessato allo scambio termico: ovvero il perimetro del condotto interno
            // quindi si considera Dhi_e:Dh condotto Interno, perimetro esterno
            CoefLiminI_e = fluido_e.CondicTermica(Tinput_e) * Nu_e / Dhe_i;

        // Calcolo coefficiente globale di scambio termico per cond coassiale   
            //Formula 23
            k12 = (1 / CoefLiminI_i) + tubo_i.Resterm() * SH + ((1 / CoefLiminI_e) * Dhi_i / Dhe_i);
            k12 = 1 / k12;

        //Fattore di raffreddamento formula 25 per tubi coassiali
            // Fluido caldo interno quindi superficie di scambio perimetro esterno 
            //tubo interno
            KR12 = tubo_i.Per_est() * k12 * lungh * ((1 / (M_e * fluido_e.CapTerm(Tmedia_e)))
                    + (1 / (M_i * fluido_i.CapTerm(Tmedia_i))));
            // Temperatura uscita caso coassiale, formula 28 condotto Interno
            Tuscita_i = (M_e * fluido_e.CapTerm(Tmedia_e)) / (M_i * fluido_i.CapTerm(Tmedia_i));
            Tuscita_i = Tuscita_i * (Tinput_e - Tuscita_e) + Tinput_i;
            // Temperatura media nel condotto caso  coassiale formula 30 
            Tmedia_i = (Tinput_i + Tuscita_i) / 2;
            Tmedia_e = (Tinput_e + Tuscita_e) / 2;
        } while (!((Tinput_i - Tuscita_e) / (Tinput_e - Tuscita_i) < 1.5));
    }
/**
 * 
 * @param fluido_1 Fluido interno (caldo)
 * @param fluido_2 Fluideo esterno
 * @param tubo_i tubo interno
 * @param tubo_e tubo esterno
 * @param velflu_1 dati velocita fluido interno
 * @param velflu_2 dati velocita fluido esterno
 * @param massa1 massa fluido interno
 * @param massa2 massa fluido esterno
 * @param Tinput_i temperatura ingresso interno
 * @param Tinput_e temperatura ingresso esterno
 */
    public void Calcola(Fluido fluido_1, Fluido fluido_2, Tubo tubo_i, Tubo tubo_e, VelocitaFluido velflu_1,
            VelocitaFluido velflu_2, double massa1, double massa2, double Tinput_i, double Tinput_e) {
        double M_i = massa1;
        double M_e = massa2;
        Fluido fluido_i = fluido_1;
        double lungh = tubo_i.Lung();
        double Dhi_i = tubo_i.Dh();
        double Dhe_i = tubo_i.Dhe();
        double NRey_i = velflu_1.getNre();
        double FattRuv_i = velflu_1.getFattAttrRuvido();
        double FattLisc_i = velflu_1.getFattAttrLiscio();

        Fluido fluido_e = fluido_2;
        //double Dhi_e = tubo_e.Dh();
        //double Dhe_e = tubo_e.Dhe();
        double NRey_e = velflu_2.getNre();
        double FattRuv_e = velflu_2.getFattAttrRuvido();
        double FattLisc_e = velflu_2.getFattAttrLiscio();

        // Calcolo coefficiente liminare
        // calcola il numero di nusselt (formula 20) interno
        double psipsi0 = FattRuv_i / FattLisc_i;
        double a = Math.pow(psipsi0, 0.67);
        double b = Math.pow(NRey_i, 0.75);
        Nu_i = a * 0.0354 * (b - 180);
        // calcola il numero di nusselt (formula 20) esterno
        psipsi0 = FattRuv_e / FattLisc_e;
        a = Math.pow(psipsi0, 0.67);
        b = Math.pow(NRey_e, 0.75);
        Nu_e = a * 0.0354 * (b - 180);
        do {
            CoefLiminI_i = fluido_i.CondicTermica(Tinput_i) * Nu_i / Dhi_i;
            CoefLiminI_e = fluido_e.CondicTermica(Tinput_e) * Nu_e / Dhe_i;

            // Calcolo coefficiente globale di scambio termico per cond coassiale   
            //Formula 23
            k12 = (1 / CoefLiminI_i) + tubo_i.Resterm() * SH + ((1 / CoefLiminI_e) * Dhi_i / Dhe_i);
            k12 = 1 / k12;

            //Fattore di raffreddamento formula 25 per tubi coassiali
            // Fluido caldo interno quindi superficie di scambio perimetro esterno 
            //tubo interno
            KR12 = tubo_i.Per_est() * k12 * lungh * ((1 / (M_e * fluido_e.CapTerm(Tmedia_e)))
                    + (1 / (M_i * fluido_i.CapTerm(Tmedia_i))));
            // Temperatura uscita caso coassiale, formula 28 condotto Interno
            Tuscita_i = (M_e * fluido_e.CapTerm(Tmedia_e)) / (M_i * fluido_i.CapTerm(Tmedia_i));
            Tuscita_i = Tuscita_i * (Tinput_e - Tuscita_e) + Tinput_i;
            // Temperatura media nel condotto caso  coassiale formula 30 
            Tmedia_i = (Tinput_i + Tuscita_i) / 2;
            Tmedia_e = (Tinput_e + Tuscita_e) / 2;
        } while (!((Tinput_i - Tuscita_e) / (Tinput_e - Tuscita_i) < 1.5));

    }

    public double getNu_i() {
        return Nu_i;
    }

    public double getNu_e() {
        return Nu_e;
    }

    public double getCoefLiminI_i() {
        return CoefLiminI_i;
    }

    public double getCoefLiminI_e() {
        return CoefLiminI_e;
    }

    public double getCoefLiminE_1() {
        return CoefLiminE_1;
    }

    public double getCoefLiminE_2() {
        return CoefLiminE_2;
    }

    public double getK12() {
        return k12;
    }

    public double getKR12() {
        return KR12;
    }

    public double getSH() {
        return SH;
    }

    public double getTuscita_i() {
        return Tuscita_i;
    }

    public double getTuscita_e() {
        return Tuscita_e;
    }

    public double getTmedia_i() {
        return Tmedia_i;
    }

    public double getTmedia_e() {
        return Tmedia_e;
    }

}
