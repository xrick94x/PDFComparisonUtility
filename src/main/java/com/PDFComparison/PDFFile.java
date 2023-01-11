package com.PDFComparison;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import com.PDFComparison.BasePDFFile.Comparison;
import com.PDFComparison.BasePDFFile.PDFDetails;

public class PDFFile  {
	
    public PDFFile(String filePath) throws NullPointerException, IOException
    {
      details = new PDFDetails(filePath);
      fileType = details.getFileType();
      comparison = fileType.equals("pdf") ? new TextComparison() : null;
      textFinder = new TextFinder(new PDFDetails(filePath));
    }
    
    private String fileType;
	
	private PDFDetails details;
	
	private Comparison comparison;
	
	private TextFinder textFinder;
	
	public PDFDetails getDetails() {
		return details;
	}

	public TextFinder get() throws NullPointerException, IOException {
		return textFinder;
	}
	
	public static void main(String[] args) throws Exception
	{
		//args = new String[]{"C:\\Users\\skum2\\Downloads\\CrimePDF.pdf","C:\\Users\\skum2\\Downloads\\CrimePDF (1).pdf","compareusingtext"};
		PDFFile baseFile =new PDFFile(args[0]);
		TextFinder basefinder = new TextFinder(baseFile.details);
		if(args.length > 1 && new File(args[1]).isFile() ) {
			PDFFile expectedFile = new PDFFile(args[1]);
		    Comparison comparison = new TextComparison();
		   
			TextFinder expectedfinder = new TextFinder(expectedFile.details);
				
			if(args[2].equalsIgnoreCase("compareusingtext"))
			{  
				if(args.length > 3)
				{
					comparison.CompareUsingText(baseFile, expectedFile, Arrays.asList(args[3].split(","))); 
				}
				else {
				comparison.CompareUsingText(baseFile, expectedFile, new ArrayList<String>())
				.forEach((key, value) -> System.out.println(key + ":" + value));
				}
			}
		}
		if(args[1].equalsIgnoreCase("getPDFPageMap"))
		{
			basefinder.getPDFPageMap().forEach((key, value) -> System.out.println(key + ":" + value));	 	
		}
		if(args[1].equalsIgnoreCase("getPagesContainingText"))
		{
			if(args[2].length() > 0) {
			basefinder.getPagesContainingText(args[2]).forEach( value -> {System.out.println(value);});}
		}
		
	
	}

}
