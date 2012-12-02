package sort;

import java.util.*;

public class Sort {
	public static void main(String[] args) {
		int[] data = {3,4,1,2,2,100,4,9,10,-1};
		bubbleSort(data.clone());
		quickSort1(data.clone());
		quickSort2(data.clone());
		directInsertSort(data.clone());
		binaryInsertSort(data.clone());
		shellSort(data.clone());
		simpleSelectSort(data.clone());
		heapSort(data.clone());
		mergeSort1(data.clone());
		mergeSort2(data.clone());
	}
	
	//swap sort
	public static int[] bubbleSort(int[] data) {
		for(int i=0;i<data.length;i++) {
			for(int j=0;j<data.length-1-i;j++) {
				if(data[j]>data[j+1]) {
					int temp  = data[j];
					data[j]   = data[j+1];
					data[j+1] = temp;
				}
			}
		}
		
		System.out.print("bubbleSort: ");
		printData(data);
		return data;
	}
	
	private static int partition(int[] data,int st,int ed) {
		if(st>=ed) return -1;
		
		int midValue = data[st];
		int i=st; int j=ed;
		while(i<j) {
			while(i<j && data[j]>=midValue) j--;
			data[i] = data[j];
			while(i<j && data[i]<midValue) i++;
			data[j] = data[i];
		}
		
		data[i] = midValue;
		return i;
	}
	
	private static void qSort1(int[] data,int st,int ed) {
		if(st>=ed) return;
		
		int pivot = partition(data,st,ed);
		qSort1(data,st,pivot-1);
		qSort1(data,pivot+1,ed);
	}
	
	private static void qSort2(int[] data,int st,int ed) {
		if(st>=ed) return;
		
		Stack<Integer> stk = new Stack<Integer>();
		stk.push(st); stk.push(ed);
		
		int low,high;
		while(!stk.empty()) {
			high = stk.peek(); stk.pop();
			low  = stk.peek(); stk.pop();
			
			int pivot = partition(data,low,high);
			
			if(pivot-1>low) {
				stk.push(low);
				stk.push(pivot-1);
			}
			if(pivot+1<high) {
				stk.push(pivot+1);
				stk.push(high);
			}
		}
	}
	
	public static int[] quickSort1(int[] data) {
		qSort1(data,0,data.length-1);
		
		System.out.print("quickSort1: ");
		printData(data);
		return data;
	}
	
	public static int[] quickSort2(int[] data) {
		//non-recurrsive
		qSort2(data,0,data.length-1);
		
		System.out.print("quickSort2: ");
		printData(data);
		return data;
	}
	
	//insert sort
	public static int[] directInsertSort(int[] data) {
		for(int i=1;i<data.length;i++) {
			int temp = data[i];
			int j;
			for(j=i-1;j>=0 && data[j]>=temp;j--)
				data[j+1] = data[j];
			data[j+1] = temp;
		}
		
		System.out.print("directInsertSort: ");
		printData(data);
		return data;
	}
	
	public static int[] binaryInsertSort(int[] data) {
		for(int i=1;i<data.length;i++) {
			int low = 0; int high = i-1;
			int temp = data[i];
			while(low<=high) {
				int mid = (low+high)/2;
			    if(data[mid]<temp) low = mid+1;
			    else high = mid-1;
			}
			
			int j;
			for(j=i-1;j>=high+1;j--)
				data[j+1] = data[j];
			data[j+1] = temp;
		}
		
		System.out.print("binaryInsertSort: ");
		printData(data);
		return data;
	}
	
	public static int[] shellSort(int[] data) {
		for(int g = data.length/2;g>0;g/=2) {
			for(int i=g;i<data.length;i++) {
				int temp = data[i];
				int j;
				for(j=i-g;j>=0 && data[j]>=temp;j-=g)
					data[j+g] = data[j];
				data[j+g] = temp;
			}
		}
		
		System.out.print("shellSort: ");
		printData(data);
		return data;
	}
	
	//select sort
	public static int[] simpleSelectSort(int[] data) {
		for(int i=0;i<data.length;i++) {
			int min = i;
			for(int j=data.length-1;j>i;j--)
				if(data[j]<data[min]) min = j;
			
			int temp = data[min];
			data[min] = data[i];
			data[i] = temp;
		}
		
		System.out.print("simpleSelectSort: ");
		printData(data);
		return data;
	}
	
	private static void heapAdjust(int[] data,int st,int ed) {
		if(st>=ed) return;
		
		int temp = data[st];
		for(int j=st*2;j<=ed;j*=2) {
			if(j+1<=ed && data[j+1]>data[j]) j++;
			if(data[j]>temp) {
				data[st] = data[j];
				st = j;
			}
		}
		data[st] = temp;
	}
	
	public static int[] heapSort(int[] data) {
		
		for(int i=data.length/2;i>=0;i--)
			heapAdjust(data,i,data.length-1);
		
		for(int j=0;j<data.length;j++) {
			int temp = data[data.length-1-j];
			data[data.length-1-j] = data[0];
			data[0] = temp;
			
			heapAdjust(data,0,(data.length-1-j)-1);
		}
		
		System.out.print("heapSort: ");
		printData(data);
		return data;
	}
	
	                                                                 //mid!!!!
	private static void merge(int[] data,int[] target, int st, int ed, int mid) {
		if(st>=ed) return;
		
		int i=st, j=mid+1, k=st;
		while(i<=mid && j<=ed)
			target[k++] = data[i]<=data[j]?data[i++]:data[j++];
		
		while(i<=mid) target[k++] = data[i++];
		while(j<=ed)  target[k++] = data[j++];
		
		//copy back
		for(int n=st;n<=ed;n++)
			data[n] = target[n];
	}
	
	private static void MSort1(int[] data,int[] target, int st, int ed) {
		if(st>=ed) return;
		
		int mid = (st+ed)/2;
		MSort1(data,target,st,mid);
		MSort1(data,target,mid+1,ed);
		merge(data,target,st,ed,mid);
	}
	
	private static void MSort2(int[] data,int[] target, int st, int ed) {
		int step = 2;
		int i;
		while(step<=data.length) {
			i = st;
			while(i+step<=data.length) {
				merge(data,target,i,i+step-1,i+(step-1)/2);
				i+=step;
			}
			merge(data,target,i,ed,(i+ed)/2);    //mid!!!!
			
			step*=2;
		}
		
		merge(data,target,st,ed,st+step/2-1);    //mid!!!!
	}
	
	//merge sort
	public static int[] mergeSort1(int[] data) {
		int[] target = new int[data.length];
		MSort1(data,target,0,data.length-1);
		
		System.out.print("mergeSort1: ");
		printData(data);
		return data;
	}
	
	public static int[] mergeSort2(int[] data) {
		//non-recurrsive
		int[] target = new int[data.length];
		MSort2(data,target,0,data.length-1);
		
		System.out.print("mergeSort2: ");
		printData(data);
		return data;
	}
	
	private static void printData(int[] data) {
		if(data==null) return;
		for(int i:data)
			System.out.print(i+" ");
		System.out.println();
	}
}
