package ceng101.project;

import java.util.ArrayList;

public class Mac {
	public int hafta;//will be used as game number
	public Takim EvSahibi;
	public Takim Deplasman;
	public ArrayList<Gol> goller;
	public Mac(int hafta, Takim evSahibi, Takim deplasman) {
		super();
		this.hafta = hafta;
		EvSahibi = evSahibi;
		Deplasman = deplasman;
		goller = new ArrayList<Gol>();
	}

	
}
