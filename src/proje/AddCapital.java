package proje;

import jpa.CapitalJpa;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class AddCapital {

	public JFrame frame;
	private JTextField capitalNameF;
	private JTextField capitalLocationF;
	private Component browserViewComponent;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public AddCapital() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
		browserView.addMouseListener(new MouseAdapter() {
			/*burada haritaya týklayarak o yerin kordinatýný alýyorum*/
			@Override
			public void mouseClicked(MouseEvent arg0) {
				browser.executeJavaScriptAndReturnValue("google.maps.event.addListener(map, 'click', function(event) {"
						+ "    addMarker(event.latLng);"
						+ " document.title=event.latLng;"

						+ "});");
				JSValue marker = browser
						.executeJavaScriptAndReturnValue("document.title");
				capitalLocationF.setText(marker.getString());

			}
		});

		frame = new JFrame("Capital Add Page");
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				browserViewComponent.setBounds(275, 11,
						350 + (frame.getWidth() - 670),
						350 + (frame.getHeight() - 415));
			}
		});
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel addInputPanel = new JPanel();
		addInputPanel.setBounds(10, 11, 259, 341);
		frame.getContentPane().add(addInputPanel);
		addInputPanel.setLayout(null);

		JLabel capitalNameL = new JLabel("Capital Name");
		capitalNameL.setBounds(10, 11, 72, 22);
		addInputPanel.add(capitalNameL);

		capitalNameF = new JTextField();
		capitalNameF.setBounds(108, 12, 86, 20);
		addInputPanel.add(capitalNameF);
		capitalNameF.setColumns(10);

		JLabel capitalLocationL = new JLabel("Capital Location");
		capitalLocationL.setBounds(9, 39, 95, 29);
		addInputPanel.add(capitalLocationL);

		capitalLocationF = new JTextField();
		capitalLocationF.setBounds(108, 43, 146, 20);
		addInputPanel.add(capitalLocationF);
		capitalLocationF.setColumns(10);

		JButton capitalAddB = new JButton("Add");
		/*burada haritadan seçtiðim yeri dosyaya ekliyorum*/
		capitalAddB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String capitalName = capitalNameF.getText();
				String capitalLocation = capitalLocationF.getText();
				if (!capitalName.isEmpty() && !capitalLocation.isEmpty()) {

					String[] latLng = capitalLocation.substring(1,
							
					capitalLocation.length() - 1).split(",");
					CapitalJpa capital = new CapitalJpa();
					capital.setName(capitalNameF.getText());
					capital.setLat(latLng[0].toString());
					capital.setLng(latLng[1].toString());
					try {
						capital.addCapital();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,
							"Þehir Baþarýyla kayýt edilmiþtir");
				} else {

					JOptionPane.showMessageDialog(null,
							"Lütfen bilgileri giriniz");

				}

			}
		});
		capitalAddB.setBounds(10, 105, 89, 23);
		addInputPanel.add(capitalAddB);

		browserViewComponent = frame.getContentPane().add(browserView);
		browserViewComponent.setBounds(275, 11, 350, 350);
		frame.setVisible(true);
		
		File mapHtml = new File("map.html");
		System.out.println(mapHtml.getAbsolutePath());
		browser.loadURL(mapHtml.getAbsolutePath());

	}
}
