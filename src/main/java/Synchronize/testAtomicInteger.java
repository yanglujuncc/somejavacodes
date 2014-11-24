package Synchronize;

import java.util.concurrent.atomic.AtomicInteger;

public class testAtomicInteger {
	public static void main(String[] args){
		AtomicInteger  aAtomicInteger =new AtomicInteger (12);
		
		System.out.println(aAtomicInteger.toString());
		aAtomicInteger.addAndGet(10);
		System.out.println(aAtomicInteger.toString());
		aAtomicInteger.decrementAndGet();//--
		System.out.println(aAtomicInteger.toString());
		aAtomicInteger.incrementAndGet();//++
		System.out.println(aAtomicInteger.toString());
	}
}
