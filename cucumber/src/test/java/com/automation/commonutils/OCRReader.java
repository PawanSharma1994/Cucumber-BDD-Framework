package com.automation.commonutils;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRReader {

	private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "\\ImageRepository\\";
	private static ITesseract itTesseract;
	private static OCRReader ocr;
	
	static {
		itTesseract = new Tesseract();
	}

	private OCRReader(){
		System.out.println("Private Constructor!!");
	}
	
	public static OCRReader get(){
		if(ocr==null)
			ocr = new OCRReader();
		return ocr;
	}
	
	public static ITesseract getOCR(){
		if(itTesseract==null){
			itTesseract = new Tesseract();
		}
		return itTesseract;
	}
	
	public String fetchTextFromImage_OCR(String imgFileName) {
		try {
			String extractedText = OCRReader.getOCR().doOCR(new File(IMAGE_DIRECTORY + imgFileName));
			return extractedText;
		} catch (TesseractException ex) {
			System.err.println(ex.getMessage());
			return "Error in reading image File!!";
		}
	}

	
}
