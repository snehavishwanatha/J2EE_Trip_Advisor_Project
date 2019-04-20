import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class ButtonEditor extends DefaultCellEditor {
  protected JButton button;
  private String    label;
  private boolean   isPushed;
  
  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }
  
  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
	try {
	String tripname = table.getModel().getValueAt(row, 0).toString();
	try {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/trip","root","root");
			Statement stmt = conn.createStatement();
			String query = "Select iternary from tripdetails where tripname = '"+tripname+"';";
			ResultSet rs= stmt.executeQuery(query);
			while(rs.next())
            {
				label=rs.getString("iternary");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
   
    button.setText("Check");
    isPushed = true;
    return button;
  }
  
  public Object getCellEditorValue() {
    if (isPushed)  {
      JOptionPane.showMessageDialog(button ,label);
    }
    isPushed = false;
    return new String( label ) ;
  }
    
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

}
