import java.util.*;

public class Fibonacci {
	Integer result, n;
	private static Map<Integer, Fibonacci> instances = new HashMap<Integer, Fibonacci>();
	
	//FlyWeight
	public static Fibonacci getInstance(Integer key) {
		if (!instances.containsKey(key)) {
			instances.put(key, new Fibonacci(key));
		}
		return instances.get(key);
	}

	public Fibonacci(Integer n) {
		this.n = n;
		calculate();
	}

	public static Integer fib(Integer n) {
		if (n < 2)
			return n;
		else
			return getInstance(n - 1).getResult() + getInstance(n-2).getResult();
	}

	public void calculate() {
		result = fib(n);
	}

	public Integer getResult() {
		return result;
	}
}
