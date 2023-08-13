package domein;

import java.util.Calendar;

public class Speler {
	private String gebruikersNaam;
	private int geboorteJaar;
	private int aantalSpellen;
	private ScoreBlad scoreBlad;

	public Speler(String gebruikersNaam, int geboorteJaar, int aantalSpellen) {
		setgebruikersNaam(gebruikersNaam);
		setgeboorteJaar(geboorteJaar);
		setAantalSpellen(aantalSpellen);
	}

	public Speler(String gebruikersNaam, int geboorteJaar) {
		this(gebruikersNaam, geboorteJaar, 5);
	}

	public int getGeboorteJaar() {
		return geboorteJaar;
	}

	public String getGebruikersNaam() {
		return gebruikersNaam;
	}

	public int getAantalSpellen() {
		return aantalSpellen;
	}

	private void setgebruikersNaam(String gebruikersNaam) {
		if (gebruikersNaam == null || gebruikersNaam.isBlank())
			throw new IllegalArgumentException("Gebruikersnaam mag niet leeg zijn");
		if (gebruikersNaam.length() < 5)
			throw new IllegalArgumentException("Gebruikersnaam moet minstens dan 5 karakters lang zijn");
		this.gebruikersNaam = gebruikersNaam;
	}

	private void setgeboorteJaar(int geboorteJaar) {
		int huidigJaar = Calendar.getInstance().get(Calendar.YEAR);
		if (geboorteJaar == 0 || geboorteJaar <= 1900 || geboorteJaar > huidigJaar)
			throw new IllegalArgumentException("Voer een geldig geboortejaar in");
		if (huidigJaar - geboorteJaar < 6)
			throw new IllegalArgumentException(
					"Een gebruiker mag minstens 6 jaar zijn of 6 jaar worden in het lopende jaar om zich te kunnen registreren");
		this.geboorteJaar = geboorteJaar;
	}

	public void setAantalSpellen(int aantalSpellen) {
		this.aantalSpellen = aantalSpellen;
	}

	public String[] toStringArray() {
		String[] res = new String[3];
		res[0] = gebruikersNaam;
		res[1] = Integer.toString(geboorteJaar);
		res[2] = Integer.toString(aantalSpellen);
		return res;
	}

	public ScoreBlad getScoreBlad() {
		return scoreBlad;
	}

	public void setScoreBlad(ScoreBlad scoreBlad) {
		this.scoreBlad = scoreBlad;
	}

}