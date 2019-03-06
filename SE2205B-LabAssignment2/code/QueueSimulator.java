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
	  timeForNextArrival = getRandTime(arrivalRate);
	  timeForNextDeparture = timeForNextArrival + serviceTime;
	  currTime = 0;

  }
  
  public double calcAverageWaitingTime(){
	    double sum = 0;
	    double arrival = 0;
	    double depart = 0;
	    int numPacket = 0;

	    while(!eventQueue.isEmpty()){
	      arrival = eventQueue.dequeue().getArrivalTime();
	      depart = eventQueue.dequeue().getDepartureTime();
	      sum += depart - arrival;
	      numPacket++;
	    }

	    return sum/numPacket;
  }
  
  public double runSimulation(){
	  while(currTime >= totalSimTime) {
		  if(timeForNextArrival < timeForNextDeparture) { //ARRIVAL EVENT
			  Data d = new Data();
			  d.setArrivalTime(timeForNextArrival);
			  currTime += timeForNextArrival;
			  buffer.enqueue(d);
			  timeForNextArrival = getRandTime(arrivalRate);
		  }
		  else { //DEPARTURE EVENT
			  timeForNextDeparture = timeForNextArrival + serviceTime;
			  Data e = buffer.dequeue();
			  e.setDepartureTime(timeForNextDeparture);
			  eventQueue.enqueue(e);
			  
		  }
	  }
	  
  }
}






