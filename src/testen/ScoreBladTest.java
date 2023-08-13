package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domein.ScoreBlad;
import domein.Speler;

public class ScoreBladTest {
	Speler speler;

	@Test
	public void BerekenTotaalPerRij_geldigeWaarden_10Punten() {
		ScoreBlad scoreBlad = new ScoreBlad();
		scoreBlad.voegScoreToe(10);
		scoreBlad.voegScoreToe(11);
		scoreBlad.voegScoreToe(12);
		scoreBlad.eindeBeurt();
		Assertions.assertEquals(10, scoreBlad.getTotaal());
	}
}
