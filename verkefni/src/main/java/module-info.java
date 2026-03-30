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
    opens is.verkefni.vidmot.controller to javafx.fxml;
    opens is.verkefni.vidmot.switcher to javafx.fxml;

    exports is.verkefni.vidmot.switcher;
    exports is.verkefni.vidmot.controller;
    exports is.verkefni.vidmot;
    exports is.verkefni.vinnsla;
}