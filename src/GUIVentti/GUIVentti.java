// Versio 0.4: Ventti toimivalla graafisella käyttöliittymällä
// Versio 0.5: Ylävalikko, jossa on ohjeet
// Versio 0.6: Pelaajat, kortit ja korttipakka olioiksi
// Versio 0.7: Voi valita pakan
// Versio 0.8: GitHub

package GUIVentti;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIVentti {
    
    private static Pelaaja pelaaja1 = new Pelaaja("Pelaaja", false), talo = new Pelaaja("Talo", true);
    private static Korttipakka pakka = new Korttipakka();
    
    private static JFrame ventti = new JFrame("Ventti 0.7"), ohje;
    private static JPanel pnPaa, pnPelaajat, pnPelaaja, pnTalo, pnNapit;
    private static JButton btOtaKortti, btPelaaKasi;
    private static JLabel lbPelaaja, lbTalo, lbPPisteet, lbTPisteet, lbPKasi, lbTKasi;
    private static JEditorPane epPelaaja, epTalo;
    private static JMenuBar menu;
    private static JMenu mPeli, mHelp, mSaannot;
    private static JMenuItem mOhje, mAloitaPeli, mLopetaPeli;
    private static JRadioButtonMenuItem mAssaOnYksi, mAssaOn14;
    private static boolean assaOnYksi = true;
    
    static class AloitaPeli implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            mSaannot.setEnabled(false);
            pakka.luoKortit(assaOnYksi);
            btOtaKortti.setEnabled(true);
            btPelaaKasi.setEnabled(true);
            pelaa(pelaaja1);
        }
    }
    
    static class Lopeta implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            System.exit(0);
        }
    }
    
    static class AssaOnYksi implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            assaOnYksi = true;
            mAssaOnYksi.setSelected(true);
            mAssaOn14.setSelected(false);
        }
    }
    
    static class AssaOn14 implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            assaOnYksi = false;
            mAssaOnYksi.setSelected(false);
            mAssaOn14.setSelected(true);
        }
    }
    
    static class OtaKortti implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            otaKortti(pelaaja1);
        }
    }
    
    static class pelaaKasi implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            btOtaKortti.setEnabled(false);
            btPelaaKasi.setEnabled(false);
            pelaa(talo);
        }
    }
    
    static class Ohje implements ActionListener {
        public void actionPerformed(ActionEvent tapahtuma) {
            luoOhjeIkkuna();
        }
    }
    
    private static void luoOhjeIkkuna() {
        ohje = new JFrame();
        ohje.setSize(500,250);
        ohje.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel ohjePaneeli = new JPanel();
        JTextArea ohjeTeksti = new JTextArea(10,12);
        
        ohje.add(ohjePaneeli);
        ohjePaneeli.add(ohjeTeksti);
        
        String ohjeString = "Ventti\n\nPelaaja pelaa ensin. Paina \"Ota kortti\" -nappia ottaaksesi uuden kortin.\n";
        ohjeString += "Paina \"Pelaa kasi\"-nappia pelataksesi kätesi. Talo pelaa tällöin vuoronsa.\n\n";
        ohjeString += "Säännöt:\n";
        ohjeString += "Pelaajat päättävät ottavatko kortteja vai pelaavatko kätensä\n";
        ohjeString += "Pelin tarkoituksena on kerätä kortteja, kunnes niiden yhteisarvo on 21 tai alle\n";
        ohjeString += "Jos yhteisarvo ylittää 21, niin pelaaja häviää\n";
        ohjeString += "Jos pelaaja pelaa kätensä, vuoro siirtyy toiselle pelaajalle ja tämä pelaa vuoronsa. \n";
        ohjeString += "Kun kaikki pelaajat ovat pelanneet kätensä, katsotaan kumpi pelaaja on lähempänä\n";
        ohjeString += "21 pistettä. Tasapelitilanteessa talo voittaa";
        
        ohjeTeksti.setText(ohjeString);
        ohje.setVisible(true);
    }
    
    private static void luoIkkuna() {
        ventti.setSize(600,400);
        ventti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        asetaMenubar();
        asetaPaneelit();
        asetaNapit();
        asetaLabelit();
        asetaEditorPanelit();
        ventti.setVisible(true);
    }
    
    private static void asetaMenubar() {
        menu = new JMenuBar();
        
        mPeli=new JMenu("Peli");
        mAloitaPeli = new JMenuItem("Aloita peli");
        mAloitaPeli.addActionListener(new AloitaPeli());
        mLopetaPeli = new JMenuItem("Lopeta");
        mLopetaPeli.addActionListener(new Lopeta());
        
        mSaannot = new JMenu("Pakka");
        mAssaOnYksi = new JRadioButtonMenuItem("Ässän arvo on 1");
        mAssaOnYksi.setSelected(true);
        mAssaOnYksi.addActionListener(new AssaOnYksi());
        mAssaOn14 = new JRadioButtonMenuItem("Ässän arvo on 14");
        mAssaOn14.setSelected(false);
        mAssaOn14.addActionListener(new AssaOn14());
        
        mHelp = new JMenu("Info");
        mOhje = new JMenuItem("Ohje");
        mOhje.addActionListener(new Ohje());
        
        mPeli.add(mAloitaPeli);
        mPeli.add(mLopetaPeli);
        mSaannot.add(mAssaOnYksi);
        mSaannot.add(mAssaOn14);
        mHelp.add(mOhje);
        
        
        menu.add(mPeli);
        menu.add(mSaannot);
        menu.add(mHelp);
        ventti.setJMenuBar(menu);
    }
    
    private static void asetaPaneelit() {
        pnPaa = new JPanel(new GridLayout(2, 1));
        pnPaa.setBorder(BorderFactory.createLineBorder(Color.black));
        ventti.add(pnPaa);
        
        pnPelaajat = new JPanel(new GridLayout(1, 2));
        pnPelaajat.setBorder(BorderFactory.createLineBorder(Color.black));
        pnPaa.add(pnPelaajat);
        pnNapit = new JPanel();
        pnNapit.setBorder(BorderFactory.createLineBorder(Color.black));
        pnPaa.add(pnNapit);

        pnPelaaja = new JPanel(new GridLayout(4, 1));
        pnPelaaja.setBorder(BorderFactory.createLineBorder(Color.black));
        pnPelaajat.add(pnPelaaja);
        pnTalo = new JPanel(new GridLayout(4, 1));
        pnTalo.setBorder(BorderFactory.createLineBorder(Color.black));
        pnPelaajat.add(pnTalo);
    }
    
    private static void asetaNapit() {
        btOtaKortti = new JButton("Ota kortti");
        btPelaaKasi = new JButton("Pelaa käsi");
        
        pnNapit.add(btOtaKortti);
        pnNapit.add(btPelaaKasi);
        btPelaaKasi.setEnabled(false);
        btOtaKortti.setEnabled(false);
    }
    
    private static void asetaLabelit() {
        lbTalo = new JLabel(talo.getNimi());
        lbTPisteet = new JLabel("Pisteet: ");
        lbTKasi = new JLabel("Käsi: ");
        
        lbPelaaja = new JLabel(pelaaja1.getNimi());
        lbPPisteet = new JLabel("Pisteet: ");
        lbPKasi = new JLabel("Käsi: ");
        
        pnTalo.add(lbTalo);
        pnTalo.add(lbTPisteet);
        pnTalo.add(lbTKasi);
        
        pnPelaaja.add(lbPelaaja);
        pnPelaaja.add(lbPPisteet);
        pnPelaaja.add(lbPKasi);
    }
    
    private static void asetaEditorPanelit() {
        epPelaaja = new JEditorPane();
        epTalo = new JEditorPane();
        epPelaaja.setEditable(false);
        epTalo.setEditable(false);
        
        pnPelaaja.add(epPelaaja);
        pnTalo.add(epTalo);
    }
    
    private static void pelaa(Pelaaja pelaaja) {
        if (!pelaaja.onkoTalo()) {
            btOtaKortti.addActionListener(new OtaKortti());
            btPelaaKasi.addActionListener(new pelaaKasi());
        }
        if (pelaaja.onkoTalo()) {
            while (true) {
                if (pelaaja.getPisteet() < 13 || pelaaja.getPisteet() < pelaaja1.getPisteet()) {
                    otaKortti(pelaaja);
                } else if (pelaaja.getPisteet() >= 13 && pelaaja.getPisteet() < 21) {
                    vertaa();
                    break;
                } else if (pelaaja.getPisteet() == 21) {
                    vertaa();
                    break;
                } else if (pelaaja.getPisteet() > 21) {
                    break;
                }
            }
        }
    }
    
    private static void otaKortti(Pelaaja player) {

        player.otaKortti(pakka);

        if (!player.onkoTalo()) {
            lbPPisteet.setText("Pisteet: " + player.getPisteet());
            lbPKasi.setText("Käsi: " + player.tulostaKasi());
        }
        
        if (player.onkoTalo()) {
            lbTPisteet.setText("Pisteet: " + player.getPisteet());
            lbTKasi.setText("Käsi: " + player.tulostaKasi());
        }
        
        if (player.getPisteet() > 21) {
            havia(player);
        }
    }
    
    private static void havia(Pelaaja pelaaja) {
        btOtaKortti.setEnabled(false);
        btPelaaKasi.setEnabled(false);
        JOptionPane.showMessageDialog(null, pelaaja.getNimi()+" hävisi pelin!");
    }
    
    private static void vertaa() {
        // katsotaan kumpi on lähempänä 21 pistettä
        String viesti = "";
        viesti += "Pelaaja sai "+pelaaja1.getPisteet()+" pistettä\n";
        viesti+= "Talo sai "+talo.getPisteet()+" pistettä\n\n";
        if (21-pelaaja1.getPisteet() < 21-talo.getPisteet()) {
            viesti += pelaaja1.getNimi()+" voitti!";
        } else {
            viesti += talo.getNimi()+" voitti!";
        }
        JOptionPane.showMessageDialog(null, viesti);
    }
    
    public static void main(String[] args) {
        luoIkkuna();
    }
}
