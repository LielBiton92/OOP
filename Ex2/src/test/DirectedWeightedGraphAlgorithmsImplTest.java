package test;

import com.api.DirectedWeightedGraph;
import com.api.DirectedWeightedGraphAlgorithms;
import com.api.NodeData;
import com.impl.DirectedWeightedGraphAlgorithmsImpl;
import com.impl.DirectedWeightedGraphImpl;
import com.impl.NodeDataImpl;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class DirectedWeightedGraphAlgorithmsImplTest {


    @Test
    public void validateCopy() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/100000.json");
        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(g1);
        DirectedWeightedGraph other = algorithms.copy();
        assertNotNull(other);
        assertEquals(g1.edgeSize(), other.edgeSize());
        assertEquals(getCounterIterator(g1.edgeIter()), getCounterIterator(other.edgeIter()));
        assertEquals(getCounterIterator(g1.nodeIter()), getCounterIterator(other.nodeIter()));
        assertEquals(getAllNodesSoredKeys(g1), getAllNodesSoredKeys(other));
    }

    @Test
    public void validateConnected() throws FileNotFoundException {
        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/100000.json"));
        assertTrue(algorithms.isConnected());
    }

//    @Test
//    public void validateNotConnected() throws FileNotFoundException {
//        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/10000Nodes.json"));
//        assertFalse(algorithms.isConnected());
//    }

//    @Test
//    public void validateShortPath() throws FileNotFoundException {
//        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/G5_SMALL_GRAPH.json"));
//        List<NodeData> list = algorithms.shortestPath(0, 1);
//        assertEquals(3, list.size());
//        assertEquals(0, list.get(0).getKey());
//        assertEquals(2, list.get(1).getKey());
//        assertEquals(1, list.get(2).getKey());
//    }
//
//    @Test
//    public void validateShortPath_Dist() throws FileNotFoundException {
//        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/G5_SMALL_GRAPH.json"));
//        assertEquals(2.0, algorithms.shortestPathDist(0, 1), 0.001);
//    }

    @Test
    public void validateSaveFile() throws FileNotFoundException {
        loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/100000.json")).save("test_save.json");
        DirectedWeightedGraph directedWeightedGraph = new DirectedWeightedGraphImpl("test_save.json");
        assertEquals(2000000, directedWeightedGraph.edgeSize());
       // assertEquals(directedWeightedGraph.getEdge(0, 2).getWeight(), 1.0, 0.001);
        assertEquals(getCounterIterator(directedWeightedGraph.nodeIter()), 100000);
    }

    @Test
    public void validateLoadFile() {
        DirectedWeightedGraphAlgorithms directedWeightedGraphAlgorithms = new DirectedWeightedGraphAlgorithmsImpl();
        assertTrue(directedWeightedGraphAlgorithms.load("data/100000.json"));
        assertEquals(2000000, directedWeightedGraphAlgorithms.getGraph().edgeSize());
     //   assertEquals(directedWeightedGraphAlgorithms.getGraph().getEdge(0, 2).getWeight(), 1.0, 0.001);
        assertEquals(getCounterIterator(directedWeightedGraphAlgorithms.getGraph().nodeIter()), 100000);
    }

    @Test
    public void validate_center() throws FileNotFoundException {
        DirectedWeightedGraphAlgorithms algorithms1 = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/G1.json"));
        DirectedWeightedGraphAlgorithms algorithms2 = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/G2.json"));
        DirectedWeightedGraphAlgorithms algorithms3 = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/G3.json"));
        NodeData c1 = algorithms1.center();
        NodeData c2 = algorithms2.center();
        NodeData c3 = algorithms3.center();
        assertEquals(c1.getKey(), 8);
        assertEquals(c2.getKey(), 0);
        assertEquals(c3.getKey(), 40);
    }

    @Test
    public void validate_1000NodesCenter() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        DirectedWeightedGraphAlgorithms algorithms1 = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/1000Nodes.json"));
        System.out.println(algorithms1.center().getKey());
        long end = System.currentTimeMillis();
        long seconds = ((end - start) / 1000) % 60;
        System.out.println("1000Nodes - DEBUG: Logic A took " + seconds + " Seconds");
    }

    @Test
    public void validate_10000NodesCenter() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        DirectedWeightedGraphAlgorithms algorithms1 = loadGraphAlgorithem(new DirectedWeightedGraphImpl("data/10000Nodes.json"));
        System.out.println(algorithms1.center().getKey());
        long end = System.currentTimeMillis();
        long seconds = ((end - start) / 1000) % 60;
        System.out.println("1000Nodes - DEBUG: Logic A took " + seconds + " Seconds");
    }

    @Test(expected = RuntimeException.class)
    public void validate_RunTimeExceptionWhenChangeGraph() throws FileNotFoundException {
        DirectedWeightedGraph g1 = new DirectedWeightedGraphImpl("data/G1.json");
        Iterator<NodeData> nodeDataIterator = g1.nodeIter();
        while (nodeDataIterator.hasNext()) {
            nodeDataIterator.next();
            g1.addNode(new NodeDataImpl(18, "1.0,2.0,0.0"));
        }
    }

    @Test
    public void validate_Tsp() {
        DirectedWeightedGraph graph = new DirectedWeightedGraphImpl();
        NodeData n1 = new NodeDataImpl(1, "1,1,0");
        NodeData n2 = new NodeDataImpl(2, "5,1,0");
        NodeData n3 = new NodeDataImpl(3, "1,5,0");
        NodeData n4 = new NodeDataImpl(4, "5,5,0");
        NodeData[] nodes = {n1, n2, n3, n4};
        for (NodeData node : nodes) {
            graph.addNode(node);
        }
        graph.connect(n1.getKey(), n2.getKey(), 1);
        graph.connect(n1.getKey(), n4.getKey(), 5);
        graph.connect(n1.getKey(), n3.getKey(), 8);
        graph.connect(n2.getKey(), n3.getKey(), 5);
        graph.connect(n2.getKey(), n4.getKey(), 1);
        graph.connect(n2.getKey(), n1.getKey(), 7);
        graph.connect(n3.getKey(), n1.getKey(), 4);
        graph.connect(n3.getKey(), n4.getKey(), 1);
        graph.connect(n4.getKey(), n3.getKey(), 3);
        DirectedWeightedGraphAlgorithms algorithms = loadGraphAlgorithem(graph);
        List<NodeData> cities = new ArrayList<>() {
            {
                add(n1);
                add(n4);
                add(n3);
            }
        };
        List<NodeData> expected = new ArrayList<>() {
            {
                add(n3);
                add(n4);
                add(n3);
                add(n1);
            }
        };
        assertEquals(algorithms.tsp(cities), expected);
    }


    private DirectedWeightedGraphAlgorithms loadGraphAlgorithem(DirectedWeightedGraph graph) {
        DirectedWeightedGraphAlgorithms directedWeightedGraphAlgorithms = new DirectedWeightedGraphAlgorithmsImpl();
        directedWeightedGraphAlgorithms.init(graph);
        return directedWeightedGraphAlgorithms;
    }

    private List<Integer> getAllNodesSoredKeys(DirectedWeightedGraph graph) {
        List<Integer> list = new ArrayList<>();
        Iterator<NodeData> iterator = graph.nodeIter();
        while (iterator.hasNext()) {
            list.add(iterator.next().getKey());
        }
        Collections.sort(list);
        return list;
    }
//
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