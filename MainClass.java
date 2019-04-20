import java.awt.Color;

public class MainClass {
	public static void main(String args[]) {
		FindMyTrip fmt = new FindMyTrip();
		fmt.setSize(500,500);
		fmt.setVisible(true);
		fmt.getContentPane().setBackground(Color.orange);
		fmt.setDefaultCloseOperation(fmt.EXIT_ON_CLOSE);
	}
}
