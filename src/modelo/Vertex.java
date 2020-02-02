/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author xecar
 */
public class Vertex<E>{
    private E data;
    private List<Edge<E>> edges;
    private boolean visited;
    private int distancia;
    private Vertex<E> antecesor;
    
    public Vertex(E data){
        this.data = data;
        this.edges = new LinkedList<>();
        this.visited = false;
        this.distancia = Integer.MAX_VALUE;
        this.antecesor = null;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<Edge<E>> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge<E>> edges) {
        this.edges = edges;
    }
    
    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Vertex<E> getAntecesor() {
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        this.antecesor = antecesor;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null||!(obj instanceof Vertex)) {
            return false;
        }
        Vertex<E> ver = (Vertex<E>) obj;
        return this.data.equals(ver.data);
    }
    
    
}
