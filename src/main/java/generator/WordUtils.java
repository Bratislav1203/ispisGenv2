package generator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

public class WordUtils {

    public static void ispisiWord(XWPFDocument doc, String text, boolean bold, int font) {
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setBold(bold);
        run.setFontSize(font);
        run.setFontFamily("Microsoft Sans Serif");
        run.setText(text);

        CTSpacing spacing = para.getCTP().addNewPPr().addNewSpacing();
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240)); // jednostruki prored
        para.setSpacingAfter(0);
    }

    public static void podesiStranicu(XWPFDocument doc) {
        CTPageSz pageSize = doc.getDocument().getBody().addNewSectPr().addNewPgSz();
        pageSize.setW(BigInteger.valueOf((long) (2.3 * 1440))); // širina u TWIPs
        pageSize.setH(BigInteger.valueOf((long) (8.3 * 1440))); // visina

        CTPageMar pageMargin = doc.getDocument().getBody().getSectPr().addNewPgMar();
        pageMargin.setLeft(BigInteger.valueOf((long) (0.2 * 1440)));
        pageMargin.setTop(BigInteger.valueOf(0));
        pageMargin.setRight(BigInteger.valueOf(0));
        pageMargin.setBottom(BigInteger.valueOf(0));
    }

    public static void printDocument(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Fajl ne postoji: " + filePath);
            return;
        }

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.PRINT)) {
            Desktop.getDesktop().print(file);
        } else {
            JOptionPane.showMessageDialog(null, "Štampanje nije podržano na ovom uređaju!");
        }
    }
}
