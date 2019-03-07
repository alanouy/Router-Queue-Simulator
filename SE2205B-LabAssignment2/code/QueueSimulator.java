import java.lang.*;

public class QueueSimulator{
  public enum Event { ARRIVAL, DEPARTURE };
  private double currTime;
  private double arrivalRate;
  private double serviceTime;
  private double timeForNextArrival;
  private double timeForNextDeparture;
  private double totalSimTime;
  LinkedListQueue<Data> buffer = new LinkedListQueue<Data>();
  LinkedListQueue<Data> eventQueue = new LinkedListQueue<Data>();
  private Event e;
  
  public double getRandTime(double arrivalRate){
    double num, time1, max=1, min=0, randNUM;
    randNUM= Math.random();
    time1= (-1/arrivalRate) * (Math.log(1-randNUM));
    //System.out.println(time1);
    return time1;
  }
  
  public QueueSimulator(double aR, double servT, double simT){
	  arrivalRate = aR;
	  serviceTime = servT;
	  totalSimTime = simT;
  }
  
  public double calcAverageWaitingTime(){
	    double sum = 0;
	    int numPacket = eventQueue.size();
	    Data current;

	    // while loop dequeues each node and calculates the sum of each wait time
	    while(!eventQueue.isEmpty()){
	    	current = eventQueue.dequeue();
	    	sum += current.getDepartureTime() - current.getArrivalTime();
	    }
	    // returns average wait time
	    return sum/numPacket;
  }
  
  public double runSimulation(){
	  currTime = 0;
	  timeForNextArrival = getRandTime(arrivalRate);
	  /*
	   * while loop will stop running when currTime is more than totalSimTime
	   * Each loop will simulate a packet in the router
	   * A packet will either arrive in the buffer queue, or depart from the
	   * buffer queue. If-else case will determine if it is arrive or depart.
	   * Switch case will occur after every loop, adding a enqueueing or 
	   * dequeueing the buffer queue.
	   */
	  while(currTime < totalSimTime) {
		  
		  //ARRIVAL EVENT
		  if(buffer.isEmpty()) {
			  e = Event.ARRIVAL;
			  timeForNextDeparture = timeForNextArrival + serviceTime;
			  
		  }
		  //ARRIVAL EVENT
		  else if(timeForNextArrival < timeForNextDeparture) {
			  e = Event.ARRIVAL;
		  }
		  //DEPARTURE EVENT
		  else { 
			  e = Event.DEPARTURE;
		  }
		  
		  switch(e) {
		  	case ARRIVAL:
		  		currTime = timeForNextArrival;
		  		Data a = new Data();
		  		a.setArrivalTime(currTime);
		  		buffer.enqueue(a);
		  		timeForNextArrival += getRandTime(arrivalRate);
		  		break;
		  	case DEPARTURE:
		  		currTime = timeForNextDeparture;
		  		Data d;
		  		d = buffer.dequeue();
		  		d.setDepartureTime(currTime);
		  		eventQueue.enqueue(d);
		  		timeForNextDeparture += serviceTime;
		  		break;
		  }
	  }
	  return calcAverageWaitingTime();
  }
}






