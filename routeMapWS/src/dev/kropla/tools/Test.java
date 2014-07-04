package dev.kropla.tools;

public class Test {
	public static void main(String[] args) {
		int pos;
		
		pos=0;
		
		int statusId=(pos==0?7:(pos==1?12:13));
		System.out.println("pos:"+pos +"| statusId:"+statusId);
		pos= 1;
		statusId=(pos==0?7:(pos==1?12:13));
		System.out.println("pos:"+pos +"| statusId:"+statusId);
	}
}
