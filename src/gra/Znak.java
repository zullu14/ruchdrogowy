package gra;


import java.util.Random;

public class Znak {

    private String[] komunikaty = {
            "Znak A-12B Zwężenie jezdni prawostronne", "Znak A-12C Zwężenie jezdni lewostronne", "3", "4"
    };

    public int losujZnak() {
        Random gen = new Random();
        return gen.nextInt(3)+1;  // losowo generuj numer znaku z dostępnych ...2
    }

    /*pas lewy = 0, pas środkowy = 1, pas prawy = 2 */

    public boolean sprawdzZnak(int nrZnaku, int pas, int velocity) {
        boolean sprawdz;

        switch (nrZnaku) {
            case 1:
                if (pas == 2) sprawdz = false;
                else sprawdz = true;
                break;
            case 2:
                if (pas == 0) sprawdz = false;
                else sprawdz = true;
                break;
            case 3:
                if (pas == 1) sprawdz = true;
                else sprawdz = false;
                break;
            default:
                sprawdz = true;  // default
                break;
        }
        return sprawdz;
    }

    public String getKomunikat(int nrZnaku) {
        return komunikaty[nrZnaku-1];
    }
}
