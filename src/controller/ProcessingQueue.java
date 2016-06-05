package controller;

import java.util.concurrent.ConcurrentLinkedQueue;
//import lineparser.DimVal;
//import lineparser.Metric;
import lineparser.LineFIS;
//import lineparser.StageLines;
import java.util.Iterator;

public class ProcessingQueue {
	private ConcurrentLinkedQueue<LineFIS> queue = new ConcurrentLinkedQueue<LineFIS>();
	public void addDimVal (LineFIS dv){
		queue.add(dv);
	}
	public Iterator<LineFIS> getQItr() {
		return queue.iterator();
	}
	
	public LineFIS deQueue() {
		return queue.poll();
	}
	
	public LineFIS testFirstElementInQueue(){
		return queue.peek();
	}

}
