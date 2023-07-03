//package com.impl;
//
//import java.util.Objects;
//
//public class SrcDest {
//    public int src;
//    public int dest;
//    private int hashCode;
//
//    public SrcDest(int src, int dest) {
//        this.src = src;
//        this.dest = dest;
//        this.hashCode = Objects.hash(src, dest);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (o == null || getClass() != o.getClass())
//            return false;
//        SrcDest that = (SrcDest) o;
//        return src == that.src && dest == that.dest;
//    }
//
//    @Override
//    public int hashCode() {
//        return this.hashCode;
//    }
//}
