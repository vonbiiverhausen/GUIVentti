package GUIVentti;

public class Pelaaja {
    private Korttipakka pelaajanKasi;
    private String pelaajanNimi;
    private int pelaajanPisteet;
    private boolean onkoTalo;
    
    public Pelaaja(String nimi, boolean onkoTalo) {
        this.pelaajanKasi = new Korttipakka();
        this.pelaajanNimi=nimi;
        this.pelaajanPisteet = 0;
        this.onkoTalo = onkoTalo;
    }
    
    public void otaKortti(Korttipakka pakka) {
        int korttiIndeksi = (int) (Math.random()*pakka.getSize());
        this.pelaajanKasi.addKortti(pakka.getKortti(korttiIndeksi));
        this.pelaajanPisteet += pakka.getKortti(korttiIndeksi).getArvo();
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
    
    public boolean onkoTalo() {
        return this.onkoTalo;
    }
}
