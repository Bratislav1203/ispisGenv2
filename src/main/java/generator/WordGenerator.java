package generator;

import model.VoziloInfo;
import model.VoziloRegistry;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class WordGenerator {

    // Širina kolone za broj (uključuje i minus, cifre, tačku i 1 decimalu)
    private static final int COL_WIDTH = 6;

    public static void generisiViseIspisa(int dan, int mesec, int[] sati, int[] minuti,
                                          double temperatura, int regBr, int temp2) throws IOException {
        VoziloInfo vozilo = VoziloRegistry.get(regBr);
        if (vozilo == null) {
            JOptionPane.showMessageDialog(null, "Nepoznat registar broj: " + regBr);
            return;
        }

        String filePath = "IspisWord.docx";

        try (FileOutputStream outStream = new FileOutputStream(filePath);
             PrintWriter textOut = new PrintWriter(new BufferedWriter(new FileWriter("Ispis")));
             XWPFDocument doc = new XWPFDocument()) {

            WordUtils.podesiStranicu(doc);

            DateTimeFormatter topFmt    = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter toFromFmt = DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy HH:mm");
            DateTimeFormatter lineFmt   = DateTimeFormatter.ofPattern("dd/MM HH:mm");

            Random rnd = new Random();

            for (int i = 0; i < sati.length; i++) {
                int trenutniDan = dan + i;
                int sat = sati[i];
                int minut = minuti[i];
                int sekunde = rnd.nextInt(60);

                // Gornje linije (zaglavlje)
                for (String linija : vozilo.getZaglavljeLinije()) {
                    WordUtils.ispisiWord(doc, linija, vozilo.koristiBoldZaglavlje(), 8);
                }

                // Početni timestamp za ovaj blok
                LocalDateTime tStart = LocalDateTime.of(2025, mesec, trenutniDan, sat, minut, sekunde);

                // Gornji red sa datumom/vremenom
                WordUtils.ispisiWord(doc, tStart.format(topFmt), vozilo.koristiBoldZaglavlje(), 8);

                WordUtils.ispisiWord(doc, "Sample rate:30", true, 8);
                WordUtils.ispisiWord(doc, "T1: Return air", true, 8);
                WordUtils.ispisiWord(doc, "T2: Rear", true, 8);

                // "To:" (početno vreme - bez sekundi)
                WordUtils.ispisiWord(doc, "To: " + tStart.format(toFromFmt), true, 8);

                // Header iznad kolona — koristi istu širinu kao i brojevi
                // %"+COL_WIDTH+"s znači desno poravnanje u polju širine COL_WIDTH
                String header = String.format("                        %"+COL_WIDTH+"s   %"+COL_WIDTH+"s     1234A", "T1", "T2");
                WordUtils.ispisiWord(doc, header, false, 8);

                // Linije merenja – 48 koraka po 30 min unazad (24h)
                LocalDateTime cursor = tStart;
                for (int n = 0; n < 48; n++) {
                    double t1 = TemperatureUtils.t1(temperatura, temp2);
                    double t2 = TemperatureUtils.t2(t1);

                    // desno poravnan broj u polju širine COL_WIDTH; T2 će se „pomeriti udesno“
                    // proporcionalno dužini T1, jer T1 zauzima više mesta u svom polju
                    String linija = String.format(
                            "%s     %"+COL_WIDTH+".1f   %"+COL_WIDTH+".1f",
                            cursor.format(lineFmt), t1, t2
                    );

                    WordUtils.ispisiWord(doc, linija, false, 8);
                    textOut.println(linija);

                    cursor = cursor.minusMinutes(30);
                }

                // "From:" – tačno 24h unazad; NE BOLD
                WordUtils.ispisiWord(doc, "From: " + cursor.format(toFromFmt), false, 8);

                if (vozilo.koristiLinijeNaKraju()) {
                    WordUtils.ispisiWord(doc,
                            vozilo.getOznaka().equals("AA 406RA")
                                    ? "- - - - - - - - - - - - - - - - - -"
                                    : "-------------------------",
                            true, 8);
                    WordUtils.ispisiWord(doc, "", true, 8);
                    WordUtils.ispisiWord(doc, "Signature", false, 8);
                }
                // 4 prazna reda kao razmak
                WordUtils.ispisiWord(doc, "", false, 8);
                WordUtils.ispisiWord(doc, "", false, 8);
                WordUtils.ispisiWord(doc, "", false, 8);
                WordUtils.ispisiWord(doc, "", false, 8);
            }

            doc.write(outStream);
        }

        JOptionPane.showMessageDialog(null, "Word fajl uspešno kreiran: " + filePath);
    }
}
