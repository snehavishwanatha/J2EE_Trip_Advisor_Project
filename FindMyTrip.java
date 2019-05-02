import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

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
	 ArrayList<Icon> list = new ArrayList<Icon>();
     JLabel labelicon,labeliconleft;
	
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
		
		/*country = new JComboBox<String>();
		for (int c = 0; c < countries.length; c++)
			country.addItem(countries[c]);*/
		
				
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
		jpt.setLayout(new GridLayout(1,3));
		
		labeliconleft = new JLabel();
	    jpt.add(labeliconleft);
	       
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
		
		Timer timer = new Timer(1000, new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            update();
	        }
	    });
	
		for (int r = 0; r < 11; r++)
		{
		list.add(new ImageIcon(new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/"+String.valueOf(r)+".jpg").getImage().getScaledInstance(700, 700, Image.SCALE_DEFAULT)));
    	}
        labelicon = new JLabel();
        jpt.add(labelicon);
        timer.start();
	    	   
		getContentPane().add(jpt);
		
		try {
        	country = new JComboBox <String>();
			Class.forName("com.mysql.jdbc.Driver");
			try {
				
				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
				Statement stmt = conn.createStatement();
    				
				String query = "select distinct country from tripdetails;";
			    	        
				ResultSet rs =stmt.executeQuery(query);
				while(rs.next())
				{
					country.addItem(rs.getString("country"));
				}
		 
			} catch (SQLException e) {
				jp.add(new JTextField("No trips created to update"));
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		jp.add(country,"Center");
	}

	    private void update() {
	        Collections.shuffle(list);
	        int index = 0;
	        labelicon.setIcon(list.get(index++));
	        labeliconleft.setIcon(list.get(index++));
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
				
					String query = "Select * from tripdetails where country='"+c+"' and (month='"+m+"' or price<='"+b+"');";
					System.out.print(query);
		        
					ResultSet rs= stmt.executeQuery(query);
					if(!rs.isBeforeFirst())
					{
						JOptionPane.showMessageDialog(search,"Sorry no trips exists as per your choice!");
					}
					else {
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
			                
			               
			                jt.setAutoResizeMode( JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS );
			                
			                for (int column = 0; column < jt.getColumnCount()-1; column++)
			                {
			                    TableColumn tableColumn = jt.getColumnModel().getColumn(column);
			                    int preferredWidth = tableColumn.getMinWidth();
			                    int maxWidth = tableColumn.getMaxWidth();
			                 
			                    for (int row = 0; row < jt.getRowCount(); row++)
			                    {
			                        TableCellRenderer cellRenderer = jt.getCellRenderer(row, column);
			                        Component co = jt.prepareRenderer(cellRenderer, row, column);
			                        int width = co.getPreferredSize().width + jt.getIntercellSpacing().width;
			                        preferredWidth = Math.max(preferredWidth, width);
			                 
			                        if (preferredWidth >= maxWidth)
			                        {
			                            preferredWidth = maxWidth;
			                            break;
			                        }
			                    }
			                    tableColumn.setMinWidth(55);
			                    tableColumn.setPreferredWidth( preferredWidth );
			                    
			                }
			                
			                jt.repaint();
			            }
			            for (int i=0;i<3;i++)
			            	for (int j=0;j<3;j++)
			            		System.out.println(data[i][j]);	
					}
					            
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}