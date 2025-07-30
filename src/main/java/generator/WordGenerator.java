package generator;

import model.VoziloInfo;
import model.VoziloRegistry;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import generator.TemperatureUtils;
import generator.WordUtils;

import javax.swing.JOptionPane;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class WordGenerator {

    public static void generisiViseIspisa(int dan, int mesec, int[] sati, int[] minuti,
                                          double temperatura, int regBr, int temp2) throws IOException, ParseException {
        VoziloInfo vozilo = VoziloRegistry.get(regBr);
        if (vozilo == null) {
            JOptionPane.showMessageDialog(null, "Nepoznat registar broj: " + regBr);
            return;
        }

        String filePath = "IspisWord.docx";
        FileOutputStream outStream = new FileOutputStream(filePath);
        PrintWriter textOut = new PrintWriter(new BufferedWriter(new FileWriter("Ispis")));

        XWPFDocument doc = new XWPFDocument();
        WordUtils.podesiStranicu(doc);

        for (int i = 0; i < sati.length; i++) {
            int trenutniDan = dan + i;
            int sat = sati[i];
            int minut = minuti[i];
            int sekunde = new Random().nextInt(60);

            String datumStr = String.format("%02d/%02d/2025", trenutniDan, mesec);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date datum = df.parse(datumStr);
            String danUNedelji = new SimpleDateFormat("EEEE").format(datum);

            // Gornje linije
            for (String linija : vozilo.getZaglavljeLinije()) {
                WordUtils.ispisiWord(doc, linija, vozilo.koristiBoldZaglavlje(), 8);
            }

            WordUtils.ispisiWord(doc, String.format("%s %02d/%02d/2025 %02d:%02d:%02d",
                    danUNedelji, trenutniDan, mesec, sat, minut, sekunde), vozilo.koristiBoldZaglavlje(), 8);

            WordUtils.ispisiWord(doc, "Sample rate:30", true, 8);
            WordUtils.ispisiWord(doc, "T1: Return air", true, 8);
            WordUtils.ispisiWord(doc, "T2: Rear", true, 8);

            WordUtils.ispisiWord(doc, String.format("To: %s %02d/%02d/2025 %02d:%02d",
                    danUNedelji, trenutniDan, mesec, sat, minut), true, 8);

            WordUtils.ispisiWord(doc, "\t        T1   T2   \t     1234A", false, 8);

            int lokalDan = trenutniDan;
            int lokalSat = sat;
            int lokalMinut = minut;

            for (int j = 0; j < 24; j++) {
                for (int k = 0; k < 2; k++) {
                    double t1 = TemperatureUtils.t1(temperatura, temp2);
                    double t2 = TemperatureUtils.t2(t1);
                    String linija = String.format("%02d/%02d %02d:%02d     %.1f   %.1f",
                            lokalDan, mesec, lokalSat, lokalMinut, t1, t2);

                    WordUtils.ispisiWord(doc, linija, false, 8);
                    textOut.println(linija);

                    if (lokalMinut >= 30) {
                        lokalMinut -= 30;
                    } else {
                        lokalMinut += 30;
                        if (lokalSat == 0) {
                            lokalSat = 24;
                            lokalDan--;
                        }
                        lokalSat--;
                    }
                }
            }

            WordUtils.ispisiWord(doc, String.format("From: %s %02d/%02d/2025 %02d:%02d",
                    danUNedelji, lokalDan, mesec, lokalSat, lokalMinut), true, 8);

            if (vozilo.koristiLinijeNaKraju()) {
                WordUtils.ispisiWord(doc, vozilo.getOznaka().equals("AA 406RA")
                        ? "- - - - - - - - - - - - - - - - - -" : "-------------------------", true, 8);
                WordUtils.ispisiWord(doc, "", true, 8);
                WordUtils.ispisiWord(doc, "Signature", false, 8);
            }

            // Dodaj razmak od 4 prazna reda između ispisa
            WordUtils.ispisiWord(doc, "", false, 8);
            WordUtils.ispisiWord(doc, "", false, 8);
            WordUtils.ispisiWord(doc, "", false, 8);
            WordUtils.ispisiWord(doc, "", false, 8);
        }

        doc.write(outStream);
        outStream.close();
        textOut.close();

        JOptionPane.showMessageDialog(null, "Word fajl uspešno kreiran: " + filePath);
    }
}
