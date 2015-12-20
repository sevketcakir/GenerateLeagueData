package ceng101.project;

import java.util.Comparator;

public class Gol implements Comparable{
	public Futbolcu atanFutbolcu;
	public Takim atanTakim;
	public int dakika;
	public Gol(Futbolcu atanFutbolcu, Takim atanTakim, int dakika) {
		this.atanFutbolcu = atanFutbolcu;
		this.atanTakim = atanTakim;
		this.dakika = dakika;
	}

	@Override
	public int compareTo(Object arg0) {
		Gol g1=(Gol)arg0;
		return this.dakika-g1.dakika;
	}
}
