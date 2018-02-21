/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.antoiovi.unicig.tubi;

/**
 * Non viene definita la forma 
 * @author antoiovi
 */
public interface Tubo {
  public double Lung();// Sviluppo del tubo
  public double Resterm();// Resistenza termica parete
  public double Rug();// Rugosita
  
  // Valori calcolati tipici della forma del tubo , vengono calcolate nelle 
  // implementazioni tipiche della forma
  public double Dh();//Diametro idraulico
  public double Dhe();//Diametro idraulico esterno
  public double Spessore();  
  public double Per_est();  
  public double Per_int();  
  public double Area_int();
  public double Area_est();
  // Metto dei setter per le propieta per potere creare un tubo indipendente dalla forma
  // da tubi creati con forma nota, serve per creare un tubo interno ed un tubo esterno
  // nei tubi coassiali
  public void setDh(double dh);//Diametro idraulico
  public void setDhe(double dhe);//Diametro idraulico esterno
  public void setSpessore(double spess);  
  public void setPer_est(double per_est);  
  public void setPer_int(double per_int);  
  public void setArea_int(double area_int);
  public void setArea_est(double area_est);
  
  

  // setter per le proprietà indipendenti dalla forma
  public void setRug(double rugosita);
  public void setResterm(double restermica);
  public void setLung(double lunghezza);
  
}
