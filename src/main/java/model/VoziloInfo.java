package model;

public class VoziloInfo {
    private final String[] zaglavljeLinije;
    private final boolean koristiBoldZaglavlje;
    private final boolean koristiLinijeNaKraju;
    private final String oznaka;
    private final String serijskiBroj;

    public VoziloInfo(String[] zaglavljeLinije, boolean koristiBoldZaglavlje,
                      boolean koristiLinijeNaKraju, String oznaka, String serijskiBroj) {
        this.zaglavljeLinije = zaglavljeLinije;
        this.koristiBoldZaglavlje = koristiBoldZaglavlje;
        this.koristiLinijeNaKraju = koristiLinijeNaKraju;
        this.oznaka = oznaka;
        this.serijskiBroj = serijskiBroj;
    }

    public String[] getZaglavljeLinije() {
        return zaglavljeLinije;
    }

    public boolean koristiBoldZaglavlje() {
        return koristiBoldZaglavlje;
    }

    public boolean koristiLinijeNaKraju() {
        return koristiLinijeNaKraju;
    }

    public String getOznaka() {
        return oznaka;
    }

    public String getSerijskiBroj() {
        return serijskiBroj;
    }
}
