package poc.controller;

//import poc.lineparser.StageLines;

import poc.lineparser.LineFIS;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

//import poc.lineparser.DimVal;
//import poc.lineparser.Metric;

public class ProcessingQueue {
    private ConcurrentLinkedQueue<LineFIS> queue = new ConcurrentLinkedQueue<LineFIS>();

    public void addDimVal(LineFIS dv) {
        queue.add(dv);
    }

    public Iterator<LineFIS> getQItr() {
        return queue.iterator();
    }

    public LineFIS deQueue() {
        return queue.poll();
    }

    public LineFIS testFirstElementInQueue() {
        return queue.peek();
    }

}
