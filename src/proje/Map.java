package proje;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.navigation.event.FrameLoadFinished;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import jpa.CapitalJpa;
import jpa.FlightJpa;


public class Map {

	private JFrame frmFlightMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Map window = new Map();
					window.frmFlightMap.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Map() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.setProperty("jxbrowser.license.key", Constants.KEY);
		Engine engine = Engine.newInstance(
		        EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED).build());

		final Browser browser = engine.newBrowser();
		BrowserView browserView = BrowserView.newInstance(browser);
		
		Frame frame = browser.mainFrame().get();
		frmFlightMap = new JFrame();
		frmFlightMap.setTitle("Flight Map");
		frmFlightMap.setBounds(100, 100, 600, 600);
		frmFlightMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFlightMap.setVisible(true);
		JMenuBar menuBar = new JMenuBar();
		frmFlightMap.setJMenuBar(menuBar);
		
		JMenu mapM = new JMenu("Map");
		menuBar.add(mapM);
				
		JMenuItem mapClearMi = new JMenuItem("Clear Map");
		 mapClearMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		frame.executeJavaScript("deleteOverlays();");
            		frame.executeJavaScript("deleteOverlaysLines()"); 
            }
        });

		mapM.add(mapClearMi);
		
		JMenuItem reloadCapitalMi = new JMenuItem("Reload Capital");
		
		/*Burada kayitli havaalanlar�n�n haritada g�sterilmesini sa�l�yorum
		 * */
		reloadCapitalMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CapitalJpa capitalList=new CapitalJpa();
            		try {
						for (CapitalJpa capitalJpa : capitalList.getList())
						  {
						
							frame
						  .executeJavaScript("var myLatlng = new google.maps.LatLng("
						  +capitalJpa.getLat()+","+capitalJpa.getLng()+");\n" +
						  "var marker"+capitalJpa.getId()+"= new google.maps.Marker({\n" +
						  "    position: myLatlng,\n" + "    map: map,\n" 
						  + "	 icon: 'icon/airport_runway.png',"
						  +  "    title: '"+capitalJpa.getName()+"'\n" + "});"); 
						  
						  
						  
							frame.executeJavaScript(" var infoWindowContent"+capitalJpa.getId()+" = "
						  		+ "'<div class=\"info_content\"> "
						  		 
						  		+ "<p>"+capitalJpa.getName()+"</p></div>';");
							frame.executeJavaScript(" var infoWindow"+capitalJpa.getId()+"  = new google.maps.InfoWindow({ content: infoWindowContent"+capitalJpa.getId()+" });");
							frame.executeJavaScript("google.maps.event.addListener(marker"+capitalJpa.getId()+", 'click', function() { "
						  		+ "infoWindow"+capitalJpa.getId()+".open(map, marker"+capitalJpa.getId()+"); "
						  				+ "});");
						 
							frame.executeJavaScript("markers.push(marker"+capitalJpa.getId()+");");
						  }
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				
              }
        });

		mapM.add(reloadCapitalMi);
		
		/*Burada kayit ucuslar�n rotas�n� g�steriyor*/
		JMenuItem drawAllRouteMi = new JMenuItem("Draw All Route");
		drawAllRouteMi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 FlightJpa flightJpa=new FlightJpa();
						try {
							for (FlightJpa flight : flightJpa.getList()) {
								CapitalJpa origin =new CapitalJpa().findById(flight.getOriginId());
								CapitalJpa destination = new CapitalJpa().findById(flight.getDestinationId());
								frame.executeJavaScript("var myLatlng = new google.maps.LatLng("
										+ origin.getLat()
										+ ","
										+ origin.getLng()
										+ ");\n"
										+ "var marker = new google.maps.Marker({\n"
										+ "    position: myLatlng,\n"
										+ "    map: map,\n"
										+ "	 icon: 'icon/direction_up.png',"
										+ "    title: '" + origin.getName() + "'\n" + "});"
												+ "markers.push(marker);");
								frame.executeJavaScript("var myLatlng = new google.maps.LatLng("
										+ destination.getLat()
										+ ","
										+ destination.getLng()
										+ ");\n"
										+ "var marker = new google.maps.Marker({\n"
										+ "    position: myLatlng,\n"
										+ "    map: map,\n"
										+ "	 icon: 'icon/direction_down.png',"
										+ "    title: '" + origin.getName() + "'\n" + "});"
												+ "markers.push(marker);");
								frame.executeJavaScript("var line = new google.maps.Polyline({"
										+ "path: ["
										+ "new google.maps.LatLng("
										+ origin.getLat()
										+ ","
										+ origin.getLng()
										+ "), "
										+ "new google.maps.LatLng("
										+ destination.getLat()
										+ ","
										+ destination.getLng()
										+ ")"
										+ "],"
										+ "            strokeColor:  getRandomColor(),"
										+ "            strokeOpacity: 1.0,"
										+ "            geodesic: true,"
										+ "            map: map"
										+ "        });"
										+ "line.setMap(map);");
								frame.executeJavaScript("lines.push(line);");
							
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 
			}
		});
		mapM.add(drawAllRouteMi);
		JMenu capitalMenu = new JMenu("Capital");
		menuBar.add(capitalMenu);

		JMenuItem capitalAddMenuItem = new JMenuItem("Capital Add");
		capitalAddMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//frmFlightMap.dispose();
				
				new AddCapital();
			}
		});

		capitalMenu.add(capitalAddMenuItem);

		JMenuItem capitalListMenuItem = new JMenuItem("Capital List");
		capitalListMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			//	frmFlightMap.dispose();
				try {
					new CapitalList();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		capitalMenu.add(capitalListMenuItem);

		JMenu flightMenu = new JMenu("Flight");
		menuBar.add(flightMenu);

		JMenuItem flightAddMenuItem = new JMenuItem("Flight Add");
		flightAddMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//frmFlightMap.dispose();
				try {
					new AddFlight();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		flightMenu.add(flightAddMenuItem);

		JMenuItem flightListMenuItem = new JMenuItem("Flight List");
		flightListMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//frmFlightMap.dispose();
				try {
					new FlightList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		flightMenu.add(flightListMenuItem);
		
		JMenu systemStartM = new JMenu("System");
		menuBar.add(systemStartM);
		
		JMenuItem startMi = new JMenuItem("Start");
		
		systemStartM.add(startMi);
		
		JMenuItem mn�tmPause = new JMenuItem("Pause");
		mn�tmPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					SystemThread.state=2;
		 
			}
		});
		systemStartM.add(mn�tmPause);
		
		JMenuItem mn�tmResume = new JMenuItem("Resume");
		mn�tmResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SystemThread.state=1;
				 
		}
	});
		systemStartM.add(mn�tmResume);

		JMenu systemTime = new JMenu("SystemTime");
		menuBar.add(systemTime);
		
		//burada sistemi ba�lat�yor ayn� zamanda rapor dosyas�n�n i�ini siliyor
		startMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	FileWriter fw;
				try {
					fw = new FileWriter("report.txt");
					fw.write("");
        		fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
            	SystemThread systemthread=new SystemThread(systemTime,browser);
            	Thread st=new Thread(systemthread);
            	st.start();
       
			}
		});
		/*Burada program ilk a��l��ta kayitli havalanlar�n� listeliyor*/
		browser.navigation().on(FrameLoadFinished.class, event -> {
			CapitalJpa capitalList=new CapitalJpa();
			  
			  try {
				for (CapitalJpa capitalJpa : capitalList.getList())
				  {
					
				  event.frame().executeJavaScript("var myLatlng = new google.maps.LatLng("
				  +capitalJpa.getLat()+","+capitalJpa.getLng()+");\n" +
				  "var marker"+capitalJpa.getId()+"= new google.maps.Marker({\n" +
				  "    position: myLatlng,\n" + "    map: map,\n" 
				  + "	 icon: 'icon/airport_runway.png',"
				  +  "    title: '"+capitalJpa.getName()+"'\n" + "});"); 
				  
				  
				  
				  event.frame().executeJavaScript(" var infoWindowContent"+capitalJpa.getId()+" = "
				  		+ "'<div class=\"info_content\"> "
				  		 
				  		+ "<p>"+capitalJpa.getName()+"</p></div>';");
				  event.frame().executeJavaScript(" var infoWindow"+capitalJpa.getId()+"  = new google.maps.InfoWindow({ content: infoWindowContent"+capitalJpa.getId()+" });");
				  event.frame().executeJavaScript("google.maps.event.addListener(marker"+capitalJpa.getId()+", 'click', function() { "
				  		+ "infoWindow"+capitalJpa.getId()+".open(map, marker"+capitalJpa.getId()+"); "
				  				+ "});");
				 
				  event.frame().executeJavaScript("markers.push(marker"+capitalJpa.getId()+");");
				  }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		});
		
		frmFlightMap.getContentPane().add(browserView);
		File mapHtml = new File("flightmap.html");
		System.out.println(mapHtml.getAbsolutePath());
		browser.navigation().loadUrl(mapHtml.getAbsolutePath());
	}

}
