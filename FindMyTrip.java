import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindMyTrip extends JFrame implements ActionListener{
	JComboBox <String> country; 
	JComboBox <String> month;
	JComboBox <String> budget;
	JPanel jp;
	JLabel head;
	JButton search;
	JTable jt;
	JScrollPane jsp;
	String[][] data = {{"abc","12","22","hello","hu"}};
	JLabel table_label;
	
	String countries[] = {"Singapore" , "Bhutan" ,"Nepal", "Hong Kong", "Macau", "Dubai", "Thailand" ,"India"};
	String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
	String budgets[] = {"50000","100000","150000","200000","300000"};
	String[] colnames = {"Trip", "Month", "Estimation", "Offers", "Know more"};

	
	FindMyTrip()
	{
		super("FindMyTrip");
		jp = new JPanel(); 
		
		head = new JLabel("I want my holiday in...");
		head.setForeground(Color.BLACK);
		jp.add(head);
		
		country = new JComboBox<String>();
		for (int c = 0; c < countries.length; c++)
			country.addItem(countries[c]);
		jp.add(country);
		
		
		month = new JComboBox<String>();
		for (int m = 0; m < months.length; m++)
			month.addItem(months[m]);
		jp.add(month);
		
		budget = new JComboBox<String>();
		for (int b = 0; b < budgets.length; b++)
			budget.addItem(budgets[b]);
		jp.add(budget);
	
		search = new JButton("Search");
		search.setBackground(Color.white);
		search.addActionListener(this);
		jp.add(search);
		
		table_label = new JLabel("Trending packages");
		jp.add(table_label);
		
		jt = new JTable(data,colnames);
		jt.setSize(25, 25);
		jsp = new JScrollPane(jt);
		jp.add(jsp);
		
		setContentPane(jp);
		
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource()==search)
		{
			table_label.setText("According to your preferences,");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					int z = 0;
					Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
					Statement stmt = conn.createStatement();
					String c = country.getSelectedItem().toString();
					String m = month.getSelectedItem().toString();
					String b = budget.getSelectedItem().toString();
				
					String query = "Select * from tripdetails where country='"+c+"' and month='"+m+"';";
					System.out.print(query);
		            ResultSet rs= stmt.executeQuery(query);
		            while(rs.next())
		            {	
		            	int q = 0;
		               
		                System.out.println("Trip name "+rs.getString(2));
		                System.out.println("Month "+rs.getString(3));
		                System.out.println("Price "+rs.getInt(4));
		               
		                data[z][q++] = rs.getString(2);
		                data[z][q++] = rs.getString(3);
		                data[z][q++] = rs.getString(4);
		                z++;
		                
		                jt.getColumn("Know more").setCellRenderer(new ButtonRenderer());
		                jt.repaint();
		               
		            }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	public static void main(String args[]) {
		FindMyTrip fmt = new FindMyTrip();
		fmt.setSize(500,500);
		fmt.setVisible(true);
		fmt.getContentPane().setBackground(Color.orange);
		fmt.setDefaultCloseOperation(fmt.EXIT_ON_CLOSE);
	}

}
