import javax.swing.*;
import java.awt.Color;
import java.awt.*;
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
	JPanel jp,jpt,jpi;
	JLabel head;
	JButton search;
	JTable jt;
	JScrollPane jsp;
	String[][] data = new String[5][6];
	JLabel iternary;
	
	String countries[] = {"Singapore" , "Bhutan" ,"Nepal", "Hong Kong", "Macau", "Dubai", "Thailand" ,"India"};
	String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
	String budgets[] = {"50000","100000","150000","200000","300000"};
	String[] colnames = {"Trip", "Month", "Estimated price", "Offers", "Know more"};

	
	FindMyTrip()
	{
		super("Find My Trip");
		jp = new JPanel();
		jp.setLayout(  new BorderLayout(8, 8) );
		
		head = new JLabel("I want my holiday in...",SwingConstants.CENTER);
		jp.add(head,"North");
		
		country = new JComboBox<String>();
		for (int c = 0; c < countries.length; c++)
			country.addItem(countries[c]);
		jp.add(country,"Center");
				
		month = new JComboBox<String>();
		for (int m = 0; m < months.length; m++)
			month.addItem(months[m]);
		jp.add(month,"West");
		
		budget = new JComboBox<String>();
		for (int b = 0; b < budgets.length; b++)
			budget.addItem(budgets[b]);
		jp.add(budget,"East");
	
		search = new JButton("Find My Trip");
		head.setForeground(Color.BLACK);
		search.addActionListener(this);
		jp.add(search,"South");
		
		jp.setBackground(Color.ORANGE);
		getContentPane().add(jp, "North"); 
		
		jpt = new JPanel();
		jpt.setLayout(new GridLayout());
		
		data[0][0]="";
		data[0][1]="";
		data[0][2]="";
		data[0][3]="";
		data[0][4]="";
		
		jt = new JTable(data,colnames);
		jsp = new JScrollPane(jt);
		jpt.add(jsp);
		jpt.setBackground(Color.ORANGE);
		jsp.getViewport().setBackground(Color.ORANGE);
		getContentPane().add(jpt);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource()==search)
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					int z = 0;
					Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
					Statement stmt = conn.createStatement();
					String c = country.getSelectedItem().toString();
					String m = month.getSelectedItem().toString();
					String b = budget.getSelectedItem().toString();
				
					String query = "Select * from tripdetails where country='"+c+"' and month='"+m+"' and price='"+b+"';";
					System.out.print(query);
		        
					ResultSet rs= stmt.executeQuery(query);
					while(rs.next())
		            {	
		            	int q = 0;
		               
		                System.out.println("Trip name "+rs.getString("tripname"));
		                System.out.println("Month "+rs.getString("month"));
		                System.out.println("Price "+rs.getInt("price"));
		                System.out.println("Offer "+rs.getString("offer"));
		                
		                data[z][q++] = rs.getString("tripname");
		                data[z][q++] = rs.getString("month");
		                data[z][q++] = String.valueOf(rs.getInt("price"));
		                data[z][q++] = rs.getString("offer");
		                z++;
		                
		                jt.getColumn("Know more").setCellRenderer(new ButtonRenderer());
		                jt.getColumn("Know more").setCellEditor(new ButtonEditor(new JCheckBox()));
		                
		                jt.repaint();
		               
		            }
		            for (int i=0;i<3;i++)
		            	for (int j=0;j<3;j++)
		            		System.out.println(data[i][j]);
		            
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	
}