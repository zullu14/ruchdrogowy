package gra;

import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Okno o = new Okno("Ruch Drogowy");
        Plansza pl = new Plansza();
        o.add(pl);
        o.setVisible(true);
    }

}

