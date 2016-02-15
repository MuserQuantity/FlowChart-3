package flowChart;

/**
 * The view-class with the GUI.
 * Includes methods for setting events, events are handled in the controller class.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View  {

    private Stage window;
    private BorderPane borderPane;
    private AnchorPane workSpaceLayout;
    private Rectangle menuRectangle;
    private Ellipse menuEllipse;
    private Rectangle menuDiamond;
    private StackPane textStack;
    private StackPane arrowStack;
    private InnerShadow shapeShadow;
    private Text helpText;
    private MenuItem newFlowchart;
    private MenuItem saveFlowchart;
    private MenuItem exitMenu;
    private MenuItem helpMenuItem;

    public View(Stage window){

        //init window:
        this.window = window;
        window.setTitle("jS-flowChart");

        //build window:
        buildWindow();

        //set scene:
        Scene scene = new Scene(borderPane, 1280, 800);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    void buildWindow(){
        //main layout for project:
        borderPane = new BorderPane();

        //VBox-layout for creating new shapes(the tool-window):
        VBox createShapeLayout = new VBox(30);
        createShapeLayout.setPadding(new Insets(10,10,20,10));
        createShapeLayout.setPrefWidth(120);
        createShapeLayout.setStyle("-fx-background-color: #336699;");
        createShapeLayout.setAlignment(Pos.TOP_CENTER);

        //create the the tool-window (the shapes):
        Text shapeText = new Text("Create shapes");
        shapeText.setStyle("-fx-font-family: 'Helvetica', Arial, sans-serif; -fx-font-size: 16px; -fx-underline: true;");

        menuEllipse = new Ellipse(50,25);
        menuEllipse.setFill(Color.WHITE);
        menuEllipse.setStroke(Color.BLACK);
        menuEllipse.setStrokeWidth(1.3);
        menuEllipse.setCursor(Cursor.HAND);

        menuRectangle = new Rectangle(100,50);
        menuRectangle.setFill(Color.WHITE);
        menuRectangle.setStroke(Color.BLACK);
        menuRectangle.setStrokeWidth(1.3);
        menuRectangle.setCursor(Cursor.HAND);

        menuDiamond = new Rectangle(70,70);
        menuDiamond.setRotate(45);
        menuDiamond.setFill(Color.WHITE);
        menuDiamond.setStroke(Color.BLACK);
        menuDiamond.setStrokeWidth(1.3);
        menuDiamond.setCursor(Cursor.HAND);

        //place shapes in a Vbox:
        VBox shapeBox = new VBox(30);
        shapeBox.setAlignment(Pos.CENTER);
        shapeBox.setStyle("-fx-background-color: #c6d9ec; -fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;");
        shapeBox.getChildren().addAll(shapeText, menuRectangle, menuDiamond, menuEllipse );
        shapeBox.setPrefWidth(110);
        shapeBox.setPrefHeight(300);
        shapeBox.setPadding(new Insets(10,10,10,10));

        //button for creating text:
        Text menuText = new Text("Add text");
        menuText.setStyle("-fx-font-family: 'Helvetica', Arial, sans-serif; -fx-font-size: 16px;");
        textStack = new StackPane(menuText);
        textStack.setPrefWidth(100);
        textStack.setPrefHeight(50);
        textStack.setStyle("-fx-background-color: #c6d9ec; -fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;");
        textStack.setCursor(Cursor.HAND);

        //button for creating line:
        Line menuArrow = new Line();
        Text arrowText = new Text("Add line");
        arrowText.setStyle("-fx-font-family: 'Helvetica', Arial, sans-serif; -fx-font-size: 16px;");
        VBox arrowBox = new VBox(8);
        arrowBox.setAlignment(Pos.CENTER);
        arrowBox.getChildren().addAll(arrowText,menuArrow);
        menuArrow.setStrokeWidth(2);
        menuArrow.setStartX(5);
        menuArrow.setEndX(95);
        arrowStack = new StackPane(arrowBox);
        arrowStack.setPrefWidth(100);
        arrowStack.setPrefHeight(50);
        arrowStack.setStyle("-fx-background-color: #c6d9ec; -fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;");
        arrowStack.setCursor(Cursor.HAND);

        //frame around workspace:
        Path frame = new Path();
        MoveTo moveTo = new MoveTo(0,1);
        LineTo lineTo1 = new LineTo(1132,1);
        LineTo lineTo2 = new LineTo(1132,681);
        LineTo lineTo3 = new LineTo(0,681);
        LineTo lineTo4 = new LineTo(0,1);
        frame.getElements().addAll(moveTo,lineTo1,lineTo2,lineTo3,lineTo4);

        //helptext for creating a line:
        helpText = new Text("");
        helpText.setFill(Color.RED);

        //effect for clicking a button in the tool-window:
        shapeShadow = new InnerShadow(20, Color.BLACK);

        //workspace layout:
        workSpaceLayout = new AnchorPane();
        workSpaceLayout.setMinWidth(1134);

        //create the top menu bar:
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        newFlowchart = new MenuItem("New flowchart");
        saveFlowchart = new MenuItem("Export as .PNG");
        exitMenu = new MenuItem("Exit");
        helpMenuItem = new MenuItem("Help");
        fileMenu.getItems().addAll(newFlowchart, saveFlowchart, helpMenuItem, exitMenu);
        menuBar.getMenus().addAll(fileMenu);

        //init all layouts:
        borderPane.setTop(menuBar);
        borderPane.setLeft(createShapeLayout);
        borderPane.setRight(workSpaceLayout);
        createShapeLayout.getChildren().addAll(shapeBox, textStack, arrowStack);
        workSpaceLayout.getChildren().addAll(frame, helpText);
        helpText.setX(3);
        helpText.setY(15);

    }

    //method for adding node to workspace:
    public void addToWorkspace(Node node){
        workSpaceLayout.getChildren().add(node);
    }

    //method for removing node from workspace:
    public void removeWorkspace(Node node){
        workSpaceLayout.getChildren().remove(node);
    }

    //method for setting helptext when creating a line:
    public void setHelptext(String helpTextIn){
        helpText.setText(helpTextIn);
    }

    //method for taking screenshot on workspace, returns the image:
    public WritableImage takeSnapShot(){
        return workSpaceLayout.snapshot(null,null);
    }

    //method for changing the workspace-background when making a line: (effect)
    public void changeWorkspaceBackgroud(String color){
        workSpaceLayout.setStyle("-fx-background-color:"+color+";");
    }

    //method for setting the effect when clicking a menu-button:
    public void setEffect(String shape){

        //Own commands depending on witch shape is clicked:
        switch (shape){
            case "rectOn":
                menuRectangle.setEffect(shapeShadow);
                break;
            case "rectOff":
                menuRectangle.setEffect(null);
                break;
            case "ellipseOn":
                menuEllipse.setEffect(shapeShadow);
                break;
            case "ellipseOff":
                menuEllipse.setEffect(null);
                break;
            case "diamondOn":
                menuDiamond.setEffect(shapeShadow);
                break;
            case "diamondOff":
                menuDiamond.setEffect(null);
                break;
            case "textOn":
                textStack.setEffect(shapeShadow);
                break;
            case "textOff":
                textStack.setEffect(null);
                break;
            case "arrowOn":
                arrowStack.setEffect(shapeShadow);
                break;
            case "arrowOff":
                arrowStack.setEffect(null);
                break;
        }
    }

    //listeners:--------------------------------------------------------------------------------------------------------
    void rectClicked (EventHandler<MouseEvent> mouseClicked){
        menuRectangle.setOnMouseClicked(mouseClicked);
    }

    void rectPressed (EventHandler<MouseEvent> mousePressed){
        menuRectangle.setOnMousePressed(mousePressed);
    }

    void rectReleased (EventHandler<MouseEvent> mouseReleased){
        menuRectangle.setOnMouseReleased(mouseReleased);
    }

    void ellipseClicked(EventHandler<MouseEvent> mouseClicked){
        menuEllipse.setOnMouseClicked(mouseClicked);
    }

    void ellipsePressed (EventHandler<MouseEvent> mousePressed){
        menuEllipse.setOnMousePressed(mousePressed);
    }

    void ellipseReleased (EventHandler<MouseEvent> mouseReleased){
        menuEllipse.setOnMouseReleased(mouseReleased);
    }

    void diamondClicked(EventHandler<MouseEvent> mouseClicked){
        menuDiamond.setOnMouseClicked(mouseClicked);
    }

    void diamondPressed (EventHandler<MouseEvent> mousePressed){
        menuDiamond.setOnMousePressed(mousePressed);
    }

    void diamondReleased (EventHandler<MouseEvent> mouseReleased){
        menuDiamond.setOnMouseReleased(mouseReleased);
    }

    void textClicked (EventHandler<MouseEvent> mouseClicked){
        textStack.setOnMouseClicked(mouseClicked);
    }

    void textPressed (EventHandler<MouseEvent> mousePressed){
        textStack.setOnMousePressed(mousePressed);
    }

    void textReleased (EventHandler<MouseEvent> mouseReleased){
        textStack.setOnMouseReleased(mouseReleased);
    }

    void arrowClicked (EventHandler<MouseEvent> mouseClicked){
        arrowStack.setOnMouseClicked(mouseClicked);
    }

    void exitPlatform (EventHandler<ActionEvent> exit){
        exitMenu.setOnAction(exit);
    }

    void setNewFlowchart (EventHandler<ActionEvent> newChart){
        newFlowchart.setOnAction(newChart);
    }

    void saveFlowChart (EventHandler<ActionEvent> saveChart){
        saveFlowchart.setOnAction(saveChart);
    }

    void setHelpMenuItem (EventHandler<ActionEvent> helpMenuEvent){
        helpMenuItem.setOnAction(helpMenuEvent);

    }
}
