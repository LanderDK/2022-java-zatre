package domein;

import java.util.ArrayList;
import java.util.List;

public class SpelBord {
	private Vakje[][] vakjes = new Vakje[15][15];
	private List<String> richtingenOverSlaan;
	private final String[] richtingen = { "links", "rechts", "boven", "onder" };

	public SpelBord() {
		richtingenOverSlaan = new ArrayList<String>();
		aanmaakWit();
		aanmaakGrijs();
		aanmaakMuur();
	}

	public void legSteentjeOpVakje(int x, int y, Steen steentje) {
		this.vakjes[x][y].legSteen(steentje);
	}

	// als er een steendje op het vakje ligt = false
	// en als er geen steentje naast ligt = false
	public boolean validatie1(int x, int y) {
		if (!this.vakjes[x][y].isLeeg())
			return false;
		int testWaarde = x - 1;
		if (testWaarde < 15 && testWaarde > 0) {
			if (!this.vakjes[testWaarde][y].isLeeg())
				return true;
		}
		testWaarde = x + 1;
		if (testWaarde < 15 && testWaarde > 0) {
			if (!this.vakjes[testWaarde][y].isLeeg())
				return true;
		}
		testWaarde = y - 1;
		if (testWaarde < 15 && testWaarde > 0) {
			if (!this.vakjes[x][testWaarde].isLeeg())
				return true;
		}
		testWaarde = y + 1;
		if (testWaarde < 15 && testWaarde > 0) {
			if (!this.vakjes[x][testWaarde].isLeeg())
				return true;
		}
		return false;
	}

	public boolean validatie2(int x, int y, List<int[]> vakjesGeklikt) {

		boolean validatieOK = true;
		if (!vakjes[x - 1][y].isLeeg()) {
			if (vakjeGekliktDezeBeurt(x - 1, y, vakjesGeklikt)) {
				validatieOK = false;
			} else {
				validatieOK = true;
			}
		}
		if (!vakjes[x + 1][y].isLeeg()) {
			if (vakjeGekliktDezeBeurt(x + 1, y, vakjesGeklikt)) {
				validatieOK = false;
			} else {
				validatieOK = true;
			}
		}
		if (!vakjes[x][y - 1].isLeeg()) {
			if (vakjeGekliktDezeBeurt(x, y - 1, vakjesGeklikt)) {
				validatieOK = false;
			} else {
				validatieOK = true;
			}
		}
		if (!vakjes[x][y + 1].isLeeg()) {
			if (vakjeGekliktDezeBeurt(x, y + 1, vakjesGeklikt)) {
				validatieOK = false;
			} else {
				validatieOK = true;
			}
		}
		return validatieOK;
	}

	// controlleerd of dat het totaal in één richting niet boven de 12 is
	// en als er op een grijs vakje werd geklikt, het totaal boven of gelijk aan 10
	// is
	public boolean validatie3(int x, int y, int waarde) {
		boolean grijsVakje = vakjes[x][y] instanceof GrijsVakje;
		boolean boven9 = false;
		for (String richting : richtingen) {
			int totaal = berekenTotaalRichting(x, y, waarde, richting, null);
			if (totaal > 12)
				return false;
			if (grijsVakje && totaal >= 10)
				boven9 = true;
		}
		if (!grijsVakje)
			return true;
		else
			return boven9;
	}

	public void validatie4(List<int[]> vakjesGeklikt, ScoreBlad scoreBlad) {
		boolean kruisje = false;
		for (int[] vakje : vakjesGeklikt) {
			for (String richting : richtingen) {
				if (richtingenOverSlaan.size() > 0) {
					if (richtingenOverSlaan.get(0) == "horizontaal" && (richting == "rechts" || richting == "links")) {
						richtingenOverSlaan.remove(0);
						break;
					}
					if (richtingenOverSlaan.get(0) == "verticaal" && (richting == "boven" || richting == "onder")) {
						richtingenOverSlaan.remove(0);
						break;
					}
				}
				if (vakjes[vakje[0]][vakje[1]] instanceof GrijsVakje)
					kruisje = true;
				int totaal = berekenTotaalRichting(vakje[0], vakje[1], vakje[2], richting, vakjesGeklikt);
				if (totaal >= 10 && totaal <= 12)
					scoreBlad.voegScoreToe(totaal);
			}
			if (kruisje)
				scoreBlad.voegDubbelToe();
		}
		scoreBlad.eindeBeurt();
	}

	private int berekenTotaalRichting(int x, int y, int waarde, String richting, List<int[]> vakjesGeklikt) {
		int totaal = waarde;
		int tellerX = 0, tellerY = 0;
		int offsetX = 0, offsetY = 0;
		if (richting.equals("links")) {
			offsetX = -1;
		} else if (richting.equals("rechts"))
			offsetX = 1;
		else if (richting.equals("boven"))
			offsetY = -1;
		else
			offsetY = 1;

		while (x + tellerX >= 0 && x + tellerX <= 14 && y + tellerY >= 0 && y + tellerY <= 14) {
			tellerX += offsetX;
			tellerY += offsetY;
			if (!this.vakjes[x + tellerX][y + tellerY].isLeeg()) {// vakje heeft een steentje
				if (vakjesGeklikt != null) {
					if (vakjeGekliktDezeBeurt(x + tellerX, y + tellerY, vakjesGeklikt)) {
						richtingenOverSlaan
								.add(richting == "links" || richting == "rechts" ? "horizontaal" : "verticaal");
						break;
					}
				}
				totaal += this.vakjes[x + tellerX][y + tellerY].getSteen().getWaarde();
			} else {
				break;
			}
		}
		return totaal;
	}

	public void maakVakjeLeeg(int x, int y) {
		this.vakjes[x][y].verwijderSteen();
	}

	private boolean vakjeGekliktDezeBeurt(int x, int y, List<int[]> vakjesGeklikt) {
		for (int[] vakje : vakjesGeklikt) {
			if (x == vakje[0] && y == vakje[1])
				return true;
		}
		return false;
	}

	private void aanmaakGrijs() {
		int punt1 = 1;
		int punt2 = 13;
		for (int i = 1; i < 15; i++) {
			this.vakjes[i][punt2] = new GrijsVakje();
			this.vakjes[i][punt1] = new GrijsVakje();
			if (i < 7) {
				punt1++;
				punt2--;
			} else {
				punt1--;
				punt2++;
			}
		}
		int[][] grijsArray = { { 0, 6 }, { 0, 8 }, { 14, 8 }, { 14, 6 }, { 6, 0 }, { 8, 0 }, { 5, 14 }, { 8, 14 } };
		for (int i = 0; i < grijsArray.length; i++)
			this.vakjes[grijsArray[i][0]][grijsArray[i][1]] = new GrijsVakje();
	}

	private void aanmaakMuur() {
		int[][] muurArray = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 7 }, { 0, 11 }, { 0, 12 }, { 0, 13 },
				{ 0, 14 }, { 1, 0 }, { 1, 14 }, { 2, 0 }, { 2, 14 }, { 3, 0 }, { 6, 14 }, { 7, 0 }, { 7, 14 },
				{ 11, 0 }, { 11, 14 }, { 12, 0 }, { 12, 14 }, { 13, 0 }, { 13, 14 }, { 14, 0 }, { 14, 1 }, { 14, 2 },
				{ 14, 3 }, { 14, 7 }, { 14, 11 }, { 14, 12 }, { 14, 13 }, { 14, 14 } };
		for (int i = 0; i < muurArray.length; i++)
			this.vakjes[muurArray[i][0]][muurArray[i][1]] = new Muur();
	}

	private void aanmaakWit() {
		for (int i = 0; i < 15; i++) {
			for (int e = 0; e < 15; e++) {
				this.vakjes[i][e] = new WitVakje();
			}
		}
	}

	public Vakje[][] getVakjes() {
		return this.vakjes;
	}
}
