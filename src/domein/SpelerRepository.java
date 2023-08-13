package domein;

import java.util.ArrayList;
import java.util.List;

import exceptions.DubbelGeselecteerdException;
import exceptions.SpelerNietGevondenException;
import exceptions.TeWeinigSpeelKansenException;
import persistentie.SpelerMapper;

public class SpelerRepository {
	private static SpelerMapper sm = new SpelerMapper();
	private static Speler laatstGeregistreerdeSpeler;
	private static List<Speler> geselecteerdeSpelers = new ArrayList<>();

	public void voegSpelerToe(Speler speler) {
		sm.voegSpelerToe(speler);
		laatstGeregistreerdeSpeler = speler;
	}

	public void updateSpelKansenSpeler(Speler speler) {
		sm.updateSpelKansen(speler);
	}

	public Speler geefLaatstGemaakteSpeler() {
		return sm.geefSpeler(laatstGeregistreerdeSpeler);
	}

	public Speler geefSpeler(Speler speler) throws SpelerNietGevondenException {
		return sm.geefSpeler(speler);
	}

	public void selecteerSpeler(Speler speler) throws DubbelGeselecteerdException {
		for (Speler geselecteerdeSpeler : geselecteerdeSpelers) {
			if (geselecteerdeSpeler.getGebruikersNaam().equals(speler.getGebruikersNaam())
					&& geselecteerdeSpeler.getGeboorteJaar() == speler.getGeboorteJaar())
				throw new DubbelGeselecteerdException();
		}
		Speler geselecteerdeSpeler = sm.geefSpeler(speler);
		if (geselecteerdeSpeler.getAantalSpellen() < 1)
			throw new TeWeinigSpeelKansenException();
		geselecteerdeSpelers.add(geselecteerdeSpeler);
	}

	public List<Speler> geefGeselecteerdeSpelers() {
		return geselecteerdeSpelers;
	}
}
