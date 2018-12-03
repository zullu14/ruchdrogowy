package gra;

import java.awt.*;
import javax.swing.*;

public class MenuButton extends JButton {

    MenuButton(String nazwa) {
        super(nazwa);
        setFont(new Font("Calibri", Font.BOLD, 40));
        setBackground(Color.green);
        setMinimumSize(new Dimension(200, 100));
        setPreferredSize(new Dimension(200, 100));
        setMaximumSize(new Dimension(200,100));

        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.BOTTOM_ALIGNMENT);
    } //MenuButton()

} //class MenuButton
