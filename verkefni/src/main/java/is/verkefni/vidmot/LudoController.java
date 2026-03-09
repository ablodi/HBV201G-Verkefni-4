package is.verkefni.vidmot;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import is.verkefni.vinnsla.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;



/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *
 *
 *****************************************************************************/
public class LudoController implements Initializable {
	private static final String[] myndir = {"one", "two", "three", "four", "five", "six", "end"};
	
	@FXML
	private StackPane fxPlayerOne;
	@FXML
	private StackPane fxPlayerTwo;
	@FXML
	private Button fxTeningur;
	@FXML
	private Button fxNyrLeikur;
	@FXML
	private Label fxTeningSkilabod;
	@FXML
	private Label fxNyrLeikurSkilabod;
	@FXML
	private GridPane fxBord;
	
	private final Map<Integer, StackPane> vidmotLeid = new HashMap<>();
	private final Map <Integer, Integer> blaLeid = new HashMap<>();
	private final Map <Integer, Integer> gulLeid = new HashMap<>();
	
	Ludo ludo = new Ludo();
	
	/**
	 * Hefur nýjan leik. Leikmenn eru utan borðs eða á reiti eitt, leikur er í merktur í gangi 
	 */

	public void nyrLeikur() {
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fxNyrLeikurSkilabod.setText("Ýttu á Nýr Leikur Hnappinn\ntil að hefja leik");
		// búa til leiðina á lúdó borðinu (6) 
		/**
		 * Leiðin er fengin úr vinnslunni (módelinu). Viðmótsreitir eru búnir til fyrir
		 * hvern reit og settur á borðið í viðmótinu. 
		 * því er bætt í HashMap vidmotLeid 
		 * Þar sem leikmenn byrja á misjöfnum stöðum á borðinu þarf að hafa sitthvort 
		 * HashMap fyrir þeirra leiðir (index af leikmannaleið inn á index á vidmotsLeid)
		 * @throws IOException ef viðmótsreiturinn er lesinn (load) úr .fxml skrá, annars óþarfi 
		 */
		int index=0;
		int blarOffset = 6;
		int gulurOffset = 26;
		for(Node node: fxBord.getChildren()) {
			if(node instanceof StackPane) {
				int row = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
				int col = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);
				
				ludo.buaTilLeid(row, col, index);
				vidmotLeid.put(index , (StackPane) node);
				
				if(index >= 0 && index+blarOffset < 40)
					blaLeid.put(index, index + blarOffset);
				if(index + blarOffset >= 40 && index < 40)
					blaLeid.put(index, index - 40 + blarOffset);
				if(index >= 0 && index+gulurOffset < 40)
					gulLeid.put(index, index + gulurOffset);
				if(index+gulurOffset >= 40 && index < 40)
					gulLeid.put(index, index - 40 + gulurOffset);
				if(index >= 40 && index < 45) { //lokaspretturinn á brautinni
					switch(index) {
					case 44 : 
						blaLeid.put(index, 48); 
						gulLeid.put(index, 48);
						break;
					default:
						blaLeid.put(index, index);
						gulLeid.put(index, index+4);
						break;
					}
				}
				index++;
			}
		}
		for(int i = 0; i < blaLeid.size(); i++) {
			System.out.println("index: " + i + " | Bláa leið: " + blaLeid.get(i) + " | Gul leið: " + gulLeid.get(i));
		}
		List<Reitur> leid = ludo.getLeid();
		
		
		//fxBord.getChildren().get().getStyleClass().add("player-two-pawn");
		// fyrir hvern reit, r, á leiðinni
		for(Reitur r: leid) {
			System.out.println(r.getIndex() + r.getType());
			//fxBord.getStyleClass().add("one");
			// búa til viðmótsreit, s
			
			// bæta viðmótsreitnum s, á borðið í dálk og röð sem er skilgreint í r
			
			// bæta við (r, s) á HashMap-ið 
			
		}
	
	    // binda teningamyndirnar við teninginn (7) 
		ludo.teningurProperty().addListener(
				(observable, oldValue, newValue)-> {
					fxTeningur.getStyleClass().remove(myndir[oldValue.intValue() -1]);
					fxTeningur.getStyleClass().add(myndir[newValue.intValue() -1]);
				});
		
	    // bindur reitina á borðinu við reitinn sem leikmaður er á (8) 
		/*
		* Fyrir hvern leikmann, uppfærir mynd á viðmótsreiit í samræmi við nýjan reit leikmanns sem 
		* er vaktaður í gegnum index leikmanns 
		*/
		ludo.leikmanReiturProperty(0).addListener(
				(observble, oldValue, newValue)-> {
					if(oldValue != null) {
						StackPane oldPane = vidmotLeid.get(blaLeid.get(oldValue));
						oldPane.getStyleClass().remove("player-one-pawn");
						if(!vidmotLeid.get(26).getStyleClass().contains("player-two-start"))
							oldPane.getStyleClass().add("player-two-start");
						if(!vidmotLeid.get(6).getStyleClass().contains("player-one-start"))
							oldPane.getStyleClass().add("player-one-start");
					}
					if(newValue != null) {
						StackPane newPane = vidmotLeid.get(blaLeid.get(newValue)) == null ? vidmotLeid.get(48) : vidmotLeid.get(blaLeid.get(newValue)); // Ef nýji reiturinn er ekki til, þá er brautin búin og peð sett á lokareit
						newPane.getStyleClass().add("player-one-pawn");
						if(vidmotLeid.get(26).getStyleClass().contains("player-two-start"))
							newPane.getStyleClass().remove("player-two-start");
						if(vidmotLeid.get(6).getStyleClass().contains("player-one-start"))
							newPane.getStyleClass().remove("player-one-start");
					}
					//System.out.println("prufa: " + oldValue + "->" + newValue);
				});
		ludo.leikmanReiturProperty(1).addListener(
				(observble, oldValue, newValue)-> {
					if(oldValue != null) {
						StackPane oldPane = vidmotLeid.get(gulLeid.get(oldValue));
						oldPane.getStyleClass().remove("player-two-pawn");
					}
					if(newValue != null) {
						StackPane newPane = vidmotLeid.get(gulLeid.get(newValue)) == null ? vidmotLeid.get(48) : vidmotLeid.get(gulLeid.get(newValue));
						newPane.getStyleClass().add("player-two-pawn");
					}
				});
		
	    // binda hnappana við ástandið á leiknum (9) 
		fxTeningur.disableProperty().bind(ludo.erIGangi().not());
		
	    // bindur skilaboðin um hver á að gera og hver er sigurvegari við gögn úr vinnslunni (10)
		ludo.teningaSkilabodProperty().addListener(
				(observable, oldValue, newValue)-> {
					fxTeningSkilabod.setText(newValue);
				});
		
	
	}
	public void onLeika(ActionEvent actionEvent) {
		fxTeningur.getStyleClass().remove("play");
		ludo.leikaLeik();
		System.out.print(ludo.getSigurvegari());
		if(ludo.getSigurvegari().equals("Blár")) vidmotLeid.get(49).getStyleClass().add("win");
		if(ludo.getSigurvegari().equals("Gulur")) vidmotLeid.get(50).getStyleClass().add("win");
	}
	public void onNyrLeikur(ActionEvent actionEvent) {
		ludo.enduraesaLeik();
		vidmotLeid.get(48).getStyleClass().clear();
		vidmotLeid.get(48).getStyleClass().add("finishline");
		vidmotLeid.get(49).getStyleClass().clear();
		vidmotLeid.get(49).getStyleClass().add("player-one");
		vidmotLeid.get(6).getStyleClass().clear();
		vidmotLeid.get(6).getStyleClass().add("player-one-start");
		vidmotLeid.get(50).getStyleClass().clear();
		vidmotLeid.get(50).getStyleClass().add("player-two");
		vidmotLeid.get(26).getStyleClass().clear();
		vidmotLeid.get(26).getStyleClass().add("player-two-start");
		fxTeningur.getStyleClass().clear();
		fxTeningur.getStyleClass().add("play");
		fxTeningSkilabod.setText("Blár á fyrsta leik");
	}
	
}


