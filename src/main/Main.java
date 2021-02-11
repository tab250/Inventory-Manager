/**
 * @author Tyler Brown
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Product;

/**
 * This is the Main class for the program.
 * All other classes, methods, and files are called via this class.
 * Please see ModifyPartController for suggested feature in an updated version.
 */
public class Main extends Application {

    /**
     * This method presents the Main Menu screen to the user.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This is the Main method for the program.
     */
    public static void main(String[] args) {

        launch(args);
    }
}
