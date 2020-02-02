/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.InputStream;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Archivo;
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
    private ScrollPane scroll, scroll2,scroll3;
    private Pane paneI;
    private Pane pane;
    private Pane paneD;
    
    public BNScene(Stage stage){
        Archivo.cargarGraph();
        grafo = Archivo.getGraph();
        root = new BorderPane();
        nodo = new StackPane();
        pane = new Pane();
        pane.setId("pane");
        
        pane.setPrefWidth(450);
        paneI = new Pane();
        paneI.setId("pane");
        paneI.setPrefWidth(450);
        paneD = new Pane();
        paneD.setId("pane");
        paneD.setPrefWidth(450);
        HBox paneBox = new HBox();
        VBox pane1 = new VBox();
        VBox pane2 = new VBox();
        VBox pane3 = new VBox();
        scroll = new ScrollPane();
        scroll3 = new ScrollPane();
        scroll2 = new ScrollPane();
        scroll.setContent(paneI);
        scroll2.setContent(pane);
        scroll3.setContent(paneD);
        pane1.getChildren().add(scroll);
        pane2.getChildren().add(scroll2);
        pane3.getChildren().add(scroll3);
        paneBox.getChildren().addAll(pane1, pane2, pane3);
        HBox.setHgrow(scroll, Priority.ALWAYS);
        HBox.setHgrow(scroll2, Priority.ALWAYS);
        HBox.setHgrow(scroll3, Priority.ALWAYS);
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
            
            Vertex<String> origen = grafo.searchOrigen(origin.getText());
            Vertex<String> destino = grafo.searchDestino(destiny.getText());
            if(origen == null || destino == null){
                actoresNotFound();
            }
            else{
                long inicio= System.currentTimeMillis();
                grafo.BFSCaminoMasCorto(origen);
                dibujarActor(grafo.rutaActores(destino), paneI);
                dibujarPelicula(grafo.rutaPeliculas(destino), paneI);
                if(!pane.getChildren().isEmpty()){
                    pane.getChildren().remove(0, pane.getChildren().size());
                }
                long finBFS = System.currentTimeMillis();
                long timeBFS = finBFS-inicio;
                Label l1 = new Label("Tiempo BFS:" + timeBFS);
                pane1.getChildren().add(l1);

                //DFS
                inicio= System.currentTimeMillis();
                grafo.DFSCamino(origen);
                dibujarActor(grafo.rutaActores(destino), pane);
                dibujarPelicula(grafo.rutaPeliculas(destino), pane);
                if(!paneD.getChildren().isEmpty()){
                    paneD.getChildren().remove(0, paneD.getChildren().size());
                }
                long finDFS = System.currentTimeMillis();
                long timeDFS = finDFS-inicio;
                Label l2 = new Label("Tiempo DFS:" + timeDFS);
                pane2.getChildren().add(l2);
                
                //Dijkstra
                inicio= System.currentTimeMillis();
                dibujarActor(grafo.dijkstraActores(origen, destino), paneD);
                dibujarPelicula(grafo.rutaPeliculas(destino), paneD);
                long finDij = System.currentTimeMillis();
                long timeDIJ = finDij-inicio;
                Label l3 = new Label("Tiempo DIJKSTRA:" + timeDIJ);
                pane3.getChildren().add(l3);
            }
        });
    }
    
    private void actoresNotFound(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("No se encontró el actor que ingresó");
        alert.showAndWait();
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
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, yPos/tamaño + 160, pane);
        }
    }
    
    private void dibujarActor(Stack<String> vList, double y, Pane pane){
        String n = vList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIMEGREEN);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, y);
        sp.getChildren().addAll(r1,t);
        pane.getChildren().add(sp);
        if(!vList.isEmpty()){
            dibujarActor(vList, y + 160, pane);
        }
    }
    
    private void dibujarPelicula(Stack<String> pList, Pane pane){
        String n = pList.pop();
        Text t = new Text();
        Rectangle r1 = new Rectangle(200,50); 
        r1.setFill(Color.LIGHTBLUE);
        StackPane sp = new StackPane();
        t.setText(n);
        sp.relocate(xPos, yPos/tamaño + 80);
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
        sp.getChildren().addAll(iv,t);
        pane.getChildren().add(sp);
    }
}
