package proje;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.TitleChanged;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.event.Subscription;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import jpa.CapitalJpa;
import jpa.FlightJpa;

public class AddFlight {

	private JFrame frame;
	private JTextField airlinesNameF;
	private JTextField flightNumberF;
	private Component browserViewComponent;
	private int hour;
	private int minute;
	private long miliseconds;
	private int takeOffTime;
	private int landingTime;
	/**
	 * Launch the application.
	 */
/*
 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFlight window = new AddFlight();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 */
	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public AddFlight() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() throws Exception {
		System.setProperty("jxbrowser.license.key", Constants.KEY);
		Engine engine = Engine.newInstance(
		        EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED).build());

		final Browser browser = engine.newBrowser();
		BrowserView browserView = BrowserView.newInstance(browser);
		CapitalJpa capitalJpa=new CapitalJpa();

		String[] comboBoxModelJpa = new String[capitalJpa.getList().size() + 1];
		int i = 1;
		comboBoxModelJpa[0] = "Seçiniz";
		for (CapitalJpa capital : capitalJpa.getList()) {
			comboBoxModelJpa[i] = capital.getName();
			i++;
		}
		frame = new JFrame("Capital Add Page");
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				browserViewComponent.setBounds(230, 11,
						385 + (frame.getWidth() - 670),
						350 + (frame.getHeight() - 415));
			}
		});
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAirlinesName = new JLabel("Airlines Name");
		lblAirlinesName.setBounds(10, 11, 89, 14);
		frame.getContentPane().add(lblAirlinesName);

		JLabel lblFlightNumber = new JLabel("Flight Number");
		lblFlightNumber.setBounds(10, 36, 89, 14);
		frame.getContentPane().add(lblFlightNumber);

		JLabel lblOrigin = new JLabel("Origin");
		lblOrigin.setBounds(10, 154, 71, 14);
		frame.getContentPane().add(lblOrigin);

		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setBounds(10, 179, 71, 14);
		frame.getContentPane().add(lblDestination);

		JLabel distanceKmLabel = new JLabel("Distance Km");
		distanceKmLabel.setBounds(10, 216, 89, 14);
		frame.getContentPane().add(distanceKmLabel);

		JLabel distanceValL = new JLabel("0");
		distanceValL.setHorizontalAlignment(SwingConstants.CENTER);
		distanceValL.setBounds(117, 211, 45, 19);
		frame.getContentPane().add(distanceValL);
		
		airlinesNameF = new JTextField();
		airlinesNameF.setBounds(117, 8, 89, 20);
		frame.getContentPane().add(airlinesNameF);
		airlinesNameF.setColumns(10);

		flightNumberF = new JTextField();
		flightNumberF.setBounds(117, 33, 89, 20);
		frame.getContentPane().add(flightNumberF);
		flightNumberF.setColumns(10);
		
		JComboBox speedJ = new JComboBox();
		
		speedJ.setModel(new DefaultComboBoxModel(new String[] {"350", "500", "600", "900","1200","1500","1800","2100","2400","3000"}));
		speedJ.setBounds(117, 64, 89, 19);
		frame.getContentPane().add(speedJ);
		
		JComboBox originCb = new JComboBox();
		originCb.setToolTipText("");
		originCb.setBounds(117, 151, 89, 20);

		frame.getContentPane().add(originCb);

		JComboBox destinationCb = new JComboBox();
		destinationCb.setBounds(117, 176, 89, 20);

		frame.getContentPane().add(destinationCb);

		JButton saveB = new JButton("Save");
		saveB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		saveB.setBounds(10, 412, 89, 23);
		frame.getContentPane().add(saveB);
		frame.addWindowListener(new WindowAdapter() {
			 
			@Override
			public void windowOpened(WindowEvent arg0) {
				destinationCb.setModel(new DefaultComboBoxModel(
						comboBoxModelJpa));
				originCb.setModel(new DefaultComboBoxModel(comboBoxModelJpa));

			}
			
		});
		frame.setVisible(true);
		browserViewComponent = frame.getContentPane().add(browserView);

		
		
		JLabel lblKm = new JLabel("KM");
		lblKm.setHorizontalAlignment(SwingConstants.CENTER);
		lblKm.setBounds(172, 210, 36, 20);
		frame.getContentPane().add(lblKm);
		
		JLabel lblAirplaneSpeed = new JLabel("Airplane Speed");
		lblAirplaneSpeed.setBounds(10, 66, 89, 14);
		frame.getContentPane().add(lblAirplaneSpeed);
		
		JLabel flightTimeL = new JLabel("Flight Time");
		flightTimeL.setBounds(10, 241, 71, 14);
		frame.getContentPane().add(flightTimeL);
		
		JLabel timeToHourL = new JLabel("0");
		timeToHourL.setHorizontalAlignment(SwingConstants.CENTER);
		timeToHourL.setBounds(127, 241, 81, 14);
		frame.getContentPane().add(timeToHourL);
		
		JLabel lblDepartureTime = new JLabel("Departure Time");
		lblDepartureTime.setBounds(10, 274, 81, 14);
		frame.getContentPane().add(lblDepartureTime);
		
		JSpinner departureDateSpinner = new JSpinner();
		
		departureDateSpinner.setModel(new SpinnerDateModel(new Date(1432933200000L), new Date(1432933200000L), null, Calendar.YEAR));
		departureDateSpinner.setBounds(51, 299, 132, 20);
		frame.getContentPane().add(departureDateSpinner);
		
		JLabel lblArrivalTime = new JLabel("Arrival Time");
		lblArrivalTime.setBounds(10, 330, 89, 14);
		frame.getContentPane().add(lblArrivalTime);
		
		JSpinner arrivalTimeS = new JSpinner();
		arrivalTimeS.setEnabled(false);
		arrivalTimeS.setModel(new SpinnerDateModel(new Date(1432933200000L), null, null, Calendar.DAY_OF_YEAR));
		arrivalTimeS.setBounds(52, 355, 131, 20);
		frame.getContentPane().add(arrivalTimeS);
		
		JLabel takeOffTimeL = new JLabel("Take Off Time");
		takeOffTimeL.setBounds(10, 99, 98, 16);
		frame.getContentPane().add(takeOffTimeL);
		
		JLabel landingTimeL = new JLabel("Landing Time");
		landingTimeL.setBounds(10, 126, 98, 14);
		frame.getContentPane().add(landingTimeL);
		
		JSpinner takeOffTimeS = new JSpinner();
		takeOffTimeS.setModel(new SpinnerNumberModel(new Long(0), null, null, new Long(1)));
		takeOffTimeS.setBounds(117, 97, 29, 20);
		frame.getContentPane().add(takeOffTimeS);
		
		JSpinner landingTimeS = new JSpinner();
		landingTimeS.setModel(new SpinnerNumberModel(new Long(0), null, null, new Long(1)));
		landingTimeS.setBounds(117, 120, 29, 20);
		frame.getContentPane().add(landingTimeS);
		/*departureDateSpinner uzerinde deðiþiklik yaptýgýmda ucus suresini anlýk olarak güncelliyorum ekranda*/
		departureDateSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				long timestamp=0;
				String departureTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(departureDateSpinner.getValue());
				try {
					 timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departureTime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				timestamp+=TimeUnit.MINUTES.toMillis(getMinute());
				timestamp+=TimeUnit.HOURS.toMillis(getHour());
				timestamp+=TimeUnit.MINUTES.toMillis((long) takeOffTimeS.getValue());
				timestamp+=TimeUnit.MINUTES.toMillis((long) landingTimeS.getValue());
				setMiliseconds(timestamp);
				arrivalTimeS.setModel(new SpinnerDateModel(new Date(getMiliseconds()), null, null, Calendar.DAY_OF_YEAR));
				 
			}
		});
		
		browserViewComponent.setBounds(275, 11, 350, 350);
		frame.setVisible(true);
		originCb.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0)  {
	        	CapitalJpa capital=new CapitalJpa();
				System.out.println(originCb.getSelectedItem().toString());
			 	CapitalJpa capitalQueryOrigin = null;
				try {
					capitalQueryOrigin = capital.findByName(originCb.getSelectedItem().toString());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				browser.mainFrame().get().executeJavaScript("origin= new google.maps.LatLng("+ capitalQueryOrigin.getLat() + ","+ capitalQueryOrigin.getLng()+ ");");
				browser.mainFrame().get().executeJavaScript("calculateDistances();");
				Subscription subscription = browser.on(TitleChanged.class, event -> {
					
					 distanceValL.setText(event.title());
		                NumberFormat formatter = new DecimalFormat("#0.00");
						Double flighTime=Double.parseDouble(distanceValL.getText())/Double.parseDouble(speedJ.getSelectedItem().toString());
						String[] timeToText=formatter.format(flighTime).split(",");
						String minute=formatter.format(Integer.parseInt(timeToText[1])*0.6);
						setHour(Integer.parseInt(timeToText[0]));
						setMinute(Integer.parseInt(minute.substring(0,2).replace(",","")));
						timeToHourL.setText(getHour()+" Saat "+getMinute()+" Dakika");
						long timestamp=0;
						String departureTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(departureDateSpinner.getValue());
						try {
							 timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departureTime).getTime();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						timestamp+=TimeUnit.MINUTES.toMillis(getMinute());
						timestamp+=TimeUnit.HOURS.toMillis(getHour());
						timestamp+=TimeUnit.MINUTES.toMillis((long) takeOffTimeS.getValue());
						timestamp+=TimeUnit.MINUTES.toMillis((long) landingTimeS.getValue());
						setMiliseconds(timestamp);
						arrivalTimeS.setModel(new SpinnerDateModel(new Date(getMiliseconds()), null, null, Calendar.DAY_OF_YEAR));
						
				});
			
				   
			}
		});

		destinationCb.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
	        	CapitalJpa capital=new CapitalJpa();
				System.out.println(originCb.getSelectedItem().toString());
			 	CapitalJpa capitalQueryDestination = null;
				try {
					capitalQueryDestination = capital.findByName(destinationCb.getSelectedItem().toString());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				browser.mainFrame().get().executeJavaScript("destination= new google.maps.LatLng("+ capitalQueryDestination.getLat()+ ","+ capitalQueryDestination.getLng() + ");");
				browser.mainFrame().get().executeJavaScript("calculateDistances();");
				/*burada iki havaalaný arasýndaki mesafi bulup ucus süresini hesaplýyorum*/
				Subscription subscription = browser.on(TitleChanged.class, event -> {
					
	                distanceValL.setText(event.title());
	                NumberFormat formatter = new DecimalFormat("#0.00");
					Double flighTime=Double.parseDouble(distanceValL.getText())/Double.parseDouble(speedJ.getSelectedItem().toString());
					String[] timeToText=formatter.format(flighTime).split(",");
					String minute=formatter.format(Integer.parseInt(timeToText[1])*0.6);
					setHour(Integer.parseInt(timeToText[0]));
					setMinute(Integer.parseInt(minute.substring(0,2).replace(",","")));
					timeToHourL.setText(getHour()+" Saat "+getMinute()+" Dakika");
				});
 
					long timestamp=0;
					String departureTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(departureDateSpinner.getValue());
					try {
						 timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departureTime).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					timestamp+=TimeUnit.MINUTES.toMillis(getMinute());
					timestamp+=TimeUnit.HOURS.toMillis(getHour());
					timestamp+=TimeUnit.MINUTES.toMillis((long) takeOffTimeS.getValue());
					timestamp+=TimeUnit.MINUTES.toMillis((long) landingTimeS.getValue());
					setMiliseconds(timestamp);
					arrivalTimeS.setModel(new SpinnerDateModel(new Date(getMiliseconds()), null, null, Calendar.DAY_OF_YEAR));

			}
		});
		/*burada compenentdeki degiþikliðe göre iki havaalaný arasýndaki mesafi bulup ucus süresini hesaplýyorum*/
		speedJ.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				NumberFormat formatter = new DecimalFormat("#0.00");
				Double flighTime=Double.parseDouble(distanceValL.getText())/Double.parseDouble(speedJ.getSelectedItem().toString());
				String[] timeToText=formatter.format(flighTime).split(",");
				String minute=formatter.format(Integer.parseInt(timeToText[1])*0.6);
				setHour(Integer.parseInt(timeToText[0]));
				setMinute(Integer.parseInt(minute.substring(0,2).replace(",","")));
				timeToHourL.setText(getHour()+" Saat "+getMinute()+" Dakika");
				long timestamp=0;
				String departureTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(departureDateSpinner.getValue());
				try {
					 timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departureTime).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				timestamp+=TimeUnit.MINUTES.toMillis(getMinute());
				timestamp+=TimeUnit.HOURS.toMillis(getHour());
				timestamp+=TimeUnit.MINUTES.toMillis((long) takeOffTimeS.getValue());
				timestamp+=TimeUnit.MINUTES.toMillis((long) landingTimeS.getValue());
				setMiliseconds(timestamp);
				arrivalTimeS.setModel(new SpinnerDateModel(new Date(getMiliseconds()), null, null, Calendar.DAY_OF_YEAR));
			}
		});
		/*ucus bilgilerini dosyaya kayit ediyorum*/
		saveB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
				CapitalJpa capital=new CapitalJpa();
				CapitalJpa capitalQueryDestination = capital.findByName(destinationCb.getSelectedItem().toString());
				CapitalJpa capitalQueryOrigin = capital.findByName(originCb.getSelectedItem().toString());
				String departureTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(departureDateSpinner.getValue());
				String arrivalTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(arrivalTimeS.getValue());
				FlightJpa flight = new FlightJpa();
				flight.setAirlinesName(airlinesNameF.getText());
				flight.setFlightNumber(flightNumberF.getText());
				flight.setOriginId(capitalQueryOrigin.getId());
				flight.setDestinationId(capitalQueryDestination.getId());
				flight.setDistance(distanceValL.getText());
				flight.setDepartureTime(departureTime);
				flight.setArrivalTime(arrivalTime);
				flight.setTakeoffTime(takeOffTimeS.getValue().toString());
				flight.setLandingTime(landingTimeS.getValue().toString());
				flight.setSpeed(Integer.parseInt(speedJ.getSelectedItem().toString()));
				flight.addFlight();
				System.out.println("Flight was added");
				   JOptionPane.showMessageDialog(null,	"Uçuþ Baþarýyla kayýt edilmiþtir");
				}
				catch(Exception e){
					System.out.println("Sorun oluþtu."+e.getMessage());
					
				}
			}
		});
		
		File mapHtml = new File("addflightmap.html");
		System.out.println(mapHtml.getAbsolutePath());
		browser.navigation().loadUrl(mapHtml.getAbsolutePath());

	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public long getMiliseconds() {
		return miliseconds;
	}

	public void setMiliseconds(long miliseconds) {
		this.miliseconds = miliseconds;
	}
	public int getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(int takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public int getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(int landingTime) {
		this.landingTime = landingTime;
	}
}
