package is.verkefni.vinnsla;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is.verkefni
 *  Lýsing  : Gerir ekki neitt í þessu forriti. Síðar hafa vinnsluklasar það hlutverk að framkvæma
 *  bakendavinnslu óháð notendaviðmóti *
 *
 *****************************************************************************/
public class Ludo {
	
	enum leikStada {
		IGANGI,
		LOKID;
	}
	
	private final SimpleObjectProperty<Leikmadur> leikTurn = new SimpleObjectProperty<Leikmadur>();
	private final SimpleObjectProperty<leikStada> stadaLeiks = new SimpleObjectProperty<Ludo.leikStada>();
	private final StringProperty teningaSkilabod =  new SimpleStringProperty();
	private BooleanBinding iGangi;
	private int umferd = 0;
	private Teningur dice = new Teningur();
	private List<Reitur> reitir = new ArrayList<>();
	private String sigurvegari = "";
	private int blarOffset = 6;
	private int gulurOffset = 26;
	
	
	public Ludo() {
		/*
		for(Reitur index : reitir){
			System.out.println(index);
		}
		*/
		stadaLeiks.setValue(leikStada.LOKID);
	}
	
	Leikmadur[] leikmenn = {
			new Leikmadur("Blár", 0),
			new Leikmadur("Gulur", 0)
	};
	
	public SimpleObjectProperty<Leikmadur> leikmanProperty() {
		return leikTurn;
	}
	public SimpleIntegerProperty teningurProperty() {
		return dice.teningurProperty();
	}
	public SimpleIntegerProperty leikmanReiturProperty (int i) {
		return leikmenn[i].reiturProperty();
	}
	public SimpleStringProperty teningaSkilabodProperty() {
		return (SimpleStringProperty) teningaSkilabod;
	}
	
	
	
	/**
	 * kastar tening, færir leikmann, setur næsta leikmann
	 *
	 * @return skilar true ef leik er lokið
	 */
	public boolean leikaLeik() {
		stadaLeiks.setValue(leikStada.IGANGI);
	    // kasta tening
		leikTurn.set(leikmenn[umferd]);
		dice.kastaTening();
		/* 	til að tryggja það að gulur lendi á bláum(debug)
		int[] rigged = {1, 3};
		dice.setDice(rigged[umferd]);
		*/
		String naesti = umferd == 0 ? "Gulur á næsta leik" : "Blár á næsta leik";		
		teningaSkilabod.set(leikmenn[umferd].getNafn() + " færðist um " + dice.getKast() + ".\n" + naesti);
	    // færa leikmann samkvæmt tening, leikmaður komst í mark er leik lokið og skilað true 
	    leikmenn[umferd].faera(dice.getKast(), 44);
	    if(leikmenn[umferd].getReitur() >= 44) {
	    	stadaLeiks.set(leikStada.LOKID);
	    	sigurvegari = umferd == 0 ? "Blár" : "Gulur";
	    	teningaSkilabod.set(sigurvegari + " hefur unnið!");
	    	dice.setDice(7);
	    	return true;
	    }
	    // athugar hvort leikmaður sem gerði seinast lendir á sama reit og hinn, ef svo er færist hinn á upphafsreit sinn.
	    if(umferd == 0 && leikmenn[umferd].getReitur() + 10 == leikmenn[umferd+1].getReitur() - 10) {
	    	teningaSkilabod.set(leikmenn[umferd].samiReitur(leikmenn[umferd+1], umferd, dice.getKast(), naesti));
	    }
	    if(umferd == 1 && leikmenn[umferd].getReitur() -10 == leikmenn[umferd-1].getReitur() + 10) {
	    	teningaSkilabod.set(leikmenn[umferd].samiReitur(leikmenn[umferd-1], umferd, dice.getKast(), naesti));
	    }
	    // næsti leikmaður gerir
	    umferd = (umferd == 0) ? 1 : 0;
	    //System.out.println("Leikstada: " + stadaLeiks.getValue());
	    return false;
	}
	
	public int getUmferd() {
		return umferd;
	}
	public String getSigurvegari() {
		return sigurvegari;
	}
	
	public BooleanBinding erIGangi() {
		iGangi = Bindings.createBooleanBinding(
			()-> stadaLeiks.get() == leikStada.IGANGI, stadaLeiks
		);
		
		return iGangi;
	}
	
	public List<Reitur> getLeid(){
		return reitir;
	}
	
	public void buaTilLeid(int row, int col, int index) {
		//System.out.println("index:" + index + " row: " + row + " col : " + col);
		if(index < 40) {
			reitir.add(new Reitur(row, col, "NORMAL"));			
		}
		if((index < 48 && index >= 45 ) || (index < 45 && index >= 40)) {
			switch(index) {
			case 47, 46, 45, 44 -> reitir.add(new Reitur(row, col, "YELLOWFINISHLINE"));
			case 40, 41, 42, 43 -> reitir.add(new Reitur(row, col, "BLUEFINISHLINE"));
			}
		}
		if(index == 48) {
			reitir.add(new Reitur(row, col, "END"));
		}
	}
	
	public void enduraesaLeik () {
		stadaLeiks.setValue(leikStada.IGANGI);
		leikTurn.set(leikmenn[0]);
		leikmenn[0].reset();
		leikmenn[1].reset();
		sigurvegari = "";
	}
	
	// mvn exec:java -Dexec.mainClass=is.verkefni.vinnsla.Ludo
		public static void main(String[] args) {
			Ludo tester = new Ludo();
			tester.stadaLeiks.setValue(leikStada.IGANGI);
			System.out.println(tester.erIGangi().get());
		}
}
