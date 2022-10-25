package com.PDFComparison.BasePDFFile;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFDetails implements BaseFile {
	
	private  PDDocument _pddocument;
	
	private File _file;	
	
	public PDFDetails()
	{	
		
	}
    
    public PDDocument getPdfDocument()
    {
    	return this._pddocument;
    }
    
	public PDFDetails(String filepath) throws NullPointerException, IOException
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

	public String getFileType()
	{
		return FilenameUtils.getExtension(_file.getAbsolutePath());
	}

	public File getPdfFile(String filepath) throws NullPointerException {		
		return new File(filepath);
	}
	
	public int getNoOfPages() {
		int count =0;
		if(_pddocument!=null)
		{
			count = _pddocument.getNumberOfPages();
		}
		return count;
	}

}
