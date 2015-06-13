package proje;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jpa.CapitalJpa;

import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;

import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CapitalList {

	private JFrame frmCapitalList;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @throws FileNotFoundException
	 */
	public CapitalList() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws FileNotFoundException
	 */
	private void initialize() throws FileNotFoundException {
		String[] columnNames = { "id", "Name" };
		CapitalJpa capitalJpa = new CapitalJpa();
		Object[][] data = new Object[capitalJpa.getList().size()][2];
		int i = 0;
		for (CapitalJpa capital : capitalJpa.getList()) {
			
			data[i][0] = capital.getId();
			data[i][1] = capital.getName();

			i++;
		}

		frmCapitalList = new JFrame();
		frmCapitalList.setTitle("Capital List");
		frmCapitalList.setBounds(100, 100, 450, 300);
		frmCapitalList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		frmCapitalList.getContentPane().add(scrollPane, BorderLayout.CENTER);
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
		      public boolean isCellEditable(int rowIndex, int mColIndex) {
		          return false;
		        }
		      };
		JTable capitalListTable = new JTable(tableModel);
		capitalListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		capitalListTable.setFillsViewportHeight(true);
		capitalListTable.getTableHeader().setReorderingAllowed(false);
		capitalListTable.setPreferredScrollableViewportSize(new Dimension(150,
				96));
		capitalListTable.addMouseListener(new MouseAdapter() {
			@Override
			/*burada listeden seçtiðim havaalanýný dosyadan siliyorum*/
			public void mouseClicked(MouseEvent e) {

				int row = capitalListTable.getSelectedRow();
				int id = (int) capitalListTable.getValueAt(row, 0);
				String name = (String) capitalListTable.getValueAt(row, 1);
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Would You Like to Delete capital '" + name + "'?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					tableModel.removeRow(row);
					try {
						List<CapitalJpa> capitalList = capitalJpa.getList();
						capitalJpa.allRecordDelete();
						for (CapitalJpa capitalJpa : capitalList) {
							if (id != capitalJpa.getId()) {
								System.out.println(capitalJpa.getId());
								 CapitalJpa c = new CapitalJpa();
								c.setId(capitalJpa.getId());
								c.setName(capitalJpa.getName());
								c.setLat(capitalJpa.getLat());
								c.setLng(capitalJpa.getLng());
								c.updateCapital();
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
		scrollPane.setViewportView(capitalListTable);
		frmCapitalList.setVisible(true);
	}

}
