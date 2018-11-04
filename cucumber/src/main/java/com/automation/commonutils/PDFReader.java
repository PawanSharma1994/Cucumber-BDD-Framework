package com.automation.commonutils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader implements Log4Interface {

	private static PDFReader pdfreader;

	private PDFReader() {
		if (pdfreader == null) {
			throw new RuntimeException("Use getPDFReader method");
		}
	}

	public static PDFReader getPDFReader() {
		if (pdfreader == null) {
			pdfreader = new PDFReader();
		}
		return pdfreader;
	}

	/**
	 * Read pdf file by passing URL(mention http while passing the param)
	 * 
	 * @author pawan
	 * @param fileName
	 * @return
	 * @throws IOException
	 */

	public String getTextFromPDF(String fileName) throws IOException {

		URL url;
		if (fileName.contains("http")) {
			url = new URL(fileName);
		} else {
			url = new URL("file:///" + fileName);
		}
		InputStream inputStream = url.openStream();
		BufferedInputStream fileParse = new BufferedInputStream(inputStream);
		PDDocument pdfDocument = null;
		pdfDocument = PDDocument.load(fileParse);
		return new PDFTextStripper().getText(pdfDocument);
	}

	@Override
	public Logger getLogs() {
		return null;
	}

}
