package proje;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import jpa.CapitalJpa;
import jpa.FlightJpa;

public class FlightList {

	private JFrame flightListFrame;


	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public FlightList() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		String[] columnNames = {"Flight Id", "Airline Name", "Flight Number","From", "To","Take Off Time","Departure Time","Arrival Time","Landing Time"};
		
		FlightJpa flightJpa= new FlightJpa();
		CapitalJpa capitalJpa=new CapitalJpa();
		
		Object[][] data = new Object[flightJpa.getList().size()][9];
		int i = 0;
		for (FlightJpa flight : flightJpa.getList()) {
			 CapitalJpa capitalQueryDestination =capitalJpa.findById(flight.getDestinationId());
			 CapitalJpa capitalQueryOrigin = capitalJpa.findById(flight.getOriginId());
		
			data[i][0] = flight.getId();
			data[i][1] = flight.getAirlinesName();
			data[i][2] = flight.getFlightNumber();
			data[i][3] = capitalQueryOrigin.getName();
			data[i][4] = capitalQueryDestination.getName();
			data[i][5] = flight.getTakeoffTime()+" min";;
			data[i][6] = flight.getDepartureTime();
			data[i][7] = flight.getArrivalTime();
			data[i][8] = flight.getLandingTime()+" min";
			i++;
		}
			
		    
		    flightListFrame = new JFrame();
		    flightListFrame.setTitle("Capital List");
			flightListFrame.setBounds(100, 100, 700, 300);
			flightListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JScrollPane scrollPane = new JScrollPane();
			flightListFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
			      public boolean isCellEditable(int rowIndex, int mColIndex) {
			          return false;
			        }
			      };
			JTable flightListTable = new JTable(tableModel);
			flightListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			flightListTable.setFillsViewportHeight(true);
			flightListTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			flightListTable.getTableHeader().setReorderingAllowed(false);
			flightListTable.setPreferredScrollableViewportSize(new Dimension(150,96));
			/*burada ucuslarý listeleyip seçime göre ucus silip dosyaya kayit ediyorum*/
			flightListTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					FlightJpa flightJpa=new FlightJpa();
					int row = flightListTable.getSelectedRow();
					int id = (int) flightListTable.getValueAt(row, 0);
					String name=(String) flightListTable.getValueAt(row, 1);
					int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Delete flight '"+name+"'?","Warning",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
							tableModel.removeRow(row);
							try {
								List<FlightJpa> flightJpaList = flightJpa.getList();
								flightJpa.allRecordDelete();
								for (FlightJpa f : flightJpaList) {
									if (id != f.getId()) {
										FlightJpa flight = new FlightJpa();
										flight.setId(f.getId());
										flight.setAirlinesName(f.getAirlinesName());
										flight.setFlightNumber(f.getFlightNumber());
										flight.setOriginId(f.getOriginId());
										flight.setDestinationId(f.getDestinationId());
										flight.setDistance(f.getDistance());
										flight.setDepartureTime(f.getDepartureTime());
										flight.setArrivalTime(f.getArrivalTime());
										flight.setTakeoffTime(f.getTakeoffTime());
										flight.setLandingTime(f.getLandingTime());
										flight.setSpeed(f.getSpeed());
										flight.updateFlight();
									}
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						 
				 
				

					   }}
			});
			scrollPane.setViewportView(flightListTable);
			flightListFrame.setVisible(true);
	}
}
