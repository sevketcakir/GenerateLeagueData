package ceng101.project;

import java.util.*;

public class Takim {
	int id;
	String adi;
	ArrayList<Futbolcu> futbolcular;

	public Takim(int id, String adi) {
		this.id = id;
		this.adi = adi;
		futbolcular = new ArrayList<Futbolcu>();
	}

	public Futbolcu getRandomPlayer() {
		return futbolcular.get(new Random().nextInt(futbolcular.size()));
	}
}
