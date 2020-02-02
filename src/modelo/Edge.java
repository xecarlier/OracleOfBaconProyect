/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author xecar
 */
public class Edge<E>{
    private Vertex<E> origen;
    private Vertex<E> destino;
    private int peso;
    private String pelicula;
    
    public Edge(Vertex<E> origen, Vertex<E> destino, int peso){
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    
    public Edge(Vertex<E> origen, Vertex<E> destino, int peso, String pelicula){
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.pelicula=pelicula;
    }

    public Vertex<E> getOrigen() {
        return origen;
    }

    public void setOrigen(Vertex<E> origen) {
        this.origen = origen;
    }

    public Vertex<E> getDestino() {
        return destino;
    }

    public void setDestino(Vertex<E> destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null||!(obj instanceof Edge)) {
            return false;
        }
        Edge<E> edg = (Edge<E>) obj;
        return (this.getOrigen().equals(edg.getOrigen()) && this.getDestino().equals(edg.getDestino()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.origen);
        hash = 89 * hash + Objects.hashCode(this.destino);
        hash = 89 * hash + this.peso;
        hash = 89 * hash + Objects.hashCode(this.pelicula);
        return hash;
    }
}
