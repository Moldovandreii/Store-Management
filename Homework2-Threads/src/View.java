import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class View extends JFrame implements Runnable{
	private volatile boolean running = true;
	private Store store = new Store(5);  
	private JFrame frame1 = new JFrame();
	private JLabel l1 = new JLabel("-");
	private JLabel l2 = new JLabel("-");
	private JTextField minArr = new JTextField("");
	private JTextField maxArr = new JTextField("");
	private JTextField minServ = new JTextField("");
	private JTextField maxServ = new JTextField("");
	private JLabel minMaxArrL = new JLabel("Enter minimum and maximum time between customers:");
	private JLabel minMaxServL = new JLabel("Enter minimum and maximum time at the server:");
	private JLabel nbOfQueues = new JLabel("Enter the number of working queues:");
	private JTextField nbQueues = new JTextField("");
	private JButton start = new JButton("start simulation");
	private JLabel simInterval = new JLabel("Simulation Interval:");
	private JTextField simulationInterval = new JTextField("");
	
	private JFrame frame2 = new JFrame();
	private TextArea simInfo = new TextArea();
	private JScrollPane simulationInfo = new JScrollPane(simInfo);
	private JLabel time = new JLabel("Time:");
	private JLabel timer = new JLabel("0");
	private JLabel[] nbCl = new JLabel[10];
	private JLabel[] avgTime = new JLabel[10];
	private JLabel peakHour = new JLabel("Peak Hour:");
	private JLabel peakHourTxt = new JLabel("0");
	
	
	
	public View(Store store) {
		this.store = store;
		frame1.setSize(1000,800);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout());
		JPanel panel5 = new JPanel();
		panel5.setLayout(new FlowLayout());
		JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout());
		minMaxArrL.setFont(new Font("Serif", Font.PLAIN, 20));
		minMaxServL.setFont(new Font("Serif", Font.PLAIN, 20));
		nbOfQueues.setFont(new Font("Serif", Font.PLAIN, 20));
		simInterval.setFont(new Font("Serif", Font.PLAIN, 20));
		minArr.setPreferredSize(new Dimension(45,30));
		maxArr.setPreferredSize(new Dimension(45,30));
		minServ.setPreferredSize(new Dimension(45,30));
		maxServ.setPreferredSize(new Dimension(45,30));
		nbQueues.setPreferredSize(new Dimension(45,30));
		simulationInterval.setPreferredSize(new Dimension(45,30));
		start.setPreferredSize(new Dimension(170,45));

		panel2.add(minMaxArrL);
		panel2.add(minArr);
		panel2.add(l1);
		panel2.add(maxArr);
		panel3.add(minMaxServL);
		panel3.add(minServ);
		panel3.add(l2);
		panel3.add(maxServ);
		panel4.add(nbOfQueues);  
		panel4.add(nbQueues);
		panel5.add(start);
		panel6.add(simInterval);
		panel6.add(simulationInterval);
		panel1.add(panel2);
		panel1.add(panel3);
		panel1.add(panel4);
		panel1.add(panel6);
		panel1.add(panel5);
		frame1.setContentPane(panel1);
		frame1.setVisible(true);
	}
	
	public synchronized void setView() {
		int nbQueues = Integer.valueOf(this.nbQueues.getText());
		frame1.setVisible(false);
		
		
		frame2.setSize(1500,800);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JPanel panel21 = new JPanel();
		panel21.setLayout(new FlowLayout());
		JPanel panel22 = new JPanel();
		panel22.setLayout(new FlowLayout());
		JPanel[] panel3 = new JPanel[nbQueues];
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS)); 
		simulationInfo.setPreferredSize(new Dimension(900,300));
		JLabel simtxt = new JLabel("               Simulation Info");
		simtxt.setFont(new Font("Serif", Font.PLAIN, 30));
		timer.setFont(new Font("Serif", Font.PLAIN, 30));
		time.setFont(new Font("Serif", Font.PLAIN, 30));
		panel2.add(simulationInfo);
		panel21.add(time);
		panel21.add(timer);
		panel22.add(peakHour);
		panel22.add(peakHourTxt);
		panel2.add(panel21);
		panel2.add(panel22);
		
		for(int i=0; i<nbQueues; i++) {
			JPanel x1 = new JPanel();
			x1.setLayout(new FlowLayout());
			JPanel x2 = new JPanel();
			x2.setLayout(new FlowLayout());
			JPanel space = new JPanel();
			space.setPreferredSize(new Dimension(50,300));
			JLabel t1 = new JLabel("Average waiting time: ");
			t1.setFont(new Font("Serif", Font.PLAIN, 20));
			JLabel t2 = new JLabel("Number of clients in Queue: ");
			t2.setFont(new Font("Serif", Font.PLAIN, 20));
			peakHour.setFont(new Font("Serif", Font.PLAIN, 20));
			peakHourTxt.setFont(new Font("Serif", Font.PLAIN, 20));
			nbCl[i] = new JLabel(String.valueOf(store.getQueuesInStore().get(i).getNbOfClients()));
			nbCl[i].setFont(new Font("Serif", Font.PLAIN, 20));
			panel3[i] = new JPanel();
			panel3[i].setLayout(new BoxLayout(panel3[i], BoxLayout.Y_AXIS));
			JLabel queueName = new JLabel("Queue " + store.getQueuesInStore().get(i).getQueueName());
			avgTime[i] = new JLabel(String.valueOf(store.getQueuesInStore().get(i).getAwerageWaitingTime()));  
			queueName.setFont(new Font("Serif", Font.PLAIN, 30));
			avgTime[i].setFont(new Font("Serif", Font.PLAIN, 30));
			x1.add(t1);
			x1.add(avgTime[i]);
			x2.add(t2);
			x2.add(nbCl[i]);
			panel3[i].add(queueName);
			panel3[i].add(x1);
			panel3[i].add(x2);
			panel3[i].add(space);
			panel4.add(panel3[i]);
		}
		panel1.add(simtxt);
		panel1.add(panel2);
		panel1.add(panel4);
		frame2.setContentPane(panel1);
		frame2.setVisible(true);
	}
	
public void run() {
	int nbQueues = Integer.valueOf(this.nbQueues.getText());
	int nbClientsMax= 0;
		while(running) {
			int nbClients = 0;
			for(int i=0; i<nbQueues; i++) {
				nbCl[i].setText(String.valueOf(store.getQueuesInStore().get(i).getNbOfClients()));
				avgTime[i].setText(String.valueOf(store.getQueuesInStore().get(i).getAwerageWaitingTime())); 
				if(store.getQueuesInStore().get(i).getMessageList().size() != 0)
				{
					simInfo.append(store.getQueuesInStore().get(i).getMessage() + "\n");
				}
				store.getQueuesInStore().get(i).setMessage();
				nbClients += store.getQueuesInStore().get(i).getNbOfClients();
			}
			if(nbClients > nbClientsMax)
			{
				peakHourTxt.setText(String.valueOf(Integer.valueOf(timer.getText())));
				nbClientsMax = nbClients;
			}
			try {
				timer.setText(String.valueOf(Integer.valueOf(timer.getText()) + Integer.valueOf(simulationInterval.getText())));
				Thread.sleep(Integer.valueOf(simulationInterval.getText()) * 1200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void addActionListener(ActionListener start) { 
		this.start.addActionListener(start);
	}
	
	public JTextField getMinArr() {
		return this.minArr;  
	}
	public JTextField getMaxArr() {
		return this.maxArr;  
	}
	public JTextField getMinSrv() {
		return this.minServ;  
	}
	public JTextField getMaxSrv() {
		return this.maxServ;  
	}
	public JTextField getNbQueues() {
		return this.nbQueues; 
	}

}
