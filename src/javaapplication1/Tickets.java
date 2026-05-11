package javaapplication1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Tickets extends JFrame implements ActionListener {

	// class level member objects
	Dao dao = new Dao(); // for CRUD operations
	Boolean chkIfAdmin = null;
	String loggedInUser;

	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");
	private JScrollPane tableScrollPane;

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;
	JMenuItem mnuItemViewById;
	JMenuItem mnuItemClose;

	public Tickets(String username, Boolean isAdmin) {
		loggedInUser = username;
		chkIfAdmin = isAdmin;
		createMenu();
		prepareGUI();

	}

	private void createMenu() {

		/* Initialize sub menu items **************************************/

		// initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// add to File main menu item
		mnuFile.add(mnuItemExit);

		// initialize first sub menu items for Admin main menu
		mnuItemUpdate = new JMenuItem("Update Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemUpdate);

		// initialize second sub menu items for Admin main menu
		mnuItemDelete = new JMenuItem("Delete Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemDelete);

		// initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemOpenTicket);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);

		// initialize any more desired sub menu items below
		mnuItemViewById = new JMenuItem("View Ticket By ID");
		mnuTickets.add(mnuItemViewById);

		mnuItemClose = new JMenuItem("Close Ticket");
		mnuAdmin.add(mnuItemClose);

		/* Add action listeners for each desired menu item *************/
		mnuItemExit.addActionListener(this);
		mnuItemUpdate.addActionListener(this);
		mnuItemDelete.addActionListener(this);
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		mnuItemViewById.addActionListener(this);
		mnuItemClose.addActionListener(this);

		 /*
		  * continue implementing any other desired sub menu items (like 
		  * for update and delete sub menus for example) with similar 
		  * syntax & logic as shown above
		 */

 
	}

	private void prepareGUI() {

		// create JMenu bar
		JMenuBar bar = new JMenuBar();
		bar.add(mnuFile); // add main menu items in order, to JMenuBar
		if (chkIfAdmin) {
			bar.add(mnuAdmin);
		}
		bar.add(mnuTickets);
		// add menu bar components to frame
		setJMenuBar(bar);

		addWindowListener(new WindowAdapter() {
			// define a window close operation
			public void windowClosing(WindowEvent wE) {
				System.exit(0);
			}
		});
		// set frame options
		setSize(400, 400);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.exit(0);
		} else if (e.getSource() == mnuItemOpenTicket) {

			// get ticket information
			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");

			// insert ticket information to database

			int id = 	dao.insertRecords(loggedInUser, ticketDesc);

			// display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("Ticket ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
				refreshTicketTable();
			} else
				System.out.println("Ticket cannot be created!!!");
		}

		else if (e.getSource() == mnuItemViewTicket) {
			refreshTicketTable();

		}
		/*
		 * continue implementing any other desired sub menu items (like for update and
		 * delete sub menus for example) with similar syntax & logic as shown above
		 */
		else if (e.getSource() == mnuItemDelete) {
			String ticketIdStr = JOptionPane.showInputDialog(null, "Enter ticket id to delete");

			if (ticketIdStr != null && !ticketIdStr.trim().isEmpty()) {
				int ticketId = Integer.parseInt(ticketIdStr);

				int confirm = JOptionPane.showConfirmDialog(
					null,
					"Are you sure you want to delete ticket number " + ticketId + "?",
					"Delete Confirmation",
					JOptionPane.YES_NO_OPTION
				);

				if (confirm == JOptionPane.YES_OPTION) {
					int status = dao.deleteRecords(ticketId);

					if (status > 0) {
						JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " deleted successfully.");
						refreshTicketTable();
					} else {
						JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " was not found.");
					}
				}
			}
		}
		else if (e.getSource() == mnuItemUpdate) {
			String ticketIdStr = JOptionPane.showInputDialog(null, "Enter ticket id to update");
			String newDesc = JOptionPane.showInputDialog(null, "Enter new ticket description");

			if (ticketIdStr != null && newDesc != null &&
				!ticketIdStr.trim().isEmpty() && !newDesc.trim().isEmpty()) {

				int ticketId = Integer.parseInt(ticketIdStr);
				int status = dao.updateRecords(ticketId, newDesc);

				if (status > 0) {
					JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " updated successfully.");
					refreshTicketTable();
				} else {
					JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " was not found.");
				}
			}
		}

		else if (e.getSource() == mnuItemViewById) {
			String ticketIdStr = JOptionPane.showInputDialog(null, "Enter ticket id to view");

			if (ticketIdStr != null && !ticketIdStr.trim().isEmpty()) {
				try {
					int ticketId = Integer.parseInt(ticketIdStr);

					JTable jt = new JTable(
						ticketsJTable.buildTableModel(
							dao.readRecordByTicketNum(ticketId, loggedInUser, chkIfAdmin)
						)
					);
					jt.setFillsViewportHeight(true);

					if (tableScrollPane != null) {
						remove(tableScrollPane);
					}

					tableScrollPane = new JScrollPane(jt);
					tableScrollPane.setBounds(20, 50, 340, 250);
					add(tableScrollPane);

					revalidate();
					repaint();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		else if (e.getSource() == mnuItemClose) {
			String ticketIdStr = JOptionPane.showInputDialog(null, "Enter ticket id to close");

			if (ticketIdStr != null && !ticketIdStr.trim().isEmpty()) {
				int ticketId = Integer.parseInt(ticketIdStr);

				int status = dao.closeTicket(ticketId);

				if (status > 0) {
					JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " closed successfully.");
					refreshTicketTable();
				} else {
					JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " was not found.");
				}
			}
		}

	}

	private void refreshTicketTable() {
		// retrieve all tickets details for viewing in JTable
		try {
			// Use JTable built in functionality to build a table model and
			// display the table model off your result set!!!
			JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords(loggedInUser, chkIfAdmin)));
			jt.setFillsViewportHeight(true);

			if (tableScrollPane != null) {
				remove(tableScrollPane);
			}

			tableScrollPane = new JScrollPane(jt);
			tableScrollPane.setBounds(20, 50, 340, 250);
			add(tableScrollPane);

			revalidate();
			repaint();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
