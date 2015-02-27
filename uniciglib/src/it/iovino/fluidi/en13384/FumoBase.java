package it.iovino.fluidi.en13384;

import java.util.List;
import java.util.Map;

import it.iovino.fluidi.Fluido;
/**
 * 30/12/2014 Implementazine della tabella Prospetto B.1 
 * @author antoiovi
 *
 */
public abstract class FumoBase implements Fluido {
double Hu;
double Vatr_min;
double VL_min;
double V_H2O;
double CO2max;
double SO2max;
double fm1;
double fm2;
double fr_no_cond;
double fr_cond;
double fR1;
double fR2;
double fc0;
double fc1;
double fc2;
double fc3;
double fw;
double fs1;
double fs2;

double CO2;

	
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

	/* (non-Javadoc)
	 * @see it.iovino.fluidi.Fluido#CapTerm(double)
	 */
	@Override
	public double CapTerm(double Temp) {
		double a=1011+0.05*Temp+0.0003*Math.pow(Temp, 2);
		double b=(fc0+fc1*Temp+fc2*Math.pow(Temp, 2))*CO2;
		double c= 1+fc3*CO2;
		return (a+b)/c;
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

}
