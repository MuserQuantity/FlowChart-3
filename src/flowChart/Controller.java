package flowChart;

/**
 * The Controller-class, the communicator between the GUI and the logic.
 */

import flowChart.shapes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Jonas on 2016-01-28.
 */

public class Controller extends Application {
    //list for all created shapes:
    ObservableList<FlowShape> shapeList = FXCollections.observableArrayList();
    //list for all created lines:
    ObservableList<Node> arrowList = FXCollections.observableArrayList();
    //Array with shapes used for drawing lines:
    FlowShape[] shapeArray = new FlowShape[2];
    //reference to the view and the model:
    View view;
    Model model;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //creating the view and model, sending the stage to the constructor of the view:
        view = new View(primaryStage);
        model = new Model();

        /**
         * *************************** LISTENERS FOR TOOL-WINDOW *************************************
         * Clicking a shape creates a new shape and adds the shape to the observableList.
         * Pressing a shape creates a shadow-effect (with own commands).
         * Releasing a shape removes the shadow-effect (with own commands).
         **/

        //create rectangle:
        view.rectClicked(event -> {
            FlowRectangle createRectangle = new FlowRectangle();
            shapeList.add(createRectangle);
        });

        view.rectPressed(event -> {
            view.setEffect("rectOn");
        });

        view.rectReleased(event -> {
            view.setEffect("rectOff");
        });

        //create ellipse:
        view.ellipseClicked(event -> {
            FlowEllipse createEllipse = new FlowEllipse();
            shapeList.add(createEllipse);
        });

        view.ellipsePressed(event -> {
            view.setEffect("ellipseOn");
        });

        view.ellipseReleased(event -> {
            view.setEffect("ellipseOff");
        });

        //create diamond:
        view.diamondClicked(event -> {
            FlowDiamond createDiamond = new FlowDiamond();
            shapeList.add(createDiamond);
        });

        view.diamondPressed(event -> {
            view.setEffect("diamondOn");
        });

        view.diamondReleased(event -> {
            view.setEffect("diamondOff");
        });

        //create text:
        view.textClicked(event -> {
            FlowText createText = new FlowText();
            shapeList.add(createText);
        });

        view.textPressed(event -> {
            view.setEffect("textOn");
        });

        view.textReleased(event -> {
            view.setEffect("textOff");
        });

        /**
         * ******* LISTENERS FOR MENU-BAR *******
         * New chart clears the workspace from shapes and lines, clears both arraylists.
         * Save chart takes a screenshot on the workspace (method in model-class).
         * Help 
         */

        //new flowchart, remove all shapes and lines from workspace and clear arraylists:
        view.setNewFlowchart(event -> {
            shapeList.forEach(shape -> {
                view.removeWorkspace(shape);
            });
            arrowList.forEach(arrow -> {
                view.removeWorkspace(arrow);
            });

            arrowList.clear();
            shapeList.clear();
        });

        //export as PNG:
        view.saveFlowChart(event -> {
            model.screenshot(view.takeSnapShot());
        });

        //help menu:
        view.setHelpMenuItem(event -> {
            model.helpMenu();
        });

        //exit platform:
        view.exitPlatform(event1 -> {
            Platform.exit();
        });

        /**
         * onChanged listener for the observableList.
         * Whenever a new object is added to the list, all shapes are removed from the window then added again.
         * All shapes gets the rightclick-listener for deleting.
         */

        shapeList.addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {

                shapeList.forEach(shape -> {
                    view.removeWorkspace(shape);
                });

                shapeList.forEach(shape -> {
                    view.addToWorkspace(shape);
                });

                rightClickListener();
            }
        });

        /**
         * Creates a line from the class FlowLine .
         * Listener for creating a line between two shapes.
         * Listens to every shape in workspace,
         * When the first shape is clicked, the shapes coordinates are saved to an array.
         * When the second shape is clicked with SHIFT hold, the shapes coordinates are saved to same array.
         * When the array got no values that is null, the line is created, then clears the array again.
         */

        view.arrowClicked(event -> {
            if (shapeList.size() >= 2){
                view.setEffect("arrowOn");
                view.changeWorkspaceBackgroud("rgba(212, 232, 251, 0.51)");
                view.setHelptext("Click on the first shape for starting point...");
                final Delta startEnd = new Delta();

                shapeList.forEach(shape -> {

                    shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                            //point A, primary click on mouse:
                            if (!event.isShiftDown()) {
                                startEnd.startX = shape.getTranslateX() + (shape.getLayoutBounds().getWidth() / 2);
                                startEnd.startY = shape.getTranslateY() + (shape.getLayoutBounds().getHeight() / 2);


                                shapeArray[0] = shape;
                                view.setHelptext("Hold shift and click on the second shape for end point.");
                            }

                            //point B, SHIFT + primary click on mouse:
                            if (event.isShiftDown()) {
                                startEnd.endX = shape.getTranslateX() + (shape.getLayoutBounds().getWidth() / 2);
                                startEnd.endY = shape.getTranslateY() + (shape.getLayoutBounds().getHeight() / 2);

                                shapeArray[1] = shape;
                            }

                            //create line:
                            if (shapeArray[0] != null && shapeArray[1] != null) {

                                FlowLine createLine2 = new FlowLine(shapeArray[0], shapeArray[1]);
                                view.addToWorkspace(createLine2);
                                arrowList.add(createLine2);
                                createLine2.toBack();

                                shapeArray[0] = null;
                                shapeArray[1] = null;

                                view.setEffect("arrowOff");
                                view.changeWorkspaceBackgroud("null");
                                view.setHelptext("");

                                //clear listeners from all shapes when done(adds the original rightclick listener):
                                rightClickListener();
                            }
                        }
                    });
                });
            }
            else{
                //helptext shows for 2seconds.
                view.setHelptext("Requires at least two shapes.");
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(2000),
                        ae -> view.setHelptext("")));
                timeline.play();
            }
        });
    }

    //menubar for rightclicking, removes the selected shape or line from workspace and arraylist:
    void rightClick(Node shape, double x, double y){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(delete);
        contextMenu.show(shape,x,y);
        delete.setOnAction(event -> {

            if (shapeList.contains(shape)) {
                shapeList.remove(shape);
            }
            else if (arrowList.contains(shape)){
                arrowList.remove(shape);
            }
            view.removeWorkspace(shape);
        });
    }

    //listener for rightclick, checks both shapes and lines:
    void rightClickListener(){
        shapeList.forEach(shape -> {
            shape.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY){
                    rightClick(shape,event.getScreenX(),event.getScreenY());
                }
            });
        });

        arrowList.forEach(arrow -> {
            arrow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY){
                    rightClick(arrow,event.getScreenX(),event.getScreenY());
                }
            });
        });
    }
    // records x and y co-ordinates.
    class Delta {
        double startX, startY, endX, endY;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
