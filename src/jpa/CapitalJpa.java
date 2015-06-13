package jpa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The persistent class for the capital database table.
 * 
 */

public class CapitalJpa {
	
	private int id;
	private String lat;
	private String lng;
	private String name;
	private String filename="capital.txt";
	public CapitalJpa() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return this.lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/*burada yeni kayit için otomatik id oluþturuyorum*/
	public int getLastId() throws FileNotFoundException{
		Scanner in=new Scanner(new FileInputStream(filename));
		String[] lastLine = null;
		int i=0;
		  while (in.hasNextLine()) 
		    {
			  
		        //System.out.println(sCurrentLine);
		        lastLine = in.nextLine().split("-");
		    i++;
		    }
		  in.close();
		  if(i==0){
			  return 0;
		  }else{
			 return Integer.parseInt(lastLine[0]); 
		  }
		
	}
	
	/*Hava alanýný txt dosyasýna kayit ediyor.*/
	public boolean addCapital() throws IOException{
		FileWriter fw=new FileWriter(filename,true);
		fw.write((getLastId()+1)+"-"+getName()+"-"+getLat()+"-"+getLng());
		fw.write("\r\n");
		fw.close();
		return true;
	}
	
	/*Hava alanýný txt dosyasýna guncellenmiþ halini kayit ediyor.*/
	public boolean updateCapital() throws IOException{
		FileWriter fw=new FileWriter(filename,true);
		fw.write((getId())+"-"+getName()+"-"+getLat()+"-"+getLng());
		fw.write("\r\n");
		fw.close();
		return true;
	}
	
	/*Havaalanlarýnýn listesini çekiyorum*/
	public List<CapitalJpa> getList() throws FileNotFoundException{
		Scanner in=new Scanner(new FileInputStream(filename));
		List<CapitalJpa>list=new ArrayList<CapitalJpa>();
		String[] item=null;
			while(in.hasNextLine()){
				item=in.nextLine().split("-");
				CapitalJpa capital=new CapitalJpa();
					capital.setId(Integer.parseInt(item[0]));
					capital.setName(item[1]);
					capital.setLat(item[2]);
					capital.setLng(item[3]);

					 System.out.println(capital.getId());
					System.out.println(capital.getName());
					System.out.println(capital.getLat());
					System.out.println(capital.getLng());
				 list.add(capital);
			}
		in.close();
		
		
		return list;
		
	}
	/*Dosyanýn içini temizliyorum*/
	public void allRecordDelete() throws IOException{
		FileWriter fw=new FileWriter(filename);
		fw.write("");
		fw.close();
		
	}
	/*Dosyadan havaalaný ismine göre havaalanýný çekiyorum*/
	public CapitalJpa findByName(String name) throws FileNotFoundException{
		Scanner in=new Scanner(new FileInputStream(filename));
		String[] currentLine = null;
		  while (in.hasNextLine()) 
		    {
		        currentLine = in.nextLine().split("-");
		        if(currentLine[1].equals(name)){
		        	CapitalJpa capital=new CapitalJpa();
					capital.setId(Integer.parseInt(currentLine[0]));
					capital.setName(currentLine[1]);
					capital.setLat(currentLine[2]);
					capital.setLng(currentLine[3]);
					 in.close();
		        	return capital;
		        }
		   
		    }
		  in.close();
		return null;
	}
	/*Dosyadan havaalaný id göre havaalanýný çekiyorum*/
	public CapitalJpa findById(int id) throws FileNotFoundException{
		Scanner in=new Scanner(new FileInputStream(filename));
		String[] currentLine = null;
		  while (in.hasNextLine()) 
		    {
		        currentLine = in.nextLine().split("-");
		        if(currentLine[0].equals(Integer.toString(id))){
		        	System.out.println(currentLine[0]);
		        	CapitalJpa capital=new CapitalJpa();
					capital.setId(Integer.parseInt(currentLine[0]));
					capital.setName(currentLine[1]);
					capital.setLat(currentLine[2]);
					capital.setLng(currentLine[3]);
					 in.close();
		        	return capital;
		        }
		   
		    }
		  in.close();
		return null;
	}

}