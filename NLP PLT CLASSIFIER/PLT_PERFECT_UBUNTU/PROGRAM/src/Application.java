package shihab;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import edu.illinois.cs.cogcomp.lbjava.*;
public class Application {

   public static void main(String[] args) {
            

            PLTclassifier sc = new PLTclassifier();
           
            System.out.print("Enter processed/annotated file location:\n ");
               
           
       
	File folder = new File("/home/shihab/data/test/1");
	File[] listOfFiles = folder.listFiles();

	for (int i = 0; i < listOfFiles.length; i++) {
      		if (listOfFiles[i].isFile()) {		
                myDocument news = new myDocument(listOfFiles[i]);
                String label = sc.discreteValue(news);

		if(label.equals("0")   )
		{
		
System.out.println("File Name: " + listOfFiles[i].getName()+ " Label: "+ label);

		}		


     		 } 
         }





               
             
    }
}
    
    
    
    
    
    

