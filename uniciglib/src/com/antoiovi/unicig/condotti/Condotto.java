/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.condotti;

import it.iovino.fluidi.Fluido;

/**
 *Interfaccia condotto 
 * @author antoiovi
 */
public interface Condotto {
    public void CalcolaTemper();
    // Somma le masse dei fluidi e crea la temperatura di ingresso, ed il fluido di ingresso
    public void Somma(Condotto c1,Condotto c2);
    // Pone i dati di c1 di uscita in ingresso 
    public void Serie(Condotto c1);
    // Imposta la massa
    public void setM1(double M1);
    // Reucper al aporatat massica [kg/s]
    public double M1();
    // ottiene la velocità media [m/s]
    public double Wm();
    // Ottine  la temperatura media
    public double Tm();
    // Ottine la tempertura di uscita
    public double Tu();
    // Ottine la pressione statica (B = +1 o -1)
    public double Ps(double B);
    // Pressione effettiva: calcolata in base alle pressioni dei 
    // condotti superiori; se sopra ho solo un comignolo metto la perdita del comignlo 
    // in negativo
    public double Peff(double PressStatSup);
    // Restituisce la pressone effettiva, già calcolata
    public double Peff();
    // Calcola le perdie di carico
    public double d_P();
    //restitusisce una perdita di carico in baese ad una csi 
    public double d_P(double csi);
    // MassaVolumica, DENSITA'
    public double ro_m();
    // fLUIDO INTERNO, E FLUIDO ESTERNO: NEI CONDOTTI NON COASSIALI FLUIDO INTERNO
    // E fLUIDO ESTERNO è L'AMBIENTE;
    public void setFluidoI(Fluido fluidoi,double m2, double T2);
    public Fluido fl_int();
    public Fluido fl_est();
    // Imposta altezza: se = >= lunghezza tubo condotto verticale e altezza= sviluppo tubo
    // se =0 tubo orizzontale 
    public void setH(double h);
    public double H(); // Altezza (differenza di altitudine), per sviluppo vedere lunghezza tubo
    // Lunghezza(=uguale a lunghezza tubo)
    public double Lung();
    // Portata aria interruttore tiraggio
    public double getMa();
    // Calcola e restituisce massa aria interruttore tiraggio
    public double MaIntTir(double csi_i,double csi_d,double Ai,double Ad);
    // Aggiunge un fluido con massa mw e temp T2 , ricalocla la temperatura
    public void addFluido(Fluido f2,double m2, double T2);
    // UTILIZZATI PER CONDOTTI COASSIALI
    // imposta il fluido interno e ricalcola temperature
    
    Condotto condottoi();
    Condotto condottoe();
    
    
}
