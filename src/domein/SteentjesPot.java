package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SteentjesPot {
	private static List<Steen> steentjes = new ArrayList<>();

	public SteentjesPot() {
		maakSteentjes();
	}

	private void maakSteentjes() {
		steentjes = new ArrayList<>();
		for (int waarde = 2; waarde <= 6; waarde++) {// maakt 20 steentjes met waarden 2 t.e.m. 6
			for (int i = 0; i < 20; i++) {
				steentjes.add(new Steen(waarde));
			}
		}
		for (int i = 0; i < 21; i++) {// maakt 21 steentjes met waarde 1
			steentjes.add(new Steen(1));
		}
		Collections.shuffle(steentjes);
	}

	public Steen[] geefSteentjes(int aantal) {
		if (steentjes.size() < 2)
			return null;
		Steen[] res = new Steen[3];
		for (int i = 0; i < aantal; i++) {
			res[i] = steentjes.get(i);
		}
		for (int i = 0; i < aantal; i++) {
			steentjes.remove(i);
		}
		if (aantal == 2)
			res[2] = new Steen(0);
		return res;
	}

	public boolean steentjesZijnOp() {
		return steentjes.size() == 0;
	}

}
