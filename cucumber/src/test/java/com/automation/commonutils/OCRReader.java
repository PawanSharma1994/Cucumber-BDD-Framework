package com.automation.commonutils;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRReader {

	private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "\\ImageRepository\\";
	private static ITesseract itTesseract;
	
	static {
		itTesseract = new Tesseract();
	}


	public static String fetchTextFromImage_OCR(String imgFileName){
		try{
		String extractedText = itTesseract.doOCR(new File(IMAGE_DIRECTORY + imgFileName));
		return extractedText;
		}catch(TesseractException ex){
			System.err.println(ex.getMessage());
			return "Error in reading image File!!";
		}
	}
	
}
