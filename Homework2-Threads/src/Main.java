
public class Main {
	
	public static void main (String[] args) {
		Store store = new Store(9);
		View view = new View(store);
		Controller ctr = new Controller(view,store);
	} 
}
