package com.example.demo8;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class PdfGeneration {

    void pdfGeneration(String filename, String text) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
            document.open();
            Image image1 =  Image.getInstance(Objects.requireNonNull(getClass().getResource("/logo.jpg")));
            image1.scaleToFit(200, 86);
            image1.setAbsolutePosition(200f, 750f);
            document.add(image1);

            document.add( new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n" + text, FontFactory.getFont("betutípus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Chunk signature = new Chunk("\n\n Generated by the Phonebook Application.");

            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        document.close();

    }

}