package flowChart.shapes;

/**
 * Class for creating a text-object, extends the superclass FlowShape.
 */

import javafx.scene.text.Text;

/**
 * Created by Jonas on 2016-02-03.
 */

public class FlowText extends FlowShape {
    public FlowText() {
        String textLabel = getText();
        if (!textLabel.equals("cancel")) {
            Text text = new Text(textLabel);
            this.getChildren().add(text);
        }
    }
}
