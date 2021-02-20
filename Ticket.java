package usecase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Ticket  {
	private static int counter = 100;
	private String pnr;
	private Date travelDate;
	private Train train;
	private TreeMap<Passenger, Integer> passengers;
	
	    //Constructors Using Fields for travelDate and train
	public Ticket() {
		super();
	}

	public Ticket(Date travelDate, Train train) {
		super();
		this.travelDate = travelDate;
		this.train = train;
	}

	     //Getters and Setters
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public TreeMap<Passenger, Integer> getPassengers() {
		return passengers;
	}

	public void setPassengers(TreeMap<Passenger, Integer> passengers) {
		this.passengers = passengers;
	}
	

	@Override
	public String toString() {
		return "pnr=" + pnr + ", travelDate=" + travelDate + ", train=" + train + ", passengers=" + passengers;
	}
           //auto-generated PNR
	public String generatePNR() {
       String sc = String.valueOf(train.getSource().charAt(0));
		String dest = String.valueOf(train.getDestination().charAt(0));
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(travelDate);
		String pnrNo = sc + dest + "_" + strDate + "_" + counter++;
		return pnrNo;
	

	}
         //The ticket Fare for each passenger is calculated using the Certain rules
	public double calcPassengerFare(Passenger passenger) {
		double ticketFare=0;
	
		if(passenger.getAge()<=12){
			ticketFare=train.getTicketPrice()-0.50*(train.getTicketPrice());
		     }
            else if(passenger.getAge()>=60){
           	 ticketFare=train.getTicketPrice()-0.60*(train.getTicketPrice());

            }
		    else if(passenger.getGender()=='F'||passenger.getGender()=='f' )
		     {
		    	ticketFare=train.getTicketPrice()-0.25*(train.getTicketPrice());
		     }
		    else{
		    	ticketFare=train.getTicketPrice();
		    }
		System.out.println(ticketFare);
	         return ticketFare;
	}
	
              //Adding passenger method
	public void addPassenger(String name, int age, char gender) {
		Passenger passenger=new Passenger(name,age,gender);
		Integer ticFare=(int)calcPassengerFare(new Passenger(name,age,gender));
		passengers=new TreeMap<Passenger,Integer>();
		passengers.put(passenger, ticFare);
		calculateTotalTicketPrice();
		for(Map.Entry<Passenger,Integer> entry : passengers.entrySet()) {
			Passenger key = entry.getKey();
			  Integer value = entry.getValue();
			  }

			}
	
	       //Calculating totalprice
	public double calculateTotalTicketPrice() {
		Integer totalTicketPrice=0;
		for(Map.Entry<Passenger,Integer> entry : passengers.entrySet()) {
			Integer value = entry.getValue();
			 totalTicketPrice=totalTicketPrice+value;
			 }
           return totalTicketPrice;
	}

	      //Generating Ticket 
	public StringBuilder generateTicket() {
		StringBuilder sb=new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(getTravelDate());
	    sb.append("PNR         : "+generatePNR()+"\n");
		sb.append("Train no    : "+train.getTrainNo()+"\n");
		sb.append("Train Name  : "+train.getTrainName()+"\n");
		sb.append("Source      : "+train.getSource()+"\n");
		sb.append("Destination : "+train.getDestination()+"\n");
		sb.append("Travel Date : "+strDate+"\n");
		sb.append("Passengers\n");
		sb.append("Name          Age        Gender      TicketFare\n");
		sb.append(getPassengers()+"\n");
		sb.append("Total Price: "+calculateTotalTicketPrice());
       return sb;
	}
	
                //Generated ticket is stored in a file followed by pnr number
	public void writeTicket()throws IOException {
		String ticketFile=generatePNR();
		
	File file=new File(ticketFile+".txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			
		    writer.write(generateTicket().toString());
		}
		
	}


	
	
}