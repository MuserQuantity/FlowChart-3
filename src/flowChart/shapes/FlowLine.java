package flowChart.shapes;

/**
 * Class for creating a Line, extends Line.
 * The constructor has two objects of FlowShape as imparameters.
 * It binds the selected shapes to the line-ends, so when the
 * shape is dragged, the line follows.
 */

import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Jonas on 2016-02-04.
 */

public class FlowLine extends Line {
    private DropShadow lineGlow = new DropShadow(2, Color.YELLOW);

    public FlowLine(FlowShape shape1, FlowShape shape2) {

        startXProperty().bind(shape1.translateXProperty().add(shape1.widthProperty().divide(2)));
        startYProperty().bind(shape1.translateYProperty().add(shape1.heightProperty().divide(2)));
        endXProperty().bind(shape2.translateXProperty().add(shape2.widthProperty().divide(2)));
        endYProperty().bind(shape2.translateYProperty().add(shape2.heightProperty().divide(2)));

        setOnMouseEntered(event -> setEffect(lineGlow));
        setOnMouseExited(event -> setEffect(null));
        setCursor(Cursor.HAND);
    }
}
