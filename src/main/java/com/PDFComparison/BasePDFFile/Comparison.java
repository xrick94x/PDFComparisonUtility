package com.PDFComparison.BasePDFFile;

import java.util.HashMap;

import com.PDFComparison.PDFFile;

public abstract class Comparison {
		
	public abstract HashMap<Integer, String> CompareUsingText(PDFFile pf, PDFFile pf2, String ignoreText) throws Exception;
	    
}

