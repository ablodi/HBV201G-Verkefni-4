package is.verkefni.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

public class Teningur{
	private final int HLIDAR = 6;
	private final SimpleIntegerProperty kast = new SimpleIntegerProperty(HLIDAR);
	
	
	public Teningur() {}
	
	public void kastaTening() {
		kast.setValue((int) (Math.random() * HLIDAR) + 1);
		//System.out.println(kast.getValue());
	}
	public int getKast() {
		return kast.getValue();
	}
	public SimpleIntegerProperty teningurProperty() {
		return kast;
	}
	// mvn exec:java -Dexec.mainClass=is.verkefni.vinnsla.Teningur
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			Teningur dice = new Teningur();
			dice.kastaTening();
			System.out.println("sæki property: "+ dice.teningurProperty());
			
		}
	}

	public void setDice(int i) {
		kast.set(i);;
		//kast.setValue(i);
	}
}

