package gra;


import java.util.Random;

public class Znak {

    public int losujZnak() {
        Random gen = new Random();
        return gen.nextInt(2)+1;  // losowo generuj numer znaku z dostępnych ...2
    }

    /*pas lewy = 0, pas środkowy = 1, pas prawy = 2 */

    public boolean sprawdzZnak(int nrZnaku, int pas, int velocity) {
        boolean sprawdz;

        if (nrZnaku == 1) {
            if (pas == 2) sprawdz = false;
            else sprawdz = true;
        }
        else if (nrZnaku == 2) {
            if (pas == 0) sprawdz = false;
            else sprawdz = true;
        }
        else sprawdz = true;  // default
        return sprawdz;
    }


}
