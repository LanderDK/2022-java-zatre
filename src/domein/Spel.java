package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import exceptions.foutAantalSpelersException;

public class Spel {
	private DomeinController dc;
	private List<Speler> spelers = new ArrayList<Speler>();
	private SpelBord spelBord;
	private SteentjesPot sp;
	private int beurtTeller = 0;
	private int aantalSpelers;
	private Steen[] getrokkenSteentjes;
	private List<int[]> vakjesGeklikt;
	private Steen inHand;
	private boolean eersteZet = true;
	private boolean eersteBeurt = true;
	private Speler spelerAanDeBeurt;

	public Spel(List<Speler> spelers, DomeinController dc, SpelBord bord) {
		this.dc = dc;
		aantalSpelers = spelers.size();
		setSpelers(spelers);
		bereidSpelersVoor();
		this.spelBord = bord;
		vakjesGeklikt = new ArrayList<>();
		sp = new SteentjesPot();
		Collections.shuffle(spelers);
	}

	public void speelBeurt(boolean volgende) {// true als een de volgende speler mag beginnen
		if (volgende) {
			beurtTeller++;
			if (beurtTeller == aantalSpelers)
				beurtTeller = 0;
			vakjesGeklikt = new ArrayList<>();
			getrokkenSteentjes = sp.geefSteentjes(eersteZet ? 3 : 2);
			if (getrokkenSteentjes == null)
				eindeSpel();
			this.spelerAanDeBeurt = spelers.get(beurtTeller);
		}
		dc.updateBeurt(getrokkenSteentjes, spelerAanDeBeurt);
	}

	public void opVakjeGeklikt(int x, int y) {
		boolean validatieOK = true;
		if (inHand != null) {// er is een steentje geselecteerd
			if (eersteZet) {// het is de eerste beurt
				if (x != 7 || y != 7) {// eerste zet is niet ok
					validatieOK = false;
					eersteZet = true;
				}
			}
			if (!eersteZet) {
				if (!spelBord.validatie1(x, y)) {// validatie 1 is niet ok
					validatieOK = false;
				}
			}
			if (!eersteBeurt) {
				if (!spelBord.validatie2(x, y, vakjesGeklikt)) {// validatie 4 is niet ok
					validatieOK = false;
				}
			}
			if (!eersteZet) {
				if (!spelBord.validatie3(x, y, inHand.getWaarde())) {// validatie 2 is niet ok
					validatieOK = false;
				}
			}
			if (validatieOK) {// als alles ok is
				registreerSteentje(x, y);
			} else {// speler moet een beurt opnieuw spelen als niet alles ok is
				speelBeurt(false);
			}
			if (getrokkenSteentjesOp()) {// als getrokken steentjes op zijn
				Collections.reverse(vakjesGeklikt);
				spelBord.validatie4(vakjesGeklikt, spelerAanDeBeurt.getScoreBlad());
				speelBeurt(true);
				eersteBeurt = false;
			}
		}
	}

	private void registreerSteentje(int x, int y) {

		vakjesGeklikt.add(new int[] { x, y, inHand.getWaarde() });
		spelBord.legSteentjeOpVakje(x, y, inHand);
		verwijderSteentjeInHand();
		inHand = null;
		dc.updateBeurt(getrokkenSteentjes, this.spelerAanDeBeurt);
		if (eersteZet)
			eersteZet = false;
	}

	private boolean getrokkenSteentjesOp() {
		return getrokkenSteentjes[0].getWaarde() == 0 && getrokkenSteentjes[1].getWaarde() == 0
				&& getrokkenSteentjes[2].getWaarde() == 0;
	}

	private void verwijderSteentjeInHand() {
		getrokkenSteentjes[Arrays.asList(getrokkenSteentjes).indexOf(inHand)] = new Steen(0);
	}

	public void getrokkenSteentjeClick(int button) {
		inHand = getrokkenSteentjes[button - 1];
	}

	private void bereidSpelersVoor() {
		for (Speler speler : spelers) {
			speler.setScoreBlad(new ScoreBlad());// geeft alle spelers een scoreblad
			dc.veranderSpeelKansenSpeler(speler, -1);// verminderd speelkansen met 1
		}
	}

	private void setSpelers(List<Speler> spelers) {
		if (aantalSpelers < 2 || aantalSpelers > 4)
			throw new foutAantalSpelersException();
		this.spelers = spelers;
	}

	private void eindeSpel() {
		dc.eindeSpel(vindWinnaar().getGebruikersNaam());
	}

	private Speler vindWinnaar() {
		Speler winnaar = spelers.get(0);
		int hoogsteScore = 0;
		for (Speler speler : spelers) {
			if (speler.getScoreBlad().getTotaal() > hoogsteScore) {
				winnaar = speler;
				hoogsteScore = speler.getScoreBlad().getTotaal();
			}
		}
		return winnaar;
	}
}
