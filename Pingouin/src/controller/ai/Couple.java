package controller.ai;

public class Couple<T1, T2> implements Cloneable{
	
	T1 first;
	T2 second;
	
	public Couple (T1 v1, T2 v2){
		first = v1;
		second = v2;
	}
	
	public T1 getFirst(){
		return first;
	}
	
	public void setFirst(T1 v){
		first = v;
	}
	
	public T2 getSecond(){
		return second;
	}
	
	public void setSecond(T2 v){
		second = v;
	}
	
	public Couple<T1, T2> clone(){
		Couple<T1, T2> res = new Couple<T1, T2>(this.getFirst(), this.getSecond());
		return res;
	}
	
	
}
