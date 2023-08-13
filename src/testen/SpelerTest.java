package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

public class SpelerTest {
	Speler speler;

	@ParameterizedTest
	@ValueSource(strings = { "", "xxxx", "      " })
	public void setNaam_ongeldigeWaarden_gooitException(String strings) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			speler = new Speler(strings, 2002, 5);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = { 1800, 2017, 2025 })
	public void setGeboortejaar_ongeldigeWaarden_gooitException(int ints) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			speler = new Speler("xxxxx", ints, 5);
		});
	}
}
