package disruptor.firstdemo;

import java.util.Date;
import java.util.Random;

public class TestGenerateOneMillionNum {
	public static void main(String[] args) {
		Date bein = new Date();
		
		for (int i = 0; i < 1000000; i++){
			System.out.println(new Random().nextInt(2000));
			
		}
		Date end = new Date();
		System.out.println("test cost time : " + (end.getTime()-bein.getTime())+" ms");
	
	}
}
