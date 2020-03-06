/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author MOMEN
 */
public class FileView extends Application {

    FileChooser fileChooser;
    File selectedFile;
    String fonts, colors;

    @Override
    public void start(Stage primaryStage) {
        MenuBar menuB = new MenuBar();

        Menu filem = new Menu("_File");

        MenuItem open = new MenuItem("_Open");
        MenuItem close = new MenuItem("_Close");
        MenuItem exit = new MenuItem("E_xit");

        filem.getItems().addAll(open, close, exit);

        Menu editm = new Menu("_Edit");

        MenuItem font = new MenuItem("_Font");
        MenuItem color = new MenuItem("_Color");

        editm.getItems().addAll(font, color);

        menuB.getMenus().addAll(filem, editm);

        HBox menuBox = new HBox(menuB);
        TextArea txtA = new TextArea("Plese Seclect Text File \n File -> open");
        txtA.setEditable(false);

        VBox txt = new VBox(txtA);

        open.setOnAction(z -> {

            fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(null);
            try ( Scanner scanner = new Scanner(selectedFile)) {

                while (scanner.hasNext()) {
                    txtA.appendText(scanner.next() + "\n");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            txtA.setEditable(true);

        });

        close.setOnAction(e -> {
            if (selectedFile != null) {
                try {
                    PrintWriter WR = new PrintWriter(selectedFile);
                    WR.flush();
                    WR.write(txtA.getText());
                    WR.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            txtA.clear();
            txtA.setEditable(false);
        });

        exit.setOnAction(r -> {
            primaryStage.close();
        });
        fonts = "11";
        colors = "black";
        String[] number = {"8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40"};
        ChoiceDialog fo = new ChoiceDialog(fonts, number);

        font.setOnAction(p -> {
            fo.setHeaderText("Selecting Font Size");
            fo.setContentText("Select the size from the list below ");
            fo.showAndWait();
            fonts = "" + fo.getSelectedItem();

            txtA.setStyle("-fx-text-fill:" + colors + "; -fx-font-size:" + fonts + "px;");

        });

        String[] colorname = {"Black", "Blue", "orange", "GOLD", "YELLOW", "Green", "FUCHSIA", "LIME", "MIDNIGHTBLUE ", "Olive", "MAROON", "PERU", "TEAL"};
        ChoiceDialog col = new ChoiceDialog(colorname[0], colorname);

        color.setOnAction(w -> {
            col.setHeaderText("Selecting Color ");
            col.setContentText("Select an color from the list below  ");
            col.showAndWait();
            colors = "" + col.getSelectedItem();

            txtA.setStyle("-fx-text-fill:" + colors + "; -fx-font-size:" + fonts + "px;");

        });
        txt.setFillWidth(true);
        VBox box = new VBox(menuBox, txtA);
        box.setAlignment(Pos.CENTER);
        box.setFillWidth(true);
        Scene scene = new Scene(box, 400, 200);

        primaryStage.setTitle("File View");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
