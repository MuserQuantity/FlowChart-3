package flowChart;

/**
 * Contains method for saving flowchart and method for helpdialog.
 */

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jonas on 2016-01-28.
 */

public class Model {

    //takes a screenshot on the workspacelayout and saves it as a .png-file:
    public void screenshot(WritableImage snapshot){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot,
                        null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //helpdialog:
    public void helpMenu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help menu");
        alert.setHeaderText(null);

        DialogPane dialogPane = alert.getDialogPane();

        DropShadow dropShadow = new DropShadow(50,Color.BLACK);
        Text textGap = new Text("\n");
        textGap.setFont(Font.font(6));
        Text text1 = new Text("                 jS-flowChart\n\n");
        text1.setStyle("-fx-font-size: 30px; -fx-fill: rgba(77, 100, 255, 0.51);-fx-font-style: oblique; -fx-stroke: black; -fx-stroke-width: 0.5px; -fx-alignment: center;");
        text1.setEffect(dropShadow);
        Text text2 = new Text(
                "■ Click on the shape you want to create, choose the shapes label and color.\n\n" +
                        "■ Place the shapes where you want them and create your layout.\n\n" +
                        "■ Click on the Add Line-symbol for creating lines between shapes.\n" +
                        "   • Left click once on a shape for point A.\n" +
                        "   • Hold shift and left click on another shape for point B\n\n" +
                        "■ Click on the Add text-symbol for creating a text.\n\n" +
                        "■ Right click on a shape for delete.\n\n" +
                        "■ File → New flowchart creates a new flowchart.\n\n" +
                        "■ File → Export as .PNG for saves the flowchart as a .PNG-file.\n\n" +
                        "Made by: Jonas Sunnari - jsunnari@gmail.com\n\n\n");
        text2.setStyle("-fx-font-size: 14px; -fx-fill: black; -fx-font-family: 'Helvetica', Arial, sans-serif;");
        Text text3 = new Text("Special thanks to:\nJohan Lindström - jolindse@hotmail.com\nMattias Larsson - mattias.larsson75@outlook.com\nRobin Listerberg - robin_listerberg@hotmail.com");
        text3.setStyle("-fx-font-size: 14px; -fx-fill: black; -fx-font-family: 'Helvetica', Arial, sans-serif; -fx-font-style: italic;");

        TextFlow flow = new TextFlow(textGap,text1,text2,text3);

        dialogPane.setContent(flow);

        alert.showAndWait();
    }
}
