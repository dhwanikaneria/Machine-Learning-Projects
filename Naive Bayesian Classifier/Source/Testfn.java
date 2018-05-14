package com.naive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Testfn {
	ArrayList<ArrayList<HashMap<String,Integer>>> confMetrics= new ArrayList<ArrayList<HashMap<String,Integer>>>();
	HashMap<String,Integer> hmclass= new HashMap<String,Integer>();
	
	public void findprob(String testingPath) {
		File f=new File(testingPath);
		a=setmap(a);
		listgetfile(f);
		print(a);
		System.out.println("Total Accuracy:"+(total-wrong)/(double)total*100);
	}

	private void print(int[][] a) {
	System.out.println("****************Confusion Metrics**************");
		System.out.print("                  ");
		for(int i=0;i<a.length-1;i++) {
			System.out.print("  "+Bayes.g[i].getName());
		}
		System.out.println("   Total");
		for(int i=0;i<a.length-1;i++) {
			int total=0;
			System.out.print(Bayes.g[i].getName() );
			for(int j=0;j<a.length-1;j++) {
total+=a[i][j];
				System.out.print("   " + a[i][j] +"   ");}
		System.out.println(" | "+total);
		a[i][a.length-1]=total;
		}
		System.out.println("-----------------------------------------------------------------");
		System.out.print("Total           ");
		for(int i=0;i<a.length-1;i++) {
			int total1=0;
			
			for(int j=0;j<a.length-1;j++) {
				total1+=a[j][i];}
			a[a.length-1][i]=total1;
			System.out.print(total1+"    ");
			
		}
		System.out.println();
		System.out.println("-----------------------------------------------------------------");
		
for(int i=0; i<a.length-1;i++) {
	System.out.println("Class : "+ Bayes.g[i].getName());
	System.out.println("            Precision : " +a[i][i]/(double)a[a.length-1][i]);
	System.out.println("            Recall : " +a[i][i]/(double)a[i][a.length-1]);
	System.out.println("            F-score : " +a[i][i]/(double)a[i][a.length-1]*a[i][i]/(double)a[a.length-1][i]*2/(a[i][i]/(double)a[a.length-1][i]+a[i][i]/(double)a[i][a.length-1]));

}
	}

	private int[][] setmap(int[][] a2) {
		for(int i=0;i<a2.length-1;i++) {
			for(int j=0;j<a2.length-1;j++)
			a2[i][j]=0;
			hmclass.put(Bayes.g[i].getName(), i);
		}
		
	
		return a2;
	}

	

	private void listgetfile(File f) {
		int i=0;
		for(File file : f.listFiles())
		{
			if(file.isDirectory())
			{	
				listgetfile(file);
			}
			else
			{
				openFile(file.getPath(),f);
			}
		}
		

		
	}
int total=0;
int wrong=0;
int[][] a=new int[Bayes.g.length+1][Bayes.g.length+1];
	private void openFile(String path, File f) {
	
		ArrayList<Double> classprob=new ArrayList<>(); 
		Integer c=-1;
		
		String str;
		File file = new File(path);
		FileReader fr;
		try {
			fr = new FileReader(file);
		
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> dl= new ArrayList<String>();
		ArrayList<String> after= new ArrayList<String>();
		removestop rs= new removestop();
		
			while((str =br.readLine())!=null)
			{								
				if(str.contains("Lines:"))
				{
					break;
				}													
			}
			while((str=br.readLine())!=null)
			{
				dl.add(str.trim().toLowerCase());
			}
			fr.close();
			after=rs.remove(dl);
			
			findProb1(after,classprob);
			c=findPrediction(classprob);
			if(!Bayes.g[c].getName().equals(f.getName())) {
				a[hmclass.get(Bayes.g[c].getName())][hmclass.get(f.getName())]++;
				wrong++;
				
			}
			a[hmclass.get(Bayes.g[c].getName())][hmclass.get(f.getName())]++;
			total++;
			classprob.clear();
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

	}
	
	
	private int findPrediction(ArrayList<Double> classprob2) {
	
		int c=0;
		int a=classprob2.size();
	double max=classprob2.get(0);
		
	for(int i=1;i<a;i++) {
			if(max<classprob2.get(i)) {
				max=classprob2.get(i);
			c=i;
			}
		}
	return c;
	}

	private void findProb1(ArrayList<String> after, ArrayList<Double> classprob) {
	
		int l=after.size();
		int k=Bayes.arrh.size();
		double prob=0.0;
		for(int j=0;j<k;j++) {
			prob=Math.log((double)Bayes.arrh.get(j).getLength()/Bayes.total);///Math.log(2);
		for(int i=0;i<l;i++) {
		
			double p=0.0;
				if(Bayes.arrh.get(j).getHm().containsKey(after.get(i))){
					p=(double)Bayes.arrh.get(j).getHm().get(after.get(i));
				}
				
				prob+=Math.log((p+1.0)/((double)Bayes.arrh.get(j).count+Bayes.unique.size()));///Math.log(2);
		}
		classprob.add(prob);
		
			
		}
		
	}

}
