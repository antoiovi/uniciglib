/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le classi che estendono ed implementano questa classe devo inizzializzare nel
 * loro costruttore l'array propieta[][];
 * Le propieta 
 *  -densita
 *  -viscosita cinematica
 * - numero di prandtl
 * non vengono recuperate dalla tabella ma vengono calcolate in funzione delle altre
 *- la densita dipende dalla pressione e dalla massa molare
 * - la viscosita cinematica è = alla visdinamica/densita
 * -il numero di prandtl=viscoscinematica/diffusivitatermica
 * dove serve la densità c'e come parametro anche la Pressione.
 * @author antoiovi
 */
 public abstract class AbstractFluido implements Fluido{
// Numero di righe, cioè numero di temperature
public static final int NUM_TEMP=15;
// numero di colonne , cioè numero di propietà
public static final int NUM_PROP=8;
// Indice colonna temperatura
public static final int TEMPERATURA=0;
public static final int DENSITA=1;// Indice colonna densità [kg/m2]
// indice colonna calore specifico a pressione costante J/(m C)
public static final int CALORESPECIFICO=2; 
public static final int CONDUCIBILITATERMICA=3;// indice colonna conducibiltà termica W/(m C)
public static final int DIFFUSIVITATERMICA=4; // m2/s
public static final int VISCOSITADINAMICA=5;// kg(m s)
public static final int VISCOSITACINEMATICA=6;// m2/s
public static final int NUMERODIPRANDTL=7;// adimensionale
// Tabella delle propietà deve essere con 15 Righe e 8 Colonne
//  [RIGA][COLONNA]
//  propieta.length=NUMERO DI RIGHE
//  propieta[x].length=Numero di colonne della riga x
protected  double propieta[][];
// Esponenti per le colonne dove necessita
// moltiplicatore per valori colonna presi da tabella
protected double expDiffTerm=1;
protected double expViscDin=1;
protected double expViscCin=1;

// coefficienti del polinomio di Taylor per il calcolo del calore specifico a pressione costante
protected static  double a;
protected static  double b;
protected static  double c;
protected static  double d;

/* Calore specifico, conducibilit� termica, viscosit� dinamica dipendono solo dalla temperatura
 * 
 * 
 */

protected void init(String filename){
	try{
	        // Apre il file "nomefile" presente nel package
        InputStream is=getClass().getResourceAsStream(filename);// per legger i dati
        InputStream is2=getClass().getResourceAsStream(filename);// per contare le righe
       // Crea un buffered reader per il file, in modo da leggere linee di testo una 
        // alla volta
  BufferedReader br = new BufferedReader(new InputStreamReader(is));
  BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
  int r=0; 
      while((br2.readLine())!= null){
	    	r++;
  }
  
  propieta= new double[r][8];
  String strLine;
  int x=0; 
    while((strLine = br.readLine())!= null)
        {
          // Creo uno scanner per scannerizzare la stringa
            Scanner scanner = new Scanner(strLine);
            //without this line the program wouldn't work on machines with different locales
            scanner.useLocale(Locale.US); 
            scanner.useDelimiter(";\\s*");
            int y=0;
            while (scanner.hasNextDouble()) {
                propieta[x][y]=scanner.nextDouble();
                y++;
            }
            x++;
        } 
    } catch (IOException ex) {
        Logger.getLogger(AzotoN2.class.getName()).log(Level.SEVERE, null, ex);
    }catch(java.lang.NullPointerException e){
        Logger.getLogger(AzotoN2.class.getName()).log(Level.SEVERE, null, e);
    }
}

/**
 * @param frazioni in questio caso � inutilizzato perche siamo in un elemento base
 * 			in miscela serve per sapere se sto iterando tra frazioni molari o frazioni ponderali
 */
 
@Override
public void CreaMap(Map<Fluido, Double> map, Double val, List frazioni) {
      if(map.containsKey(this)){
     // se presente sommo la massa a quella gia presente
     map.put(this,map.get(this)+val);
 }else{
     // altrimenti aggingo fluido e massa
     map.put(this, val);
 }
  }

   /*
    *Costante dei gas J/(K kmol)
    */
    @Override
    public double CostElasticita() {
        /**
     * Costante universale dei gas J/(K kmol)
     * public static final double Ru=8314.472;
     */
        return FluidiMath.Ru/MassaMolare();
    }
/**
 * 1- Massa Volumica (Densita)
 * @param Pressione
 * @param Temperatura
 * @return 
 */
    @Override
    public double MassaVolumica(double Pressione, double Temperatura) {
        return Pressione/(this.CostElasticita()*Temperatura);
    }

    
// 2- Calore specifico(Dipende solo dalla temperatura)
    // kj/(kmol K)
@Override
    public double CapTerm(double Temp) {
	String s=String.format("%1.3f\t%1.3f\t%e\t%e",a,b,c,d);
	//System.out.println(s);
	double x=a+b*Temp+c*Math.pow(Temp,2)+d*Math.pow(Temp, 3);
	double y=1000*x/MassaMolare();
	s=String.format("somma= %f  somma/massamolare per mille= %f", x,y);
	//System.out.println(s);
	//return y;
  return getPropieta(CALORESPECIFICO,Temp);
    }

// 3 - cONDUCIBILITA TERMICA
    @Override
    public double CondicTermica(double Temp) {
                return getPropieta(CONDUCIBILITATERMICA,Temp);
    }
//4- diffusività termica
    @Override
    public double DiffusivitaTermica(double Temperatura) {
           return getPropieta(DIFFUSIVITATERMICA,Temperatura)*expDiffTerm;
    }
    
    // 5- Viscosita dinamica
    @Override
    public double ViscositaDinamica(double Temperatura) {
        return getPropieta(VISCOSITADINAMICA,Temperatura)*expViscDin;
    }
    
// 6 -viscosita cinematica
@Override
    public double ViscCin(double Pressione, double Temperatura) {
        return ViscositaDinamica(Temperatura)/MassaVolumica(Pressione,Temperatura);
    }
    // Rapporto tra diffusdivita cinematica e diffudsivita termica (adimensionale)
@Override
    public double NumeroPrandtl(double Pressione, double Temperatura) {
        return ViscCin(Pressione,Temperatura)/DiffusivitaTermica(Temperatura);
    }

 /**
     * restituisce un array contenente la colonna ncol della tabella
     * delle propietà
     * @param ncol
     * @return 
     */
    public double[] getCol(int ncol){
        double col[]=new double[propieta.length];
        for(int x=0;x<propieta.length;x++){
            col[x]=propieta[x][ncol];
        }
        return col;
    }
    /**
     * Restituisce una array contenente la riga nriga di valori della tabella
     * @param nrow
     * @return 
     */
    public double[] getRow(int nrow){
        double row[]=new double[propieta[nrow].length];
        for(int x=0;x<propieta[nrow].length;x++){
            row[x]=propieta[nrow][x];
        }
        return row;
    }
    /**
     * Restituisce l'indice di riga della tabella in base alla temperatura:
     * retsituisce l'indice della prima riga precedente a temperatura inferiore
     * @param temperature
     * @return 
     */
    public int getRowBeforeTemperature(double temperature){
        int row=0;
        for(int x=0;x<propieta.length;x++){
            double T=propieta[x][TEMPERATURA];
            if(T==temperature){
                break;
            }else if(temperature<T){// "T letta" SCAVALCA temperature
                row--;
                break;
            }
            row++;
        }
        if(row<0)
            row++;
        return row;
    }
    
    /**
     * Restituisce l'indice di riga della tabella in base alla temperatura:
     * retsituisce l'indice della prima riga successiva a temperatura superiore
     * @param temperature
     * @return 
     */
    public int getRowAfterTemperature(double temperature){
        int row=0;
        for(int x=0;x<propieta.length;x++){
            double T=propieta[x][TEMPERATURA];
            if(T==temperature){
                break;
            }else if(temperature<T){// T letta SCAVALCA temperature
                break;
            }
            row++;
        }
        if(row>propieta.length)
            row--;
        return row;
    }
    
    /**
     * 
     * @param propieta intero contenente l'indice colonna propietà: 0-8
     *  TEMPERATURA=0;
        DENSITA=1;
        CALORESPECIFICO=2;
        CONDUCIBILITATERMICA=3;
    * @param Temperatura TEMPERATURA a cui si vuole ottenere la propietà
     * @return la propietà alla data temperatura
     */
    public double getPropieta(int PROPIETA,double Temperatura){
        int rowbefore=this.getRowBeforeTemperature(Temperatura);
        int rowafter=this.getRowAfterTemperature(Temperatura);
        if(rowbefore==rowafter){
            return propieta[rowafter][PROPIETA];
        }
        return Interpolation(
                propieta[rowbefore][TEMPERATURA],propieta[rowafter][TEMPERATURA],
                propieta[rowbefore][PROPIETA],propieta[rowafter][PROPIETA],
                Temperatura);
     }
    
    
 protected double Interpolation(double T1,double T2,double P1,double P2,double T){
//Calculate slope from p1 to p2 
double m = (T2-T1)/(P2-P1); 
double a=P1*(T-T2)/(T1-T2);
double b=P2*(T-T1)/(T1-T2);
return a-b;        
    }
    
    
}
