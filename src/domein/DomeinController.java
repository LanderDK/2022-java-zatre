package domein;

import java.util.ArrayList;
import java.util.List;

import gui.SpeelSchermMain;

public class DomeinController {
	private SpelerRepository sr = new SpelerRepository();
	private Spel spel;
	private SpelBord sb;
	private SpeelSchermMain sm;

	public void maakSpel(DomeinController dc) {
		sb = new SpelBord();
		spel = new Spel(sr.geefGeselecteerdeSpelers(), dc, sb);
	}

	public void startSpel() {
		spel.speelBeurt(true);
	}

	public void eindeSpel(String gebruikersNaam) {
		sm.eindeSpel(gebruikersNaam);
	}

	public void veranderSpeelKansenSpeler(Speler speler, int aantal) {
		speler.setAantalSpellen(speler.getAantalSpellen() + aantal);
		sr.updateSpelKansenSpeler(speler);
	}

	public void registreerSpeler(String gebruikersNaam, int geboorteJaar) {
		Speler speler = new Speler(gebruikersNaam, geboorteJaar);
		sr.voegSpelerToe(speler);
	}

	public void selecteerSpeler(String gebruikersNaam, int geboorteJaar) {
		Speler speler = new Speler(gebruikersNaam, geboorteJaar);
		sr.selecteerSpeler(speler);
	}

	public List<String[]> geefGeselecteerdeSpelers() {
		List<String[]> res = new ArrayList<>();
		for (Speler speler : sr.geefGeselecteerdeSpelers()) {
			res.add(speler.toStringArray());
		}
		return res;
	}

	public void getrokkenSteentjeClick(int button) {
		spel.getrokkenSteentjeClick(button);
	}

	public String[] geefLaatstGemaakteSpeler() {
		return sr.geefLaatstGemaakteSpeler().toStringArray();
	}

	public void opVakjeGeklikt(int x, int y) {
		spel.opVakjeGeklikt(x, y);
	}

	public void updateBeurt(Steen[] getrokkenSteentjes, Speler spelerAanDeBeurt) {
		this.sm.updateBeurt(naarIntArray(getrokkenSteentjes), spelerAanDeBeurt.getGebruikersNaam(),
				spelerAanDeBeurt.getScoreBlad().getScoreBlad());
	}

	public void setSpeelScherm(SpeelSchermMain speelscherm) {
		this.sm = speelscherm;
	}

	private int[] naarIntArray(Steen[] steentjes) {
		int[] res = new int[3];
		res[0] = steentjes[0].getWaarde();
		res[1] = steentjes[1].getWaarde();
		res[2] = steentjes[2].getWaarde();
		return res;
	}

	public ScoreBlad geefScoreBladVanSpeler(String gebruikersNaam) {
		ScoreBlad scoreBlad = new ScoreBlad();
		List<Speler> spelers = sr.geefGeselecteerdeSpelers();
		for (Speler speler : spelers) {
			if (speler.getGebruikersNaam() == gebruikersNaam) {
				scoreBlad = speler.getScoreBlad();
				break;
			}
		}
		return scoreBlad;
	}

	public List<List<String[]>> geefSpelBord() {// index 0 = type vakje; index 1 = waarde
		List<List<String[]>> res = new ArrayList<List<String[]>>();
		Vakje[][] vakjes = sb.getVakjes();
		for (int x = 0; x < 15; x++) {
			List<String[]> rij = new ArrayList<String[]>();
			for (int y = 0; y < 15; y++) {
				String[] vakjeString = new String[2];
				Vakje vakje = vakjes[x][y];
				vakjeString[0] = vakje.toString();
				if (!vakje.isLeeg()) {// als vakje een steentje bevat
					vakjeString[1] = Integer.toString(vakje.getSteen().getWaarde());
				} else
					vakjeString[1] = "0";
				rij.add(vakjeString);
			}
			res.add(rij);
		}
		return res;
	}
}