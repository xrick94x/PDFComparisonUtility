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

import org.apache.pdfbox.pdmodel.PDDocument;

import com.PDFComparison.BasePDFFile.PDFDetails;

/**
 * @author Saurabh
 *
 */
public class TextFinder extends TextComparison {
	
	private PDFDetails _details = null;
	
	private  HashMap<Integer, String> textMap ;
	
	public TextFinder(PDFDetails details) throws NullPointerException, IOException
	{		
		_details = new PDFDetails(details.getFileType());
		_details = details;
	}
	
	private String getMatchingText(String text,String textToMatch) 
	{
		String finalText="";
		String regex = getRegex(textToMatch);
		Pattern reg = Pattern.compile(regex);
		Matcher match = reg.matcher(text);
		while(match.find())
		{
			finalText = match.group();
		}
		return finalText;
	}
	
	
	/**
	 * @author Saurabh
	 * Gets the text for every page of a PDF file along with page number 
	 * @throws IOException 
	 */
     public HashMap<Integer, String> getPDFPageMap() throws IOException
     {
         if(textMap==null)
         {
	         int pagesCount = _details.getNoOfPages();
	         textMap = new HashMap<>();
	         PDDocument pdfDoc = _details.getPdfDocument();
	         for (int i = 0; i < pagesCount; i++)
	         {
	        	 String text = ignoreText("",getTextFromPDF(i+1,pdfDoc));
	        	 textMap.put(i+1, text);
	         }
	         pdfDoc.close();
         }
         return textMap;
     }
     
     /**
 	 * @author Saurabh
 	 * Gets the pages number and text matching pattern for every pages in a Pdf,
 	 * @throws IOException 
 	 */
      public HashMap<Integer, String> getMatchingTextWithPageNo(String textToMatch) throws IOException
      {
          if(textMap==null)
          {
        	  getPDFPageMap();
          }
          HashMap<Integer, String> pageMap = new HashMap<>();
          for (int i = 0; i < textMap.size(); i++)
	       {
	         pageMap.put((i+1), getMatchingText(textMap.get((i+1)),textToMatch));
	       }
          
          return pageMap;
      }
     
     
     public List<Integer> getPagesContainingText(String text) throws Exception
     {
    	 List<Integer> textFinder = new ArrayList<>();
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
