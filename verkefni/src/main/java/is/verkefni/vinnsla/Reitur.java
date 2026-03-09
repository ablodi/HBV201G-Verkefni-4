package is.verkefni.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Reitur {
	private SimpleIntegerProperty row = new SimpleIntegerProperty();
	private SimpleIntegerProperty col = new SimpleIntegerProperty();
	private SimpleObjectProperty<reiturType> reiturTypa = new SimpleObjectProperty<Reitur.reiturType>();
	enum reiturType {
		NORMAL,
		START,
		BLUEFINISHLINE,
		YELLOWFINISHLINE,
		END;
	}
	
	public Reitur(int row, int col, String type) {
		this.row.setValue(row);
		this.col.setValue(col);
		if(type.equals("NORMAL")) reiturTypa.setValue(reiturType.NORMAL);
		if(type.equals("START")) reiturTypa.setValue(reiturType.START);
		if(type.equals("BLUEFINISHLINE")) reiturTypa.setValue(reiturType.BLUEFINISHLINE);
		if(type.equals("YELLOWFINISHLINE")) reiturTypa.setValue(reiturType.YELLOWFINISHLINE);
		if(type.equals("END")) reiturTypa.setValue(reiturType.END);
	}
	/*
	public  SimpleObjectProperty<reiturType> getType(){
		return this.reiturTypa;
	}*/
	public boolean erLokaType() {
		return this.reiturTypa.get() == reiturType.END;
	}
	public String getType() {
		return " Type: " + reiturTypa.getValue().toString();
	}
	public String getIndex() {
		return "Col: " + col.intValue() + " | Row: " + row.intValue();
	}
	public int getCol() {
		return col.intValue();
	}
	public int getRow() {
		return row.intValue();
	}
}
