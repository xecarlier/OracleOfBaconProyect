/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author paula
 */
public class MainPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Archivo a = new Archivo();
        Archivo.cargarGraph();
        BNGraphLA grap = Archivo.getGraph();
        //Archivo.guardarArbol();
        //System.out.println(grap.toString());
        //Archivo.muestraContenido();
    }
    
}
