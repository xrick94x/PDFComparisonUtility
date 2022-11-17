package com.PDFComparison.BasePDFFile;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import com.PDFComparison.PDFFile;

public abstract class Comparison {
		
	public abstract HashMap<Integer, String> CompareUsingText(PDFFile pf, PDFFile pf2, List<String> ignoreText) throws Exception;
	
	public void CompareUsingImages(PDFFile pf, PDFFile pf2, List<String> ignoreText) 
	{
		throw new NotImplementedException("It's not implemented yet");
	}
	    
}

