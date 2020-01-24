/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    private final double xPos = 150;
    private final double yPos = 450;
    private BNGraphLA<String> grafo;
    private BorderPane root;
    private HBox panelInferior;
    private Button findb;
    private TextField origin;
    private TextField destiny;
    private StackPane nodo;
    private GraphicsContext gc;
    private int tamaño;
    private Pane paneI;
    private Pane pane;
    private Pane paneD;
    
    public BNScene(Stage stage){
        grafo = new BNGraphLA<>(false);
        grafo.addVertex("a");
        grafo.addVertex("b");
        grafo.addVertex("c");
        grafo.addEdge("a", "b", 1, "cosa");
        grafo.addEdge("b", "c", 1, "otro");
        //cargarGrafo();
        root = new BorderPane();
        nodo = new StackPane();
        pane = new Pane();
        pane.setId("pane");
        pane.prefWidth(500);
        paneI = new Pane();
        paneI.setId("pane");
        paneI.prefWidth(500);
        paneD = new Pane();
        paneD.setId("pane");
        paneD.prefWidth(500);
        HBox paneBox = new HBox();
        paneBox.getChildren().addAll(paneI, pane, paneD);
        HBox.setHgrow(paneI, Priority.ALWAYS);
        HBox.setHgrow(pane, Priority.ALWAYS);
        HBox.setHgrow(paneD, Priority.ALWAYS);
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
        root.setCenter(paneBox);
        panelInferior.getChildren().addAll(origin, new Label("to"), destiny, findb);
        panelInferior.setSpacing(10);
        findb.setOnAction(e->{
            if(!paneI.getChildren().isEmpty()){
                paneI.getChildren().remove(0, paneI.getChildren().size());
            }
            grafo.BFSCaminoMasCorto(origin.getText());
            dibujarActor(grafo.rutaActores(destiny.getText()), paneI);
            dibujarPelicula(grafo.rutaPeliculas(destiny.getText()), paneI);
            if(!pane.getChildren().isEmpty()){
                pane.getChildren().remove(0, pane.getChildren().size());
            }
            grafo.BFSCaminoMasCorto(origin.getText());
            dibujarActor(grafo.rutaActores(destiny.getText()), pane);
            dibujarPelicula(grafo.rutaPeliculas(destiny.getText()), pane);
            if(!paneD.getChildren().isEmpty()){
                paneD.getChildren().remove(0, paneD.getChildren().size());
            }
            grafo.BFSCaminoMasCorto(origin.getText());
            dibujarActor(grafo.rutaActores(destiny.getText()), paneD);
            dibujarPelicula(grafo.rutaPeliculas(destiny.getText()), paneD);
        });
    }
    
    private void dibujarActor(Stack<String> vList, Pane pane){
        tamaño = vList.size();
        String n = vList.pop();
        System.out.println(n);
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIMEGREEN);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, yPos/tamaño);
        System.out.println(yPos/tamaño);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, yPos/tamaño + 160, pane);
        }
    }
    
    private void dibujarActor(Stack<String> vList, double y, Pane pane){
        String n = vList.pop();
        System.out.println(n);
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIMEGREEN);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, y);
        System.out.println(y);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, y + 160, pane);
        }
    }
    
    private void dibujarPelicula(Stack<String> pList, Pane pane){
        System.out.println(pList);
        String n = pList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, yPos/tamaño + 80);
        System.out.println(yPos/tamaño + 80);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!pList.isEmpty()){
            dibujarPelicula(pList, yPos/tamaño + 240, pane);
        }
        dibujarFlecha("was in", (yPos/tamaño) + 50, xPos - 5, pane);
        dibujarFlecha("with", yPos/tamaño + 130, xPos, pane);
    }
    
    private void dibujarPelicula(Stack<String> pList, double y, Pane pane){
        String n = pList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, y);
        System.out.println(y);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!pList.isEmpty()){
            dibujarPelicula(pList, y + 160, pane);
        }
        dibujarFlecha("was in", y - 30, xPos - 5, pane);
        dibujarFlecha("with", y + 50, xPos, pane);
    }
    
    public BorderPane getRoot(){
        return root;
    }

    private void dibujarFlecha(String n, double d, double x, Pane pane) {
        Text t = new Text();
        InputStream input = BNScene.class.getResourceAsStream("/recursos/edgeImg.png");
        Image i = new Image(input,30,30,false,true);
        ImageView iv = new ImageView(i);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.setLayoutX(x+85);
        sp.setLayoutY(d);
        sp.relocate(x+85, d);
        System.out.println(d);
        sp.getChildren().addAll(iv,t);
        pane.getChildren().add(sp);
    }
}
