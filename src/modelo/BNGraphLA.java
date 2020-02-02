/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author xecar
 */
public class BNGraphLA<E> implements Serializable{
    private List<Vertex<E>> vertexes;
    private boolean directed;
    private Vertex<E> origen = null;
    private Vertex<E> destino = null;
    
    public BNGraphLA(boolean directed){
        this.vertexes = new LinkedList<>();
        this.directed = directed;
    }
    
    public boolean isEmpty(){
        return vertexes.isEmpty();
    }
    
    public boolean addVertex(E data){
        if(data==null){
            return false;
        }
        Vertex<E> v = new Vertex<>(data);
        if(vertexes.contains(v)){
            return false;
        }
        return vertexes.add(v);
    }
    
    public boolean addEdge(E origen, E destino, int peso, String pelicula){
        if(origen==null || destino==null){
            return false;
        }
        Vertex<E> og, des;
        og = searchVertex(origen);
        des = searchVertex(destino);
        if(og==null||des==null){
            return false;
        }else{
            Edge<E> edge = new Edge<>(og, des, peso, pelicula);
            if(og.getEdges().contains(edge)){
                return false;
            }
            og.getEdges().add(edge);
            if(!directed){
                edge = new Edge<>(des, og, peso, pelicula);
                des.getEdges().add(edge);
            }
            return true;
        }
        
    }
    
    private Vertex<E> searchVertex(E data){
        for(Vertex<E> v: vertexes){
            if(v.getData().equals(data)){
                return v;
            }
        }
        return null;
    }
    
    public boolean removeVertex(E data){
        if(data==null){
            return false;
        }
        for(Vertex<E> v: vertexes){
            Iterator it = v.getEdges().iterator();
            while(it.hasNext()){
                Edge<E> edge = (Edge<E>) it.next();
                if((edge.getDestino().getData().equals(data))||(edge.getOrigen().getData().equals(data))){
                    it.remove();
                }
            }
        }
        return vertexes.remove(new Vertex<>(data));
    }
    
    public boolean removeEdge(E origen, E destino){
        if(origen==null || destino==null){
            return false;
        }
        Vertex<E> og, des;
        og = searchVertex(origen);
        des = searchVertex(destino);
        if(og==null||des==null){
            return false;
        }else{
            Edge<E> edge = new Edge<>(og, des, 1);
            og.getEdges().remove(edge);
            if(!directed){
                edge = new Edge<>(des, og, 1);
                des.getEdges().remove(edge);
            }
            return true;
        }
    }
    
    public int outDegree(E data){
        if(data == null){
            return -1;
        }
        Vertex<E> v = searchVertex(data);
        if(v==null){
            return -1;
        }else{
            return v.getEdges().size();
        }
    }
    
    public int inDegree(E data){
        if(data == null){
            return -1;
        }else{
            int deg = 0;
            for(Vertex<E> v: vertexes){
                for(Edge<E> e: v.getEdges()){
                    if(e.getDestino().getData().equals(data)){
                        deg++;
                    }
                }
            }
            return deg;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder cadena = new StringBuilder();
        cadena.append("V:{");
        for(Vertex<E> v: vertexes){
            cadena.append(v.getData()).append(",");
        }
        String cd = cadena.substring(0, cadena.length() - 1) + "}";
        StringBuilder cadena2 = new StringBuilder();
        cadena2.append(", E:{(");
        for(Vertex<E> v: vertexes){
            for(Edge<E> e: v.getEdges()){
                cadena2.append(e.getOrigen().getData()).append(",").append(e.getDestino().getData()).append(",").append(e.getPelicula()).append("), (");
            }
        }
        cd += cadena2.substring(0, cadena2.length() -3) + "}";
        return cd;
    }
    
    public List<E> bfs(E data){
        List<E> l = new LinkedList<>();
        if(data==null){
            return l;
        }
        Vertex<E> v = searchVertex(data);
        if(v==null){
            return l;
        }
        Queue<Vertex<E>> q = new LinkedList<>();
        v.setVisited(true);
        q.offer(v);
        while(!q.isEmpty()){
            v = q.poll();
            l.add(v.getData());
            for(Edge<E> e :v.getEdges()){
                if(!e.getDestino().getVisited()){
                    e.getDestino().setVisited(true);
                    q.offer(e.getDestino());
                }
            }
        }
        cleanVertex();
        return l;
    }
    
    public List<E> dfs(E data){
        List<E> l = new LinkedList<>();
        if(data==null){
            return l;
        }
        Vertex<E> v = searchVertex(data);
        if(v==null){
            return l;
        }
        Stack<Vertex<E>> q = new Stack<>();
        v.setVisited(true);
        q.push(v);
        while(!q.isEmpty()){
            v = q.pop();
            l.add(v.getData());
            for(Edge<E> e :v.getEdges()){
                if(!e.getDestino().getVisited()){
                    e.getDestino().setVisited(true);
                    q.push(e.getDestino());
                }
            }
        }
        cleanVertex();
        return l;
    }
    
    public int distanciaMasCorta(E origen, E destino){
        if(origen==null||destino==null){
            return -1;
        }
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vd = searchVertex(destino);
        if(vo==null||vd==null){
            return -1;
        }
        Dijkstra(vo);
        return vd.getDistancia();
    }
    
    /*
    Recorridos
    Anchura : BFS
    Profundidad : DFS
    */

    private void cleanVertex() {
        for(Vertex<E> v : vertexes){
            v.setVisited(false);
        }
    }
    
    private void cleanVertexD() {
        for(Vertex<E> v : vertexes){
            v.setVisited(false);
            v.setAntecesor(null);
            v.setDistancia(Integer.MAX_VALUE);
        }
    }
    
    public Vertex<E> searchOrigen(E origen){
        if(origen == null) return null;
        else if(this.origen != null && this.origen.getData() == origen){
            return this.origen;
        }
        else{
            return searchVertex(origen);
        }
    }
    
    public Vertex<E> searchDestino(E destino){
        if(destino == null) return null;
        else if(this.destino != null && this.destino.getData() == destino){
            return this.destino;
        }
        else{
            return searchVertex(destino);
        }
    }
    
    private void Dijkstra(Vertex<E> origen){
        cleanVertexD();
        origen.setDistancia(0);
        PriorityQueue<Vertex<E>> pq = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2)->v1.getDistancia()-v2.getDistancia());
        pq.offer(origen);
        while(!pq.isEmpty()){
            origen = pq.poll();
            origen.setVisited(true);
            for(Edge<E> e: origen.getEdges()){
                Vertex<E> v = e.getDestino();
                if(!v.getVisited()){
                    if(v.getDistancia()>(origen.getDistancia()+e.getPeso())){
                        v.setDistancia(origen.getDistancia()+e.getPeso());
                        v.setAntecesor(origen);
                        pq.offer(v);
                    }
                }
            }
        }
    }
    
    
    public Stack<E> dijkstraActores(Vertex<E> vo, Vertex<E> vd){
        Stack<E> st = new Stack<>();
//        if(origen==null||destino==null){
//            return l;
//        }
//        Vertex<E> vo = searchVertex(origen);
//        Vertex<E> vd = searchVertex(destino);
//        if(vo==null||vd==null){
//            return l;
//        }
        Dijkstra(vo);
        Vertex<E> v = vd;
        
        while(v!=null){
            st.push(v.getData());
            v = v.getAntecesor();
            
        }
        return st;
    }
    
    
    
    public void BFSCaminoMasCorto(Vertex<E> origen){
        cleanVertexD();
        //Vertex<E> origen = searchVertex(og);
        if(origen!=null){
            origen.setDistancia(0);
            Queue<Vertex<E>> q = new LinkedList<>();
            origen.setVisited(true);
            q.offer(origen);
            while(!q.isEmpty()){
                origen = q.poll();
                for(Edge<E> e: origen.getEdges()){
                    Vertex<E> v = e.getDestino();
                    if(!v.getVisited()){
                        v.setAntecesor(origen);
                        v.setDistancia(origen.getDistancia()+1);
                        v.setVisited(true);
                        q.offer(v);
                    }
                }
            }
        }
    }
    
    public void DFSCamino(Vertex<E> origen){
        cleanVertexD();
        
        if(origen != null){
            Stack<Vertex<E>> q = new Stack<>();
            origen.setVisited(true);
            q.push(origen);
            Vertex<E> v = origen;
            while(!q.isEmpty()){
                v = q.pop();
                //l.add(v.getData());
                for(Edge<E> e :v.getEdges()){
                    if(!e.getDestino().getVisited()){
                        e.getDestino().setVisited(true);
                        e.getDestino().setAntecesor(v);
                        q.push(e.getDestino());
                    }
                }
            }
        }
//        List<E> l = new LinkedList<>();
//        if(data==null){
//            return l;
//        }
//        Vertex<E> v = searchVertex(data);
//        if(v==null){
//            return l;
//        }
//        Stack<Vertex<E>> q = new Stack<>();
//        v.setVisited(true);
//        q.push(v);
//        while(!q.isEmpty()){
//            v = q.pop();
//            l.add(v.getData());
//            for(Edge<E> e :v.getEdges()){
//                if(!e.getDestino().getVisited()){
//                    e.getDestino().setVisited(true);
//                    q.push(e.getDestino());
//                }
//            }
//        }
//        cleanVertex();
//        return l;
        
        
        
    }
    
    public Stack<E> rutaActores(Vertex<E> vd){
        Stack<E> l = new Stack<>();
//        if(destino==null){
//            return l;
//        }
//        Vertex<E> vd = searchVertex(destino);
//        if(vd==null){
//            return l;
//        }
        Vertex<E> v = vd;
        while(v!=null){
            l.add(v.getData());
            v = v.getAntecesor();
        }
        return l;
    }
    
    public Stack<String> rutaPeliculas(Vertex<E> vd){
        Stack<String> l = new Stack<>();
//        if(destino==null){
//            return l;
//        }
//        Vertex<E> vd = searchVertex(destino);
//        if(vd==null){
//            return l;
//        }
        Vertex<E> v = vd;
        while(v.getAntecesor()!=null){
            Vertex<E> va = v.getAntecesor();
            for(Edge<E> e : va.getEdges()){
                if(e.getDestino().equals(v)){
                    l.push(e.getPelicula());
                }
            }
            v = v.getAntecesor();
        }
        return l;
    }
    
}
