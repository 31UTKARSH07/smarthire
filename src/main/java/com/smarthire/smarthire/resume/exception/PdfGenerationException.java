package com.smarthire.smarthire.resume.exception;

public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String message) {
        super("Failed to generate PDF : "+ message);
    }
    public PdfGenerationException(String message , Throwable cause){
        super("Failed to Generate PDF : "+ message,cause);
    }
}
