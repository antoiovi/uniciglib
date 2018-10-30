package com.antoiovi.unicig.condotti;
import com.antoiovi.unicig.tubi.*;
import com.antoiovi.unicig.fluidi.*;

/**
 * @author antoiovi
 */
public class CondottoCamino extends Condotto {


	/**
	* Usato per calcolo coefficiente liminare interno
	**/
	double frazContatEsterno=1;

	double tempEsterna;
	double frazioneEsterna;
	double pressione;
	Aria_base aria=new Aria_base();

	double massaVolAria;

	public CondottoCamino(Tubo _tubo,Fluido _fluido, double _portatamassica,double _frazContatEsterno ){
		super( _tubo, _fluido, _portatamassica,0.0);
		coeffLimEsterno=coeffLiminEsterno(_frazContatEsterno);

	}

	public static double coeffLiminEsterno(double frazioneEsterna){
		return frazioneEsterna*23+(1-frazioneEsterna)*8;
	}

	public void setFrazContatEsterno(double fraz){
		this.frazContatEsterno=fraz;
		coeffLimEsterno=coeffLiminEsterno(frazContatEsterno);
	}


/**
* @param Pressione : pressone del fluido interno; se calcolo tiraggio uso pressione atmosferica;
*
**/
	@Override
	public void Calcolatemperature(double Tinput,double Testerna,double pressione_){
		super.Calcolatemperature( Tinput, Testerna, pressione_);
		pressione=pressione_;
		tempEsterna=Testerna;
	}


 	/*********
	* Se Altezza è negativa; il carico statico sarà negativo (ovvero in discesa)
	* 'E il tiraggio teorico
	************/
	public double pressioneStatica(){
		return 9.81*altezza*(aria.MassaVolumica()- fluido.MassaVolumica() );
	}


}
