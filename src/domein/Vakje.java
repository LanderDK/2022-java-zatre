package domein;

public class Vakje {
	private Steen steen;

	public Vakje(Steen steen) {
		this.steen = steen;
	}

	public Vakje() {
		this.steen = null;
	}

	public boolean isLeeg() {
		return this.steen == null;
	}

	public void legSteen(Steen steen) {
		this.steen = steen;
	}

	public Steen getSteen() {
		return this.steen;
	}

	public void verwijderSteen() {
		this.steen = null;
	}
}
