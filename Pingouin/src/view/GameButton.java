package view;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {
    // Types de boutons.
    public final static int TYPE_DEFAULT = 0;
    public final static int TYPE_INFO = 1;
    public final static int TYPE_ALERT = 2;

    GameButton(String text, int type) {
        super(text);
        switch (type) {
            case TYPE_DEFAULT:
                setBackground(new Color(32, 200, 200));
                break;
            case TYPE_INFO:
                setBackground(new Color(0, 148, 255));
                break;
            case TYPE_ALERT:
                setBackground(new Color(200, 0, 72));
                break;
            default:
                break;
        }
    }
}
