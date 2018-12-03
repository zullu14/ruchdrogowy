package gra;

import java.awt.*;
import javax.swing.*;

public class ChoiceButton extends JButton {

    ChoiceButton(String nazwa) {
        super(nazwa);
        setFont(new Font("Calibri", Font.BOLD, 40));
        setBackground(Color.orange);
        setMaximumSize(new Dimension(300, 200));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
    }
}
