package com.PDFComparison.BasePDFFile;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface BaseFile {
	
	public String getFileName();
		
	public PDDocument getPdfDocument();
	
	public String getFileType();
	
}
