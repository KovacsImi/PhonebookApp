package com.example.demo8;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class PdfGeneration {

    void pdfGeneration(String filename, ObservableList<Human> data) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(Objects.requireNonNull(getClass().getResource("/logo.jpg")));
            image1.scaleToFit(400, 172);
            image1.setAbsolutePosition(170f, 650f);
            document.add(image1);


            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));
            //táblázat
            float[] columnWidths = {2, 4, 4, 6};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Contact List"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4); //szélesség: 3 oszlop széles
            table.addCell(cell); // táblához való hozzáadás

            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("ID");
            table.addCell("Last Name");
            table.addCell("First Name");
            table.addCell("E-mail address");
            table.setHeaderRows(1);

            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            for (int userDataRow = 1; userDataRow <= data.size(); userDataRow++) {
                Human human = data.get(userDataRow - 1);

                table.addCell(String.valueOf(userDataRow));
                table.addCell(human.getLastName());
                table.addCell(human.getFirstName());
                table.addCell(human.getEmail());
            }

            document.add(table);

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
