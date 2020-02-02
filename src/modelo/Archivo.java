/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import recursos.Constantes;

/**
 *
 * @author paula
 */
public class Archivo{
    private static BNGraphLA graph = new BNGraphLA(false);
    
    private Archivo(){
        
    }

    public static BNGraphLA getGraph() {
        return graph;
    }
    
    private static void asignarActores(String[] actores, String pelicula){
        for(String actor:actores){
            graph.addVertex(actor);
            for(String destino:actores){
                if( destino == null ? actor != null : !destino.equals(actor)){
                    if(!graph.addEdge(actor, destino, 1, pelicula))break;
                }
            }
        }
    }
    
    public static void cargarGraph(){
        String cadena;
        String archivo = Constantes.PATH_FILE;
        try (BufferedReader b = new BufferedReader(new FileReader(archivo));) {
            while((cadena = b.readLine())!=null) {
                String[] lista = cadena.split(":");
                String[] lista2 = lista[1].split(",");
                String pelicula = lista2[0];
                String[] list = cadena.split("\\[");
                if(list.length >= 1){
                    String[] actores = list[1].split(",");
                    asignarActores(actores,pelicula);
                }
            }
        
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
