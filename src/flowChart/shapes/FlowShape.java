package flowChart.shapes;

/**
 * Superclass for all shapes, extends stackpane.
 * Includes inputdialogs (for choosing color and labeltext) and drag'n'drop-function.
 */

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jonas on 2016-02-04.
 */

public abstract class FlowShape extends StackPane {
    private DropShadow shapeGlow = new DropShadow(5, Color.YELLOW);

    //sets the dragNdrop-function and changes the cursor to a hand och every shape:
    public FlowShape() {
        setDragListeners(this);
        this.setCursor(Cursor.HAND);
    }

    //method for getting the label on the created shape:
    public static String getLabel(){
        String label = "";

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("");
        dialog.setHeaderText("Create shape");
        dialog.setContentText("Please enter a label for the shape:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            label = result.get();

            //if the label is longer than 10chars, and there is a whitespace after 6chars, change the whitespace to a new row.
            int index = label.indexOf(" ", 6);
            if (label.length() > 10 && index != -1){
                String beginning = label.substring(0,index);
                String ending = label.substring(index+1);
                label = beginning+"\n"+ending;
            }
        }
        else {
            label = "cancel";
        }
        return label;
    }

    //method for getting text when creating a text-object:
    public static String getText(){
        String text = "";

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("");
        dialog.setHeaderText("Create text");
        dialog.setContentText("Please enter your text:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            text = result.get();
        }
        else {
            text = "cancel";
        }
        return text;
    }

    //Dialog for choosing color when creating a shape:
    public static Color getColor(){
        String color = "";
        Color shapeColor = Color.WHITE;

        List<String> choices = new ArrayList<>();
        choices.add("White");
        choices.add("Blue");
        choices.add("Red");
        choices.add("Green");
        choices.add("Yellow");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("White", choices);
        dialog.setTitle(null);
        dialog.setHeaderText("Create shape");
        dialog.setContentText("Choose the color of the shape:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            color = result.get();
        }

        if (color.equals("White")){
            shapeColor = Color.WHITE;
        }
        else if (color.equals("Blue")){
            shapeColor = Color.BLUE;
        }
        else if (color.equals("Red")){
            shapeColor = Color.RED;
        }
        else if (color.equals("Green")){
            shapeColor = Color.GREEN;
        }
        else if (color.equals("Yellow")){
            shapeColor = Color.YELLOW;
        }

        return shapeColor;
    }

    //method that sets a glowing frame around the shape when you hoover the mouse over it:
    public void setGlow(StackPane shape){
        shape.setOnMouseEntered(event -> {
            shape.setEffect(shapeGlow);
        });

        shape.setOnMouseExited(event -> {
            shape.setEffect(null);
        });
    }

    //method for drag and drop-function on shapes:
    public void setDragListeners(final StackPane shape) {
        final Delta dragDelta = new Delta();
        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragDelta.x = shape.getTranslateX() - mouseEvent.getSceneX();
                dragDelta.y = shape.getTranslateY() - mouseEvent.getSceneY();

                shape.setCursor(Cursor.NONE);
                shape.toFront();
            }
        });
        shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shape.setCursor(Cursor.HAND);
            }
        });
        shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double translateX = mouseEvent.getSceneX() + dragDelta.x;
                double translateY = mouseEvent.getSceneY() + dragDelta.y;

                if ((translateX + shape.getWidth()) < 1134 && translateX > 0){
                    shape.setTranslateX(translateX);
                }

                if (translateY < 637 && translateY > 0){
                    shape.setTranslateY(translateY);
                }
            }
        });
    }
    //records relative x and y coordinates:
    class Delta {
        double x, y;
    }
}
