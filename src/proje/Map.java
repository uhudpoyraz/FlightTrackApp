package proje;

import jpa.CapitalJpa;
import jpa.FlightJpa;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


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
		final Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
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
                browser.executeJavaScript("deleteOverlays();");
               browser.executeJavaScript("deleteOverlaysLines()"); 
            }
        });

		mapM.add(mapClearMi);
		
		JMenuItem reloadCapitalMi = new JMenuItem("Reload Capital");
		
		/*Burada kayitli havaalanlarýnýn haritada gösterilmesini saðlýyorum
		 * */
		reloadCapitalMi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CapitalJpa capitalList=new CapitalJpa();
					try {
						for (CapitalJpa capitalJpa : capitalList.getList())
						  {
						
						  browser
						  .executeJavaScript("var myLatlng = new google.maps.LatLng("
						  +capitalJpa.getLat()+","+capitalJpa.getLng()+");\n" +
						  "var marker"+capitalJpa.getId()+"= new google.maps.Marker({\n" +
						  "    position: myLatlng,\n" + "    map: map,\n" 
						  + "	 icon: 'icon/airport_runway.png',"
						  +  "    title: '"+capitalJpa.getName()+"'\n" + "});"); 
						  
						  
						  
						  browser.executeJavaScript(" var infoWindowContent"+capitalJpa.getId()+" = "
						  		+ "'<div class=\"info_content\"> "
						  		 
						  		+ "<p>"+capitalJpa.getName()+"</p></div>';");
						  browser.executeJavaScript(" var infoWindow"+capitalJpa.getId()+"  = new google.maps.InfoWindow({ content: infoWindowContent"+capitalJpa.getId()+" });");
						  browser.executeJavaScript("google.maps.event.addListener(marker"+capitalJpa.getId()+", 'click', function() { "
						  		+ "infoWindow"+capitalJpa.getId()+".open(map, marker"+capitalJpa.getId()+"); "
						  				+ "});");
						 
						  browser.executeJavaScript("markers.push(marker"+capitalJpa.getId()+");");
						  }
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
			
            }
        });

		mapM.add(reloadCapitalMi);
		
		/*Burada kayit ucuslarýn rotasýný gösteriyor*/
		JMenuItem drawAllRouteMi = new JMenuItem("Draw All Route");
		drawAllRouteMi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 FlightJpa flightJpa=new FlightJpa();
				 
				
				try {
					for (FlightJpa flight : flightJpa.getList()) {
						CapitalJpa origin =new CapitalJpa().findById(flight.getOriginId());
						CapitalJpa destination = new CapitalJpa().findById(flight.getDestinationId());
						browser.executeJavaScript("var myLatlng = new google.maps.LatLng("
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
						browser.executeJavaScript("var myLatlng = new google.maps.LatLng("
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
						browser.executeJavaScript("var line = new google.maps.Polyline({"
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
						browser.executeJavaScript("lines.push(line);");
					
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
		
		JMenuItem mnýtmPause = new JMenuItem("Pause");
		mnýtmPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					SystemThread.state=2;
		 
			}
		});
		systemStartM.add(mnýtmPause);
		
		JMenuItem mnýtmResume = new JMenuItem("Resume");
		mnýtmResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SystemThread.state=1;
				 
		}
	});
		systemStartM.add(mnýtmResume);

		JMenu systemTime = new JMenu("SystemTime");
		menuBar.add(systemTime);
		
		//burada sistemi baþlatýyor ayný zamanda rapor dosyasýnýn içini siliyor
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
		/*Burada program ilk açýlýþta kayitli havalanlarýný listeliyor*/
		browser.addLoadListener(new LoadAdapter() {

			public void onFinishLoadingFrame(FinishLoadingEvent  event) {
				 	CapitalJpa capitalList=new CapitalJpa();
				  
				  try {
					for (CapitalJpa capitalJpa : capitalList.getList())
					  {
					
					  browser
					  .executeJavaScript("var myLatlng = new google.maps.LatLng("
					  +capitalJpa.getLat()+","+capitalJpa.getLng()+");\n" +
					  "var marker"+capitalJpa.getId()+"= new google.maps.Marker({\n" +
					  "    position: myLatlng,\n" + "    map: map,\n" 
					  + "	 icon: 'icon/airport_runway.png',"
					  +  "    title: '"+capitalJpa.getName()+"'\n" + "});"); 
					  
					  
					  
					  browser.executeJavaScript(" var infoWindowContent"+capitalJpa.getId()+" = "
					  		+ "'<div class=\"info_content\"> "
					  		 
					  		+ "<p>"+capitalJpa.getName()+"</p></div>';");
					  browser.executeJavaScript(" var infoWindow"+capitalJpa.getId()+"  = new google.maps.InfoWindow({ content: infoWindowContent"+capitalJpa.getId()+" });");
					  browser.executeJavaScript("google.maps.event.addListener(marker"+capitalJpa.getId()+", 'click', function() { "
					  		+ "infoWindow"+capitalJpa.getId()+".open(map, marker"+capitalJpa.getId()+"); "
					  				+ "});");
					 
					  browser.executeJavaScript("markers.push(marker"+capitalJpa.getId()+");");
					  }
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
 		 	  
			}

		});  
		frmFlightMap.getContentPane().add(browserView);
		File mapHtml = new File("flightmap.html");
		System.out.println(mapHtml.getAbsolutePath());
		browser.loadURL(mapHtml.getAbsolutePath());
	}

}
