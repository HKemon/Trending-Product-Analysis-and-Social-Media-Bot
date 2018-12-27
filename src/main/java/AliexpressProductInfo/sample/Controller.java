package AliexpressProductInfo.sample;

import AliexpressProductInfo.util.ProjectUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import static AliexpressProductInfo.util.ProjectUtils.*;

public class Controller{
    @FXML
    public TextArea display;

    @FXML
    public void start(ActionEvent event) {
        System.out.println("YA CALLED");
        stopProgram = false;
        ProjectUtils.startScheduler();
    }

    @FXML
    public void stop(ActionEvent event) {
        if (ProjectUtils.scheduler != null) {
            System.out.println("STOPED");
            stopProgram = true;
            unVisited = null;
            visited = null;
            ProjectUtils.stopScheduler();
        } else System.out.println("Did not Start");
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        OutputStream out = new OutputStream() {
//            @Override
//            public void write(int b) throws IOException {
//                appendText(String.valueOf((char) b));
//            }
//        };
//        System.setOut(new PrintStream(out, true));
//    }
//
//    public void appendText(String str) {
//        if (str != null)
//            Platform.runLater(() -> display.appendText(str));
//    }
}
