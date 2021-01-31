package proje;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JMenu;

import com.teamdev.jxbrowser.browser.Browser;

import jpa.FlightJpa;

public class SystemThread implements Runnable {
	private JMenu menuTime;
	private long miliseconds;
	private Browser browser;
	public static int state = 1;

	public SystemThread(JMenu menuTime, Browser browser) {
		this.setMenuTime(menuTime);
		this.setMiliseconds(1432933200000L);
		this.browser = browser;
	}

	@Override
	public void run() {
		JMenu menuTime = this.getMenuTime();
		long miliseconds = 1432933200000L;
		Timer myTimer = new Timer();
		TimerTask gorev = new TimerTask() {

			long mili = miliseconds;
			/*Sistemi baþlatýyor ve her bir gercek saniye benim sistemimde dakika olarak sistem saatini ayarlayýp
			 * sistem iþlemeye baþlýyor*/
			@Override
			public void run() {

				if (state == 1) {
					Date time = new Date(mili);
					String timeToText = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(time);
					menuTime.setText(timeToText);
					mili += TimeUnit.MINUTES.toMillis(1);
					System.out.println(timeToText);
					FlightJpa flightJpa = new FlightJpa();
					/*burada surekli txt dosyasýný okuyup sistem saatine göre kalkýþ zamaný gelen ucus varsa
					 * onu thread olarak baþlatýp haritada görünmesini saðlýyor*/
					
					List<FlightJpa> results2 = null;
					try {
						results2 = flightJpa.findByDepartureTime(timeToText);

						
						for (FlightJpa flight : results2) {
							if (results2.size() > 0) {
								Tower ft = new Tower(browser,	flight);
								Thread t = new Thread(ft);
								t.start();
							
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("Hata :"+e.getMessage());
					}

				}
			}
		};

		myTimer.schedule(gorev, 0, 1000);

	}

	public JMenu getMenuTime() {
		return menuTime;
	}

	public void setMenuTime(JMenu menuTime) {
		this.menuTime = menuTime;
	}

	public long getMiliseconds() {
		return miliseconds;
	}

	public void setMiliseconds(long miliseconds) {
		this.miliseconds = miliseconds;
	}

}
