package domein;

public class GrijsVakje extends Vakje {

	public GrijsVakje(Steen steen) {
		super(steen);
	}

	public GrijsVakje() {
		super();
	}

	@Override
	public String toString() {
		return "grijs";
	}
}
