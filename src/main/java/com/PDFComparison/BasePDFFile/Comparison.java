package com.PDFComparison.BasePDFFile;

import java.util.HashMap;

import com.PDFComparison.PDFFile;

public abstract class Comparison extends PDFDetails{

	protected String getfileType()
	{
	  return super.getFileType();	
	}
		
	public abstract HashMap<Integer, String> Text(PDFFile pf, PDFFile pf2, String ignoreText) throws Exception;
	    
}

