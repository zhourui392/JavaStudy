package javatest;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeDemo {
	public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
		A o1 = new A(); // constructor
		o1.a(); // prints 1
		 
		A o2 = A.class.newInstance(); // reflection
		o2.a(); // prints 1
		 
		Field f = Unsafe.class.getDeclaredField("theUnsafe"); //Internal reference  
		f.setAccessible(true);  
		Unsafe unsafe = (Unsafe) f.get(null); 
		A o3 = (A) unsafe.allocateInstance(A.class); // unsafe
		o3.a(); // prints 0
	}
	
	
}

class A{
    private long a; // not initialized value
 
    public A() {
        this.a = 1; // initialization
    }
 
    public void a() { System.out.println(this.a); }
}
