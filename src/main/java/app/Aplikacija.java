package app;

import generator.WordGenerator;
import model.VoziloRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aplikacija extends JFrame {

    private JTextField txtDan, txtMesec, txtTemperatura, txtTemp2, txtBrojDana;
    private JComboBox<String> cmbRegBr;
    private JButton btnGenerisi, btnDodajDane;
    private JPanel panelDani;
    private List<JTextField> satiPolja = new ArrayList<>();
    private List<JTextField> minutiPolja = new ArrayList<>();

    public Aplikacija() {
        setTitle("Generator Ispisa");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);


        JLabel lblDan = new JLabel("Dan:");
        lblDan.setBounds(20, 20, 80, 20);
        add(lblDan);
        txtDan = new JTextField();
        txtDan.setBounds(100, 20, 150, 20);
        add(txtDan);

        JLabel lblMesec = new JLabel("Mesec:");
        lblMesec.setBounds(20, 50, 80, 20);
        add(lblMesec);
        txtMesec = new JTextField();
        txtMesec.setBounds(100, 50, 150, 20);
        add(txtMesec);

        JLabel lblTemperatura = new JLabel("Temperatura:");
        lblTemperatura.setBounds(20, 80, 80, 20);
        add(lblTemperatura);
        txtTemperatura = new JTextField();
        txtTemperatura.setBounds(100, 80, 150, 20);
        add(txtTemperatura);

        JLabel lblTemp2 = new JLabel("T+ :");
        lblTemp2.setBounds(20, 110, 80, 20);
        add(lblTemp2);
        txtTemp2 = new JTextField();
        txtTemp2.setBounds(100, 110, 150, 20);
        add(txtTemp2);

        JLabel lblRegBr = new JLabel("Reg. broj:");
        lblRegBr.setBounds(20, 140, 80, 20);
        add(lblRegBr);
        cmbRegBr = new JComboBox<>();
        for (Integer regBr : VoziloRegistry.getAll().keySet()) {
            cmbRegBr.addItem(regBr.toString());
        }
        cmbRegBr.setBounds(100, 140, 150, 20);
        add(cmbRegBr);

        JLabel lblBrojDana = new JLabel("Broj dana:");
        lblBrojDana.setBounds(20, 170, 80, 20);
        add(lblBrojDana);
        txtBrojDana = new JTextField();
        txtBrojDana.setBounds(100, 170, 150, 20);
        add(txtBrojDana);

        btnDodajDane = new JButton("Dodaj dane");
        btnDodajDane.setBounds(100, 200, 150, 25);
        add(btnDodajDane);

        panelDani = new JPanel();
        panelDani.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(panelDani);
        scrollPane.setBounds(20, 230, 340, 200);
        add(scrollPane);

        btnGenerisi = new JButton("Generiši");
        btnGenerisi.setBounds(100, 450, 150, 25);
        add(btnGenerisi);

        btnDodajDane.addActionListener(e -> {
            panelDani.removeAll();
            satiPolja.clear();
            minutiPolja.clear();
            try {
                int dan = Integer.parseInt(txtDan.getText());
                int mesec = Integer.parseInt(txtMesec.getText());
                int brojDana = Integer.parseInt(txtBrojDana.getText());

                for (int i = 0; i < brojDana; i++) {
                    int trenutniDan = dan + i;
                    String datumStr = String.format("%02d/%02d/2025", trenutniDan, mesec);
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date datum = df.parse(datumStr);
                    String danUNedelji = new SimpleDateFormat("EEEE").format(datum);

                    JLabel lbl = new JLabel(danUNedelji + ":");
                    lbl.setBounds(10, i * 40, 100, 25);
                    panelDani.add(lbl);

                    JTextField txtSat = new JTextField();
                    txtSat.setBounds(120, i * 40, 40, 25);
                    panelDani.add(txtSat);
                    satiPolja.add(txtSat);

                    JTextField txtMin = new JTextField();
                    txtMin.setBounds(170, i * 40, 40, 25);
                    panelDani.add(txtMin);
                    minutiPolja.add(txtMin);
                }
                panelDani.setPreferredSize(new Dimension(300, brojDana * 65));
                panelDani.revalidate();
                panelDani.repaint();
            } catch (NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Popunite validno Dan, Mesec i Broj dana.");
            }
        });

        btnGenerisi.addActionListener(e -> {
            try {
                int dan = Integer.parseInt(txtDan.getText());
                int mesec = Integer.parseInt(txtMesec.getText());
                double temperatura = Double.parseDouble(txtTemperatura.getText());
                int temp2 = Integer.parseInt(txtTemp2.getText());
                int regBr = Integer.parseInt((String) cmbRegBr.getSelectedItem());
                int brojDana = satiPolja.size();

                int[] sati = new int[brojDana];
                int[] minuti = new int[brojDana];
                for (int i = 0; i < brojDana; i++) {
                    sati[i] = Integer.parseInt(satiPolja.get(i).getText());
                    minuti[i] = Integer.parseInt(minutiPolja.get(i).getText());
                }

                WordGenerator.generisiViseIspisa(dan, mesec, sati, minuti, temperatura, regBr, temp2);
                JOptionPane.showMessageDialog(this, "Uspešno generisano!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage());
                ex.printStackTrace();
            } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Aplikacija();
    }
}