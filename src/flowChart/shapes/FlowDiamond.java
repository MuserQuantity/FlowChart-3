package flowChart.shapes;

/**
 * Class for creating a diamond, extends the superclass FlowShape.
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Jonas on 2016-02-03.
 */

public class FlowDiamond extends FlowShape {

    public FlowDiamond() {
        String label = getLabel();  //gets label from superclass:
        Text diamondText = new Text(label);
        diamondText.setTextAlignment(TextAlignment.CENTER);

        if (!label.equals("cancel")) {
            double diamondwidth = diamondText.getLayoutBounds().getWidth() + 17;
            if (diamondwidth < 35){
                diamondwidth = 40;
            }
            Rectangle diamond = new Rectangle(diamondwidth, diamondwidth);
            diamond.setFill(getColor());
            diamond.setStroke(Color.BLACK);
            diamond.setRotate(45);
            setGlow(this);
            this.getChildren().addAll(diamond, diamondText);
            this.setMinWidth(diamondwidth+30);
            this.setMinHeight(diamondwidth+30);
        }
    }
}
