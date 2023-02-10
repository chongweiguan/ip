package duke;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Duke duke = new Duke(new UI(), new Parser());
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("../images/DaDuke.png"));
    MainWindow mainWindow = new MainWindow();

    @Override
    public void start(Stage stage) {
        System.out.println("running 1");
        duke.initialize();
        duke.handleUserInput(new TextField("greet"), duke.dialogContainer);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
