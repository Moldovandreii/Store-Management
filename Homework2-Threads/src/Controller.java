import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
	private Store store;
	private View view;
	
	public Controller(View view, Store store) {
		this.view = view;
		this.store = store;
		view.addActionListener(new Start());
	}
	
	class Start implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setView();
			store.setMinMaxTimers(Integer.valueOf(view.getMinArr().getText()), Integer.valueOf(view.getMaxArr().getText()), Integer.valueOf(view.getMinSrv().getText()), Integer.valueOf(view.getMaxSrv().getText()));
			store.setNbOfQueues(Integer.valueOf(view.getNbQueues().getText()));
			store.start();
			Thread t1 = new Thread(view);
			t1.start();
		}
		
	}
}
