//0 = x2
//1 = 10 (1pt)
//2 = 11 (2pt)
//3 = 12 (4pt)
//4 = Bonus
//5 = totaal
package domein;

import java.util.ArrayList;
import java.util.List;

public class ScoreBlad {
	private List<List<Integer>> scoreBlad;
	private int totaal = 0;
	private final int MAX_AANTAL_RIJEN = 22;
	private final int DUBBEL = 0;
	private final int TIEN = 1;
	private final int ELF = 2;
	private final int TWAALF = 3;
	private final int BONUS = 4;
	private final int TOTAAL = 5;
	private boolean alToegevoegd10;
	private boolean alToegevoegd11;
	private boolean alToegevoegd12;

	public ScoreBlad() {
		alToegevoegd10 = false;
		alToegevoegd11 = false;
		alToegevoegd12 = false;
		maakNieuwScoreBlad();
		voegBonussenToe();
	}

	public void voegDubbelToe() {
		scoreBlad.get(BONUS).add(0);
		scoreBlad.get(DUBBEL).add(1);
	}

	public void voegScoreToe(int waarde) {
		System.out.println(scoreBlad.get(TIEN).get(scoreBlad.get(TIEN).size() - 1));
		if (waarde == 10) {
			if (scoreBlad.get(TIEN).get(scoreBlad.get(TIEN).size() - 1) == 0) {
				if (!alToegevoegd10)
					scoreBlad.get(TIEN).add(0);
			}
			int score = scoreBlad.get(TIEN).get(scoreBlad.get(TIEN).size() - 1);
			score += 1;
			scoreBlad.get(TIEN).set(scoreBlad.get(TIEN).size() - 1, score);
			alToegevoegd10 = true;
		} else if (waarde == 11) {
			if (scoreBlad.get(ELF).get(scoreBlad.get(ELF).size() - 1) == 0) {
				if (!alToegevoegd11)
					scoreBlad.get(ELF).add(0);
			}
			int score = scoreBlad.get(ELF).get(scoreBlad.get(ELF).size() - 1);
			score += 2;
			scoreBlad.get(ELF).set(scoreBlad.get(ELF).size() - 1, score);
			alToegevoegd11 = true;
		} else if (waarde == 12) {
			if (scoreBlad.get(TWAALF).get(scoreBlad.get(TWAALF).size() - 1) == 0) {
				if (!alToegevoegd12)
					scoreBlad.get(TWAALF).add(0);
			}
			int score = scoreBlad.get(TWAALF).get(scoreBlad.get(TWAALF).size() - 1);
			score += 4;
			scoreBlad.get(TWAALF).set(scoreBlad.get(TWAALF).size() - 1, score);
			alToegevoegd12 = true;
		}
	}

	private void berekenTotaalAlleRijen() {
		scoreBlad.get(TOTAAL).clear();
		for (int rij = 0; rij < MAX_AANTAL_RIJEN; rij++) {
			scoreBlad.get(TOTAAL).add(berekenTotaalPerRij(rij));
		}
	}

	private int berekenTotaalPerRij(int rij) {

		int totaalRij = 0;
		boolean krijgBonus = true;
		if (scoreBlad.get(TIEN).size() - 1 == rij) {
			totaalRij += scoreBlad.get(TIEN).get(rij);
		} else
			krijgBonus = false;

		if (scoreBlad.get(ELF).size() - 1 == rij) {
			totaalRij += scoreBlad.get(ELF).get(rij);
		} else
			krijgBonus = false;

		if (scoreBlad.get(TWAALF).size() - 1 == rij) {
			totaalRij += scoreBlad.get(TWAALF).get(rij);
		} else
			krijgBonus = false;
		if (krijgBonus) {
			if (scoreBlad.get(BONUS).size() - 1 >= rij) {
				totaalRij += scoreBlad.get(BONUS).get(rij);
			}

		}
		if (scoreBlad.get(DUBBEL).size() - 1 == rij)
			if (scoreBlad.get(DUBBEL).get(rij) == 1)
				totaalRij *= 2;

		return totaalRij;

	}

	public int getTotaal() {
		return this.totaal;
	}

	private void berekenTotaal() {
		int totaal = 0;
		for (int i = 0; i < MAX_AANTAL_RIJEN; i++) {
			if (i < scoreBlad.get(TOTAAL).size()) {
				totaal += scoreBlad.get(TOTAAL).get(i);
			}
		}
		this.totaal = totaal;
		scoreBlad.get(5).add(22, totaal);
	}

	private void voegBonussenToe() {
		int[] bonussen = new int[] { 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6 };
		for (int rij = 0; rij < MAX_AANTAL_RIJEN; rij++) {
			if (rij < bonussen.length - 1) {
				scoreBlad.get(BONUS).add(bonussen[rij]);
			}
		}
	}

	private void maakNieuwScoreBlad() {
		scoreBlad = new ArrayList<List<Integer>>();
		for (int i = 0; i < 6; i++) {
			scoreBlad.add(new ArrayList<Integer>());
			scoreBlad.get(i).add(0);
		}
	}

	public List<List<Integer>> getScoreBlad() {
		return scoreBlad;
	}

	public void eindeBeurt() {
		alToegevoegd10 = false;
		alToegevoegd11 = false;
		alToegevoegd12 = false;
		berekenTotaalAlleRijen();
		berekenTotaal();
	}

}
