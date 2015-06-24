package test;

public class TestO2 {
double fattori[][]={
		{0.05681,0.7189,0.1259,-0.01199},//N2
		{ -0.05413,0.7123,-0.09456,-0.08449}//CO2
		};
double cp[][]={
		{3.280,0.593e-3,0,0.040e5},//N2
		{ 5.457,1.045e-3,0,-1.157e5}//CO2
		}; 
double R[]={8314.472/28.013,8314.72/44.10};
	int CO2=1;
	int N2=0;
	public static void main(String[] args){
		TestO2 testo2=new TestO2();
		testo2.vai();
	}

	private void vai() {
		// TODO Auto-generated method stub
		double tstart=300;
		double t=tstart;
		int E=CO2;
		for(int x=0;x<10;x++){
			t+=50;
			double k=trefattori(E,t);
			String s= String.format("\nTemperatura %1.1f \tk=%1.5f", t,k);
		//	System.out.print(s);
		}
		t=tstart;
		for(int x=0;x<10;x++){
			t+=50;
			double cp_calc=cp_(E,t);
			String s= String.format("\n\tR %1.1f\tTemperatura %1.1f \tcp=%1.5f",R[E], t,cp_calc);
			System.out.print(s);
		}
	}
	
	double trefattori(double t){

		 double a1=-0.05413;
	     double b1=0.7123*1e-3;
	     double c1=-0.09456*1e-6;
	     double d1=-0.08449*1e-9;
			double x = a1 + b1 * t -c1 * Math.pow(t, 2.0)
					+d1*Math.pow(t, 3.0);
			
			  
  		return x/10 ;
	}
	/**
	 * conduc termica
	 * @param E
	 * @param t
	 * @return conduc termica
	 */
	double trefattori(int E,double t){

		 double a1=fattori[E][0];
	     double b1=fattori[E][1]*1e-3;
	     double c1=fattori[E][2]*1e-6;
	     double d1=fattori[E][3]*1e-9;
			double x = a1 + b1 * t -c1 * Math.pow(t, 2.0)
					+d1*Math.pow(t, 3.0);
			
			  
 		return x/10 ;
	}
	double cp_(int E,double t){

		 double a1=cp[E][0];
	     double b1=cp[E][1];
	     double c1=cp[E][2];
	     double d1=cp[E][3];
			double x = a1 + b1 * t +c1 * Math.pow(t, 2.0)
					+d1*Math.pow(t, -2.0);
			
			  
		return x*R[E];
	}
}
