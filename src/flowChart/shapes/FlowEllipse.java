package flowChart.shapes;

/**
 * Class for creating a ellipse, extends the superclass FlowShape.
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Jonas on 2016-02-03.
 */

public class FlowEllipse extends FlowShape {

    public FlowEllipse() {
        String label = getLabel();  //gets label from superclass:
        Text ellipseText = new Text(label);
        ellipseText.setTextAlignment(TextAlignment.CENTER);

        if (!label.equals("cancel")) {
            double ellipseWidth = ellipseText.getLayoutBounds().getWidth() + 7;
            if (ellipseWidth < 26){
                ellipseWidth = 30;
            }
            Ellipse ellipse = new Ellipse(ellipseWidth, 23);
            ellipse.setFill(getColor());
            ellipse.setStroke(Color.BLACK);
            setGlow(this);
            this.getChildren().addAll(ellipse, ellipseText);
        }
    }
}
