/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.iovino.fluidi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Viene conseiderata una miscela come somma di coppie (frazione PONDERALE,
 * FLUIDO) Tutte le propiet√† vengono calcolate cme media pesata con le frazioni
 * PONDERALI 20/08/2014 : Da verificare se alcune propiet√† vanno considerate con
 * la frazione MOLARE ... Da rivedere il calcolo della costante universale
 * R(costanteElasticit√†, in quanto dipende dalle frazioni molari)
 *
 * @author antoiovi
 */
public class Miscela implements Fluido {

    HashMap<Fluido, Double> map_p;// map ponderali elemneti base
    HashMap<Fluido, Double> map_m;// map_m molarielementi base
    List<Frazione> frazioni_ponderali;
    List<Frazione> frazioni_molari;
    double massamolareapparente;

    

    /**
     * Creo la miscela passando una lista di coppie <massa-fluido>
     *
     * @param pair_massa_fluido
     */
    public Miscela(List<PairMassaFluido> pair_massa_fluido) {
        // Crea la lista di frazioni ponderali
        frazioni_ponderali = new ArrayList<Frazione>();
        // Iteratore ad ogni coppia massa-fluido
        Iterator i_mf = pair_massa_fluido.iterator();
        double massaTot = 0;
        // Calcolo la massa totale
        while (i_mf.hasNext()) {
            PairMassaFluido pmf = (PairMassaFluido) i_mf.next();
            massaTot += pmf.massa;
        }
        // torno all'inizio dell'elenco coppie massa-fluido
        i_mf = pair_massa_fluido.iterator();
        while (i_mf.hasNext()) {
            PairMassaFluido pmf = (PairMassaFluido) i_mf.next();
            double m = pmf.massa;
            Fluido f = pmf.fluido;
            // Creo una frazione ponderale come coppia frazioneponderale-Fluido
            Frazione frazpond = new Frazione(m / massaTot, f);
            frazioni_ponderali.add(frazpond);
        }
   // CREO LE FRAZIONI MOLARI come lista di oggetti 
        //       FrazioneMolare= coppia frazionemolare-fluido
        frazioni_molari = new ArrayList<Frazione>();
        // torno all'inizio dell'elenco coppie massa-fluido
        i_mf = pair_massa_fluido.iterator();
// CREo una lista di coppie <Moli,Fluido>
        List<PairMoliFluido> listMoliFluido = new ArrayList<PairMoliFluido>();
// Calcolo le moli per ogni componente ed il numero di molir totali    
        double molitotali = 0;
        while (i_mf.hasNext()) {
            PairMassaFluido pmf = (PairMassaFluido) i_mf.next();
            double massa = pmf.massa;
            double massaMolare = pmf.fluido.MassaMolare();
            double numeromoli = massa / massaMolare;
            PairMoliFluido pairmolifluido = new PairMoliFluido(numeromoli, pmf.fluido);
            listMoliFluido.add(pairmolifluido);
            molitotali += numeromoli;
        }
        Iterator i = listMoliFluido.iterator();//puntatore alle copiie moli-fluido
        // Calcolo le frazioni molari
        while (i.hasNext()) {
            PairMoliFluido molefluido = (PairMoliFluido) i.next();
            double frazionemolare = molefluido.moli / molitotali;
            Frazione frazmol = new Frazione(frazionemolare, molefluido.fluido);
            frazioni_molari.add(frazmol);
        }

/** CREO HASMMAP CON FRAZIONI PONDERALI ELEMENTI BASE
 * 
 */
        map_p = new HashMap<Fluido, Double>();
        // Chianmo creamap, che recursivamente scandaglia l'albero fino agli 
        //      elementi base
        this.CreaMap(map_p, 1.0,frazioni_ponderali);
        // Creo la mappa frazioni molari elementi base     
        map_m = new HashMap<Fluido, Double>();
        this.CreaMap(map_m, 1.0,frazioni_molari);

        // CALCOLO LA MASSA MOLARE APPARENTE
        // = soommatoria di frazionemolare_i x Massamolare_i
        massamolareapparente=0;
        for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            massamolareapparente+=((entry.getKey().MassaMolare())*entry.getValue());
        }
            }
    /**
     * Creo la miscela passando una lista di coppie <massa-fluido>
     *
     * @param pair_moli_fluido
     * @paran mm Ë inserito solo per permettere un costruttore diverso
     * da quello List<PairMassaFluido>
     */
    public Miscela(List<PairMoliFluido> pair_moli_fluido,int mm) {
       /**
        * 1- creo le frazioni molari
        * 2- Creo l'hasmap con le frazioni molari degli elementi base
        * 3- creo le frazioni ponderali
        * 4- creo l'hasmap delle frazioni ponderali elementi base
        */
// 1 - a - Calcolo le moli totali 
    	// Crea la lista di frazioni molari
        frazioni_molari = new ArrayList<Frazione>();
        // Iteratore ad ogni coppia mole-fluido
        Iterator i_moli_fl = pair_moli_fluido.iterator();
        double moliTot = 0;
        // Calcolo le moli totale
        while (i_moli_fl.hasNext()) {
            PairMoliFluido pmf = (PairMoliFluido) i_moli_fl.next();
            moliTot += pmf.moli;
        }
// 1- b - Calcolo le frazioni molari        
        // torno all'inizio dell'elenco coppie moli-fluido
        i_moli_fl = pair_moli_fluido.iterator();
        while (i_moli_fl.hasNext()) {
            PairMoliFluido pmf = (PairMoliFluido) i_moli_fl.next();
            double frazmol = pmf.moli/moliTot;
            Fluido fluid = pmf.fluido;
            // Creo una frazione molare come coppia frazionemolare-Fluido
            Frazione f_mol = new Frazione(frazmol, fluid);
            frazioni_molari.add(f_mol);
        }
        
// 2 -  Creo le frazioni ponderali
     /* 2 a- CREO LE FRAZIONI PONDERALI come lista di oggetti 
               FrazionePonderale= coppia frazionponderale-fluido*/
        frazioni_ponderali = new ArrayList<Frazione>();
        // torno all'inizio dell'elenco coppie moli-fluido
        i_moli_fl = pair_moli_fluido.iterator();
        // CREo una lista di coppie <Massa,Fluido>
        List<PairMassaFluido> listMassaFluido = new ArrayList<PairMassaFluido>();
    /* 2 b- Calcolo le masse per ogni componente ed la massa totale*/    
        double massatotale = 0;
        while (i_moli_fl.hasNext()) {
            PairMoliFluido pair_mol_fl = (PairMoliFluido) i_moli_fl.next();
            double moli = pair_mol_fl.moli;
            double massaMolare = pair_mol_fl.fluido.MassaMolare();
            double massa = moli* massaMolare;
            PairMassaFluido pairmassafluido = new PairMassaFluido(massa, pair_mol_fl.fluido);
            listMassaFluido.add(pairmassafluido);
            massatotale += massa;
        }
   /* 2 c- Calcolo le frazioni ponderali     */
        // iteratore che puntata alle coppie massa-fluido
        Iterator i_mass_fl = listMassaFluido.iterator();
        // Calcolo le frazioni ponderali
        while (i_mass_fl.hasNext()) {
            PairMassaFluido pair_massafluido = (PairMassaFluido) i_mass_fl.next();
            double frazioneassica = pair_massafluido.massa / massatotale;
            Frazione frazpond = new Frazione(frazioneassica, pair_massafluido.fluido);
            frazioni_ponderali.add(frazpond);
        }

       
        
        // 3 CREO HASMMAP CON FRAZIONI MOLARI ELEMENTI BASE
        map_m = new HashMap<Fluido, Double>();
        /* 3 a- Chiamo fluido.CreaMap, che recursivamente scandaglia l'albero fino agli 
              		elementi base*/
        this.CreaMap(map_m, 1.0,frazioni_molari);
        // Creo la mappa frazioni ponderali elementi base     
        map_p = new HashMap<Fluido, Double>();
        this.CreaMap(map_p, 1.0,frazioni_ponderali);
// CALCOLO LA MASSA MOLARE APPARENTE
        // = soommatoria di frazionemolare_i x Massamolare_i
        massamolareapparente=0;
        for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            massamolareapparente+=((entry.getKey().MassaMolare())*entry.getValue());
        }
        
  /*      for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            System.out.println(" -Elemento :" + entry.getKey() + "frazione MOALRE :" + entry.getValue());
        }*/

    }

    /**
     * Calcola la pressione parziale del fluido gas; se il gas non Ë presente restituisce -1
     * Questo metodo non Ë presente in fluido, ma solo in Miscela, quindi per usarlo devo
     * fare attensione e fare un casting;
     * @param gas
     * @return
     */
    public double PressioneParzial(Fluido gas,double pressione){
    	
    	
    	if(map_m.get(gas)==null)
    		return -1;
    	else
    		return map_m.get(gas)*pressione;
    }
    
    @Override
    public double CostElasticita() {
        /**
         * Iterator i=componenti.iterator(); double costel=0;
         * while(i.hasNext()){ FrazionePonderale fp=(FrazionePonderale)i.next();
         * costel+=fp.fluido.CostElasticita()*fp.frazione; } return costel;
        *
         */
        return FluidiMath.Ru / MassaMolare();
    }

    
    /**
     * @return
     */
    @Override
    public double MassaMolare() {
       return massamolareapparente;
    }

    
    @Override
    public double CapTerm(double Temp) {
        // media pesata frazioni ponderali elementi base
        double cp2=0;
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
             cp2+=entry.getKey().CapTerm(Temp)*entry.getValue();
        }
        double cp3=0;
        for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            cp3+=entry.getKey().CapTerm(Temp)*entry.getValue();
        }
        
        return cp2;
    }


    @Override
    public double CondicTermica(double Temp) {
        double cp2=0;
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
             cp2+=entry.getKey().CondicTermica(Temp)*entry.getValue();
        }
        return cp2;
    }
// Densit√†
    @Override
    public double MassaVolumica(double Pressione, double Temperatura) {
        return Pressione / (this.CostElasticita() * Temperatura);
    }


//Media frazioni ponderali componenti
    @Override
    public double ViscositaDinamica(double Temperatura) {
        double vd=0;
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
             vd+=entry.getKey().ViscositaDinamica(Temperatura)*entry.getValue();
        }
/*       Iterator i = frazioni_ponderali.iterator();
        double v = 0;
        while (i.hasNext()) {
            FrazionePonderale fp = (FrazionePonderale) i.next();
            v += fp.fluido.ViscositaDinamica(Temperatura) * fp.frazione;
        }
        System.out.println("v-vd="+(v-vd));*/
        return vd;
    }

    @Override
    public double ViscCin(double Pressione, double Temperatura) {
        double vd=0;
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
             vd+=entry.getKey().ViscCin(Pressione, Temperatura)*entry.getValue();
        }
        return vd;
    }

    // Rapporto tra diffusdivita cinematica e diffudsivita termica (adimensionale)

    @Override
    public double NumeroPrandtl(double Pressione, double Temperatura) {
        return ViscCin(Pressione, Temperatura) / DiffusivitaTermica(Temperatura);
    }

    // Modeia ponderale frazioni ponderali
    @Override
    public double DiffusivitaTermica(double Temperatura) {
        double dt=0;
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
             dt+=entry.getKey().DiffusivitaTermica(Temperatura)*entry.getValue();
        }
        return dt;
    }
/**
 * Iterazione su frazioni :
 *  	se passo List<PairMassaFluido> calcolo frazioni ponderali	fluido base
 *  	se passo List<PairMoliFluido> calcolo frazioni molari	fluido base
 * 		inserisco i valori calcolati in un HasMap con 
 * 		<chiave,valore>=<Fluido base,Double_frazione>
 * 			
 */
    @Override
    public void CreaMap(Map<Fluido, Double> map, Double val,List frazioni) {
// Per ogni fluido della lista pair massa fluido
    	/** iterazione recursiva ... 
    	* All'elemento base esegue il seguente codice:
    	*   if(map.containsKey(this)){// se Ë gia presente
        *      map.put(this,map.get(this)+val); }else{
        *	  map.put(this, val)//altrimenti aggingo fluido e massa
    	*/
    	
        /*Iterator iter_pair_mass_fl = frazioni_ponderali.iterator();
        while (iter_pair_mass_fl.hasNext()) {
            FrazionePonderale frazpond = (FrazionePonderale) iter_pair_mass_fl.next();
            Fluido fluido = frazpond.fluido;
            double frazmass = frazpond.frazione;
            fluido.CreaMap(map, val * frazmass);

        }*/
    	Iterator iter_frazione = frazioni.iterator();
        while (iter_frazione.hasNext()) {
            Frazione frazione = (Frazione) iter_frazione.next();
            Fluido fluido = frazione.fluido;
            double frazmass = frazione.frazione;
            fluido.CreaMap(map, val * frazmass,frazioni);
        }

    }

   public class Frazione{
	   public double frazione;
       public Fluido fluido;
       Frazione(double frazion, Fluido fluid) {
           this.frazione = frazion;
           this.fluido = fluid;
       }
       public void setFrazioni(double frazion,Fluido fluid){
       	this.frazione = frazion;
           this.fluido = fluid;
       }
   }
    
    
        
    

    @Override
    public String toString() {
String formula="(";        
// Frazioni ponderali
        for (Map.Entry<Fluido, Double> entry : map_p.entrySet()) {
        double q=100*entry.getValue();
            String sval=String.format("%.2f", q);
            formula+=sval+"%"+
            entry.getKey().toString()+" + ";
        }
        formula+=" )peso; (";
                //  FRAZIONI MOLARI
        for (Map.Entry<Fluido, Double> entry : map_m.entrySet()) {
            double q=100*entry.getValue();
            String sval=String.format("%.2f", q);
            formula+=sval+"% "+
            entry.getKey().toString()+", ";
        }
        formula+=") Moli; ";
                return formula; //To change body of generated methods, choose Tools | Templates.
    }

}
