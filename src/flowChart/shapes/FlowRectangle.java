package flowChart.shapes;

/**
 * Class for creating a rectangle, extends the superclass FlowShape.
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Jonas on 2016-02-03.
 */

public class FlowRectangle extends FlowShape {

    public FlowRectangle(){
        String label = getLabel();  //gets label from superclass:
        Text rectText = new Text(label);
        rectText.setTextAlignment(TextAlignment.CENTER);

        if (!label.equals("cancel")) {
            double rectWidth = rectText.getLayoutBounds().getWidth() + 40;
            if (rectWidth < 60){
                rectWidth = 60;
            }
            Rectangle rectangle = new Rectangle(rectWidth, 43);
            rectangle.setFill(getColor());
            rectangle.setStroke(Color.BLACK);
            setGlow(this);
            this.getChildren().addAll(rectangle, rectText);
        }
    }

}
