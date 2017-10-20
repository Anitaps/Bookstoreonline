import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class popupwindow {

	private JFrame frame2;
	PreparedStatement pst;
	private String title;

	private Description description;
	DBconnection conn = new DBconnection();

	private final JLabel lblDescription = new JLabel("Information");

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// popupwindow window = new popupwindow();
	// // window.frame2.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public popupwindow(String tabletitle) {
		this.title = tabletitle;
		initialize();
		frame2.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 500, 500);
		frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.getContentPane().add(lblDescription);

		JTextArea information = new JTextArea();
		information.setWrapStyleWord(true);
		information.setLineWrap(true);
		information.setBounds(10, 49, 464, 401);
		frame2.getContentPane().add(information);
		lblDescription.setBounds(212, 11, 77, 26);
		information.setVisible(true);
		information.setEditable(false);

		// information.setText("Title: "+ table.getModel().getValueAt(row, 0) +
		// "\n" +
		// "Author: " + table.getModel().getValueAt(row, 1)+ "\n" + "Genre: " +
		// table.getModel().getValueAt(row, 2)+ "\n" + "Price: " +
		// table.getModel().getValueAt(row, 3) + " £ \n" +
		// "Year: " + table.getModel().getValueAt(row, 4) + "\n" );

		try {
			String query = "select * from Books where Title='" + title + "'";

			pst = conn.connection.prepareStatement(query);
			ResultSet Rs = pst.executeQuery();
			while (Rs.next()) {
				information.setText("Title: " + Rs.getString(1) + "\n" + "Author: " + Rs.getString(2) + "\n" + "Price: "
						+ Rs.getString(3) + "\n" + "Year:" + Rs.getString(4) + "\n" + "\n" + "\n" + "Description: "
						+ Rs.getString(5));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		// try {
		//
		// frame2.dispose();
		// } catch (Exception ex) {
		// System.out.println(ex.getMessage());
		// }
		// }
		// });
	}
}
