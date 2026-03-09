/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : er module skrá sem skilgreinir hvaða forritasöfn eru nauðsynleg og hver eru
 *  aðgengileg forritasöfnum  *
 *
 *****************************************************************************/
module ModuleNafn {
    requires transitive javafx.fxml;
    requires transitive javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
    opens is.verkefni.vidmot to javafx.fxml;

    exports is.verkefni.vidmot;
    exports is.verkefni.vinnsla;
}