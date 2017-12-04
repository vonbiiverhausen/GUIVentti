// Versio 0.4: Ventti toimivalla graafisella käyttöliittymällä
// Versio 0.5: Ylävalikko, jossa on ohjeet
// Versio 0.6: Pelaajat, kortit ja korttipakka olioiksi
// Versio 0.7: Voi valita pakan
// Versio 0.8: Kysytään ässän arvo, poistetaan pakanvalinta turhana
// Versio 0.9: Uuden pelin aloittaminen
// Versio 0.10: Venttistatus. Pelaajalla viiden kortin ventti, talo voittaa 20 pisteellä ventistä riippumatta

package GUIVentti;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUIVentti {
    
    private static Pelaaja pelaaja1 = new Pelaaja("Pelaaja", false), talo = new Pelaaja("Talo", true);
    private static Korttipakka pakka = new Korttipakka();
    
    private static JFrame ventti = new JFrame("Ventti 0.10"), ohje;
    private static JPanel pnPaa, pnPelaajat, pnPelaaja, pnTalo, pnNapit;
    private static JButton btOtaKortti, btPelaaKasi;
    private static JLabel lbPelaaja, lbTalo, lbPPisteet, lbTPisteet, lbPKasi, lbTKasi;
    private static JEditorPane epPelaaja, epTalo;
    private static JMenuBar menu;
    private static JMenu mPeli, mHelp;
    private static JMenuItem mOhje, mAloitaPeli, mLopetaPeli;
    
    static class AloitaPeli implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent tapahtuma) {
            pakka.nollaa();
            pakka.luoKortit();
            pelaaja1.nollaa();
            talo.nollaa();
            btOtaKortti.setEnabled(true);
            btPelaaKasi.setEnabled(true);
            lbPPisteet.setText("Pisteet: ");
            lbTPisteet.setText("Pisteet: ");
            lbPKasi.setText("Käsi: ");
            lbTKasi.setText("Käsi: ");
            pelaa(pelaaja1);
        }
    }
    
    static class Lopeta implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent tapahtuma) {
            System.exit(0);
        }
    }    
    
    static class OtaKortti implements ActionListener {
        @Override
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
        @Override
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
        
        mHelp = new JMenu("Info");
        mOhje = new JMenuItem("Ohje");
        mOhje.addActionListener(new Ohje());
        
        mPeli.add(mAloitaPeli);
        mPeli.add(mLopetaPeli);
        mHelp.add(mOhje);
        
        
        menu.add(mPeli);
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
        btOtaKortti.addActionListener(new OtaKortti());
        pnNapit.add(btPelaaKasi);
        btPelaaKasi.addActionListener(new pelaaKasi());
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
        if (pelaaja.onTalo()) {
            while (!talo.Ventti()) { // talo havittelee venttiä
                if (pelaaja.getPisteet() < 13 || pelaaja.getPisteet() < pelaaja1.getPisteet()) {
                    otaKortti(pelaaja);
                } else if (pelaaja.getPisteet() >= 13 && pelaaja.getPisteet() < 21) {
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
        
        if (!player.onTalo()) {
            lbPPisteet.setText("Pisteet: " + player.getPisteet());
            lbPKasi.setText("Käsi: " + player.tulostaKasi());
        }
        
        if (player.onTalo()) {
            lbTPisteet.setText("Pisteet: " + player.getPisteet());
            lbTKasi.setText("Käsi: " + player.tulostaKasi());
        }
        
        // yli 21 pistettä -> häviö
        if (player.getPisteet() > 21) {
            havia(player);
        }
        
        // Tasan 21 pistettä -> ventti
        if (player.getPisteet() == 21) {
            System.out.println("Ventti!");
            annaVentti(player);
        }
        
        // Jos pelaajalla on 5 korttia, joiden arvo on alle 21 -> ventti
        if (!player.onTalo() && player.kadenKoko() >= 5 && player.getPisteet() < 21) {
            System.out.println("Ventti!");
            annaVentti(player);
        }
        
    }
    
    private static void annaVentti(Pelaaja pelaaja) {
        pelaaja.saaVentin();
        if (!pelaaja.onTalo()) {
            // vuoro siirtyy talolle
            lbPPisteet.setText("Pisteet: " + pelaaja.getPisteet()+" VENTTI");
            pelaa(talo);
        } else {
            // pelaaja on talo -> mennään vertaukseen
            lbTPisteet.setText("Pisteet: " + pelaaja.getPisteet()+" VENTTI");
            vertaa();
        }
    }
    
    private static void havia(Pelaaja pelaaja) {
        btOtaKortti.setEnabled(false);
        btPelaaKasi.setEnabled(false);
        JOptionPane.showMessageDialog(null, pelaaja.getNimi()+" hävisi pelin!");
    }
    
    private static void vertaa() {
        
        String viesti = "";
        
        // Generoidaan tilannetiedot ventistä ja pisteistä
        if (pelaaja1.Ventti()) {
            viesti += "Pelaaja sai ventin\n";
        } else {
            viesti += "Pelaaja sai "+pelaaja1.getPisteet()+" pistettä\n";
        }
        if (talo.Ventti()) {
            viesti += "Talo sai ventin\n";
        } else {
            viesti+= "Talo sai "+talo.getPisteet()+" pistettä\n\n";
        }
        
        // jos talolla ja pelaajalla ventit, talo voittaa
        // jos talolla on ventti, mutta pelaajalla ei, talo voittaa
        if (talo.Ventti()) {
            viesti += talo.getNimi()+" voitti!";
        }
        
        // jos pelaajalla ventti ja talolla ei, pelaaja voittaa
        // Mutta jos talolla on 20 pistettä, niin talo voittaa pelaajan ventistä huolimatta
        if (pelaaja1.Ventti() && !talo.Ventti()) {
            if (talo.getPisteet() == 20) {
                viesti += talo.getNimi()+" voitti!";
            } else {
                viesti += pelaaja1.getNimi()+" voitti!";
            }
        }
        
        // jos kummallakaan ei ole venttiä, niin verrataan pisteitä
        // Lähimpänä 21 pistettä olija voittaa
        if (!pelaaja1.Ventti() && !talo.Ventti()) {
            if (21 - pelaaja1.getPisteet() < 21 - talo.getPisteet()) {
                viesti += pelaaja1.getNimi() + " voitti!";
            } else {
                viesti += talo.getNimi() + " voitti!";
            }
        }
        
        JOptionPane.showMessageDialog(null, viesti);
    }
    
    public static void main(String[] args) {
        luoIkkuna();
    }
}
