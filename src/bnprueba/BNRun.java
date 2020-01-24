/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bnprueba;

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.BNScene;

/**
 *
 * @author xecar
 */
public class BNRun extends Application {
    
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {  
        Scene sc = new Scene(new BNScene(primaryStage).getRoot(),1500,1000);
        String fontSheet = fileToStylesheetString( new File ("src/css/estilos.css") );

        if ( fontSheet != null ) {
            sc.getStylesheets().add( fontSheet );
        }
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    
    public static String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }
    
}
