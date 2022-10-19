package com.PDFComparison;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFTextExtractor implements BasePDF{
	
	private  PDDocument _pddocument;
	private File _file;
    
    public PDDocument getPdfDocument()
    {
    	return this._pddocument;
    }
	public PDFTextExtractor(String filepath) throws NullPointerException, IOException
	{		
		_file = new File(filepath);
		if(_file.exists() && _file.isFile())
		{
			_pddocument = PDDocument.load(_file);      
		}
	}

	public String getFileName() {
		return _file.getName();
	}

	public int getNoOfPages() {
		int count =0;
		if(this._pddocument!=null)
		{
			count = _pddocument.getNumberOfPages();
		}
		return count;
	}

	public File getPdfFile(String filepath) throws NullPointerException {		
		return new File(filepath);
	}

	
	public static void main(String[] args) throws NullPointerException, IOException
	{
		PDFTextExtractor pdfTextExtractor = new PDFTextExtractor("C:/New folder/PDFs/BaseLine/Rewrite Quote.pdf");
		System.out.println(pdfTextExtractor.getFileName()+"   "+pdfTextExtractor.getNoOfPages());
		
	}

}
