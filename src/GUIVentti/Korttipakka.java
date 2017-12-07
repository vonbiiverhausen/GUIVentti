package GUIVentti;
import java.util.ArrayList;

public class Korttipakka {
    private ArrayList<Kortti> korttipakka;
    
    public Korttipakka() {
        this.korttipakka = new ArrayList<>();
    }
    
    public void luoKortit() {
        int alkuArvo = 2;
        
        // Luodaan hertat
        for (int i = alkuArvo; i <= alkuArvo+12; i++) {
            this.korttipakka.add(new Kortti("Hertta", i));
        }
        
        // Luodaan ruudut
        for (int i = alkuArvo; i <= alkuArvo+12; i++) {
            this.korttipakka.add(new Kortti("Ruutu", i));
        }
        
        // Luodaan padat
        for (int i = alkuArvo; i <= alkuArvo+12; i++) {
            this.korttipakka.add(new Kortti("Pata", i));
        }
        
        // Luodaan ristit
        for (int i = alkuArvo; i <= alkuArvo+12; i++) {
            this.korttipakka.add(new Kortti("Risti", i));
        }
    }
    
    public Kortti getKortti(int i) {
        return this.korttipakka.get(i);
    }
    
    public int getSize() {
        return this.korttipakka.size();
    }
    
    public void addKortti(Kortti kortti) {
        this.korttipakka.add(kortti);
    }
    
    public void poistaKortti(int indeksi) {
        this.korttipakka.remove(indeksi);
    }
    
    public void nollaa() {
        this.korttipakka.clear();
    }
}
