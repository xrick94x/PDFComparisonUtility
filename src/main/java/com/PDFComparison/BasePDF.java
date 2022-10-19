package com.PDFComparison;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface BasePDF {
	
	public String getFileName();
	
	public int getNoOfPages();
	
	public PDDocument getPdfDocument();

}
