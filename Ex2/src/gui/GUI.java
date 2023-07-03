package gui;

import com.api.*;
import com.impl.DirectedWeightedGraphAlgorithmsImpl;
import com.impl.DirectedWeightedGraphImpl;
import com.impl.NodeDataImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    //File
    private static final String SAVE = "Save Graph";
    private static final String LOAD = "Load Graph";
    private static final String RESET_GRAPH = "Reset Graph";
    //Graph Actions
    private static final String ADD_NODE = "Add Node";
    private static final String ADD_EDGE = "Add Edge";
    private static final String REMOVE_NODE = "Remove Node";
    private static final String REMOVE_EDGE = "Remove Edge";
    //Algos
    private static final String IS_CONNECTED = "Is Connected";
    private static final String SHORT_PATH = "Short Path";
    private static final String TSP_ACTION = "TSP";
    private static final String CENTER = "CENTER";


    private final DirectedWeightedGraphAlgorithms mGraphAlgo = new DirectedWeightedGraphAlgorithmsImpl();
    private DirectedWeightedGraph mGraph;

    private double minYOffset = Double.MAX_VALUE;
    private double minXOffset = Double.MAX_VALUE;
    private double scaleXWIDTH = 0;
    private double scaleHeight = 0;
    private final int HEIGHT_OFFSET = 80;

    public GUI(DirectedWeightedGraph graph) {
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu();
        drawGraph(graph);
        drawGraph(null);
    }

    private void clearScreen() {
        getGraphics().clearRect(0, 0, 800, 600);
    }

    private void drawGraph(DirectedWeightedGraph graph) {
        clearScreen();
        if (graph != null) {
            mGraph = graph;
            mGraphAlgo.init(mGraph);
            setScaleMinMax();
        }
        paintGraph();
    }


    public void setScaleMinMax() {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        Iterator<NodeData> NodeI = mGraph.nodeIter();
        while (NodeI.hasNext()) {
            NodeData currNode = NodeI.next();
            GeoLocation geoLocation = currNode.getLocation();
            if (currNode.getLocation().x() < minXOffset) {
                minXOffset = geoLocation.x();
            }
            if (currNode.getLocation().x() > maxX) {
                maxX = geoLocation.x();
            }
            if (currNode.getLocation().y() < minYOffset) {
                minYOffset = geoLocation.y();
            }
            if (currNode.getLocation().y() > maxY) {
                maxY = geoLocation.y();
            }
        }
        scaleXWIDTH = Math.abs(maxX - minXOffset);
        scaleHeight = Math.abs(maxY - minYOffset);

    }

    private void paintNodes(Graphics g) {
        Iterator<NodeData> NodeI = mGraph.nodeIter();
        g.setColor(Color.red);
        while (NodeI.hasNext()) {
            NodeData currNode = NodeI.next();
            double x = (currNode.getLocation().x() - minXOffset) * getXScale() + HEIGHT_OFFSET;
            double y = (currNode.getLocation().y() - minYOffset) * getYScale() + HEIGHT_OFFSET;
            g.fillOval((int) x - 5, (int) y - 5, 12, 12);
            g.drawString(String.format("%s", currNode.getKey()), (int) x - 20, (int) y - 7);
        }
    }

    private void paintEdges(Graphics g) {
        Iterator<EdgeData> EdgeI = mGraph.edgeIter();
        while (EdgeI.hasNext()) {
            EdgeData currEdge = EdgeI.next();
            g.setColor(Color.BLACK);
            drawEdge(g, currEdge);
        }
    }

    private void drawEdge(Graphics g, EdgeData edgeData) {
        double srcX = (mGraph.getNode(edgeData.getSrc()).getLocation().x() - minXOffset) * getXScale() + HEIGHT_OFFSET;
        double srcY = (mGraph.getNode(edgeData.getSrc()).getLocation().y() - minYOffset) * getYScale() + HEIGHT_OFFSET;
        double destX = (mGraph.getNode(edgeData.getDest()).getLocation().x() - minXOffset) * getXScale() + HEIGHT_OFFSET;
        double destY = (mGraph.getNode(edgeData.getDest()).getLocation().y() - minYOffset) * getYScale() + HEIGHT_OFFSET;
        drawArrowLine(g, (int) srcX, (int) srcY, (int) destX, (int) destY, 15, 3);
    }

    private double getXScale() {
        return getWidth() / scaleXWIDTH * 0.8;
    }

    private double getYScale() {
        return getHeight() / scaleHeight * 0.8;
    }

    private void paintGraph() {
        paintNodes(getGraphics());
        paintEdges(getGraphics());
    }

    private void addFileMenu(MenuBar menuBar) {
        Menu file = new Menu("File");
        menuBar.add(file);
        setMenuBar(menuBar);
        MenuItem save = new MenuItem(SAVE);
        MenuItem resetGraph = new MenuItem(RESET_GRAPH);
        MenuItem init = new MenuItem(LOAD);
        init.addActionListener(this);
        save.addActionListener(this);
        resetGraph.addActionListener(this);
        file.add(save);
        file.add(resetGraph);
        file.add(init);
    }

    private void addGraphActions(MenuBar menuBar) {
        Menu algo = new Menu("Graph Actions");
        menuBar.add(algo);
        MenuItem addVertex = new MenuItem(ADD_NODE);
        MenuItem addEdge = new MenuItem(ADD_EDGE);
        MenuItem removeVertex = new MenuItem(REMOVE_NODE);
        MenuItem removeEdge = new MenuItem(REMOVE_EDGE);
        addEdge.addActionListener(this);
        removeVertex.addActionListener(this);
        removeEdge.addActionListener(this);
        addVertex.addActionListener(this);
        algo.add(addVertex);
        algo.add(removeVertex);
        algo.add(addEdge);
        algo.add(removeEdge);

    }

    private void addMenu() {
        MenuBar menuBar = new MenuBar();
        addFileMenu(menuBar);
        addGraphActions(menuBar);
        addAlgoActions(menuBar);
        setVisible(true);
    }

    private void addAlgoActions(MenuBar menuBar) {
        Menu algoFunc = new Menu("Algo");
        menuBar.add(algoFunc);
        MenuItem isConnect = new MenuItem(IS_CONNECTED);
        MenuItem sortPath = new MenuItem(SHORT_PATH);
        MenuItem TSP = new MenuItem(TSP_ACTION);
        MenuItem center = new MenuItem(CENTER);
        TSP.addActionListener(this);
        center.addActionListener(this);
        isConnect.addActionListener(this);
        sortPath.addActionListener(this);
        algoFunc.add(sortPath);
        algoFunc.add(isConnect);
        algoFunc.add(TSP);
        algoFunc.add(center);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SAVE -> saveGraphGui();
            case LOAD -> loadGraph();
            case RESET_GRAPH -> drawGraph(null);
            case ADD_NODE -> addNode();
            case ADD_EDGE -> addEdge();
            case REMOVE_NODE -> removeNode();
            case REMOVE_EDGE -> removeEdge();
            case IS_CONNECTED -> isConnected();
            case SHORT_PATH -> shortPath();
            case TSP_ACTION -> tspAction();
            case CENTER -> center();
        }
    }

    private void center() {
        final JFrame window = new JFrame("Center");
        window.setLocationRelativeTo(null);
        JLabel center = new JLabel(String.format("%s", mGraphAlgo.center().getKey()));
        center.setFont(new Font("David", center.getFont().getStyle(), 20));
        JButton close = new JButton("Close");
        window.setLayout(createGridLayout(2, 0));
        packWindow(window, center, close);
        window.setSize(200, 100);
        window.setVisible(true);
        close.addActionListener(e -> window.setVisible(false));
    }

    private void tspAction() {
        final JFrame window = new JFrame("TSP");
        final JTextField targetsText = new JTextField();
        JLabel targetsLabel = new JLabel("List of Cities splitted by ',' 1,2,3...\n: Cities:");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(2, 1));
        packWindow(window, targetsLabel, targetsText, cancel, enter);
        window.setSize(800, 150);
        window.setVisible(true);
        enter.addActionListener(e -> {
            try {
                List<NodeData> cities = getAllNodesDataFromInput(targetsText.getText());
                List<NodeData> result = mGraphAlgo.tsp(cities);
                if (result != null) {
                    for (int i = 0; i < result.size() - 1; i++) {
                        NodeData from = result.get(i);
                        if (result.get(i + 1) != null) {
                            NodeData to = result.get(i + 1);
                            EdgeData edgeData = mGraph.getEdge(from.getKey(), to.getKey());
                            if (edgeData != null) {
                                Graphics2D g2 = (Graphics2D) getGraphics();
                                g2.setStroke(new BasicStroke(4));
                                g2.setColor(Color.BLUE);
                                drawEdge(g2, edgeData);
                            }
                        }
                    }
                }
                window.setVisible(false);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, e2 + "");
            }
        });
        cancel.addActionListener(e -> window.setVisible(false));
    }


    private List<NodeData> getAllNodesDataFromInput(String text) {
        List<NodeData> result = new ArrayList<>();
        String[] split = text.split(",");
        for (int i = 0; i < split.length; i++) {
            NodeData nodeData = mGraph.getNode(Integer.parseInt(split[i]));
            if (nodeData == null) {
                return null;
            }
            result.add(nodeData);
        }
        return result;
    }


    private void shortPath() {
        final JFrame window = new JFrame("Short Path");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        JLabel srcLabel = new JLabel("src: ");
        JLabel destLabel = new JLabel("dest: ");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(3, 2));
        packWindow(window, srcLabel, srcText, destLabel, destText, cancel, enter);
        window.setSize(300, 200);
        window.setVisible(true);
        enter.addActionListener(e -> {
            try {
                int src = Integer.parseInt(srcText.getText());
                int dest = Integer.parseInt(destText.getText());
                List<NodeData> result = mGraphAlgo.shortestPath(src, dest);

                if (result != null) {
                    for (int i = 0; i < result.size() - 1; i++) {
                        NodeData from = result.get(i);
                        if (result.get(i + 1) != null) {
                            NodeData to = result.get(i + 1);
                            EdgeData edgeData = mGraph.getEdge(from.getKey(), to.getKey());
                            if (edgeData != null) {
                                Graphics2D g2 = (Graphics2D) getGraphics();
                                g2.setStroke(new BasicStroke(4));
                                g2.setColor(Color.GREEN);
                                drawEdge(g2, edgeData);

                            }
                        }
                    }
                }
                window.setVisible(false);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        });
        cancel.addActionListener(e -> window.setVisible(false));
    }

    private void isConnected() {
        final JFrame window = new JFrame("Is Connected");
        window.setLocationRelativeTo(null);
        JLabel isConnect = new JLabel(String.format("%s", mGraphAlgo.isConnected()));
        isConnect.setFont(new Font("David", isConnect.getFont().getStyle(), 20));
        JButton close = new JButton("Close");
        window.setLayout(createGridLayout(2, 0));
        packWindow(window, isConnect, close);
        window.setSize(200, 100);
        window.setVisible(true);
        close.addActionListener(e -> window.setVisible(false));
    }

    private void removeEdge() {
        final JFrame window = new JFrame("Remove Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        JLabel src = new JLabel("src: ");
        JLabel dest = new JLabel("dest: ");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(3, 2));
        packWindow(window, src, srcText, dest, destText, enter, cancel);
        window.setSize(300, 200);
        window.setVisible(true);
        enter.addActionListener(e -> {
            try {
                window.setVisible(false);
                mGraph.removeEdge(Integer.parseInt(srcText.getText()), Integer.parseInt(destText.getText()));
                drawGraph(null);

            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        });
        cancel.addActionListener(e -> window.setVisible(false));
    }

    private GridLayout createGridLayout(int rows, int colums) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(rows);
        gridLayout.setColumns(colums);
        return gridLayout;
    }

    private void packWindow(JFrame window, JComponent... components) {

        for (JComponent component : components) window.add(component);
    }

    private void removeNode() {
        final JFrame window = new JFrame("Add Vertex");
        final JTextField vertexKey = new JTextField();
        JLabel verKey = new JLabel("Node number");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(2, 2));
        packWindow(window, verKey, vertexKey, enter, cancel);
        window.setSize(300, 200);
        window.setVisible(true);
        enter.addActionListener(e -> {
            try {
                mGraph.removeNode(Integer.parseInt(vertexKey.getText()));
                window.setVisible(false);
                drawGraph(null);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        });
        cancel.addActionListener(e -> window.setVisible(false));
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    private void addEdge() {
        final JFrame window = new JFrame("Add Edge");
        final JTextField srcText = new JTextField();
        final JTextField destText = new JTextField();
        final JTextField weightText = new JTextField();
        JLabel src = new JLabel("src:");
        JLabel dest = new JLabel("dest:");
        JLabel weight = new JLabel("weight");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(4, 2));
        packWindow(window, src, srcText, dest, destText, weight, weightText, enter, cancel);
        window.setSize(300, 200);
        window.setVisible(true);
        enter.addActionListener(e -> {
            try {
                int src1 = Integer.parseInt(srcText.getText());
                int dest1 = Integer.parseInt(destText.getText());
                double w = Double.parseDouble(weightText.getText());
                mGraph.connect(src1, dest1, w);
                window.setVisible(false);
                drawGraph(null);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        });

        cancel.addActionListener(e -> window.setVisible(false));
    }

    private void addNode() {
        final JFrame window = new JFrame("Add Node");
        final JTextField nodeX = new JTextField();
        final JTextField nodeY = new JTextField();
        final JTextField numerN = new JTextField();
        JLabel number = new JLabel("Number: ");
        JLabel verX = new JLabel("X: ");
        JLabel verY = new JLabel("Y: ");
        JButton enter = new JButton("Enter");
        JButton cancel = new JButton("Cancel");
        window.setLayout(createGridLayout(4, 2));
        packWindow(window, number, numerN, verX, nodeX, verY, nodeY, enter, cancel);
        window.setSize(300, 200);
        window.setVisible(true);

        enter.addActionListener(e -> {
            try {
                double x = Double.parseDouble(nodeX.getText());
                double y = Double.parseDouble(nodeY.getText());
                int num = Integer.parseInt(numerN.getText());
                mGraph.addNode(new NodeDataImpl(num, String.format("%f,%f,%f", x, y, 0.0)));
                drawGraph(null);
                window.setVisible(false);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        });
        cancel.addActionListener(e -> window.setVisible(false));
    }

    private void loadGraph() {
        FileDialog fd = new FileDialog(this, "Load Graph", FileDialog.LOAD);
        fd.setFilenameFilter((dir, name) -> name.endsWith(".json"));
        fd.setVisible(true);
        String folder = fd.getDirectory();
        String fileName = fd.getFile();
        if (fileName != null) {
            try {
                DirectedWeightedGraph directedWeightedGraph = new DirectedWeightedGraphImpl(folder + fileName);
                drawGraph(directedWeightedGraph);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, String.format("%s", e2));
            }
        }
    }

    private void saveGraphGui() {
        FileDialog fd = new FileDialog(this, "Save graph into json", FileDialog.SAVE);
        fd.setFile("graph.json");
        fd.setFilenameFilter((dir, name) -> name.endsWith(".json"));
        fd.setVisible(true);
        if (fd.getFile() != null) {
            mGraphAlgo.save(fd.getDirectory() + fd.getFile());
        }

    }

}