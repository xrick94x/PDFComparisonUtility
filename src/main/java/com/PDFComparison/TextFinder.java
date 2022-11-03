/**
 * 
 */
package com.PDFComparison;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.directory.InvalidAttributeValueException;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.PDFComparison.BasePDFFile.PDFDetails;

/**
 * @author Saurabh
 *
 */
public class TextFinder {
	
	private PDFDetails _details = null;
	
	private TextComparison _comparison;
	
	private  HashMap<Integer, String> textMap ;
	
	public TextFinder(PDFDetails details) throws NullPointerException, IOException
	{		
		_details = new PDFDetails();
		_details = details;
		_comparison = new TextComparison();
	}
	
	private String getMatchingText(String text,String textToMatch) 
	{
		StringBuilder finalText= new StringBuilder();
		String regex = _comparison.getRegex(textToMatch);
		Pattern reg = Pattern.compile(regex);
		Matcher match = reg.matcher(text);
		while(match.find())
		{
			finalText.append(match.group()+";");
		}
		return finalText.toString();
	}
	
	
	/**
	 * @author Saurabh
	 * Gets the text for every page of a PDF file along with page number 
	 * @throws IOException 
	 * @throws InvalidAttributeValueException 
	 */
     public HashMap<Integer, String> getPDFPageMap() throws IOException, InvalidAttributeValueException
     {
         if(textMap==null)
         {
	         int pagesCount = _details.getNoOfPages();
	         textMap = new HashMap<Integer, String>();
	         PDDocument pdfDoc = _details.getPdfDocument();
	         System.out.println("Extracting text of file- "+_details.getFileName());
	         for (int i = 0; i < pagesCount; i++)
	         {
	        	 String text = _comparison.ignoreText(new ArrayList<String>(),_comparison.getTextFromPDF(i+1,pdfDoc));
	        	 textMap.put(i+1, text);
	         }
	         pdfDoc.close();
	         System.out.println("Extracting Done");
         }
         return textMap;
     }
     
     /**
 	 * @author Saurabh
 	 * Gets the pages number and text matching pattern for every pages in a Pdf,
 	 * @throws IOException 
     * @throws InvalidAttributeValueException 
 	 */
      public HashMap<Integer, String> getMatchingTextWithPageNo(String textToMatch) throws IOException, InvalidAttributeValueException
      {
          if(textMap==null)
          {
        	  getPDFPageMap();
          }
          HashMap<Integer, String> pageMap = new HashMap<Integer, String>();
          for (int i = 0; i < textMap.size(); i++)
	       {
	         pageMap.put((i+1), getMatchingText(textMap.get((i+1)),textToMatch));
	       }
          
          return pageMap;
      }
     
     /**
      * 
      * @param text
      * @return List of page numbers containing the given text
      * @throws Exception
      */
     public List<Integer> getPagesContainingText(String text) throws Exception
     {
    	 List<Integer> textFinder = new ArrayList<Integer>();
    	 if(textMap==null)
    	 {
    		getPDFPageMap();
    	 }
    	 else
    	 {
    		 for (int i =1; i < textMap.size(); i++)
    		 {
    			 if(textMap.get(i).contains(text))
    			 {
    				 textFinder.add(i);
    			 }
    		 }
    	 }
    	 return textFinder;
     }
}
