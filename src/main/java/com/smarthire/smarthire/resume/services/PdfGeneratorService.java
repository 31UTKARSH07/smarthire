package com.smarthire.smarthire.resume.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.smarthire.smarthire.resume.exception.PdfGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class PdfGeneratorService {
    public byte[] generatePdf(String html) {
        log.info("Starting PDF generation from HTML");
        log.debug("HTML length: {} characters", html != null ? html.length() : 0);

        if(html == null || html.trim().isEmpty()){
            throw new PdfGenerationException("HTML content is EMPTY");
        }
        if(!isValidHtml(html)){
            throw new PdfGenerationException("Invalid HTML structure");
        }

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(html,null);
            builder.toStream(outputStream);

            builder.run();

            byte[] pdfBytes = outputStream.toByteArray();

            log.info("PDF generated successfully , size: {} bytes ({} KB)",
            pdfBytes.length,pdfBytes.length / 1024);

            return pdfBytes;
        }catch (IOException e){
            log.error("IO error during PDF generation", e);
            throw new PdfGenerationException("IO error: " + e.getMessage(), e);
        }catch (Exception e) {
            log.error("Failed to generate PDF",e);
            throw new PdfGenerationException("Conversion error:" + e.getMessage(),e);
        }
    }
    public byte[] generatePdfWithCustomSize(String html, float pageWidth, float pageHeight) {
        log.info("Generating PDF with custom size: {}x{} inches", pageWidth, pageHeight);

        if (!isValidHtml(html)) {
            throw new PdfGenerationException("Invalid HTML structure");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);

            // Set custom page size
            builder.useDefaultPageSize(
                    pageWidth,
                    pageHeight,
                    PdfRendererBuilder.PageSizeUnits.INCHES
            );

            builder.toStream(outputStream);
            builder.run();

            byte[] pdfBytes = outputStream.toByteArray();
            log.info("Custom-sized PDF generated: {} bytes", pdfBytes.length);

            return pdfBytes;

        } catch (Exception e) {
            log.error("Failed to generate custom-sized PDF", e);
            throw new PdfGenerationException("Custom size conversion failed", e);
        }
    }
    public byte[] generatePdfLandscape(String html) {
        log.info("Generating PDF in landscape orientation");
        return generatePdfWithCustomSize(html, 11f, 8.5f);
    }
    public byte[] generatePdfAdvanced(String html) {
        log.info("Generating PDF with advanced settings");

        if (!isValidHtml(html)) {
            throw new PdfGenerationException("Invalid HTML structure");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.useColorProfile(null);                    // Color profile
            builder.usePdfAConformance(                       // PDF/A compliance
                    PdfRendererBuilder.PdfAConformance.PDFA_1_B
            );
            builder.run();

            byte[] pdfBytes = outputStream.toByteArray();
            log.info("Advanced PDF generated: {} bytes", pdfBytes.length);
            return pdfBytes;
        } catch (Exception e) {
            log.error("Failed to generate advanced PDF", e);
            throw new PdfGenerationException("Advanced conversion failed", e);
        }
    }

    public boolean isValidHtml(String html) {
        if (html == null || html.trim().isEmpty()) {
            return false;
        }

        String lowerHtml = html.toLowerCase();
        boolean hasHtmlTag = lowerHtml.contains("<html");
        boolean hasBodyTag = lowerHtml.contains("<body");
        boolean hasClosingHtml = lowerHtml.contains("</html>");

        return hasHtmlTag && hasBodyTag && hasClosingHtml;
    }

    public PdfMetadata getMetadata(byte[] pdfBytes) {
        return PdfMetadata.builder()
                .sizeInBytes(pdfBytes.length)
                .sizeInKB(pdfBytes.length / 1024)
                .sizeInMB(pdfBytes.length / (1024 * 1024))
                .build();
    }
    @lombok.Data
    @lombok.Builder
    public static class PdfMetadata {
        private long sizeInBytes;
        private long sizeInKB;
        private long sizeInMB;
    }
}
