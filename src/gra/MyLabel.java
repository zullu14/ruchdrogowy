package gra;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa określająca wygląd wykorzystywanych pól JLabel.
 */
public class MyLabel extends JLabel {

    /**
     * Konstruktor tworzący JLabel do ekranu pola gry.
     * @param tekst treść do wyświetlenia w polu MyLabel.
     */
    MyLabel(String tekst) {
        super(tekst);
        setMinimumSize(new Dimension(300, 40));
        setPreferredSize(new Dimension(300, 40));
        setMaximumSize(new Dimension(300, 40));
        Font f = new Font("Calibri", Font.BOLD, 40);
        setFont(f);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.BOTTOM_ALIGNMENT);

    } //MyLabel

}
