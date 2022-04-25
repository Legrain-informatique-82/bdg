package fr.legrain.visualisation.controller;

import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t = new Test();
		System.err.println(new Date());
		
		for (int i = 0; i < 99093; i++) {
			t.f(i);
		}
		System.err.println(new Date());

	}
	
	public int f(int i) {
		if(i%1000==0) {
			System.err.println(i);
		}
		return i;
	}

}
