/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.iovino.fluidi;

import java.util.List;
import java.util.Map;

/**
 *
 * @author antoiovi
 */
public interface Fluido {
  public double CostElasticita();
  public double MassaMolare();
  
  public double MassaVolumica(double Pressione,double Temperatura);
  public double CapTerm(double Temp);// da tabella
  public double CondicTermica(double Temp);// da tabella
  public double DiffusivitaTermica(double Temperatura);// da tabella
  public double ViscositaDinamica(double Temperatura);
  // Dipende dalla densita quindi passo anche la pressione per il calcolo della densita
  public double ViscCin(double Pressione,double Temperatura);
     // Rapporto tra diffusdivita cinematica e diffudsivita termica (adimensionale)
  public double NumeroPrandtl(double Pressione,double Temperatura);
  public void CreaMap(Map<Fluido,Double> map,Double val,List frazioni);
  
  
  
  
}
