import java.util.*;

class Student {
	
	private static int counter = 1;// Used to assign roll Numbers to students
	private double cgpa; // Stores the CGPA of the student
	private String branch; // Stores the branch of the student
	private boolean placementStatus; // false is not placed ; true is Placed 
	private final int rollNumber; // Stores the rollNumber of the student
	private HashMap<String,Double> techRoundMarks; // A map of the company that the student has applied for and 
	private String placedIn = null;
	
	public Student(double cgpa,String branch ) {
		//Constructor
		this.cgpa = cgpa;
		this.branch = branch;
		this.placementStatus = false;
		this.rollNumber = counter++;
		techRoundMarks = new HashMap<>();
	}
	
	//Getters and Setters
	public double getMarks(String name) {
		return techRoundMarks.get(name);
	}
	
	public String getPlacedIn() {
		return placedIn;
	}

	public void setPlacedIn(String placedIn) {
		this.placedIn = placedIn;
	}

	public void setTechRoundMarks(String coName, double score) {
		this.techRoundMarks.put(coName, score);
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public double getCgpa() {
		return cgpa;
	}

	public String getBranch() {
		return branch;
	}

	public boolean getPlacementStatus() {
		return placementStatus;
	}

	public void setPlacementStatus(boolean placementStatus, String companyName) {
		this.placementStatus = placementStatus;
		this.placedIn = companyName;
	}
	//Getters and Setters End
	
	public void displayStudentDetails() {
		//Prints out the student details
		System.out.println(this.getRollNumber());
		System.out.println(this.getCgpa());
		System.out.println(this.getBranch());
		if (this.getPlacementStatus() == false) {
			System.out.println("Placement Status = Not Placed");
		}
		else {
			System.out.println("Placement Status = Placed");
			System.out.println("Placed in " + this.getPlacedIn());
		}
	}
	
	public void displayApplicationDetails() {
		//Prints the technical round marks for each company that the student has applied for
		techRoundMarks.forEach((name,score) -> System.out.println(name + " " + score));
	}
	
}
class Company{
	
	private int numberOfRecruits; // Stores the required number of Candidates by the company
	private int status; // Stores the placement status of the company ; 0 denotes open 1 denotes closed
	private String name;// Stores the name of the company
	private HashSet<String> courses; // Stores the course requirements of the company
	private HashMap<Integer,Double> applicantMarks; // Stores the marks in technical round of each students
	private ArrayList<Student> appliedCandidates; // Stores the Students that have applied for the company
	private int recruited; // Stores the no of candidates that have been recruited by the Company
	
	public Company(String name , int numberOfRecruits , HashSet<String> courses) {
		//Constructor
		status = 0;
		this.numberOfRecruits = numberOfRecruits;
		this.name = name;
		this.courses = courses;
		applicantMarks = new HashMap<>();
		recruited = 0;
	}
	
	//Getters and Setters
	public double getApplicantMarks(int rollNo) {
		return applicantMarks.get(rollNo);
	}
	
	public ArrayList<Student> getAppliedCandidates() {
		return appliedCandidates;
	}

	public void setAppliedCandidates(ArrayList<Student> appliedCandidates) {
		this.appliedCandidates = appliedCandidates;
	}
	
	public void setApplicantMarks(int rollNo , double marks) {
		this.applicantMarks.put(rollNo, marks);
	}
		
	public int getNumberOfRecruits() {
		return numberOfRecruits;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public HashSet<String> getCourses() {
		return courses;
	}

	public int getRecruited() {
		return recruited;
	}

	public void setRecruited(int recruited) {
		this.recruited = recruited;
	}
	// Getters and Setters End

	public void display() {
		// This function displays the details of the company
		System.out.println(name);
		System.out.println("Course Criteria");
		for (String string : courses) {
			System.out.println(string);
		}
		System.out.println("No. of Required Students = " + this.getNumberOfRecruits());
		if (this.getStatus() == 0){
			System.out.println("Application Status = OPEN");
		}
	}
	
	public void shortListStudents(String coName) {
		// This functions prepares a ranking of the students that have applied for this company
		// It sorts an ArrayList of applied students according to marks scored in the technical round
		// and their respective CGPA's
		Collections.sort(this.getAppliedCandidates(),new Comparator<Student>() {
			@Override
			public int compare(Student current,Student other) {
				double curMarks = current.getMarks(coName);
				double otherMarks = other.getMarks(coName);
				if ( curMarks > otherMarks ) 
					return -1;
				else if ( curMarks == otherMarks ) {
					double curCgpa = current.getCgpa();
					double otherCgpa = other.getCgpa();
					if ( curCgpa > otherCgpa)
						return -1;
					else
						return 1;
				}
				else
					return 1;
			}
		});
		
	}
	
	public void recruit() {
		this.shortListStudents(this.getName());
		System.out.println("Roll Number of Selected Students");
		if (this.getAppliedCandidates().size() >= this.getNumberOfRecruits()) {
			for (int i = 0; i < this.getNumberOfRecruits(); i++) {
				Student temp = this.getAppliedCandidates().get(i);
				System.out.println(temp.getRollNumber());
				temp.setPlacementStatus(true, this.getName());
			}
			this.setStatus(1);
			this.setRecruited(this.getNumberOfRecruits());
		}
		else {
			for (Student student : this.getAppliedCandidates()) {
				System.out.println(student.getRollNumber());
				student.setPlacementStatus(true, this.getName());
				this.setRecruited(this.getRecruited()+1);
			}
		}
	}
}
class PlacementOffice{
	
	private ArrayList<Student> students; // List of registered students
	private ArrayList<Company> companies; // List of registered companies
	
	public PlacementOffice() {
		students = new ArrayList<Student>();
		companies = new ArrayList<Company>();
	}
	
	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}
	
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public void addNewStudent(Student myStudent) {
		students.add(myStudent);
	}
	
	public ArrayList<Student> addNewCompany(Company myCompany) {
		// This function is used to add a new company to the companies ArrayList 
		// It also finds the students that are eligible for that company and returns its array
		companies.add(myCompany);
		ArrayList<Student> eligibleStudents = new ArrayList<>();
		for (Student i : students) {
			if (i.getPlacementStatus()==false && myCompany.getCourses().contains(i.getBranch())) 
				eligibleStudents.add(i);
		}
		System.out.println("Enter scores for Technical Round ");
		myCompany.setAppliedCandidates(eligibleStudents);
		return eligibleStudents;
	}
	
	public void removeStudents() {
		// This function removes the accounts of the students that have already been placed
		System.out.println("Accounts Removed for");
		ArrayList<Student> temp = new ArrayList<>();
		for (Student i : students) {
			if (i.getPlacementStatus() == false) 
				temp.add(i);
			else 
				System.out.println(i.getRollNumber());
		}
		setStudents(temp);
	}
	
	public void removeCompanies() {
		// This function removes the companies whose recruitment requirements have been fulfilled
		System.out.println("Accounts removed for ");
		ArrayList<Company> temp = new ArrayList<>();
		for (Company company : companies) {
			if (company.getStatus() == 1)
				System.out.println(company.getName());
			else 
				temp.add(company);
		}
		setCompanies(temp);
	}
	
	public void displayOpenCompanies() {
		// This displays the names of the companies that are still accepting students
		for (Company company : companies) {
			if (company.getStatus() == 0)
				System.out.println(company.getName());
		}
	}
	
	public int getNotPlaced() {
		// This function returns the number of students that have not been placed
		int count = 0;
		for (Student student : students) {
			if (student.getPlacementStatus() == false) 
				count++;
		}
		return count;
	}
	
	public void getCompany(String coName) {
		// This function searches for the company whose name has been passed as an argument in the ArrayList of companies
		// It calls the display function to display company details
		int flag = 0;
		for (Company company : companies) {
			if (company.getName().equals(coName)) {
				flag = 1;
				company.display();
				break;
			}
		}
		if (flag == 0) 
			System.out.println("No such Company has been Registered");
	}
	
	public void getStudent(int rollNo) {
		// This function searches for the student whose roll number has been passed as an argument in the ArrayList of students
		// It calls the display function to display students details
		// It also checks whether the given student is present in the record or not
		int flag = 0;
		for (Student student : students) {
			if (student.getRollNumber() == rollNo) {
				flag = 1;
				student.displayStudentDetails();
				break;
			}
		} 
		if (flag == 0) 
			System.out.println("No such student registered");
	}
	
	public void getStudentAppliedCompanies(int rollNo) {
		// This function searches for the student with given roll number that has been passed as an argument
		// It then calls displayApplicationDetails function which displays the companies that the student has applied for 
		// and the marks in the technical round of that company
		int flag = 0;
		for (Student student : students) {
			if (student.getRollNumber() == rollNo) {
				student.displayApplicationDetails();
				flag = 1;
				break;
			}
		}
		if (flag == 0) 
			System.out.println("No student with the given roll number has an account");
	}
	
	public void placeStudents(String coName) {
		// This function is simply a wrapper 
		// It calls the recruit function for the company with the name that has been passed as an argument 
		for (Company company : companies) {
			if (company.getName().equals(coName))
				company.recruit();
		}
	}
}

public class Placements{
	public static void main(String[] args) {
		PlacementOffice myOffice  = new PlacementOffice();
		Scanner in = new Scanner(System.in);
		int noOfStudents = in.nextInt();
		for (int i = 0; i < noOfStudents ; i++) {
			double cgpa = in.nextDouble();
			String branch = in.next();
			myOffice.addNewStudent(new Student(cgpa,branch));
		}
		System.out.println("---- students registered ----");
		while (myOffice.getNotPlaced() != 0 ){
			int query = in.nextInt();
			switch (query) {
			case 1 :
				String name = in.next();
				System.out.print("Number of Eligible Courses = ");
				int noOfCourses = in.nextInt();
				HashSet<String> courses = new HashSet<String>();
				while(noOfCourses-->0) {
					courses.add(in.next());
				}
				System.out.print("Number of Required Students = ");
				int noOfRecruits = in.nextInt();
				Company company = new Company(name,noOfRecruits,courses);
				List<Student> eligibleStudents = myOffice.addNewCompany(company);
				for (Student student : eligibleStudents) {
					System.out.println("Enter score for Roll no. " + student.getRollNumber());
					double score  = in.nextDouble();
					student.setTechRoundMarks(company.getName(), score);
					company.setApplicantMarks(student.getRollNumber(), score);
				}
				break;				
			case 2 :
				myOffice.removeStudents();
				break;
			case 3 :
				myOffice.removeCompanies();
				break;
			case 4 : 
				int count = myOffice.getNotPlaced();
				System.out.println(count  +  " students left");
				break;
			case 5 : 
				myOffice.displayOpenCompanies();
				break;
			case 6 :
				String comName = in.next();
				myOffice.placeStudents(comName);
				break;
			case 7 :
				String coName = in.next();
				myOffice.getCompany(coName);
				break;
			case 8:
				int rollno1 = in.nextInt();
				myOffice.getStudent(rollno1);
				break;
			case 9:
				int rollno2 = in.nextInt();
				myOffice.getStudentAppliedCompanies(rollno2);
				break;
			}
		}
		in.close();
	}
}