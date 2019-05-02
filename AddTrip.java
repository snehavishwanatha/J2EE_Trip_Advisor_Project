import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
 
public class AddTrip implements ItemListener {
    JPanel cards; 
    ImageIcon image;
    JLabel jl1,jl2,jl3,jl4,jl5,jl6;
    JTextField jt1,jt2,jt3,jt4;
    JTextField ju1,ju2,ju3,ju4;
    JTextArea ja,ju;
    JComboBox <String> tn, dtn;
    JButton addbutton,upbutton,delbutton;
	JComboBox <String> month, update_month;
    String add = "Add a trip";
    String update = "Update existing trip";
    String delete = "Delete a trip";
     
    public void addComponentToPane(Container pane) {

        JPanel comboBoxPane = new JPanel(); 
        comboBoxPane.setBackground(Color.BLUE);
        String comboBoxItems[] = { add, update, delete };
        JComboBox <String> cb = new JComboBox <String> (comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        JPanel card1 = new JPanel();
        card1.setLayout(new GridLayout(2,6,20,25));
        card1.setBackground(Color.orange);
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/country.jpg");
        jl1 = new JLabel(image, SwingConstants.CENTER);
        jt1 = new JTextField("Country");
        jt1.setBackground(Color.ORANGE);
        card1.add(jl1);
        card1.add(jt1);
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/trip.jpg");
        jl2 = new JLabel(image,SwingConstants.CENTER);
        jt2 = new JTextField("Trip name");
        jt2.setBackground(Color.ORANGE);
        card1.add(jl2);
        card1.add(jt2);
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/price.jpg");
        jl3 = new JLabel(image,SwingConstants.CENTER);
        jt3 = new JTextField("Estimated price");
        jt3.setBackground(Color.ORANGE);
        card1.add(jl3);
        card1.add(jt3);
        
        addbutton = new JButton("Add the trip");
        card1.add(addbutton);
        addbutton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt)
            {
              	if(evt.getSource()==addbutton)
        		{
        			try {
        				Class.forName("com.mysql.jdbc.Driver");
        				try {
        					
        					Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
        					Statement stmt = conn.createStatement();
                				
        					String query = "insert into tripdetails values('"+jt1.getText()+"', '"+jt2.getText()+"',"+Integer.parseInt(jt3.getText())+",'"+jt4.getText()+"','"+month.getSelectedItem().toString()+"','"+ja.getText()+"');";
        				    System.out.print(query);
        		        
        					stmt.executeUpdate(query);
        					
        					jt1.setText("Country");
        					jt3.setText("Estimated price");
        					jt4.setText("Any offer");
        					ja.setText("Scheduled Iternary");
        					
        					
        					Statement check = conn.createStatement();
        					String cquery = "select * from tripdetails where tripname='"+jt2.getText()+"';";
        					
        					ResultSet rs = check.executeQuery(cquery);
        					int f = 1;
        					while(rs.next())
        					{
        						JOptionPane.showMessageDialog(pane, "Trip added");
        						f = 0;
        					}
        					if(f==1)
        						JOptionPane.showMessageDialog(pane, "Trip not added - Duplicate entry");
        			 
        				} catch (Exception e) {
        					e.printStackTrace();
        					JOptionPane.showMessageDialog(pane, "Trip not added - Empty value(s) or Invalid entry");
        				}

        			} catch (ClassNotFoundException e) {
        				e.printStackTrace();
        			}
        		}
            }
        });
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/offer.jpg");
        jl4 = new JLabel(image, SwingConstants.CENTER);
        jt4 = new JTextField("Any offer");
        jt4.setBackground(Color.ORANGE);
        card1.add(jl4);
        card1.add(jt4);
    
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/month.jpg");
        jl5 = new JLabel(image,SwingConstants.CENTER);
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    	month = new JComboBox<String>();
		for (int m = 0; m < months.length; m++)
			month.addItem(months[m]);
		card1.add(jl5);
		card1.add(month);
        
		image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/it.jpg");
        ja = new JTextArea("Scheduled iternary");
        ja.setBackground(Color.ORANGE);
        jl6 = new JLabel(image, SwingConstants.CENTER);
        card1.add(jl6);
        card1.add(ja);
        
         
        JPanel card2 = new JPanel();
        card2.setLayout(new GridLayout(2,6,20,25));
        card2.setBackground(Color.green);
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/trip.jpg");
        jl2 = new JLabel(image,SwingConstants.CENTER);
        card2.add(jl2);
        try {
        	tn = new JComboBox <String>();
			Class.forName("com.mysql.jdbc.Driver");
			try {
				
				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
				Statement stmt = conn.createStatement();
    				
				String query = "select tripname from tripdetails;";
			    	        
				ResultSet rs =stmt.executeQuery(query);
				while(rs.next())
				{
					tn.addItem(rs.getString("tripname"));
				}
		 
			} catch (SQLException e) {
				card2.add(new JTextField("No trips created to update"));
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        card2.add(tn);
        tn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                
                JComboBox comboBox = (JComboBox) event.getSource();

                Object selected = comboBox.getSelectedItem();
                try {
    				
    				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
    				Statement stmt = conn.createStatement();
        				
    				String query = "select * from tripdetails where tripname = '"+ selected.toString()+"';";
    			    	        
    				ResultSet rs =stmt.executeQuery(query);
    				while(rs.next())
    				{
    					ju1.setText(rs.getString("country"));
     	                ju3.setText(String.valueOf(rs.getInt("price")));
     	                month.setSelectedItem(rs.getString("month"));
 		                ju4.setText(rs.getString("offer"));
 		                ju.selectAll();
 		                ju.replaceSelection("");
 		                ju.insert(rs.getString("iternary"), 0);
    				}
    		 
    			} catch (SQLException e) {
    				card2.add(new JTextField("No trips created to update"));
    				e.printStackTrace();
    			}
    		
    		

            }
        });
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/country.jpg");
        jl1 = new JLabel(image, SwingConstants.CENTER);
        ju1 = new JTextField("Country");
        ju1.setBackground(Color.green);
        card2.add(jl1);
        card2.add(ju1);
       
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/price.jpg");
        jl3 = new JLabel(image,SwingConstants.CENTER);
        ju3 = new JTextField("Estimated price");
        ju3.setBackground(Color.green);
        card2.add(jl3);
        card2.add(ju3);
        
        upbutton = new JButton("Update the trip");
        card2.add(upbutton);
        upbutton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt)
            {
              	if(evt.getSource()==upbutton)
        		{
        			try {
        				Class.forName("com.mysql.jdbc.Driver");
        				try {
        					
        					Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
        					Statement stmt = conn.createStatement();
                				
        					String query = "update tripdetails set country ='"+ju1.getText()+"', price = "+Integer.parseInt(ju3.getText())+", offer = '"+ju4.getText()+"', month = '"+update_month.getSelectedItem().toString()+"', iternary = '"+ju.getText()+"' where tripname = '"+tn.getSelectedItem().toString()+"';";
        				    System.out.print(query);
        		        
        					stmt.executeUpdate(query);
        					
        					Statement check = conn.createStatement();
        					String cquery = "select * from tripdetails where tripname='"+tn.getSelectedItem().toString()+"';";
        					
        					ResultSet rs = check.executeQuery(cquery);
        					int f = 1;
        					while(rs.next())
        					{
        						if(rs.getString("country").equals(ju1.getText()) && rs.getInt("price")==Integer.parseInt(ju3.getText()) && rs.getString("offer").equals(ju4.getText()) && rs.getString("iternary").equals(ju.getText()) && rs.getString("month").equals(update_month.getSelectedItem().toString()))
        						{	
        							JOptionPane.showMessageDialog(pane, "Trip Updated");
        							f = 0;
                					ju1.setText("Country");
                					ju3.setText("Estimated Price");
                					ju4.setText("Any Offer");
                					ju.setText("Scheduled Iternary");
        						}
        					}
        					if(f==1)
        						JOptionPane.showMessageDialog(pane, "Trip not Updated");
        			 
        					
        				} catch (Exception e) {
        					//e.printStackTrace();
        					JOptionPane.showMessageDialog(pane, "Trip not Updated - Invalid Entry");
        				}
        			} catch (ClassNotFoundException e) {
        				e.printStackTrace();
        			}
        		}
            }
        });
        
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/offer.jpg");
        jl4 = new JLabel(image, SwingConstants.CENTER);
        ju4 = new JTextField("Any offer");
        ju4.setBackground(Color.green);
        card2.add(jl4);
        card2.add(ju4);
    
        image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/month.jpg");
        jl5 = new JLabel(image,SwingConstants.CENTER);
        String monthslist[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    	update_month = new JComboBox<String>();
		for (int m = 0; m < monthslist.length; m++)
			update_month.addItem(monthslist[m]);
		card2.add(jl5);
		card2.add(update_month);
        
		image = new ImageIcon("/home/sneha/eclipse-workspace/Trip Advisor/src/it.jpg");
        ju = new JTextArea("Scheduled iternary");
        ju.setBackground(Color.green);
        jl6 = new JLabel(image, SwingConstants.CENTER);
        card2.add(jl6);
        card2.add(ju);
         
        JPanel card3 = new JPanel();
        card3.setLayout(new FlowLayout(5,55,70));
        card3.setBackground(Color.orange);
        
       
        try {
        	dtn = new JComboBox <String>();
			Class.forName("com.mysql.jdbc.Driver");
			try {
				
				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
				Statement stmt = conn.createStatement();
    				
				String query = "select tripname from tripdetails;";
			    	        
				ResultSet rs =stmt.executeQuery(query);
				while(rs.next())
				{
					dtn.addItem(rs.getString("tripname"));
				}
		 
			} catch (SQLException e) {
				card2.add(new JTextField("No trips created to delete"));
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        card3.add(dtn);
        delbutton = new JButton("Delete trip");
        card3.add(delbutton);
        delbutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		
        		if(evt.getSource()==delbutton)
        		{
        			try {
        				Class.forName("com.mysql.jdbc.Driver");
        				try {
        					
        					Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
        					Statement stmt = conn.createStatement();
        					
        					String delquery = "delete from tripdetails where tripname='"+dtn.getSelectedItem().toString()+"';";
        					stmt.executeUpdate(delquery);
        					
        					String check = "select * from tripdetails where tripname='"+dtn.getSelectedItem().toString()+"';";
        					ResultSet rs = stmt.executeQuery(check);
        					if(!rs.isBeforeFirst())
        					{
        						JOptionPane.showMessageDialog(pane, "Trip entry deleted");
        					}
        					else
        						JOptionPane.showMessageDialog(pane, "Deletion not possible");
        					
        				} catch(Exception e) {
        					e.printStackTrace();
        				}
        			} catch(Exception e) {
        				e.printStackTrace();
        			}
        		}
        	}
        });
        cards = new JPanel(new CardLayout());
        cards.add(card1, add);
        cards.add(card2, update);
        cards.add(card3, delete);
        
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        
    }
     
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    
    private static void createAndShowGUI() {
    
        JFrame frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AddTrip at = new AddTrip();
        at.addComponentToPane(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}