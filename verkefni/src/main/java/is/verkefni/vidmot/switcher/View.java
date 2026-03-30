package is.verkefni.vidmot.switcher;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 *
 * viðbætur fyrir Ferdaplan verkefni
 */
public enum View {
    ADAL("/is/vidmot/ludo-view.fxml"),
    SETUP("/is/vidmot/setup-view.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
