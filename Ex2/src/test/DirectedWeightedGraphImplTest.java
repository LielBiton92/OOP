package test;

import com.api.DirectedWeightedGraph;
import com.api.EdgeData;
import com.impl.DirectedWeightedGraphImpl;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class DirectedWeightedGraphImplTest {

    @Test
    public void validateJsonReader() throws FileNotFoundException {
        assertNotNull(new DirectedWeightedGraphImpl("data/G1.json"));
        assertNotNull(new DirectedWeightedGraphImpl("data/G2.json"));
        assertNotNull(new DirectedWeightedGraphImpl("data/G3.json"));

    }

    @Test
    public void validateNodes() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/G1.json");
        assertEquals(getCounterIterator(g1.nodeIter()), 17);
        DirectedWeightedGraph g2 = new DirectedWeightedGraphImpl("data/G2.json");
        assertEquals(getCounterIterator(g2.nodeIter()), 31);
        DirectedWeightedGraph g3 = new DirectedWeightedGraphImpl("data/G3.json");
        assertEquals(getCounterIterator(g3.nodeIter()), 48);
    }

    @Test
    public void validateEdges() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/G1.json");
        assertEquals(getCounterIterator(g1.edgeIter()), 36);
        assertEquals(g1.edgeSize(), 36);
        DirectedWeightedGraph g2 = new DirectedWeightedGraphImpl("data/G2.json");
        assertEquals(getCounterIterator(g2.edgeIter()), 80);
        assertEquals(g2.edgeSize(), 80);
        DirectedWeightedGraph g3 = new DirectedWeightedGraphImpl("data/G3.json");
        assertEquals(getCounterIterator(g3.edgeIter()), 166);
        assertEquals(g3.edgeSize(), 166);
    }

    @Test
    public void validateNodeData() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/G1.json");
        assertEquals(g1.getNode(0).getKey(), 0);
        assertEquals(g1.getNode(0).getLocation().x(), 35.19589389346247, 0.00001);
        assertEquals(g1.getNode(0).getLocation().y(), 32.10152879327731, 0.00001);
        assertEquals(g1.getNode(0).getLocation().z(), 0.0, 0.001);
    }

    @Test
    public void validateEdgeData() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/G1.json");
        EdgeData edgeData = g1.getEdge(0, 16);
        assertNotNull(edgeData);
        assertEquals(edgeData.getSrc(), 0);
        assertEquals(edgeData.getDest(), 16);
        assertEquals(edgeData.getWeight(), 1.3118716362419698, 0.00001);
    }



    private int getCounterIterator(Iterator data) {
        int counter = 0;
        Iterator it = data;
        while (data.hasNext()) {
            Object i = it.next();
            counter++;
        }
        return counter;
    }

}
