// Name    : Sanchit Trivedi
// Roll No : 2018091
// Branch  : CSE
// Advanced Programming LAB 2

import java.util.*;

class Pair<T1,T2>{
	// User defined class similar to pair in CPP;
	T1 key;
	T2 value;
	public Pair(T1 key,T2 value) {
		this.key = key;
		this.value = value;
	}
	public T1 getKey() {
		return key;
	}
	public T2 getValue() {
		return value;
	}
}

interface Client{
	public void displayDetails();
}
interface Menu{
	public void displayMenu();
}

class Customer implements Client,Menu {
	
	private String address;
	private double money;
	private int purchaseCount5;
	private double rewardMoney;
	private int totalRewards;
	private String name;
	private Queue<Pair<Item,Integer>> cart;
	private int noOfOrders = 0;
	private static int idCounter = 0;
	private final int customerId;
	ArrayList<Pair<Item,Double[]>> pastTransactions;
	
	public Customer(String name,String address) {
		this.name = name;
		this.money = 100;
		this.purchaseCount5 = 0;
		this.rewardMoney = 0;
		this.cart = new LinkedList<>();
		this.address = address;
		this.customerId = ++idCounter;
		this.pastTransactions = new ArrayList<>();
	}
	
	public void addToCart(Item item , int qty) {
		// Adding to the QUEUE that is the cart
		cart.add(new Pair<Item,Integer>(item,qty));
	}
	
	public double buyItem(Item item,int qty) {
		// Buying the item in the given quantity
		int offer = item.getOfferCode();
		double offerFactor = 1;
		switch(offer) {
		case 1:
			qty = qty*2;
			offerFactor = 0.5;
			break;
		case 2:
			offerFactor = 0.75;
			break;
		}
		if (item.getQuantityInStock() < qty) {
//			System.out.println("OUT OF STOCK");
			return 0;
		}
		else {
			double cost = 1.005 * item.getPrice() * qty * offerFactor;
			if (money + rewardMoney >= cost) {
				money -= cost;
				if (money < 0) {
					rewardMoney +=money;
					money = 0; 
				}
				item.setQuantityInStock(item.getQuantityInStock() - qty);
//				System.out.println("Item bought Successfully");
				Double[] arr = {(double)qty,cost};
				this.pastTransactions.add(0,new Pair<Item,Double[]>(item,arr));
				return item.getPrice() * qty * offerFactor;
			}
			else
//				System.out.println("OUT OF MONEY");
				return -1;
		}
	}
	 	
	public void getPreviousTransactions() {
		// Displaying last 10 transaction histories
		int end = Math.min(this.pastTransactions.size(), 10);
		for (int i = 0; i < end; i++) {
			Pair<Item, Double[]> cur = this.pastTransactions.get(i);
			System.out.println("Bought Item "+ cur.getKey().getName() + " Quantity : " + cur.getValue()[0] + " for Rs." + cur.getValue()[1] +" from " + cur.getKey().getSeller());
		}
	}
	
	public double checkoutCart() {
		//Checking out the items from the cart
		double cartValue=0;
		if(this.cart.isEmpty()) {
			System.out.println("Cart is Empty");
			return 0;
		}
		while (!this.cart.isEmpty()) {
			Pair<Item,Integer> cur = cart.remove();
			double price = this.buyItem(cur.getKey(), cur.getValue());
			if (price == 0) {
				System.out.println("OUT OF STOCK");
				break;
			}
			else if (price ==-1) {
				System.out.println("OUT OF MONEY");
				break;
			}
			else {
				System.out.println(cur.getKey().getName()+" bought Successfully");
				System.out.println("Item details : " + cur.getKey());
				cartValue += price;
			}
		}
		this.incrementOrders();
		this.incrementPurchaseCount();
		this.checkForRewards();
		return cartValue;
	}
	
	public void incrementPurchaseCount() {
		//used to calulate reward money
		this.purchaseCount5++;
	}
	
	public void incrementOrders() {
		// Tracking the number of orders the customer has placed till date
		this.noOfOrders++;
	}
	
	public void checkForRewards() {
		// checking when the customer is eligible for rewards
		if (this.purchaseCount5 == 5) {
			this.rewardMoney +=10;
			this.purchaseCount5 = 0;
		}
	}
	
	public int getTotalRewards() {
		 // Rewards earned till date
		this.totalRewards = 10*(this.getNoOfOrders()/5);
		return this.totalRewards;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNoOfOrders() {
		return this.noOfOrders;
	}

	public void displayMenu() {
		// Display customer menu
		System.out.println("Welcome "+this.getName());
		System.out.println("Customer Menu\r\n" + 
				"1) Search item\r\n" + 
				"2) checkout cart\r\n" + 
				"3) Reward won\r\n" + 
				"4) print latest orders\r\n" + 
				"5) Exit");
	}
	
	public void displayDetails() {
		// Display customer details
		System.out.println("Id  : " +customerId );
		System.out.println("Name  :" + name);
		System.out.println("Address : " + address);
		System.out.println("Total orders placed : " + noOfOrders);
	}
}

class Merchant implements Client,Menu{
	
	private String address; 
	private HashMap<Integer,Item> itemTable;
	private String name;
	private int slots;
	private double revenueToCompany;
	private int rewardSlots;
	private final int merchantId;
	private static int idCounter = 0;
	
	public Merchant(String name,String address) {
		this.itemTable = new HashMap<Integer, Item>();
		this.slots = 10;
		this.name = name ; 
		this.address = address;
		this.merchantId = ++idCounter;
	}
	
	public int getMerchantId() {
		return merchantId;
	}

	public String getName() {
		return this.name;
	}
	
	public void setRewardSlots() {
		rewardSlots = (int) revenueToCompany / 100;
	}
	
	public int getRewardSlots() {
		this.setRewardSlots();
		return rewardSlots;
	}
	
	public void addItem(Item item) {
		// Adding a new item
		this.setRewardSlots();
		if (this.slots + this.rewardSlots >= itemTable.size()) {
			itemTable.put(item.getItemId(), item);
			System.out.println(item);
		}
		else {
			System.out.println("All slots full");
		}
	}
	
	public void editItem(int id , int newQty , double newPrice) {
		// Editing an existing item
		Item item = itemTable.get(id);
		if (item!=null) {
			item.setQuantityInStock(newQty);
			item.setPrice(newPrice);
			System.out.println(item);
		}
		else
			System.out.println("Item code not found");
	}
	
	public void addOffer(int id,int offerCode) {
		// Adding offers to a particular item
		Item item = itemTable.get(id);
		if (item!=null) {
			item.setOfferCode(offerCode);
			System.out.println(item);
		}
		else
			System.out.println("Enter a valid Item ID");
	}
	
	public void display() {
		// Displaying all the items sold by the Merchant
		for (Item item : itemTable.values()) {
			System.out.println(item);
		}
	}
	
	public void displayMenu() {
		// Display Merchant Menu
		System.out.println("Welcome " + this.getName());
		System.out.println("Merchant Menu");
		System.out.println("1) Add item");
		System.out.println("2) Edit item");
		System.out.println("3) Search by Category");
		System.out.println("4) Add Offer");
		System.out.println("5) Rewards won");
		System.out.println("6) Exit");
	}
	
	public void displayDetails() {
		// Displaying the details of the Merchant
		System.out.println("Id  : " +merchantId );
		System.out.println("Name  :" + name);
		System.out.println("Address : " + address);
		System.out.println("Total Contribution to Company : " + revenueToCompany);
	}
	
}

class Item{
	
	private static int itemIdCounter = 0;
	private final int itemId;
	private String name;
	private String category;
	private int quantityInStock;
	private double price;
	private int offerCode;
	private String seller;
		
	public Item(String name, String category, int quantityInStock, double price, int offerCode,String seller) {
		this.itemId = ++itemIdCounter;
		this.name = name;
		this.category = category;
		this.quantityInStock = quantityInStock;
		this.price = price;
		this.offerCode = offerCode;
		this.seller = seller;
	}
	
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategoryId(String category) {
		this.category = category;
	}
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public void setQuantityInStock(int quantity) {
		this.quantityInStock = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(int offerCode) {
		this.offerCode = offerCode;
	}
	public int getItemId() {
		return itemId;
	}

	@Override
	public String toString() {
		String msg = "";
		switch(offerCode) {
			case 0:
				msg = "None";
				break;
			case 2:
				msg = "25% off";
				break;
			case 1:
				msg = "BOGO";
				break;
		}
		return itemId + " " + name + " " + price + " " + quantityInStock + " " + msg + " " + category;		
	}
	
}

class EcomCompany implements Menu{
	
	private ArrayList<Customer> customers;
	private ArrayList<Merchant> merchants;
	private ArrayList<String> allCategories;
	private ArrayList<Item> inventory;
	private double companyAccountBal;
	private String coName;
	
	public EcomCompany(String name) {
		
		this.coName = name;
		this.companyAccountBal = 0;
		
		this.customers = new ArrayList<>();
		this.customers.add(new Customer("ali","Dubai"));
		this.customers.add(new Customer("nobby","Islamabad"));
		this.customers.add(new Customer("bruno","Paris"));
		this.customers.add(new Customer("borat","Amsterdam"));
		this.customers.add(new Customer("aladeen","Hong Kong"));
		
		this.merchants = new ArrayList<>();
		this.merchants.add(new Merchant("jack","New York"));
		this.merchants.add(new Merchant("john","London"));
		this.merchants.add(new Merchant("james","Barcalona"));
		this.merchants.add(new Merchant("jeff","Moscow"));
		this.merchants.add(new Merchant("joseph","Delhi"));
		
		this.allCategories = new ArrayList<>();
		this.inventory = new ArrayList<>();
	}
	
	public String getName() {
		return this.coName;
	}
	
	public void displayMenu() {
		// Displaying company Menu
		System.out.println("Welcome to " + this.getName());
		System.out.println("1) Enter as Merchant");
		System.out.println("2) Enter as Customer");
		System.out.println("3) See user Details");
		System.out.println("4) Company account balance");
		System.out.println("5) Exit ");
	}
	
	public double getCompanyAccountBal() {
		return this.companyAccountBal;
	}

	public void addToInventory(Item item) {
		// Adding to inventory a list of all the items on the website
		this.inventory.add(item);
		if (!allCategories.contains(item.getCategory()))
			this.allCategories.add(item.getCategory());
	}
	
	public void displayMerchants() {
		// Displaying all the merchants present on the platform
		for (Merchant merchant : merchants) {
			System.out.println(merchant.getMerchantId() + " " + merchant.getName());
		}
	}
	
	public Merchant selectMerchant(int index) {
		// Selecting a merchant with given Id
		for (Merchant merchant : merchants) {
			if (merchant.getMerchantId() == index) {
				return merchant;
			}
		}
		return null;
	}
	
	public void displayCustomers() {
		// Display all the customer accounts on the platform
		System.out.println("Choose Customer");
		for (Customer customer : customers) {
			System.out.println(customer.getCustomerId() + " " + customer.getName());
		}
	}
	
	public Customer selectCustomer(int index) {
		// Selecting a particular 
		for (Customer customer : customers) {
			if (customer.getCustomerId() == index)
				return customer;
		}
		return null;
	}
	
	public void displayCategories() {
		int i = 0;
		for (String string : allCategories) {
			System.out.println(++i + " " + string);
		}
	}
	
	public ArrayList<Item> displaySelectedCategory(int index) {
		String category = allCategories.get(index); 
		ArrayList<Item> itemsOfCategory = new ArrayList<Item>();
		for(Item item : inventory) {
			if (item.getCategory().equals(category)) {
				System.out.println(item);
				itemsOfCategory.add(item);
			}
		}
		return itemsOfCategory;
	}
	
	public void addToBalance(double p) {
		this.companyAccountBal += p;
	}
}

public class OnlineMarketplace {

	public static void main(String[] args) {
		int flag = 0;
		Scanner in = new Scanner(System.in);
		EcomCompany mercury = new EcomCompany("Mercury");
		while (flag == 0) {
			mercury.displayMenu();
			int n = in.nextInt();
			switch(n) {
			case 1: 
				mercury.displayMerchants();
				int index = in.nextInt();
				Merchant merchant = mercury.selectMerchant(index);
				int exitflag = 0;
				while (exitflag == 0) {
					merchant.displayMenu();
					int option = in.nextInt();
					switch(option) {
					
					case 1:
						System.out.println("Enter item Details ");
						System.out.println("Item Name");
						String name = in.next();
						System.out.println("Item Price ");
						double price = in.nextDouble();
						System.out.println("Item Quantity");
						int quantity = in.nextInt();
						System.out.println("Item category");
						String category = in.next();
						Item newItem = new Item(name, category, quantity, price, 0, merchant.getName());
						mercury.addToInventory(newItem);
						merchant.addItem(newItem);
						break;
					
					case 2:
						System.out.println("Choose item by code");
						merchant.display();
						int itemCode = in.nextInt();
						System.out.println("Enter Edit Details");
						System.out.println("Item Price");
						int newPrice = in.nextInt();
						System.out.println("Item Quantity");
						int newQty = in.nextInt();
						merchant.editItem(itemCode, newQty, newPrice);
						break;
					
					case 3:
						mercury.displayCategories();
						int catChoice = in.nextInt();
						mercury.displaySelectedCategory(catChoice-1);
						break;
					
					case 4:
						System.out.println("Choose item by code");
						merchant.display();
						int ItemCode = in.nextInt();
						System.out.println("Choose Offer");
						System.out.println("1) buy one get one\r\n" + "2) 25% off");
						int choice = in.nextInt();
						merchant.addOffer(ItemCode, choice);
						break;
					
					case 5:
						System.out.println(merchant.getRewardSlots() + " extra slots have been awarded");
						break;
					
					case 6:
						exitflag = 1;
						break;
					}
				}
				break;
			
			case 2:
				mercury.displayCustomers();
				int custId = in.nextInt();
				Customer customer = mercury.selectCustomer(custId);
				int custExitFlag = 0;
				while (custExitFlag == 0) {
					customer.displayMenu();
					int custOption = in.nextInt();
					switch (custOption) {
					case 1:
						//Search using category;
						mercury.displayCategories();
						int catChoice = in.nextInt();
						ArrayList<Item> items = mercury.displaySelectedCategory(catChoice-1);
						System.out.println("Enter Item code");
						int itemChoice = in.nextInt();
						System.out.println("Enter Item Quantity");
						int itemQty = in.nextInt();
						Item chosenItem = null;
						for (Item item : items) {
							if (item.getItemId() == itemChoice) {
								chosenItem = item;
								break;
							}
						}
						if (chosenItem == null){
							System.out.println("Enter valid Id");
							continue;
						}
						System.out.println("Choose method of transaction\r\n" + 
								"1) Buy item\r\n" + 
								"2) Add item to cart\r\n" + 
								"3) Exit");
						int transactionCode = in.nextInt();
						switch(transactionCode) {
						case 1:
							double amt = customer.buyItem(chosenItem,itemQty);
							if (amt == 0) 
								System.out.println("OUT OF STOCK");
							else if (amt ==-1)
								System.out.println("OUT OF MONEY");
							else {
								System.out.println("Item bought Succesfully");
								mercury.addToBalance(0.01*amt);
								customer.incrementPurchaseCount();
								customer.incrementOrders();
								customer.checkForRewards();
								break;
							}
							break;
						case 2:
							customer.addToCart(chosenItem, itemQty);
							break;
						case 3:
							break;
						}
						
						break;
					case 2:
						//checkout cart
						double amt = customer.checkoutCart();
						mercury.addToBalance(0.01*amt);
						break;
					case 3:
						//Rewards Won
						System.out.println("Rewards won till date");
						System.out.println(customer.getTotalRewards());
						break;
					case 4 :
						//Print latest orders
						customer.getPreviousTransactions();
						break;
					case 5:
						custExitFlag = 1;
						break;
					}
				}
				break;
			
			case 3:
				System.out.println("Merchants");
				mercury.displayMerchants();
				System.out.println("Customers");
				mercury.displayCustomers();
				System.out.println("Choose user");
				String user = in.next();
				int id = in.nextInt();
				// Polymorphism
				Client client = null;
				switch(user) {
				case ("M"):
					client = mercury.selectMerchant(id);
					break;
				case("C"):
					client = mercury.selectCustomer(id);
					break;
				}
				client.displayDetails();
				break;
			
			case 4:
				System.out.println("COMPANY BALANCE : " + mercury.getCompanyAccountBal());
				break;
			
			case 5:
				flag = 1;
				break;
			}
		}
		in.close();
	}
}
