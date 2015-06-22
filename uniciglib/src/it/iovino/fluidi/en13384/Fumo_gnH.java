package it.iovino.fluidi.en13384;

import it.iovino.fluidi.Fluido;
import it.iovino.fluidi.Miscela.Frazione;

import java.util.List;
import java.util.Map;

public class Fumo_gnH implements Fluido {

	@Override
	public double CostElasticita() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double MassaMolare() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double MassaVolumica(double Pressione, double Temperatura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double CapTerm(double Temp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double CondicTermica(double Temp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double DiffusivitaTermica(double Temperatura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ViscositaDinamica(double Temperatura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double ViscCin(double Pressione, double Temperatura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double NumeroPrandtl(double Pressione, double Temperatura) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void CreaMap(Map<Fluido, Double> map, Double val, List frazioni) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Frazione> getFrazioni_ponderali() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frazione> getFrazioni_molari() {
		// TODO Auto-generated method stub
		return null;
	}

}
