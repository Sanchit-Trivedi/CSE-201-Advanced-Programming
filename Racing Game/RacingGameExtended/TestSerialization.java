import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

public class TestSerialization {
	@Test
	public void testSerialization() throws IOException {
		Scanner s = new Scanner("2");
		Computer myComp = new Computer(100);
		myComp.setUserName("Jacob");
		boolean users = true;
		User.Users = new HashMap<String, Computer>();
		
		try {
			myComp.startGame(s);
		} catch (SaveGameException sge) {
			User.Users.put(myComp.getUsername(), myComp);
			User.serialize();
		} catch (Exception e) {
			assertEquals(2,1);
		}
		HashMap <String,Computer> copy = User.Users;
		User.Users = new HashMap<String, Computer>();
		try {
			User.deserialize();
		}
		catch (Exception e){
			assertEquals(2, 1);
		}
		File userFile = new File("Users.txt");
		assertTrue(userFile.exists());
		assertTrue(User.Users instanceof HashMap);
		boolean flag = true;
		for (String str: copy.keySet()) {
			if (User.Users.containsKey(str) && copy.get(str).equals(User.Users.get(str))) {
				continue;
			}
			else {
				flag = true;
			}
		}
		assertTrue(flag);
		assertEquals(users, flag);
		User.Users.remove("Jacob");
		User.serialize();
		System.out.println("Exiting ...");
	}
}

