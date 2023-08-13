package main;

import cui.ZatreApplicatie;
import domein.DomeinController;

public class Startup {

	public static void main(String[] args) {
		new ZatreApplicatie(new DomeinController()).start();
	}

}