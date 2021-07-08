
public class Consumer implements Runnable  {
	
	private Object lock = new Object();
	
	public Consumer() {
		
	}
	
	@Override
	public synchronized void run() {
		try {
			while (true) {
				if (!Producer.inputs.isEmpty()) {
					Long start = System.nanoTime();
					Integer result = Fibonacci.getInstance(Producer.inputs.poll()).getResult();
					Long end = System.nanoTime();
					Long time = end - start;
					Producer.time.add(time);
					Producer.outputs.add(result);
				}
			}
		}
		catch(Exception e) {
			
		}
	}
	
}
