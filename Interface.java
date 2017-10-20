import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

//import bookstore.Secondwindow;
import net.proteanit.sql.DbUtils;

public class Interface {

	public JFrame frame;
	private JTextField title;
	private JLabel lblPrice;
	private JTextField pricemin;
	private JLabel lblTo;
	private JTextField pricemax;
	private JLabel lblGenre;
	public JTable table;
	private JScrollPane scrollPane = new JScrollPane();
	double min;
	double max;
	PreparedStatement pst;
	ResultSet Rs;
	private String query;
	private JComboBox comboGenre;
	private JComboBox comboAuthor;
	DefaultTableModel model = new DefaultTableModel();

	/**
	 * Create the application.
	 */
	// declare connection object to sqlite class, the first
	// Connectin(class)bleongs to Dbconnection class
	DBconnection conn = null;

	public Interface() {
		conn = new DBconnection();
		try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(25, 11, 46, 14);
		frame.getContentPane().add(lblTitle);

		title = new JTextField();
		title.setBounds(81, 8, 280, 20);
		frame.getContentPane().add(title);
		title.setColumns(10);

		table = new JTable(model);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(177, 232, 89, 23);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * DefaultTableModel model = (DefaultTableModel)
				 * table.getModel(); model.setRowCount(0);
				 * table.setModel(model);
				 */

				try {
					if (pricemin.getText().equals("")) {
						min = 0;
					} else {
						min = Integer.parseInt(pricemin.getText());
					}
					if (pricemax.getText().equals("")) {
						max = 20;
					} else {
						max = Integer.parseInt(pricemax.getText());
					}

					query = "Select * from Books,Genre,book_genre where Books.ID="
							+ "book_genre.BookID and book_genre.GenreID=Genre.ID and price>=" + min + " and price<="
							+ max;
					
					if (title.getText()!="") {
						query += " and Title='" + title.getText() + "'";
					}
System.out.println(query);
					if (comboGenre.getSelectedItem() != "") {
						query += " and genre='" + comboGenre.getSelectedItem().toString() + "'";
					}
					if (comboAuthor.getSelectedItem().toString() != "") {
						query += " and author='" + comboAuthor.getSelectedItem().toString() + "'";
					}

					pst = conn.connection.prepareStatement(query);
					Rs = pst.executeQuery();

					model = new DefaultTableModel(new Object[][] {}, new String[] { "Title", "Author" });
					table = new JTable(model);
					scrollPane.setViewportView(table);

					table.addMouseListener(new MouseAdapter() { // Adding
																// Mouse-click
																// event
																// for
																// row-selection
						public void mouseClicked(MouseEvent e) {
							int row = table.getSelectedRow();
							popupwindow popup = new popupwindow(table.getModel().getValueAt(row, 0).toString());

						}
					});
					table.removeEditor(); // Disables the user from editing the
											// cells in the table

					table.setBackground(Color.WHITE);
					while (Rs.next()) {
						model.addRow(new Object[] { Rs.getString("Title"), Rs.getString("Author") });
						model.addRow(new Object[] { Rs.getString("Title"), Rs.getString("Genre") });
						

					}

					table.setVisible(true);

				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});

		JLabel lblAuthor = new JLabel("Price");
		lblAuthor.setBounds(25, 36, 46, 14);
		frame.getContentPane().add(lblAuthor);

		pricemin = new JTextField();
		pricemin.setBounds(81, 33, 72, 20);
		frame.getContentPane().add(pricemin);
		pricemin.setColumns(10);

		lblTo = new JLabel("-");
		lblTo.setBounds(162, 36, 46, 14);
		frame.getContentPane().add(lblTo);

		pricemax = new JTextField();
		pricemax.setBounds(177, 33, 74, 20);
		frame.getContentPane().add(pricemax);
		pricemax.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 414, 135);
		scrollPane.add(table);
		frame.getContentPane().add(scrollPane);

		lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(25, 61, 46, 14);
		frame.getContentPane().add(lblAuthor);

		comboAuthor = new JComboBox();
		comboAuthor.setBounds(81, 58, 142, 20);
		frame.getContentPane().add(comboAuthor);
		comboAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		try {

			query = "Select distinct author from Books";
			comboAuthor.addItem("");

			pst = conn.connection.prepareStatement(query);
			ResultSet Rs = pst.executeQuery();
			while (Rs.next()) {
				comboAuthor.addItem(Rs.getString(1));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		lblGenre = new JLabel("Genre");
		lblGenre.setBounds(233, 61, 46, 14);
		frame.getContentPane().add(lblGenre);

		comboGenre = new JComboBox();
		comboGenre.setBounds(272, 58, 152, 20);
		frame.getContentPane().add(comboGenre);
		comboGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		try {

			query = "Select distinct genre from Genre";
			comboGenre.addItem("");
			pst = conn.connection.prepareStatement(query);
			ResultSet Rs = pst.executeQuery();
			while (Rs.next()) {
				comboGenre.addItem(Rs.getString(1));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}