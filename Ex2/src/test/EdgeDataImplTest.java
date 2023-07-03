package test;

import com.api.DirectedWeightedGraph;
import com.impl.DirectedWeightedGraphImpl;
import com.impl.EdgeDataImpl;
import com.impl.NodeDataImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class EdgeDataImplTest  {
    DirectedWeightedGraphImpl graph = new DirectedWeightedGraphImpl("data/G1.json");

    public EdgeDataImplTest() throws FileNotFoundException {


    }


    @Test
    public void getSrc() throws FileNotFoundException {

        EdgeDataImpl edge= (EdgeDataImpl) graph.getEdge(0,16);
        assertEquals(0,edge.getSrc());
    }

    @Test
    public void getDest() throws FileNotFoundException {
        EdgeDataImpl edge= (EdgeDataImpl) graph.getEdge(0,16);
        assertEquals(16,edge.getDest());
    }

    @Test
    public void getWeight() throws FileNotFoundException {
      double u=  graph.getEdge(0,16).getWeight();
      float i = (float) 1.3118716362419698;
        Assertions.assertEquals(1.3118716362419698,u);
    }

    @Test
    public void getInfo() throws FileNotFoundException {
        EdgeDataImpl edge = new EdgeDataImpl(1,1,5);
       String s=  edge.getInfo();
       assertEquals("WHITE", s);


    }

    @Test
    public void setInfo() throws FileNotFoundException {
        EdgeDataImpl edge = new EdgeDataImpl(1,1,5);
        edge.setInfo("BLACK");
        assertEquals("BLACK", edge.getInfo());
    }

    @Test
    public void getTag() throws FileNotFoundException {
        EdgeDataImpl edge = new EdgeDataImpl(1,1,5);
        int s = edge.getTag();
        assertEquals(-1,s);
    }

    @Test
    public void setTag() throws FileNotFoundException {
        EdgeDataImpl edge = new EdgeDataImpl(1,1,5);
        edge.setTag(8);
        assertEquals(8,edge.getTag());
    }
}