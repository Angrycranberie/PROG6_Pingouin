package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileButton extends JButton {

    public TileButton(String name, int w, int h) {
        super();
        setOpaque(false);
        setBackground(Color.BLACK);
        setName(name);
        setSize(w, h);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }
}
