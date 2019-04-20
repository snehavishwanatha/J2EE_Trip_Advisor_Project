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
    JLabel jl1,jl2,jl3,jl4,jl5,jl6;
    JTextField jt1,jt2,jt3,jt4;
    JTextArea ja;
    JButton addbutton,upbutton;
	JComboBox <String> month;
    String add = "Add a trip";
    String update = "Update existing trip";
     
    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { add, update };
        JComboBox <String> cb = new JComboBox <String> (comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        JPanel card1 = new JPanel();
        card1.setLayout(new GridLayout(2,6,2,1));
        card1.setBackground(Color.GREEN);
        
        jl1 = new JLabel("Country", SwingConstants.CENTER);
        jt1 = new JTextField(20);
        jt1.setBackground(Color.ORANGE);
        card1.add(jl1);
        card1.add(jt1);
        
        jl2 = new JLabel("Trip Name",SwingConstants.CENTER);
        jt2 = new JTextField(20);
        jt2.setBackground(Color.ORANGE);
        card1.add(jl2);
        card1.add(jt2);
        
        jl3 = new JLabel("Price",SwingConstants.CENTER);
        jt3 = new JTextField(20);
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
        			 
        				} catch (SQLException e) {
        					e.printStackTrace();
        				}
        			} catch (ClassNotFoundException e) {
        				e.printStackTrace();
        			}
        		}
            }
        });
        
        
        jl4 = new JLabel("Offers", SwingConstants.CENTER);
        jt4 = new JTextField(20);
        jt4.setBackground(Color.ORANGE);
        card1.add(jl4);
        card1.add(jt4);
    
        jl5 = new JLabel("Month",SwingConstants.CENTER);
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    	month = new JComboBox<String>();
		for (int m = 0; m < months.length; m++)
			month.addItem(months[m]);
		card1.add(jl5);
		card1.add(month);
        
        ja = new JTextArea(200,200);
        ja.setBackground(Color.ORANGE);
        jl6 = new JLabel("Iternary", SwingConstants.CENTER);
        card1.add(jl6);
        card1.add(ja);
        
         
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, add);
        cards.add(card2, update);
         
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