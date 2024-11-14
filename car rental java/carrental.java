[05/04/24, 8:25:23 PM] Laren Pinto: mport java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// CarShowRoom interface to display showroom
interface CarShowRoom {
    void display(); // Method to display showroom
}

// TermsAndConditions interface to confirm terms and conditions
interface TermsAndConditions {
    boolean displayAndConfirm(); // Method to display and confirm terms and conditions
}

// RentalDetails interface to set rental details
interface RentalDetails {
    void setRentalStartDate(LocalDate startDate); // Method to set rental start date
    void setRentalDuration(int duration); // Method to set rental duration
    void setEmail(String email); // Method to set customer email
    void setIdProof(String idProof); // Method to set customer ID proof
    void setPaymentMethod(String paymentMethod); //Method to choose payment method
    void setPaymentPin(String paymentPin); // Method to set payment PIN
}

// Place class representing a place where showrooms are located
class Place implements CarShowRoom {
    private String name; // Name of the place
    private List<CarShowRoom> showrooms; // List of showrooms in the place

    // Constructor to initialize Place object with a name
    public Place(String name) {
        this.name = name;
        this.showrooms = new ArrayList<>(); // Initialize showrooms list
    }

    // Method to add a showroom to the list of showrooms
    public void addShowroom(CarShowRoom showroom) {
        showrooms.add(showroom);
    }

    // Method to display the name of the place and its showrooms
    @Override
    public void display() {
        System.out.println("Place: " + name);
        for (CarShowRoom showroom : showrooms) {
            showroom.display(); // Display each showroom in the place
        }
    }
}

// Showroom class representing a showroom
class Showroom implements CarShowRoom {
    private String name; // Name of the showroom

    // Constructor to initialize Showroom object with a name
    public Showroom(String name) {
        this.name = name;
    }

    // Method to display the name of the showroom
    @Override
    public void display() {
        System.out.println("Showroom: " + name);
    }
}

// Vehicle class representing a vehicle
class Vehicle {
    private String make; // Make of the vehicle
    private String model; // Model of the vehicle
    private int year; // Year of manufacture of the vehicle
    private double rentalRate; // Rental rate of the vehicle

    // Constructor to initialize Vehicle object with make, model, year, and rental rate
    public Vehicle(String make, String model, int year, double rentalRate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.rentalRate = rentalRate;
    }

    // Method to display information about the vehicle
    public void displayInfo() {
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Rental Rate: " + rentalRate);
    }

    // Method to get the rental rate of the vehicle
    public double getRentalRate() {
        return rentalRate;
    }

    // Method to get the make of the vehicle
    public String getMake() {
        return make;
    }

    // Method to get the model of the vehicle
    public String getModel() {
        return model;
    }
}

// Car class representing a car, extending Vehicle class and implementing TermsAndConditions interface
class Car extends Vehicle implements TermsAndConditions {
    private int numDoors; // Number of doors of the car
    private String isConvertible; // Whether the car is convertible or not
    private String fuelType; // Fuel type of the car

    // Constructor to initialize Car object with make, model, year, rental rate, number of doors, convertible status, and fuel type
    public Car(String make, String model, int year, double rentalRate, int numDoors, String isConvertible, String fuelType) {
        super(make, model, year, rentalRate);
        this.numDoors = numDoors;
        this.isConvertible = isConvertible;
        this.fuelType = fuelType;
    }

    // Method to display information about the car
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of Doors: " + numDoors);
        System.out.println("Convertible: " + isConvertible);
        System.out.println("Fuel Type: " + fuelType);
    }

    // Method to display and confirm terms and conditions for renting the car
    @Override
    public boolean displayAndConfirm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Terms and Conditions:");
        System.out.println("1. You agree to abide by the rental policies and regulations.");
        System.out.println("2. You are responsible for any damages to the car during the rental period.");
        System.out.println("3. Rental charges will be as per the agreed terms.");
        System.out.println("Do you agree to the terms and conditions? (agree/disagree)");

        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("agree");
    }
}

// CarRentalSystem class representing a car rental system, implementing RentalDetails interface
class CarRentalSystem implements RentalDetails {
    private List<Vehicle> vehicles = new ArrayList<>(); // List of vehicles available for rental
    private Place place; // Place where the rental system is located
    private LocalDate rentalStartDate; // Start date of the rental
    private int rentalDuration; // Duration of the rental
    private String email; // Email of the customer
    private String idProof; // ID proof of the customer
    private String paymentMethod; // Payment method chosen by the customer
    private String paymentPin; // Payment PIN provided by the customer

    // Constructor to initialize CarRentalSystem object with a place
    public CarRentalSystem(Place place) {
        this.place = place;
    }

    // Method to add a vehicle to the list of available vehicles
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    // Method to display available vehicles
    public void displayAvailableVehicles() {
        for (Vehicle vehicle : vehicles) {
            System.out.println("*******************");
            System.out.println("*******************");

            vehicle.displayInfo(); // Display information about each available vehicle
        }
    }

    // Method to rent a vehicle
    public void rentVehicle() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter vehicle make:");
            String make = scanner.nextLine().trim();
            System.out.println("Enter vehicle model:");
            String model = scanner.nextLine().trim();

            Vehicle selectedVehicle = null;

            for (Vehicle vehicle : vehicles) {
                if (vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model)) {
                    selectedVehicle = vehicle;
                    break;
                }
            }

            if (selectedVehicle == null) {
                System.out.println("Vehicle not available for rental.");
                return;            }

            if (!(selectedVehicle instanceof TermsAndConditions)) {
                System.out.println("This vehicle cannot be rented.");
                return;
            }

            TermsAndConditions rentalAgreement = (TermsAndConditions) selectedVehicle;

            if (!rentalAgreement.displayAndConfirm()) {
                System.out.println("Order canceled. You did not agree to the terms and conditions.");
                return;
            }

            System.out.println("Enter rental start date (YYYY-MM-DD):");
            String startDateInput = scanner.nextLine().trim();
            rentalStartDate = LocalDate.parse(startDateInput);

            System.out.println("Enter rental duration (in days):");
            rentalDuration = scanner.nextInt();
            double totalPrice = rentalDuration * selectedVehicle.getRentalRate();
            System.out.println("Total rental price: " + totalPrice + " INR");

            System.out.println("Enter customer name:");
            String customerName = scanner.next().trim();

            System.out.println("Enter customer contact number:");
            String contactNumber = scanner.next().trim();

            System.out.println("Enter customer email:");
            setEmail(scanner.next().trim());

            System.out.println("Enter customer ID proof (like Aadhaar card number):");
            setIdProof(scanner.next().trim());

            System.out.println("Enter payment method: \n 1. Credit Card \n 2. Debit Card \n 3. UPI");
            setPaymentMethod(scanner.next().trim());

            System.out.println("Enter payment PIN:");
            setPaymentPin(scanner.next().trim());

            // Display confirmation of rental order
            System.out.println("=== Rental Order Summary ===");
            System.out.println("Vehicle: " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
            System.out.println("Rental Start Date: " + rentalStartDate);
            System.out.println("Rental Duration: " + rentalDuration + " days");
            System.out.println("Customer Name: " + customerName);
            System.out.println("Contact Number: " + contactNumber);
            System.out.println("Customer Email: " + getEmail());
            System.out.println("Customer ID Proof: " + getIdProof());
            System.out.println("Payment Method: " + getPaymentMethod());
            System.out.println("Total Price: " + totalPrice + " INR");

            // Ask for confirmation
            System.out.println("Confirm rental order? (yes/no)");
            String confirmation = scanner.next().trim().toLowerCase();
            if (confirmation.equals("yes")) {
                System.out.println("Vehicle rented successfully. Enjoy your ride!");
            } else {
                System.out.println("Order canceled.");
            }
            LocalDate returnDate = rentalStartDate.plusDays(rentalDuration);
            System.out.println("Return date: " + returnDate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());


        }
    }

    // Method to display places and showrooms
    public void displayPlacesAndShowrooms() {
        place.display();
    }

    // Method to set rental start date
    @Override
    public void setRentalStartDate(LocalDate startDate) {
        rentalStartDate = startDate;
    }

    // Method to set rental duration
    @Override
    public void setRentalDuration(int duration) {
        rentalDuration = duration;
    }

    // Method to set customer email
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    // Method to set customer ID proof
    @Override
    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    // Method to set payment method
    @Override
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Method to set payment PIN
    @Override
    public void setPaymentPin(String paymentPin) {
        this.paymentPin = paymentPin;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Getter method for ID proof
    public String getIdProof() {
        return idProof;
    }

    // Getter method for payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }
}

// Main class for the car rental application
class CarRentalApp {
    public static void main(String[] args) {
        // Create a place for the rental system
        Place place = new Place("UDUPI");
        Showroom showroom1 = new Showroom("ElITE AUTO RENTAL");

        // Add showroom to the place
        place.addShowroom(showroom1);

        // Create a car rental system with the place
        CarRentalSystem rentalSystem = new CarRentalSystem(place);

        // Create some cars to add to the rental system
        Car car1 = new Car("Porsche", "911 carrera", 2016, 25000, 2, "yes", "Petrol");
        Car car2 = new Car("Lexus", "LFA", 2015, 30000, 4, "no", "Petrol");
        Car car3 = new Car("Audi", "Q5", 2018, 10000, 4, "no", "Diesel");
        Car car4 = new Car("BMW", "Z4 roadster", 2017, 13000, 2, "yes", "Petrol");
        Car car5 = new Car("Porsche", "Panamera", 2018, 35000, 4, "no", "Diesel");
        Car car6 = new Car("Mercedes", "E-Class", 2022, 8000, 4, "no", "Petrol");
        Car car7 = new Car("Audi", "A4 Roadster", 2016, 13000, 2, "yes", "Petrol");
        Car car8 = new Car("BMW", "X5 M", 2023, 10000, 4, "no", "Diesel");
        Car car9 = new Car("Nissan", "GT-R", 2018, 20000, 2, "no", "Petrol");

        // Add cars to the rental system
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(car3);
        rentalSystem.addVehicle(car4);
        rentalSystem.addVehicle(car5);
        rentalSystem.addVehicle(car6);
        rentalSystem.addVehicle(car7);
        rentalSystem.addVehicle(car8);
        rentalSystem.addVehicle(car9);

        // Display available showrooms
        rentalSystem.displayPlacesAndShowrooms();

        // Display available vehicles
        System.out.println("=================================");
        System.out.println("===== Available Vehicles =====");
        System.out.println("=================================");
        rentalSystem.displayAvailableVehicles();
        System.out.println("=================================");

        // Rent a vehicle
        rentalSystem.rentVehicle();
        System.out.println();
    }
}
