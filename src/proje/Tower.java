package proje;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import jpa.CapitalJpa;
import jpa.FlightJpa;

import com.teamdev.jxbrowser.chromium.Browser;

public class Tower implements Runnable {

	private Browser browser;
	private FlightJpa flight;

	public Tower(Browser browser, FlightJpa flight) {
		this.browser = browser;

		setFlight(flight);

	}

	//isDelayDeparture!=5 uçuþ iptal;
	//isDelayDeparture=2 kalkýsý belli bir süre erteliyor
	//isDelayArrive=3 kule iniþ izni vermiyor bekliyor belli bir süre
	
	@Override
	public void run() {
		int isDelayDeparture=0,isDelayArrive=0,delayTime=0;
		String status="";
		Random random = new Random();
		synchronized (random) {
			
		isDelayDeparture = random.nextInt(6) + 1;
		isDelayArrive=random.nextInt(6) + 1;
		}
		
		int i = 0;
		CapitalJpa capital = new CapitalJpa();
		CapitalJpa capitalQueryDestination = null;
		CapitalJpa capitalQueryOrigin = null;
		try {
			capitalQueryDestination = capital.findById(flight
					.getDestinationId());
			capitalQueryOrigin = capital.findById(flight.getOriginId());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(isDelayDeparture!=5){
		
		 
		String directionX;
		String directionY;

		Double diffrenceLat = Double.parseDouble(capitalQueryDestination
				.getLat()) - Double.parseDouble(capitalQueryOrigin.getLat());
		Double diffrenceLng = Double.parseDouble(capitalQueryDestination
				.getLng()) - Double.parseDouble(capitalQueryOrigin.getLng());

		double originLat = Double.parseDouble(capitalQueryOrigin.getLat());
		double originLng = Double.parseDouble(capitalQueryOrigin.getLng());
		double destinationLat = Double.parseDouble(capitalQueryDestination
				.getLat());
		double destinationLng = Double.parseDouble(capitalQueryDestination
				.getLng());
		int speedOfdistanceRatio = (int) (Math.round(Double
				.parseDouble(getFlight().getDistance()) * 60) / (getFlight()
				.getSpeed()));

		if (diffrenceLng < 0) {

			directionX = "left";
		} else {
			directionX = "right";
		}
		if (diffrenceLat < 0) {

			directionY = "down";
		} else {
			directionY = "up";
		}

	/*burada aradaki mesafeyi zamana göre adýmlara böldüm.Burada ucak adým adým gidiyor yani haritada bir gözüküp kaybolarak ilerliyor
	 * haritada ilerleme landing time ve take off time bekleme ve kule inis ve kalkýs erteleme gibi iþlemlerde var
	 * */
		while (i < speedOfdistanceRatio) {

			int pointDistance = i * Integer.parseInt(getFlight().getDistance())
					/ speedOfdistanceRatio * 1000;

			browser.executeJavaScript("var origin" + getFlight().getId()
					+ "= new google.maps.LatLng(" + originLat + "," + originLng
					+ ");\n");
			browser.executeJavaScript("var destination" + getFlight().getId()
					+ "= new google.maps.LatLng(" + destinationLat + ","
					+ destinationLng + ");\n");
			browser.executeJavaScript("var angle" + getFlight().getId()
					+ " = google.maps.geometry.spherical.computeHeading(origin"
					+ getFlight().getId() + ",destination"
					+ getFlight().getId() + ")");
			browser.executeJavaScript("var newLatLng"
					+ getFlight().getId()
					+ " = new google.maps.geometry.spherical.computeOffset(origin"
					+ getFlight().getId() + "," + pointDistance + ", angle"
					+ getFlight().getId() + ");");

			browser.executeJavaScript("var marker" + getFlight().getId()
					+ "= new google.maps.Marker({\n" + "  position: newLatLng"
					+ getFlight().getId() + ",\n" + "" + "	 map: map,\n"
					+ "	 icon: 'icon/airport_" + directionY + "_" + directionX
					+ ".png'," + "  title: '"+getFlight().getAirlinesName()+"'\n" + "});");
			browser.executeJavaScript("markers.push(marker"
					+ getFlight().getId() + ");");

			browser.executeJavaScript(" var infoWindowContent"
					+ getFlight().getId() + " = "
					+ "'<div class=\"info_content\"> "

					+ "<p>Airlines Name: " + getFlight().getAirlinesName()
					+ "</br>" + "Flight Number: " + getFlight().getId()
					
					+ "</br>" + "Departure City: "	+ capitalQueryOrigin.getName() + "</br>" 
					+ "Arrival City: "
					+ capitalQueryDestination.getName() + "</br>" 
					+ "Speed: "	+ getFlight().getSpeed() + "</br>" 
					+ "Take off Time: "+ getFlight().getTakeoffTime() + " min </br>"
					+ "Departure Time: "+ getFlight().getDepartureTime() + "</br>"
					+ "Arrival Time: " + getFlight().getArrivalTime() + "</br>"
					+ "Landing Time: "+ getFlight().getLandingTime() + " min </br>"
					+ "</p></div>';");
			browser.executeJavaScript(" var infoWindow"
					+ getFlight().getId()
					+ "  = new google.maps.InfoWindow({ content: infoWindowContent"
					+ getFlight().getId() + " });");
			browser.executeJavaScript("google.maps.event.addListener(marker"
					+ getFlight().getId() + ", 'click', function() { "
					+ "infoWindow" + getFlight().getId() + ".open(map, marker"
					+ getFlight().getId() + "); " + "});");
			synchronized (browser) {

				while (SystemThread.state == 2) {

					try {
						browser.wait(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		
	 	
			
			if (i == 0) {
				//Kule kalkýþý rasgele bir süre erteliyor.
				if (isDelayDeparture == 2) {
					try {
					delayTime=random.nextInt(10) + 1;
						System.out.print("Delay By Kule when Airplane departure");
						Thread.sleep(delayTime * 1000);
						 
						status="Departure Delayed "+delayTime+" min";
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
				
				 //Ucak kalkýsa hazýrlanma süresi kadar bekliyor yani takeofftime için bekletiliyor
				try {
					System.out.print("in Take off Time");
					Thread.sleep(Integer.parseInt(flight.getTakeoffTime()) * 1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				} 
			}
		 
			//ucak hedef havaalanýna ulaþtýgýnda iniþ süresi için bekletiliyor.Landing time
			if (i == speedOfdistanceRatio - 1) {
				
				if (isDelayArrive == 3) {
					try {
						
					delayTime=random.nextInt(10) + 1;
					System.out.print("Delay By Kule when Airplane arrive");
						Thread.sleep(delayTime * 1000);
						 
						status="Arrive Delayed "+delayTime+" min ";
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			 	try {
					System.out.print("in Landing Time");
					Thread.sleep(Integer.parseInt(flight.getLandingTime()) * 1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				} 
			}
			try {
				System.out.println("marker" + getFlight().getId()
						+ ".setMap(null);");

				Thread.sleep(1000);
				browser.executeJavaScript("marker" + getFlight().getId()
						+ ".setMap(null);");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			i++;

			}
		}
	
		synchronized (flight) {
			if(i==0){
				status="Flight Canceled";	
				
			}
	/*Burad her ucus bilgilerini report dosyasýna yazdýrýyorum*/
		FileWriter fw = null;
		try {
			fw = new FileWriter("report.txt",true);
			fw.write("Airlines Name: "+flight.getAirlinesName()+" ,");
			fw.write("Flight Number: "+flight.getFlightNumber()+" ,"); 
			fw.write("Speed: " +flight.getSpeed()+" ,");
			fw.write("Departure: "+capitalQueryDestination.getName()+" ,");
			fw.write("Arrive : "+capitalQueryOrigin.getName()+" ,");
			fw.write("Take Off Time : "+flight.getTakeoffTime()+" ,");
			fw.write("Departure Time : "+flight.getDepartureTime()+" ,");
			fw.write("Arrival Time : "+flight.getArrivalTime()+" ,");
			fw.write("Landin Time : "+flight.getLandingTime()+" ,");
			fw.write("Status : "+status); 
			fw.write("\r\n");
			fw.write("\r\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	public FlightJpa getFlight() {
		return flight;
	}

	public void setFlight(FlightJpa flight) {
		this.flight = flight;
	}

}
