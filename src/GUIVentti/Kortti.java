package GUIVentti;

public class Kortti {
    private String maa;
    private int arvo;
    
    public Kortti(String maa, int arvo) {
        
        switch (arvo) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.maa = maa+" "+arvo;
                break;
            case 1:
            case 14:
                this.maa = maa + "ässä";
                break;
            case 11:
                this.maa = maa + "jätkä";
                break;
            case 12:
                this.maa = maa + "kuningatar";
                break;
            case 13:
                this.maa = maa + "kuningas";
                break;
        }
        
        this.arvo = arvo;
    }
    
    public String getMaa() {
        return this.maa;
    }
    
    public int getArvo() {
        return this.arvo;
    }
}
