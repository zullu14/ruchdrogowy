package gra;

import java.awt.*;
import javax.swing.*;

/**
 * Klasa określająca wygląd wykorzystywanych pól przycisków wyboru.
 */
public class ChoiceButton extends JButton {

    /**
     * Konstruktor tworzący przycisk do ekranu menu.
     * @param nazwa napis na przycisku.
     */
    ChoiceButton(String nazwa) {
        super(nazwa);
        setFont(new Font("Calibri", Font.BOLD, 40));
        setBackground(Color.orange);
        setMaximumSize(new Dimension(300, 200));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
    }
}
