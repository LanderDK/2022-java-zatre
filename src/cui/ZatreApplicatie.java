package cui;

import java.util.InputMismatchException;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.DubbelGeselecteerdException;
import exceptions.SpelerBestaatAlException;
import exceptions.SpelerNietGevondenException;
import exceptions.TeWeinigSpeelKansenException;
import exceptions.foutAantalSpelersException;

public class ZatreApplicatie {
	private Scanner scanner = new Scanner(System.in);
	private DomeinController dc = new DomeinController();
	private int aantalGeselecteerdeSpelers = 0;

	public ZatreApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		boolean invoerOK = false;
		do {
			try {
				System.out.println(
						"Typ 1 voor je te registreren\rTyp 2 om spelers te selecteren\rTyp 3 om het spel te starten\rTyp 4 om het spel te verlaten");
				int invoer = scanner.nextInt();
				if (invoer == 1) {
					registreerSpeler();
				} else if (invoer == 2) {
					selecteerSpeler();
				} else if (invoer == 3) {
					dc.startSpel();
				} else if (invoer == 4) {
					System.out.println("Tot de volgende keer!");
					System.exit(0);
				}
			} catch (foutAantalSpelersException ex) {
				System.out.println(ex.getMessage());
			} catch (InputMismatchException ex) {
				scanner.nextLine();
				System.out.println("Voer een nummer tussen 1-4");
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		} while (!invoerOK);
	}

	private void selecteerSpeler() {
		boolean stop = false;
		scanner.nextLine();
		do {
			if (aantalGeselecteerdeSpelers < 4)
				System.out.println("\rTyp 1 om een speler te selecteren (4 spelers max.)");
			System.out.println(
					"Typ 2 om de geselecteerde spelers te tonen\rTyp 3 om te stoppen met spelers te selecteren");
			try {
				int invoer = scanner.nextInt();
				if (invoer == 1 && aantalGeselecteerdeSpelers < 4) {
					valideerSelecteerSpeler();
				} else if (invoer == 2) {
					if (aantalGeselecteerdeSpelers == 0)
						System.out.println("Er zijn nog geen spelers geselecteerd");
					toonGeselecteerdeSpelers();
				} else if (invoer == 3) {
					stop = true;
				}
			} catch (TeWeinigSpeelKansenException ex) {
				System.out.println(ex.getMessage());
			} catch (DubbelGeselecteerdException ex) {
				System.out.println(ex.getMessage());
			} catch (SpelerNietGevondenException ex) {
				System.out.println(ex.getMessage());
			} catch (InputMismatchException ex) {
				scanner.nextLine();
				System.out.println("Voer 1, 2 of 3 in.");
			}
		} while (!stop);

	}

	private void valideerSelecteerSpeler() {
		try {
			scanner.nextLine();
			System.out.println("SELECTEREN:\rGeef de naam van de speler: ");
			String naam = scanner.nextLine();
			System.out.println("Geef het geboortejaar van de speler: ");
			int geboorteJaar = scanner.nextInt();
			dc.selecteerSpeler(naam, geboorteJaar);
			aantalGeselecteerdeSpelers = dc.geefGeselecteerdeSpelers().size();
			if (aantalGeselecteerdeSpelers == 1)
				System.out.println("Er is één speler geselecteerd");
			else
				System.out.printf("Er zijn %d spelers geselecteerd%n", aantalGeselecteerdeSpelers);
		} catch (InputMismatchException ex) {
			scanner.nextLine();
			System.out.println("Invoer is ongeldig");
		}

	}

	public void registreerSpeler() {
		try {
			scanner.nextLine();
			System.out.println("REGISTREREN:\rGeef de naam van de speler: ");
			String naam = scanner.nextLine();
			System.out.println("Geef het geboortejaar van de speler: ");
			int geboorteJaar = scanner.nextInt();
			dc.registreerSpeler(naam, geboorteJaar);
			System.out.println("De volgende speler is geregistreerd:\r");
			toonSpeler(dc.geefLaatstGemaakteSpeler());
		} catch (SpelerBestaatAlException ex) {
			System.out.println(ex.getMessage());
		} catch (InputMismatchException ex) {
			scanner.nextLine();
			System.out.println("Invoer is ongeldig");
		}
	}

	public void toonGeselecteerdeSpelers() {
		for (String[] speler : dc.geefGeselecteerdeSpelers()) {
			toonSpeler(speler);
		}
	}

	public void toonSpeler(String[] speler) {
		if (speler != null) {
			System.out.printf("%nGebruikersnaam: %s%n", speler[0]);
			System.out.printf("Geboorte jaar: %s%n", speler[1]);
			System.out.printf("En heeft momenteel %s speelkansen%n", speler[2]);
		}
	}
}