package GUIVentti;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Pelaaja {
    private Korttipakka pelaajanKasi;
    private String pelaajanNimi;
    private int pelaajanPisteet;
    private boolean onTalo, onVentti;
    
    public Pelaaja(String nimi, boolean onkoTalo) {
        this.pelaajanKasi = new Korttipakka();
        this.pelaajanNimi=nimi;
        this.pelaajanPisteet = 0;
        this.onTalo = onkoTalo;
        this.onVentti = false;
    }
    
    private int assaKysely() {
        Object[] arvot = {1, 14};
        JFrame frame = new JFrame();
        int s = (Integer) JOptionPane.showOptionDialog(frame, 
        "Valitse ässän arvo",
        "Ässän arvo",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, 
        arvot, 
        arvot[0]);
        
        if (s == JOptionPane.YES_OPTION) {
            s = 1;
        } else {
            s = 14;
        }
        
        return s;
    }
    
    public void otaKortti(Korttipakka pakka) {
        int korttiIndeksi = (int) (Math.random()*pakka.getSize());
        this.pelaajanKasi.addKortti(pakka.getKortti(korttiIndeksi));
        if (pakka.getKortti(korttiIndeksi).getMaa().contains("ässä")) {
            // jos ässä, kysy onko ässä arvoltaan 1 vai 14
            if (!this.onTalo) {
                this.pelaajanPisteet += assaKysely();   // pelaaja päättää ässän arvon
            } else {
                this.pelaajanPisteet++;                 // talolle ässä on 1
            }
        } else {
            this.pelaajanPisteet += pakka.getKortti(korttiIndeksi).getArvo();
        }
        
        pakka.poistaKortti(korttiIndeksi);
    }
    
    public String tulostaKasi() {
        
        String kasi = "";
        if (this.pelaajanKasi.getSize() == 0) {
            kasi += "Käsi on tyhjä";
        } else {
            for (int i = 0; i < this.pelaajanKasi.getSize(); i++) {
                kasi += this.pelaajanKasi.getKortti(i).getMaa()+" ";
            }
        }
        
        return kasi;
    }
    
    public int getPisteet() {
        return this.pelaajanPisteet;
    }
    
    public String getNimi() {
        return this.pelaajanNimi;
    }
    
    public boolean onTalo() {
        return this.onTalo;
    }
    
    public void nollaa() {
        this.pelaajanPisteet=0;
        this.onVentti=false;
        this.pelaajanKasi.nollaa();
    }
    
    public int kadenKoko() {
        return this.pelaajanKasi.getSize();
    }
    
    public void saaVentin() {
        this.onVentti=true;
    }
    
    public boolean Ventti() {
        return this.onVentti;
    }
}
