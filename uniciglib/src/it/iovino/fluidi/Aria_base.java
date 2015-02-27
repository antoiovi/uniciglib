/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.iovino.fluidi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aria base : implementazione di default dell'interfaccia Fluido, 
 * con valori costatntiti tipici dell'aria a composozione tipica
 * @author antoiovi
 */
public class Aria_base implements Fluido {
Fluido aria;
Fluido o2= OssigenoO2.getInstance();
Fluido n2=AzotoN2.getInstance();

public void Aria_base(){
	PairMoliFluido ossigeno=new PairMoliFluido(21,o2);
	PairMoliFluido azoto=new PairMoliFluido(79,n2);
	
	List<PairMoliFluido> listmoli=new ArrayList<PairMoliFluido>();
	listmoli.add(azoto);
	listmoli.add(ossigeno);
	aria=new Miscela(listmoli,0);
}

public double CostElasticita() {
	return aria.CostElasticita();
}

public double MassaMolare() {
	return aria.MassaMolare();
}

public double MassaVolumica(double Pressione, double Temperatura) {
	return aria.MassaVolumica(Pressione, Temperatura);
}

public double CapTerm(double Temp) {
	return aria.CapTerm(Temp);
}

public double CondicTermica(double Temp) {
	return aria.CondicTermica(Temp);
}

public double DiffusivitaTermica(double Temperatura) {
	return aria.DiffusivitaTermica(Temperatura);
}

public double ViscositaDinamica(double Temperatura) {
	return aria.ViscositaDinamica(Temperatura);
}

public double ViscCin(double Pressione, double Temperatura) {
	return aria.ViscCin(Pressione, Temperatura);
}

public double NumeroPrandtl(double Pressione, double Temperatura) {
	return aria.NumeroPrandtl(Pressione, Temperatura);
}

public void CreaMap(Map<Fluido, Double> map, Double val, List frazioni) {
	aria.CreaMap(map, val, frazioni);
}

    
}
