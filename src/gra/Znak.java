package gra;

import java.util.Random;

/**
 * Klasa przechowująca dane i metody o używanych znakach drogowych.
 * Zawiera metody pozwalające wylosować nowy znak, sprawdzić poprawność zastosowania się do znaku
 * oraz przekazać komunikat o właściwym znaczeniu danego znaku w przypadku błędu.
 */
public class Znak {

    private String[] komunikaty = {
            "Znak A-12b Zwężenie jezdni prawostronne", "Znak A-12c Zwężenie jezdni lewostronne", "Znak A-12a zwężenie jezdni dwustronne",
            "Znak B-33 Ograniczenie prędkości do 50 kph", "Znak B-20 STOP: całkowite zatrzymanie pojazdu", "Znak B-33 Ograniczenie prędkości do 30 kph",
            "Znak B-33 Ograniczenie prędkości do 40 kph", "Znak B-33 Ograniczenie prędkości do 90 kph", "Znak D-40 Strefa zamieszkania: ograniczenie do 20 kph",
            "Znak D-14b koniec pasa ruchu na jezdni trzypasowej", "Znak D-14b koniec pasa ruchu na jezdni trzypasowej", "Znak F-16 koniec pasa ruchu na jezdni dwukierunkowej",
            "Znak D-42 obszar zabudowany: ograniczenie do 50 kph", "Znak F-17 koniec pasa ruchu na jezdni jednokierunkowej"
    };

    /**
     * Metoda losująca numer kolejnego znaku z dostępnej listy znaków (można uzupełniać)
     * @return pseudolosowo wygenerowany numer kolejnego znaku (z dostępnego zakresu)
     */
    public int losujZnak() {
        Random gen = new Random();
        return gen.nextInt(14)+1;  // losowo generuj numer znaku z dostępnych ...14
    }

    /*pas lewy = 0, pas środkowy = 1, pas prawy = 2 */

    /**
     * Metoda sprawdzająca czy poprawnie zastosowano się do aktywnego znaku drogowego.
     * @param nrZnaku numer znaku z dostępnej listy
     * @param pas aktualnie zajmowany pas przez gracza
     * @param velocity chwilowa prędkość pojazdu gracza
     * @return zwraca True jeżeli zastosowano się do znaku, False w przypadku błędu
     */
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
            case 4:
                if (velocity > 50) sprawdz = false;
                else sprawdz = true;
                break;
            case 5:
                if (velocity == 0) sprawdz = true;
                else sprawdz = false;
                break;
            case 6:
                if (velocity > 30) sprawdz = false;
                else sprawdz = true;
                break;
            case 7:
                if (velocity > 40) sprawdz = false;
                else sprawdz = true;
                break;
            case 8:
                if (velocity > 90) sprawdz = false;
                else sprawdz = true;
                break;
            case 9:
                if (velocity > 20) sprawdz = false;
                else sprawdz = true;
                break;
            case 10:
                if (pas == 1) sprawdz = true;
                else sprawdz = false;
                break;
            case 11:
            case 12:
                if (pas == 2) sprawdz = true;
                else sprawdz = false;
                break;
            case 13:
                if (velocity > 50) sprawdz = false;
                else sprawdz = true;
                break;
            case 14:
                if (pas == 1) sprawdz = false;
                else sprawdz = true;
                break;
            default:
                sprawdz = true;  // default
                break;
        }
        return sprawdz;
    }

    /**
     * Metoda zwracająca treść komunikatu o znaczeniu danego znaku drogowego.
     * @param nrZnaku numer znaku z dostępnej listy
     * @return treść komunikatu z listy
     */
    public String getKomunikat(int nrZnaku) {
        return komunikaty[nrZnaku-1];
    }
}
