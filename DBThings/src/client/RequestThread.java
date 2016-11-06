package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class RequestThread implements Runnable
{
	HttpURLConnection conn;
    BufferedReader rd;
    String line;
    String result = "";
    
    URL url;
    
    int dbCount = 0;
    int currentId = 0;
    int threadNumber;
    
    StringBuilder buffer;
    
    public static AtomicLong sum = new AtomicLong(0);
    public static AtomicInteger delimeter = new AtomicInteger(0);
    
    public static void printAvarage () {
    	System.out.println("Average time: " + sum.get()/delimeter.get() + " ms");
    }
	
    public RequestThread(int dbCount, int threadNumber) {
		this.dbCount = dbCount;
		this.threadNumber = threadNumber;
		
		buffer = new StringBuilder();
	}
    
  @Override
  public void run()
  {
	  int step = 0;
	  while (step < 20){ //use while (true) //for doing continuous requests
		   currentId = ThreadLocalRandom.current().nextInt(1, dbCount);
		   long start = System.currentTimeMillis();
		   step++;
		   try {
		         url = new URL("http://localhost:4567/select/" + currentId);
		         conn = (HttpURLConnection) url.openConnection();
		         conn.setRequestMethod("GET");
		         conn.setConnectTimeout(1000);
		         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		         while ((line = rd.readLine()) != null) {
		            result += line;
		         }
		         rd.close();
		         
		         long end = System.currentTimeMillis();
		         
		         buffer.append(end - start + " ms ; Thread № " + threadNumber + "; ID: " + currentId + "; Response: " + result + "\n");
		         
		         sum.getAndAdd(end - start);
		         result = "";
		         //Thread.sleep(1);
		         delimeter.incrementAndGet();
		      } catch (java.net.SocketTimeoutException e) { 
				System.out.println("Timeout Exception");
		      } catch (java.net.ConnectException e) { 
				System.out.println("Connection Error");
		      } catch (Exception e) {
		         e.printStackTrace();
		      }	   
	   }

		System.out.println(buffer.toString());
  }
}
