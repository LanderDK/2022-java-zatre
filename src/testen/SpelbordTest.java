package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.SpelBord;
import domein.Steen;

public class SpelbordTest {
	private SpelBord bord;

	@BeforeEach
	public void setUp() {
		bord = new SpelBord();
	}

	@Test
	public void validatie1_ongeldigeZet_retourneertFalse() {
		bord.legSteentjeOpVakje(7, 7, new Steen(1));
		Assertions.assertFalse(bord.validatie1(7, 7));
	}

	@Test
	public void validatie1_ongeldigeZetZelfdeCords_retourneertFalse() {
		bord.legSteentjeOpVakje(7, 7, new Steen(1));
		Assertions.assertFalse(bord.validatie1(0, 0));
	}

	@Test
	public void validatie1_geldigeZet_retourneertTrue() {
		bord.legSteentjeOpVakje(7, 7, new Steen(1));
		Assertions.assertTrue(bord.validatie1(7, 8));
	}

	@Test
	public void validatie1_geldigeZet2_retourneertTrue() {
		bord.legSteentjeOpVakje(7, 7, new Steen(1));
		Assertions.assertTrue(bord.validatie1(6, 7));
	}
}
