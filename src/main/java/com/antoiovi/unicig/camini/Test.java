package com.antoiovi.unicig.camini;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

	public static void main(String[] args) {

		/*double secondari[] = { 4.7, 5.1, 5.4, 5.7, 6, 10.5 };
		double second_p[] = { 4.20, 8.76, 5.7, 3.28, 1.93, 2.74 };
		double primari[] = { 0, 4.36, 4.33, 4.37, 4.42, 7.46 };
		double primar_p[] = { -3.3, -1, -0.06, 0.48, 0.93, 1.74 };
		double canali[] = 	{ 0.49, 0.54, 0.57, 0.59, 0.63, 0.67 };
		double canali_p[] = { 4.32, 3.44, 3.01, 2.66, 2.35, 2.03 };*/

		double primari[] = {  1, 1, 1, 1, 1,1 };
		double primar_p[] = { 0, 0, 0, 0, 0,0 };
		double secondari[] = {1, 1, 1, 1, 1,1 };
		double second_p[] = { 0, 0, 0, 0, 0,0 };
		double canali[] = 	{ 1, 1, 1, 1, 1,1 };
		double canali_p[] = { 0, 0, 0, 0, 0,0 };
		
		// TODO Auto-generated method stub
		/*
		 * Tratto t1=new Tratto(0 ,-0.97-0.34); Tratto t2=new Tratto(5
		 * ,-0.03+0.16); Tratto t3=new Tratto(4 ,0.12+0.99); Tratto t4=new
		 * Tratto(4 ,0.13+1.92); Tratto t5=new Tratto(4 ,0.12+2.99); Tratto
		 * t6=new Tratto(7 ,0+5.39);
		 */

		List<Pressioni> tratti = new ArrayList();
		tratti.add(new Pressioni(primari[0], primar_p[0]));
		tratti.add(new Pressioni(primari[1], primar_p[1]));
		tratti.add(new Pressioni(primari[2], primar_p[2]));
		tratti.add(new Pressioni(primari[3], primar_p[3]));
		tratti.add(new Pressioni(primari[4], primar_p[4]));
		tratti.add(new Pressioni(primari[5], primar_p[5]));
		
		List<Pressioni> tratti2 = new ArrayList();
		tratti2.add(new Pressioni(secondari[0], second_p[0]));
		tratti2.add(new Pressioni(secondari[1], second_p[1]));
		tratti2.add(new Pressioni(secondari[2], second_p[2]));
		tratti2.add(new Pressioni(secondari[3], second_p[3]));
		tratti2.add(new Pressioni(secondari[4], second_p[4]));
		tratti2.add(new Pressioni(secondari[5], second_p[5]));
		List<Pressioni> tcanali = new ArrayList();
		tcanali.add(new Pressioni(canali[0], canali_p[0]));
		tcanali.add(new Pressioni(canali[1], canali_p[1]));
		tcanali.add(new Pressioni(canali[2], canali_p[2]));
		tcanali.add(new Pressioni(canali[3], canali_p[3]));
		tcanali.add(new Pressioni(canali[4], canali_p[4]));
		tcanali.add(new Pressioni(canali[5], canali_p[5]));
		
		CaminoCCR camino = new CaminoCCR(tratti, tratti2,tcanali,0);
		camino.calcola();
		System.out.println(camino.getSwr().toString());		
		List press=camino.getPrimario();
		for(int x=0;x<press.size();x++){
			System.out.println("Peffetiiva piano"+x+"  "+((Pressioni)press.get(x)).peffettiva);
		}
	}

}
