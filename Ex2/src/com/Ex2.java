package com;

import com.api.DirectedWeightedGraph;
import com.api.DirectedWeightedGraphAlgorithms;
import com.impl.DirectedWeightedGraphAlgorithmsImpl;
import com.impl.DirectedWeightedGraphImpl;
import gui.GUI;

import java.io.FileNotFoundException;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) throws FileNotFoundException {
        return new DirectedWeightedGraphImpl(json_file);
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) throws FileNotFoundException {
        DirectedWeightedGraphAlgorithms ans = new DirectedWeightedGraphAlgorithmsImpl();
        ans.init(getGrapg(json_file));
        return ans;
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) throws FileNotFoundException {
        new GUI(getGrapg(json_file));

    }

    public static void main(String[] args) throws FileNotFoundException {
       runGUI(args[0]);
    }
}
