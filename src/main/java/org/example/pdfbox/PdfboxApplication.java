package org.example.pdfbox;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PdfboxApplication {

    public static void test() throws IOException {
        System.out.println("Test method starting");

        File file = new File("C:\\Users\\User\\Downloads\\passport_akburaeva_meerim.pdf");
        System.out.println(file);
        PDDocument document = Loader.loadPDF(file);;
        System.out.println(document);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
//        System.out.println(text);

        String[] allWords = text.split("\\r?\\n");
        List<String> listOfWords = Arrays.asList(allWords);
        System.out.println(listOfWords);
        System.out.println(listOfWords.get(12)); // МЭЭРИМ / MEERIM
        System.out.println(listOfWords.get(14)); // АКБУРАЕВА / AKBURAEVA
        System.out.println(listOfWords.get(16)); // АБДИМИТАЛОВНА
        System.out.println(listOfWords.get(32)); // MKK 211011
        System.out.println(listOfWords.get(36)); // 10605199900771
        System.out.println(listOfWords.get(38)); // ID3523356
        System.out.println(listOfWords.get(34)); // 03.08.2023

        document.close();

    }

    public static void createPdf() throws IOException {
        PDDocument document = new PDDocument();

        PDPage my_page = new PDPage();
        document.addPage(my_page);
        document.save("C:\\Users\\User\\Downloads\\new1.pdf");

        document.close();

    }

    public static void convertPhotoToPdf() throws IOException {
        File file = new File("C:\\Users\\User\\Downloads\\new1.pdf");
        PDDocument doc = Loader.loadPDF(file);
        PDPage page = doc.getPage(0);
        PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\Users\\User\\Downloads\\pho.png", doc);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.drawImage(pdImage, 70, 250);
        contentStream.close();
        doc.save("C:\\Users\\User\\Downloads\\new12.pdf");
        doc.close();

    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(PdfboxApplication.class, args);
        png();
    }


    public static void png() {
        // Путь к Tesseract OCR. Укажите путь к исполняемому файлу Tesseract, если необходимо.
//        String tesseractPath = "C:/Program Files/Tesseract-OCR/tesseract.exe";

        // Путь к изображению
        File imageFile = new File("C:\\Users\\User\\Downloads\\rarara.jpg");

        // Создание экземпляра Tesseract
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\User\\Downloads\\tessdata"); // Укажите путь к tessdata
        tesseract.setLanguage("rus+eng"); // Установите язык, например, русский

        try {
            // Извлечение текста с изображения
            String text = tesseract.doOCR(imageFile);
            System.out.println("Извлеченный текст:\n" + text);
        } catch (TesseractException e) {
            System.err.println("Ошибка OCR: " + e.getMessage());
        }
    }

    public static void preparePhoto() {

        // Шаг 2: Распознавание текста с помощью Tesseract
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\User\\Downloads\\tessdata"); // Укажите путь к tessdata
        tesseract.setLanguage("rus+eng"); // Укажите языки, которые нужно использовать

        try {
            String text = tesseract.doOCR(new java.io.File(":\\Users\\User\\Downloads\\pho111.jpg"));
            System.out.println("Распознанный текст: " + text);
        } catch (Exception e) {
            System.out.println("Ошибка распознавания текста: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
