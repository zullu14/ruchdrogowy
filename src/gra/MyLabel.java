package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyLabel extends JLabel {

    private Font f = new Font("Calibri", Font.BOLD, 40);

    MyLabel(String tekst) {
        super(tekst);
        setMinimumSize(new Dimension(300, 40));
        setPreferredSize(new Dimension(300, 40));
        setMaximumSize(new Dimension(300, 40));
        setFont(f);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.BOTTOM_ALIGNMENT);

    } //MyLabel

}
