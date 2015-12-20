package ceng101.project;

import java.io.*;
import java.util.*;

public class MainClass {
	ArrayList<Mac> fikstur;
	ArrayList<Takim> takimlar;
	String[] ilceler;
	String[] adlar;
	String[] soyadlar;
	String[] takimEkAd = { "Spor", "Utd.", "Rangers", "County", "City" };
	int ts, tfs, ecgs;
	double kkgo;
	Random random = new Random();

	/*
	 * parametre olarak verilen bir diziden rasgele bir deðer seçmek
	 */
	public String getRandom(String[] str) {
		return str[Math.abs(random.nextInt()) % str.length];
	}

	public MainClass() {
		fikstur = new ArrayList<Mac>();
		takimlar = new ArrayList<Takim>();
		ilceler = dosyadanOku("input\\ilceler.txt");
		adlar = dosyadanOku("input\\adlar.txt");
		soyadlar = dosyadanOku("input\\soyadlar.txt");
	}

	public MainClass(int ts, int tfs, int ecgs, double kkgo) {
		this();
		this.ts = ts;
		this.tfs = tfs;
		this.ecgs = ecgs;
		this.kkgo = kkgo;
		int fi = 0;
		for (int i = 0; i < ts; i++) {
			Takim t = new Takim(i, getRandom(ilceler) + " "
					+ getRandom(takimEkAd));
			for (int j = 0; j < tfs; j++) {
				Futbolcu f = new Futbolcu(fi++, getRandom(adlar) + " "
						+ getRandom(soyadlar));
				t.futbolcular.add(f);
			}
			takimlar.add(t);
		}
		fiksturOlustur();
		maclariOynat(ecgs, kkgo);
		dosyayaYaz();
		/*
		 * for(Mac m :fikstur) { for(Gol g:m.goller) {
		 * System.out.println(m.hafta
		 * +" "+m.EvSahibi.id+" "+m.Deplasman.id+" "+g.
		 * dakika+" "+g.atanTakim.id+" "+g.atanFutbolcu.id); } }
		 */
	}

	/*
	 * Mevcut maç bilgisini ilgili dosyalara yaz
	 */
	public void dosyayaYaz() {
		try {
			FileOutputStream tkmlar = new FileOutputStream(
					"output\\takimlar.txt");
			FileOutputStream ftblclr = new FileOutputStream(
					"output\\futbolcular.txt");
			for (Takim t : takimlar) {
				String text = Integer.toString(t.id) + " " + t.adi + "\n";
				tkmlar.write(text.getBytes());
				for (Futbolcu f : t.futbolcular) {
					String text2 = Integer.toString(f.id) + " "
							+ Integer.toString(t.id) + " " + f.adi + "\n";
					ftblclr.write(text2.getBytes());
				}
			}
			ftblclr.close();
			tkmlar.close();
			FileOutputStream maclar = new FileOutputStream("output\\maclar.txt");
			FileOutputStream goller = new FileOutputStream("output\\goller.txt");
			for (Mac m : fikstur) {
				String str = Integer.toString(m.hafta) + " "
						+ Integer.toString(m.EvSahibi.id) + " "
						+ Integer.toString(m.Deplasman.id) + "\n";
				maclar.write(str.getBytes());
				for (Gol g : m.goller) {
					String str2 = Integer.toString(m.hafta) + " "
							+ Integer.toString(g.dakika) + " "
							+ Integer.toString(g.atanTakim.id) + " "
							+ Integer.toString(g.atanFutbolcu.id) + "\n";
					goller.write(str2.getBytes());
				}
			}
			goller.close();
			maclar.close();
			FileOutputStream db = new FileOutputStream(
					"output\\dosyabilgileri.txt");
			String str = "Bu dosya bilgi amaçlýdýr. Projede kullanýlmayacaktýr.\n";
			str += "Takým sayýsý: " + ts + "\n";
			str += "Her bir takýmdaki futbolcu sayýsý: " + tfs + "\n";
			str += "Bir maçtaki en çok gol sayýsý: " + ecgs + "\n";
			str += "Kendi kalesine gol oraný: " + kkgo + "\n";
			str += "Dosyalardaki satýr sonu karakteri: \\n";
			db.write(str.getBytes());
			db.close();
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}
	}

	/*
	 * Round Robin Turnuva algoritmasýný kullanarak fikstürü oluþtur
	 */
	public void fiksturOlustur() {
		ArrayList<Takim> es = new ArrayList<Takim>();
		ArrayList<Takim> dp = new ArrayList<Takim>();
		for (int i = 0; i < takimlar.size() / 2; i++)
			es.add(takimlar.get(i));
		for (int i = takimlar.size() - 1; i >= takimlar.size() / 2; i--)
			dp.add(takimlar.get(i));
		int mi = 0;
		for (int i = 0; i < takimlar.size() - 1; i++) {
			for (int j = 0; j < es.size(); j++) {
				fikstur.add(new Mac(mi++, es.get(j), dp.get(j)));
				// System.out.println(i+" "+es.get(j).id+" "+dp.get(j).id);
			}
			Takim t = dp.remove(0);
			es.add(1, t);
			dp.add(es.remove(es.size() - 1));
		}
		// add reverse
		int flength = fikstur.size();
		for (int i = 0; i < flength; i++)
			fikstur.add(new Mac(mi++, fikstur.get(i).Deplasman,
					fikstur.get(i).EvSahibi));
		// fikstur.add(new Mac(fikstur.get(i).hafta+takimlar.size()-1,
		// fikstur.get(i).Deplasman, fikstur.get(i).EvSahibi));
		/*
		 * for(Mac m: fikstur)
		 * System.out.println(m.hafta+" "+m.EvSahibi.id+" "+m.Deplasman.id);
		 */}

	/*
	 * Rasgele olarak maçlarý oynat
	 */
	public void maclariOynat(int maxGoals, double ownGoalRate) {
		for (Mac m : fikstur) {

			int homeScore = random.nextInt(maxGoals);
			int awayScore = random.nextInt(maxGoals);
			for (int i = 0; i < homeScore; i++) {
				if (random.nextDouble() > ownGoalRate) {
					m.goller.add(new Gol(m.EvSahibi.getRandomPlayer(),
							m.EvSahibi, random.nextInt(91)));
				} else// own goal
				{
					m.goller.add(new Gol(m.Deplasman.getRandomPlayer(),
							m.EvSahibi, random.nextInt(91)));
				}
			}
			for (int i = 0; i < awayScore; i++) {
				if (random.nextDouble() > ownGoalRate) {
					m.goller.add(new Gol(m.Deplasman.getRandomPlayer(),
							m.Deplasman, random.nextInt(91)));
				} else// own goal
				{
					m.goller.add(new Gol(m.EvSahibi.getRandomPlayer(),
							m.Deplasman, random.nextInt(91)));
				}
			}
			// m.goller.sort(c);
			Collections.sort(m.goller);
		}
	}

	public static void main(String[] args) {

		System.out.println("Leage Data Generator v0.1");
		Scanner s = new Scanner(System.in);
		System.out.print("Takým sayýsý: ");
		int takimSayisi = s.nextInt();
		System.out.print("Takýmlardaki futbolcu sayýsý: ");
		int futbolcuSayisi = s.nextInt();
		System.out.print("En çok gol sayýsý: ");
		int enCokGol = s.nextInt();
		System.out.print("Kendi kalesine gol oraný([0,1]): ");
		double kkgo = s.nextDouble();
		MainClass mc = new MainClass(takimSayisi, futbolcuSayisi, enCokGol,
				kkgo);

		s.close();
	}

	public String[] dosyadanOku(String dosyaAdi) {
		List<String> lines = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(dosyaAdi);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
			bufferedReader.close();
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}
		return lines.toArray(new String[lines.size()]);
	}

}
