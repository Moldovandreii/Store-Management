import java.util.ArrayList;
import java.util.List;


public class Queue extends Thread {
	private String queueName = "";
	private List<Client> clientsInQueue;
	private int totalNbOfClients = 0;
	private List<String> queueMessage = new ArrayList<String>();
	private volatile boolean running = true;
	
	public Queue(String name) {
		this.queueName = name;
		this.clientsInQueue = new ArrayList<Client>();
		queueMessage.add("");
	}
	
	
	
	public synchronized void addClient(Client c) throws InterruptedException{
		this.clientsInQueue.add(c); 
		this.totalNbOfClients += 1;
		this.setTotalTimeToC(c);  
		//queueMessage = queueMessage.concat("Client " + c.getcID() + " has arrived at time " + c.getArrivalTime() + " at Queue" + queueName + " | ");
		String txt = "Client " + c.getcID() + " has arrived at time " + c.getArrivalTime() + " at Queue" + queueName + " | " + " st: " + c.getServiceTime() + " wt: " + c.getWaitingTime() + " tt: " + c.getTotalTime();
		if(queueMessage.size() != 0)
		{
			queueMessage.remove(0);
		}
		queueMessage.add(txt);
		//System.out.println(txt);
		System.out.println(queueMessage);
		notifyAll();
	}
	
	public synchronized void deleteClient() throws InterruptedException {
		while(this.clientsInQueue.size() == 0)
		{
			wait();
		}
		//System.out.println(clientsInQueue.get(0).getTotalTime());
		//this.totalWaitingTime += this.clientsInQueue.get(0).getTotalTime();  
		//this.queueMessage = this.queueMessage.concat("Client " + this.clientsInQueue.get(0).getcID() + " has left at " + (this.clientsInQueue.get(0).getTotalTime() + this.clientsInQueue.get(0).getArrivalTime()) + " from Queue " + queueName + " | ");
		float avgTime = getAwerageWaitingTime();
		String txt = "Client " + this.clientsInQueue.get(0).getcID() + " has left at " + (this.clientsInQueue.get(0).getTotalTime() + this.clientsInQueue.get(0).getArrivalTime()) + " from Queue " + queueName + " | " + "average wainting time = " + avgTime;
		if(queueMessage.size() != 0)
		{
			queueMessage.remove(0);
		}
		queueMessage.add(txt);
		this.clientsInQueue.remove(0);
		totalNbOfClients -= 1;
		//System.out.println(txt);
		System.out.println(queueMessage);
		notifyAll();
	}
	
	public synchronized void setTotalTimeToC(Client c) {
		if(this.getClientList().get(0) == c)  
		{
			c.setWaitingTime(0);
			c.setTotalTime();
			return;
		}
		int totalTimeOfPreviousClient = this.getClientList().get(this.getNbOfClients() - 2).getTotalTime();
		int arrivalTimeOfPreviousClient = this.getClientList().get(this.getNbOfClients() - 2).getArrivalTime();
		if((c.getArrivalTime() - totalTimeOfPreviousClient) >= arrivalTimeOfPreviousClient)
		{
			c.setWaitingTime(0);
			c.setTotalTime();
			return;
		}
		if((c.getArrivalTime() - totalTimeOfPreviousClient) < arrivalTimeOfPreviousClient)
		{
			int i = totalTimeOfPreviousClient - (c.getArrivalTime() - arrivalTimeOfPreviousClient);
			c.setWaitingTime(i);
			c.setTotalTime();
			return;
		}
		else
		{
			c.setWaitingTime(0);
			c.setTotalTime();
		}
	}
	
	public synchronized int arrivalTimeOfLastClient() {
		if(this.totalNbOfClients == 0)
		{
			return 0;
		}
		else
		{
			return this.clientsInQueue.get(totalNbOfClients - 1).getArrivalTime();  
		}
	}
	
	public float getAwerageWaitingTime() {
		int totalWaitingTime = 0;
		if(this.totalNbOfClients == 0)
		{
			return 0;
		}
		for(int i=0; i<totalNbOfClients; i++) {
			totalWaitingTime += clientsInQueue.get(i).getWaitingTime();
		}
		return totalWaitingTime/totalNbOfClients;    
	}
	
	public synchronized void waitIfNoClient() {
		try {
			while(this.clientsInQueue.size() == 0) {
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMessage() {
		return this.queueMessage.get(0);
	}
	
	public void setMessage() {
		if(queueMessage.size() != 0)
		{
			this.queueMessage.remove(0);  
		}
	}
	
	public List<Client> getClientList(){
		return this.clientsInQueue;
	}
	
	public List<String> getMessageList(){
		return this.queueMessage;
	}
	
	public int getNbOfClients() {
		return this.totalNbOfClients;
	}
	
	public String getQueueName() {
		return this.queueName;
	}
	
	public void run() {
		
		while(running) {
			try {
				this.waitIfNoClient();
				Thread.sleep(clientsInQueue.get(0).getTotalTime() * 1000);
				this.deleteClient();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Client c1 = new Client(1,1,5);  
		Client c2 = new Client(2,4,3);
		Client c3 = new Client(3,11,2);
		Queue q = new Queue("l1");
		 
		q.start();
		Thread.sleep(1000);     
		q.addClient(c1);
		Thread.sleep(3000);
		q.addClient(c2);
		Thread.sleep(7000);
		q.addClient(c3);
		Thread.sleep(2000);
		q.running = false;
		
		
		/*q.addClient(c1);
		q.addClient(c2);
		q.setTotalTimeToC(c1);
		q.setTotalTimeToC(c2);
		System.out.println(c1.getTotalTime());
		System.out.println(c2.getWaitingTime());
		System.out.println(c2.getTotalTime());
		 */
	}
	
	
}








