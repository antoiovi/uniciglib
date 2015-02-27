/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antoiovi.unicig.condotti;
import com.antoiovi.unicig.tubi.Tubo;
import com.antoiovi.unicig.tubi.TuboCoass;

import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela;
import it.iovino.fluidi.PairMassaFluido;

import java.util.ArrayList;
import java.util.List;
/**
 * La classe implementa Condotto perchè così posso utilizzarla quando
 * 
 *  Si ritiene che il fluido inetrno sia il più caldo; 
 *  le operazioni sulla classe genitrice vengono fatte sul condotto interno, in modo
 *  che quando metto un canale da fumo esso può essere sia normale che concentrico, 
 *  basta che estenda la classe Condotto.
 *     
 * @author antoiovi
 */
public class CondottoConcBase implements Condotto{
double Tamb;// Tempereatura ambiente
double patm;// Pressione atmosferica
double tempiniz_condint,tempmed_condint; // TemperaturaInziale Iniziale , e TemperaturaMedia  condotto interno 
double tempiniz_condest,tempmed_condest;// TemperaturaInziale Iniziale , e TemperaturaMedia  condotto esterno
double massa_condint,massa_condest;// Massa fluido condotto interno e condotto esterno
double CoefLimEst;// Coefficiente Liminare esterno
double Altezza;// Se tubo verticale altezza=lunghezza tubo; altrimenti <
Condotto condotto_i;
Condotto condotto_e;
// Restituisce tubuInterni,
// e tubo esterno come ciambella...
TuboCoass tubo_coassiale;

Fluido fluidoi;
Fluido fluidoe;

//incapsula CoeffLimin inetrnied esterni, CoeffGlobScambTermico, FattoreRaffr
// Temperature fluido input, media e uscita
VarTempCoas vartempcoas;
/*Incapsula e  Vengono calcolati per il condotto interno ed esterno : 
     *  MassaVolumicaMedia
     *  VelocitaMedia
     *  NumerodiReynold
     *  Fattore di attrito liscio
     *  Fattore di attrito ruvido
     */
VelocitaFluido velocitafluido_i;
VelocitaFluido velocitafluido_e;


public CondottoConcBase(TuboCoass tubo, double Patm, double CoefLimE,
        double M_i,double M_e, double Ta, double Ti_i,double Ti_e, Fluido FluidoI,Fluido FluidoE) {
        // Tubo coassiale
        this.tubo_coassiale = tubo;
        Altezza=tubo.Lung();// Tubo VERTICALE
        patm = Patm;
        Tamb = Ta;
        massa_condint = M_i;
        massa_condest = M_e;
        this.tempiniz_condint=Ti_i ;
        this.tempiniz_condest=Ti_e ;
        fluidoi = FluidoI;
        fluidoe=FluidoE;
        this.CoefLimEst=CoefLimE;// non itlizzato perchè non considero lo scambio con l'esterno
        velocitafluido_i=new VelocitaFluido();
        velocitafluido_e=new VelocitaFluido();
        vartempcoas=new VarTempCoas();
        
        condotto_i=new CondottoBase(tubo.Tubo_interno(),Patm,CoefLimEst  ,M_i,Tamb,Ti_i,fluidoi);
        
        condotto_e=new CondottoBase(tubo.Tubo_esterno(),Patm,CoefLimEst  ,M_e,Tamb,Ti_e,fluidoe);
        
      }

public void Calcola(){
    

// primo ciclo metto Tm=Ti
    velocitafluido_i.Calcola(patm,massa_condint, tempmed_condint, fluidoi, tubo_coassiale.Tubo_interno());
    velocitafluido_e.Calcola(patm,massa_condest, tempmed_condest, fluidoe, tubo_coassiale.Tubo_esterno());
    
    vartempcoas.Calcola(fluidoi, fluidoe,tubo_coassiale.Tubo_interno(), velocitafluido_i, velocitafluido_e,massa_condint,massa_condest,tempiniz_condint,tempiniz_condest);
    tempmed_condint=vartempcoas.getTmedia_i();
    tempmed_condest=vartempcoas.getTmedia_e();
 }

/**
 * INDIPENDINTI DAL NON COASSIALE:
 */
@Override
public void setH(double h) {
if(h>=tubo_coassiale.Lung()){
	Altezza=tubo_coassiale.Lung();
}else{
	Altezza=h;
}}
//Imposta altezza: se = >= lunghezza tubo condotto verticale e altezza= sviluppo tubo
//se =0 tubo orizzontale 

@Override
public double H() {
return Altezza;
}
@Override
public double Lung(){
	return tubo_coassiale.Lung();
}

/******************************************************************
 * METODI IMPLEMENTATI DI CONDOTTO: RIFERITI AL CONDOTTO INTERNO
public Fluido fl_int();
public Fluido fl_est();

Condotto condottoi();
Condotto condottoe();

// Aggiunge un fluido con massa mw e temp T2 , ricalocla la temperatura
public void addFluido(Fluido f2,double m2, double T2);

// imposta il fluido interno e ricalcola temperature
public void setFluidoI(Fluido fluidoi,double m2, double T2);
  */

@Override
public void Somma(Condotto c1, Condotto c2) {
    condotto_i.Somma(c1.condottoi(), c2.condottoi());
    massa_condint=condotto_i.M1();
    tempiniz_condint=condotto_i.Tm();
    tempmed_condint=tempiniz_condint;
    condotto_e.Somma(c1.condottoe(), c2.condottoe());
    massa_condest=condotto_e.M1();
    tempiniz_condest=condotto_e.Tm();
    tempmed_condest=tempiniz_condest;
}

@Override
public void Serie(Condotto c1) {
    condotto_i.Serie(c1.condottoi());
    condotto_e.Serie(c1.condottoe());
    massa_condint=condotto_i.M1();
    tempiniz_condint=condotto_i.Tm();
    tempmed_condint=tempiniz_condint;
    massa_condest=condotto_e.M1();
    tempiniz_condest=condotto_e.Tm();
    tempmed_condest=tempiniz_condest;
    
}
@Override
public void CalcolaTemper() {
velocitafluido_i.Calcola(patm,massa_condint, tempmed_condint, fluidoi, tubo_coassiale.Tubo_interno());
velocitafluido_e.Calcola(patm,massa_condest, tempmed_condest, fluidoe, tubo_coassiale.Tubo_esterno());
tempmed_condint=vartempcoas.getTmedia_i();
tempmed_condest=vartempcoas.getTmedia_e();
}

/******************************************************************
 * METODI IMPLEMENTATI DI CONDOTTO: RIFERITI AL CONDOTTO INTERNO
********************************************************************/

/**
 * iMPOSTA LA MASSA DEL FLUIDO INTERNO
 */
@Override
public void setM1(double M1) {
    this.massa_condint=M1;
}

/**
 * @return  Massa fluido interno
 */
@Override
public double M1() {
    return massa_condint;
}


@Override
public double Wm() {
    return this.velocitafluido_i.getVelocitaMedia();
}

@Override
public double Tm() {
    return this.tempmed_condint;
}

@Override
public double Tu() {
    return this.vartempcoas.getTuscita_i();
}

@Override
public double Ps(double B) {
    return condotto_i.Ps(B);
}

@Override
public double Peff(double PressStaticSuper) {
    return condotto_i.Peff(PressStaticSuper);
}

// Restituisce la pressione ffettiva( gi� calcolata in prcedemnza )
@Override
public double Peff() {
	   return condotto_i.Peff();
}

@Override
public double d_P() {
	return condotto_i.d_P();
}

@Override
public double d_P(double csi) {
	return condotto_i.d_P(csi);
}

@Override
public double ro_m() {
    return velocitafluido_i.getMassaVolumicaMedia();
}
@Override
public void addFluido(Fluido f2,double m2, double T2){
	condotto_i.addFluido(f2, m2, T2);
	massa_condint=condotto_i.M1();
    tempiniz_condint=condotto_i.Tm();
    tempmed_condint=tempiniz_condint;
}

/******************************************************************
 * fINE METODI CHE RESTITUISCONO I DATI CONDOTTO INTERNO E CHE IMPLEMENTANO
 * L'INTERFACCIA CONDOTTO;
 * PER OTTENERE I FATI DEL CONDOTTO ESTERNO UTILIZZARE getCondottoe()....
 **********************************************************************/
   
    
    @Override
	public void setFluidoI(Fluido fluidoi, double m2, double T2) {
        massa_condint = m2;
        this.tempiniz_condint=T2 ;
        fluidoi = fluidoi;
	}
    
    // Fluido condotto interno
    @Override
    public Fluido fl_int() {
        return this.fl_int();
    }
    // Fluido condotto esterno; nei condotti non coassiali
    // questo � l'ambiente: 
    @Override
    public Fluido fl_est() {
        return this.fluidoe;
    }

    @Override
    public Condotto condottoi() {
        return condotto_i;
    }

    @Override
    public Condotto condottoe() {
        return condotto_e;
    }

 /**
 * 	NON UTILIZZATI IN CONDOTTI COASSIALI
 */
	@Override
	public double getMa() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double MaIntTir(double csi_i, double csi_d, double Ai, double Ad) {
		// TODO Auto-generated method stub
		return 0;
	}

	
   
   
   
}
