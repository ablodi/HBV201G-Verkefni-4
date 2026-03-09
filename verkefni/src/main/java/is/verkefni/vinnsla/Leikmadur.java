package is.verkefni.vinnsla;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Leikmadur {
	private final SimpleStringProperty nafn = new SimpleStringProperty();
	private final SimpleIntegerProperty reitur = new SimpleIntegerProperty();
	private final int blarOffset = 6;
	private final int gulurOffset = 26;
	
	
	public Leikmadur(String leikmanNafn, int leikmanReitur) {
		nafn.setValue(leikmanNafn + " Leikmaður");
		reitur.setValue(leikmanReitur);
		//System.out.println("Bjó til leikmann: " + nafn + " á reit " + reitur);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nafn.getValue() + " " + this.reitur.getValue();
	}
	
	public void faera(int i, int max) {
		int faersla;
		faersla = reitur.intValue() + i;
		if(reitur.getValue() < max) {
			reitur.setValue(faersla);
			//System.out.println("Færði " + nafn.getValue() + " um " + faersla + " reiti");
		}
		if(reitur.getValue() >= max) System.out.println(nafn.getValue()+ " vann!");
	}
	
	public int getReitur() {
		return reitur.getValue();
	}
	public void setReitur(int reitur) {
		this.reitur.setValue(reitur);
	}
	public String getNafn() {
		return nafn.getValue();
	}
	public String samiReitur(Leikmadur reitur, int umferd, int kast, String naesti) {
		String output = "";
		switch(umferd) {
			case 0	:
				if(this.getReitur()+10== reitur.getReitur()-10) {
					reitur.setReitur(0);
					output = "Blár lenti á gulum, \ngulur fer á byrjun og á næsta leik.";
				}
				else output = this.nafn.get() + " færðist um " + kast + ".\n" + naesti;
				break;
			case 1 :
				if(this.getReitur()-10 == reitur.getReitur()+10) {
					reitur.setReitur(0);
					output = "Gulur lenti á bláum, \nblár fer á byrjun og á næsta leik.";
				}
				else output = this.nafn.get() + " færðist um " + kast + ".\n" + naesti;
				break;					
		}
		return output;
	}
	public SimpleIntegerProperty reiturProperty() {
		return reitur;
	}
	
	
	// mvn exec:java -Dexec.mainClass=is.verkefni.vinnsla.Leikmadur
	public static void main(String[] args) {
		Leikmadur playerOne = new Leikmadur("Gulur",0);
		Leikmadur playerTwo = new Leikmadur("Blár",0);
		playerOne.faera(3, 6);
		playerTwo.faera(4, 6);
		System.out.println(playerOne.toString());
	}

	public void reset() {
		reitur.setValue(0);
	}
}
