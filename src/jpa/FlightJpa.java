package jpa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The persistent class for the flight database table.
 * 
 */

public class FlightJpa {
	private int id;
	private String airlinesName;
	private String arrivalTime;
	private String departureTime;
	private int destinationId;
	private String distance;
	private String flightNumber;
	private String landingTime ;
	private int originId;
	private int speed;
	private String takeoffTime;
	private String filename = "flight.txt";

	public FlightJpa() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirlinesName() {
		return this.airlinesName;
	}

	public void setAirlinesName(String airlinesName) {
		this.airlinesName = airlinesName;
	}

	public String getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public int getDestinationId() {
		return this.destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	public String getDistance() {
		return this.distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getLandingTime() {
		return this.landingTime;
	}

	public void setLandingTime(String landingTime) {
		this.landingTime = landingTime;
	}

	public int getOriginId() {
		return this.originId;
	}

	public void setOriginId(int originId) {
		this.originId = originId;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getTakeoffTime() {
		return this.takeoffTime;
	}

	public void setTakeoffTime(String takeoffTime) {
		this.takeoffTime = takeoffTime;
	}
	/*burada yeni kayit için otomatik id oluþturuyorum*/
	public int getLastId() throws FileNotFoundException {
		Scanner in = new Scanner(new FileInputStream(filename));
		String[] lastLine = null;
		int i = 0;
		while (in.hasNextLine()) {

			// System.out.println(sCurrentLine);
			lastLine = in.nextLine().split("#");
			i++;
		}
		in.close();
		if (i == 0) {
			return 0;
		} else {
			return Integer.parseInt(lastLine[0]);
		}

	}
	/*dosyaya ucus ekliyorum*/
	public boolean addFlight() throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		fw.write((getLastId() + 1) + "#" 
				+ getAirlinesName() + "#"
				+ getFlightNumber() + "#" 
				+ getDistance() + "#"
				+ getOriginId()+ "#" 
				+ getDestinationId() + "#" 
				+ getTakeoffTime() + "#"
				+ getDepartureTime() + "#" 
				+ getArrivalTime() + "#"
				+ getLandingTime() + "#" + getSpeed());
				fw.write("\r\n");
				fw.close();
				return true;
	}
	/*dosyaya ucus listesinin güncellenmiþ halini ekliyorum*/
	public boolean updateFlight() throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		fw.write((getId()) + "#" 
				+ getAirlinesName() + "#"
				+ getFlightNumber() + "#" 
				+ getDistance() + "#"
				+ getOriginId()+ "#" 
				+ getDestinationId() + "#" 
				+ getTakeoffTime() + "#"
				+ getDepartureTime() + "#" 
				+ getArrivalTime() + "#"
				+ getLandingTime() + "#" + getSpeed());
				fw.write("\r\n");
				fw.close();
				return true;
	}
	/*dosyadan tüm ucuslarý cekiyorum*/
	public List<FlightJpa> getList() throws FileNotFoundException {
		Scanner in = new Scanner(new FileInputStream(filename));
		List<FlightJpa> list = new ArrayList<FlightJpa>();
		String[] item = null;
		while (in.hasNextLine()) {
			item = in.nextLine().split("#");
			FlightJpa flight = new FlightJpa();
			flight.setId(Integer.parseInt(item[0]));
			flight.setAirlinesName(item[1]);
			flight.setFlightNumber(item[2]);
			flight.setDistance(item[3]);
			flight.setOriginId(Integer.parseInt(item[4]));
			flight.setDestinationId(Integer.parseInt(item[5]));
			flight.setTakeoffTime(item[6]);
			flight.setDepartureTime(item[7]);
			flight.setArrivalTime(item[8]);
			flight.setLandingTime(item[9]);
			flight.setSpeed(Integer.parseInt(item[10]));
		

			list.add(flight);
		}
		in.close();

		return list;

	}
	/*dosyayý temizliyorum*/
	public void allRecordDelete() throws IOException {
		FileWriter fw = new FileWriter(filename);
		fw.write("");
		fw.close();

	}
	/*Dosyadan kalkýs zamanýna göre ucus cekiyorum*/
	public List<FlightJpa> findByDepartureTime(String time)
			throws FileNotFoundException {
		Scanner in = new Scanner(new FileInputStream(filename));
		List<FlightJpa> list = new ArrayList<FlightJpa>();
		String[] item = null;
		while (in.hasNextLine()) {
			item = in.nextLine().split("#");
			if (item[7].equals(time)) {
				System.out.println(item[1]);
				FlightJpa flight = new FlightJpa();
				flight.setId(Integer.parseInt(item[0]));
				flight.setAirlinesName(item[1]);
				flight.setFlightNumber(item[2]);
				flight.setDistance(item[3]);
				flight.setOriginId(Integer.parseInt(item[4]));
				flight.setDestinationId(Integer.parseInt(item[5]));
				flight.setTakeoffTime(item[6]);
				flight.setDepartureTime(item[7]);
				flight.setArrivalTime(item[8]);
				flight.setLandingTime(item[9]);
				flight.setSpeed(Integer.parseInt(item[10]));

				list.add(flight);
			}
		}
		in.close();

		return list;

	}
}