package com.PDFComparison;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.directory.InvalidAttributeValueException;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.PDFComparison.BasePDFFile.Comparison;
import com.PDFComparison.BasePDFFile.PDFDetails;

class TextComparison extends Comparison {
	
	protected HashMap<Integer, String> differenceMap;
	
	   private PDFTextStripper pdfTextStripper;
	   
	   private List<PDDocument> docList;
	   
		protected String getTextFromPDF(int i, PDDocument Pdf) throws IOException
		{
			if(pdfTextStripper==null)
			{
				pdfTextStripper = new PDFTextStripper();
			}
			pdfTextStripper.setStartPage(i);
			pdfTextStripper.setEndPage(i);
			pdfTextStripper.setSortByPosition(true);
			String text = pdfTextStripper.getText(Pdf);
			return text;
		}
		
		protected String ignoreText(String text, String expectedPDFText) throws InvalidAttributeValueException
		{
			if(text==null || expectedPDFText == null){
				throw new InvalidAttributeValueException("Text or expectedPDFText can't be null");
			}
			String finalText=expectedPDFText;
			String[] arr = text.split(",");
			List<String> textList = java.util.Arrays.asList(arr);
			for (int i =0; i <textList.size(); i++)
			{
				String regex = getRegex(textList.get(i));
				Pattern reg = Pattern.compile(regex);
				Matcher match = reg.matcher(finalText);
				finalText = match.replaceAll("");	
			}
			
			return finalText;
		}
		
		protected String getRegex(String text)
		{
			 StringBuilder stringBuilder = new StringBuilder();

		        for (int i = 0; i < text.length(); i++) {
		            char c = text.charAt(i);

		            if (Character.isDigit(c)) {
		                stringBuilder.append("\\d");
		            } else if (Character.isLetter(c)) {
		                stringBuilder.append("\\w");
		            } else { 
		                stringBuilder.append(c);
		            }
		        }
		        return stringBuilder.toString();
		}

	
	@Override
	public HashMap<Integer, String> CompareUsingText(PDFFile basePdffile, PDFFile actualPdffile, String textToIgnore) throws IOException, InvalidAttributeValueException {
		
		PDFDetails basePdf = basePdffile.getDetails();
		PDFDetails actualPdf = actualPdffile.getDetails();
		differenceMap = new HashMap<Integer, String>();
		docList = new ArrayList<PDDocument>();
		if(basePdf.getFileType().toLowerCase().equals(actualPdf.getFileType().toLowerCase()))
		{	
			String fileType = basePdf.getFileType().toLowerCase();
			boolean areEqual = false;
			String textdiff = "";	
			
			if(fileType.contains("pdf")) {

				System.out.println("Comparing Files - "+basePdf.getFileName()+" and "+actualPdf.getFileName());
				int baseCount = basePdf.getNoOfPages();
				int actCount = actualPdf.getNoOfPages();
				areEqual = baseCount == actCount ? true:false;
				if(!areEqual)
				{
					 System.out.println("BaseLine and Actual PDFs pages' count are not equal");
					 System.out.println("Baseline's file page count "+baseCount);
					 System.out.println("Actual file's page count "+actCount);
					 return differenceMap;
				}
	            PDDocument expectedPdf = basePdf.getPdfDocument();
	            PDDocument newPdf = actualPdf.getPdfDocument();
	            docList.add(expectedPdf);
	            docList.add(newPdf);
	            for(int i = 0; i < baseCount; i++) {
	           	 String expectedPDFText = ignoreText(textToIgnore,getTextFromPDF(i+1,expectedPdf));
	   			 String actualPDFText = ignoreText(textToIgnore,getTextFromPDF(i+1,newPdf));
	   			 if(!expectedPDFText.equals(actualPDFText))
	   			  {
	   				 textdiff = StringUtils.difference(expectedPDFText, actualPDFText);
	   				 differenceMap.put((i+1), textdiff);
	   				 System.out.println("Difference found on page - "+(i+1));
	   			  } 
	   			 else
	   			 {
	   				differenceMap.put(i, textdiff); 
	   			 }
	            }
	  
			 }
		 }
			
	  return differenceMap;
	}
		

 } 

