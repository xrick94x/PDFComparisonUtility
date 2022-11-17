package com.PDFComparison;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	

}
