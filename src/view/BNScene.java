/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.BNGraphLA;
import modelo.Vertex;

/**
 *
 * @author xecar
 */
public class BNScene {
    private double xPos;
    private double yPos;
    private BNGraphLA<String> grafo;
    private BorderPane root;
    private HBox panelInferior;
    private Button findb;
    private TextField origin;
    private TextField destiny;
    private StackPane nodo;
    private GraphicsContext gc;
    private int NodoANodo = 20;
    private Pane pane;
    
    public BNScene(Stage stage){
        grafo = new BNGraphLA<>(false);
        grafo.addVertex("a");
        grafo.addVertex("b");
        grafo.addEdge("a", "b", 1, "cosa");
        //cargarGrafo();
        root = new BorderPane();
        nodo = new StackPane();
        pane = new Pane();
        pane.setId("pane");
        panelInferior = new HBox();
        panelInferior.setId("hbox");
        findb = new Button("Find");
        origin = new TextField();
        destiny = new TextField();
        Label titulo = new Label("Oracle of Bacon");
        HBox box = new HBox();
        box.setId("hbox");
        box.getChildren().add(titulo);
        root.setTop(box);
        root.setBottom(panelInferior);
        root.setCenter(pane);
        panelInferior.getChildren().addAll(origin, new Label("to"), destiny, findb);
        panelInferior.setSpacing(10);
        xPos = 250;
        yPos = 250;
        findb.setOnAction(e->{
            grafo.BFSCaminoMasCorto(origin.getText());
            dibujarActor(grafo.rutaActores(destiny.getText()));
            dibujarPelicula(grafo.rutaPeliculas(destiny.getText()));
        });
    }
    
    private void dibujarActor(Stack<String> vList){
        String n = vList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.setLayoutX(xPos);
        sp.setLayoutY(yPos/vList.size());
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, yPos/vList.size() + 160);
        }
    }
    
    private void dibujarActor(Stack<String> vList, double y){
        String n = vList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.setLayoutX(xPos);
        sp.setLayoutY(y);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, y + 160);
        }
    }
    
    private void dibujarPelicula(Stack<String> pList){
        String n = pList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.setLayoutX(xPos);
        sp.setLayoutY(yPos/(pList.size()+1) + 80);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!pList.isEmpty()){
            dibujarPelicula(pList, yPos/(pList.size()+1) + 160);
        }
    }
    
    private void dibujarPelicula(Stack<String> pList, double y){
        String n = pList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.setLayoutX(xPos);
        sp.setLayoutY(y);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!pList.isEmpty()){
            dibujarPelicula(pList, y + 160);
        }
    }
    
    public BorderPane getRoot(){
        return root;
    }
}
