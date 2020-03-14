import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class Store extends Thread{
	
	private volatile int nbOfQueues = 0;
	//BlockingQueue<Queue> queuesInStore = new ArrayBlockingQueue<Queue>(nbOfQueues);
	private volatile List<Queue> queuesInStore = new ArrayList<Queue>();
	private volatile  int simulationTime = 0;
	private volatile boolean running = true;
	private int minTimeBetweenArrivals= 1;
	private int maxTimeBetweenArrivals = 1;
	private int minTimeAtService = 1;
	private int maxTimeAtService = 1;
	private volatile int time = 0;
	
	public Store(int nbQueues) {
		this.nbOfQueues = nbQueues;
		for(int i=0; i<nbQueues; i++) {
			Queue q = new Queue(Integer.toString(i+1));
			queuesInStore.add(q);
		}
	}
	
	public void setMinMaxTimers(int minTimeArr, int maxTimeArr, int minTimeServ, int maxTimeServ) {
		this.minTimeBetweenArrivals = minTimeArr;
		this.maxTimeBetweenArrivals = maxTimeArr;
		this.minTimeAtService = minTimeServ;
		this.maxTimeAtService = maxTimeServ;
	}
	
	public synchronized Queue queueLeastClients() {
		Queue q = new Queue("aux");
		int minNbOfClients = this.queuesInStore.get(0).getNbOfClients(); 
		q = this.queuesInStore.get(0);
		for(int i=1; i<this.nbOfQueues; i++) {
			if(this.queuesInStore.get(i).getNbOfClients() < minNbOfClients)
			{
				minNbOfClients = this.queuesInStore.get(i).getNbOfClients();
				q = this.queuesInStore.get(i);
			}
		}
		return q;   
	}
	
	
	public void run() {  
		Random random = new Random();
		int nbClients = 0;
		int arrTimeOfPrevClient = 0;
		for(int i=0; i<nbOfQueues; i++) {
			queuesInStore.get(i).start(); 
		}
		//this.setMinMaxTimers(2, 5, 10, 15);
		int id = 1;
		while(running) {
			int timeBetweenArrivals = random.nextInt((maxTimeBetweenArrivals - minTimeBetweenArrivals) + 1) + minTimeBetweenArrivals;  
			int servingTime = random.nextInt((maxTimeAtService - minTimeAtService) + 1) + minTimeAtService;
			//int arrivalOfLast = this.queueLeastClients().arrivalTimeOfLastClient();  
			Client c = new Client(id, simulationTime + timeBetweenArrivals, servingTime);
			simulationTime += timeBetweenArrivals;
			try {
				if(nbClients == 0)
				{
					Thread.sleep(c.getArrivalTime() * 1000);  
					arrTimeOfPrevClient = c.getArrivalTime();
					nbClients++;
				}
				else
				{
					Thread.sleep((c.getArrivalTime() - arrTimeOfPrevClient) * 1000);
					arrTimeOfPrevClient = c.getArrivalTime();
					nbClients++;
				}
				this.queueLeastClients().addClient(c);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			id++; 
		}
	}
	
	public int getNbOfQueues() {
		return this.nbOfQueues;
	}
	
	public void setNbOfQueues(int nb) {
		this.nbOfQueues = nb;
	}
	
	public List<Queue> getQueuesInStore(){
		return this.queuesInStore;
	}
	
	public int getTime() {
		return this.time;
	}
	  
	public static void main(String[] args) throws InterruptedException {
		Store s = new Store(3);
		s.start();
		//Thread.sleep(1000);
	}
	
}
