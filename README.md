# FlightTrackApp
Object-Oriented Programming Term Project

##### SUBJECT

You will implement a FLIGHT TRACK application. This program should
simulate the flights between the major capitals, such as New York, Sydney,
Paris, Tokyo, Istanbul etc. around the world. The details of the system are
given below. Please read them carefully and strictly conform to these details
while implementing the system.

<ol>
  <li><Strong>FLIGHT TRACK APP</Strong></li>
  </br>
    <p>Your system will include at least 10 routes between 5 major capitals from
different continents. The user would also add new capitals as well as new
destinations to the system. In addition to that, the user could delete and
update existing destinations via the GUI. Besides, the user should declare
the flight details, such as the weekdays, the departure and arrival times,
the aircraft model, the flight number and the name of the airlines. All the
information should be saved into the related files.<p/>

    <p>Your system will have a system date, and one second in your system would
represent one minute in the real life. You would be able to start, pause,
resume and stop the time of your system. The scheduled flights would be
simulated regarding to the system time.
Each capital would have a control tower to manage take offs and landings.
For example, the tower manager would set some delay for a scheduled flight
or she/he could cancel certain flights or even make the aircrafts, which are
close to the related tower, wait in the air.</p>
   
  <li><Strong>System Details</Strong></li>
  
  </br>
<p>This section describes the inputs and the outputs of your implementation.
You must design your implementation according to the rules mentioned in
this section.</p>

<p>The program should be written considering the event-driven programming
techniques. The user would use the GUI for running the defined modules.
You should exploit window(s), panel(s), button(s), and menu item(s) to
implement the GUI.</p>

<p>There would be only one user, who would be in charge of all system activities.
The main activities are given as follows:</p>
<pre><code>
<ul>
<li>Add/Delete capitals.</li>
<li>Add/Delete/Update destinations and flight information.</li>
<li>Set some delay for a specific flight or cancel a certain flight in a
given airport.</li>
<li>Make the flights, which are close enough to a given airport, wait and
then give them permission for landing.</li>
<li>Report the information (scheduled arrival/departure time, take off
and landing time, delay, date, flight number, the name of the airlines,
the departure and the arrival city), for each taking off and landing
into a file.</li>
<li>Start, pause, resume and stop the system time.</li>
</ul>
</pre></code>

<li>How the program should work?</li>
<p>
Once the program starts, the user would add new destinations. Then, the
user would set and start the system time. Each flight would occur as a
thread. The system would create a thread for each scheduled flight. After
the completion of the landing, the system should kill the related thread.
</p>
</ol>
## Installation

Please check the system requirements before installing FlightTrackApp.

1. You should use "download zip" button for downloading.
2. You should extract zip to eclipse workspace.
3. Open eclipse and File->import->General->Exiting Project ... and next..
4. You should select "Select Root Directory" and your eclipse workspace 
5. You should select this project from Projects List and finish.
6. Run Map.java

### Important !!
If this project throw "JxBrowser license check failed: No valid license found. "
you must go to http://www.teamdev.com/jxbrowser and download evaluation key.
Import this "license.jar" to project.

###### UML Diagram
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/uml.png "Uml Diagram")

##### Screenshots

![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/1.PNG "Show Capital on Map")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/2.PNG "Capital and Flight List")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/3.PNG "Show Route of Flight on Map")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/4.PNG "Add Capital")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/5.PNG "Add Flight")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/6.PNG "Show Flight with route on map")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/7.PNG "other")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/8.PNG "other")
![alt text](https://github.com/uhudpoyraz/FlightTrackApp/blob/master/otherfiles/9.PNG "report file")


### Licence

[MIT license](http://opensource.org/licenses/MIT)
