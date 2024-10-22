package Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TrainBooking {
	static int availableSeats =3, upperBerth=2,lowerBerth = 1,wlLimit=5,id=1;
	static List<Passenger> bkdPassengers = new ArrayList<Passenger>();
	

	static List<Passenger> wlPassengers = new ArrayList<Passenger>();
	
	private static void allocateBerth(Passenger p) {
		if(p.getPrefBerth().equalsIgnoreCase("LOWER")) {
			if(lowerBerth<1) {
				p.setAllocatedBerth("UPPER");
				upperBerth--;
			}else {
				p.setAllocatedBerth("LOWER");
				lowerBerth--;
			}
		}else if(p.getPrefBerth().equalsIgnoreCase("UPPER")) {
			if(upperBerth<1) {
				p.setAllocatedBerth("LOWER");
				lowerBerth--;
			}else {
				p.setAllocatedBerth("UPPER");
				upperBerth--;
			}
		}else {
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String choice = "Y";
		Scanner sc = new Scanner(System.in);
		while(choice.equalsIgnoreCase("Y")) {
			System.out.println("Enter Your Choice \n 1. Book Ticket \n 2. View Ticket \n 3. Cancel Ticket \n 4. Get Your Id \n 5. getStatus \n 6. Check available seats" );
			int ch = sc.nextInt();
			switch(ch) {
			case 1:
				System.out.println("Enter your Name");
				String name =sc.next();
				boolean berthCheck = true;
				String prefBerth ="";
				while(berthCheck) {
					System.out.println("Enter your PrefBerth UPPER/LOWER");
					prefBerth= sc.next();
					if(prefBerth.equalsIgnoreCase("UPPER") || prefBerth.equalsIgnoreCase("LOWER")) {
						berthCheck=false;
					}else {
						System.out.println("please enter a valid berth");
					}
				}
				Passenger p = new Passenger(name,id,prefBerth);
				bookTicket(p);
				id++;
				break;
			case 2:
				System.out.println("Enter your id to view ticket");
				int id = sc.nextInt();
				viewTicket(id);
				break;
			case 3:
				System.out.println("Enter your id to cancel your ticket");
				int cancelId = sc.nextInt();
				cancelTicket(cancelId);

				break;
			case 4 :
				System.out.println("Please enter your Name");
				String nameForId=sc.next();
				getYourId(nameForId);
				break;
			case 5:
				System.out.println("Enter your id to view status");
				int statusId = sc.nextInt();
				viewStatus(statusId);
				break;
			case 6:
				checkSeatAvailability();
			}
			System.out.println("Would you Want to Continue Y/N");
			choice = sc.next();
		}
		sc.close();
	}

	private static void checkSeatAvailability() {
		System.out.println("Available Seats : " + availableSeats);
		System.out.println("Upper Berths available : " + upperBerth);
		System.out.println("Lower Berths available : " + lowerBerth);
		System.out.println("Waiting List slots available : " + wlLimit);
	}

	private static void viewStatus(int statusId) {
		Optional<Passenger> p = bkdPassengers.stream().filter(e->( e.getpId()==statusId)).findAny();
		Optional<Passenger> pwl = wlPassengers.stream().filter(e->(e.getpId()==statusId)).findAny();
		
		if(p.isPresent()) {
			System.out.println("Your Status is : "+"You are currently in Booked List");
		}else if(pwl.isPresent()) {
			System.out.println("Your Status is : "+"You are currently in Waiting List");
		}else {
			System.out.println("Your Status is : " +"Your id is not matching " );
		}
	}

	private static void getYourId(String nameForId) {
		Optional<Passenger> pBkd = bkdPassengers.stream().filter(e->e.getName().equalsIgnoreCase(nameForId)).findAny();
		Optional<Passenger> pWl = wlPassengers.stream().filter(e->e.getName().equalsIgnoreCase(nameForId)).findAny();

		if(pBkd.isPresent()) {
			Passenger pDetails= pBkd.get();
			int personId= pDetails.getpId();
			System.out.println("Your passenger ID is : "+ personId);
		}else if(pWl.isPresent()) {
			Passenger pDetails= pWl.get();
			int personId= pDetails.getpId();
			System.out.println("Your passenger ID is : "+ personId);
		}else {
			System.out.println("Not match any ID  for your Name");
		}
	}

	private static void cancelTicket(int cancelId) {
		Optional<Passenger> p= bkdPassengers.stream().filter(e->e.getpId()==cancelId).findAny();
		if(p.isPresent()) {
			if(wlPassengers.isEmpty()) {
				Passenger cancelledP = p.get();
				if(cancelledP.getAllocatedBerth().toUpperCase().equals("Lower")) {
					lowerBerth++;
				}else {
					upperBerth++;
				}
				bkdPassengers.removeIf(e->e.getpId()==cancelId);
				availableSeats++;
		        System.out.println("Ticket canceled successfully.");
			}else {
				Passenger cancelledP = p.get();
				Passenger wlPerson= wlPassengers.get(0);
				wlPerson.setAllocatedBerth(cancelledP.getAllocatedBerth());
				bkdPassengers.removeIf(e->e.getpId()==cancelId);
				wlPassengers.removeIf(e->e.getpId()==wlPerson.getpId());
				wlLimit++;
				bkdPassengers.add(wlPerson);
		        System.out.println("Ticket canceled successfully.");
	            System.out.println("Passenger " + wlPerson.getName() + " has been moved from the waiting list to the booked list.");
				}
		}else {
			System.out.println("Unable to cancel your ticket since  you are not booked");
		}
	}

	private static void viewTicket(int id) {
		Optional<Passenger> p = bkdPassengers.stream().filter(e->( e.getpId()==id)).findAny();
		if(p.isPresent()) {
			System.out.println("you are in booked list \n " + p.get());
		}else {
			Optional<Passenger> pwl = wlPassengers.stream().filter(e->(e.getpId()==id)).findAny();
			if(pwl.isPresent()) {
				System.out.println("you are in waiting  list \n " + pwl.get());
			}else {
				System.out.println("No passenger is found with this Id");
			}
		}
	}

	private	static void bookTicket(Passenger p) {
		if(availableSeats < 1) {
			if(wlLimit<1) {
				System.out.println("Regret");
				return;
			}else {
				wlPassengers.add(p);
				wlLimit--;
				System.out.println("you are in waiting list");
				return;
			}
		}
		allocateBerth(p);
		bkdPassengers.add(p);
		availableSeats--;
		System.out.println("Your ticket was booked successfully");
		}	
	}
