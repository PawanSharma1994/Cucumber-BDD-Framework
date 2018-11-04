package com.automation.commonutils;

import java.io.File;
import org.apache.log4j.Logger;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRReader implements Log4Interface {

	private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "\\ImageRepository\\";
	private static ITesseract itTesseract;
	private static OCRReader ocr;

	static {
		itTesseract = new Tesseract();
	}

	private OCRReader() {
		log.info("Private Constructor!!");
	}

	public static OCRReader get() {
		if (ocr == null)
			ocr = new OCRReader();
		return ocr;
	}

	public static ITesseract getOCR() {
		if (itTesseract == null) {
			itTesseract = new Tesseract();
		}
		return itTesseract;
	}

	public String fetchTextFromImageOCR(String imgFileName) {
		try {
			return itTesseract.doOCR(new File(IMAGE_DIRECTORY + imgFileName));
		} catch (TesseractException ex) {
			log.error(ex.getMessage());
			return "Error in reading image File!!";
		}
	}

	@Override
	public Logger getLogs() {
		return null;
	}

}
