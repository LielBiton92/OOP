//package com.impl;
//
//import com.api.DirectedWeightedGraph;
//import com.api.NodeData;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//
//public class WarshalAlgo {
//    private final DirectedWeightedGraph mGraph;
//    private double[][] mPathDistanceWeightFromSrcToDest;
//    private int[][] mNext;
//    private Integer mLastMc = null;
//
//    public WarshalAlgo(DirectedWeightedGraph graph) {
//        mGraph = graph;
//        performFloydWarshalAlgoIfNeeded();
//    }
//
//    public void performFloydWarshalAlgoIfNeeded() {
//        if (mLastMc == null || mLastMc != mGraph.getMC()) {
//            doAction();
//        }
//    }
//
//    private void setDefaultValues() {
//        mLastMc = mGraph.getMC();
//        int size = mGraph.nodeSize() + 1;
//        mPathDistanceWeightFromSrcToDest = new double[size][size];
//        mNext = new int[size][size];
//        Iterator<NodeData> nodeI = mGraph.nodeIter();
//        while (nodeI.hasNext()) {
//            int src = nodeI.next().getKey();
//            Iterator<NodeData> nodeJ = mGraph.nodeIter();
//            while (nodeJ.hasNext()) {
//                int dest = nodeJ.next().getKey();
//                SrcDest srcDest = new SrcDest(src, dest);
//                if (src == dest) {
//                    mPathDistanceWeightFromSrcToDest[src][dest] = 0.0;
//                    mNext[src][dest] = srcDest.dest;
//                } else if (mGraph.getEdge(src, dest) == null) {
//                    mNext[src][dest] = -1;
//                    mPathDistanceWeightFromSrcToDest[src][dest] = Double.MAX_VALUE;
//                } else {
//                    mNext[src][dest] = srcDest.dest;
//                    mPathDistanceWeightFromSrcToDest[src][dest] = mGraph.getEdge(src, dest).getWeight();
//                }
//            }
//        }
//    }
//
//    private void doAction() {
//        setDefaultValues();
//        Iterator<NodeData> iter1 = mGraph.nodeIter();
//        while (iter1.hasNext()) {
//            int k = iter1.next().getKey();
//            Iterator<NodeData> iter2 = mGraph.nodeIter();
//            while (iter2.hasNext()) {
//                int srcI = iter2.next().getKey();
//                Iterator<NodeData> iter3 = mGraph.nodeIter();
//                while (iter3.hasNext()) {
//                    int destJ = iter3.next().getKey();
//                    SrcDest srcDestIJ = new SrcDest(srcI, destJ);
//                    SrcDest srcIK = new SrcDest(srcI, k);
//                    SrcDest srcKJ = new SrcDest(k, destJ);
//                    if (mPathDistanceWeightFromSrcToDest[srcIK.src][srcIK.dest] != Double.MAX_VALUE && mPathDistanceWeightFromSrcToDest[srcKJ.src][srcKJ.dest] != Double.MAX_VALUE) {
//                        double sum = mPathDistanceWeightFromSrcToDest[srcIK.src][srcIK.dest] + mPathDistanceWeightFromSrcToDest[srcKJ.src][srcKJ.dest];
//                        if (mPathDistanceWeightFromSrcToDest[srcDestIJ.src][srcDestIJ.dest] > sum) {
//                            mPathDistanceWeightFromSrcToDest[srcDestIJ.src][srcDestIJ.dest] = sum;
//                            mNext[srcDestIJ.src][srcDestIJ.dest] = mNext[srcIK.src][srcIK.dest];
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public double getWeight(SrcDest srcDest) {
//        return mPathDistanceWeightFromSrcToDest[srcDest.src][srcDest.dest];
//    }
//
//    public List<NodeData> getShortethPathList(SrcDest srcDest) {
//        if (mNext[srcDest.src][srcDest.dest] == -1 || srcDest == null) {
//            return null;
//        }
//        int u = srcDest.src;
//        int v = srcDest.dest;
//        List<NodeData> path = new ArrayList<>();
//        path.add(mGraph.getNode(u));
//        while (u != v) {
//            u = mNext[u][v];
//            path.add(mGraph.getNode(u));
//        }
//        return path;
//    }
//}