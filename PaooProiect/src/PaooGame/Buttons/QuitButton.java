package PaooGame.Buttons;

import PaooGame.RefLinks;

import java.awt.*;

public class QuitButton  extends Button implements GameButton {
    RefLinks refLink;
    public QuitButton(RefLinks refLink, int x, int y, int width, int height, String text) {
        super(text);
        this.refLink = refLink;
        setBounds(x, y, width,height);
        setForeground(Color.GRAY);
        setBackground(Color.BLACK);

    }

    @Override
    public void SetForeground(Color c) {
        setForeground(c);
    }

    @Override
    public int GetX() {
        return this.getX();
    }

    @Override
    public int GetY() {
        return this.getY();
    }

    @Override
    public int GetWidth() {
        return this.getWidth();
    }

    @Override
    public int GetHeight() {
        return this.getHeight();
    }

    @Override
    public String GetText() {
        return getLabel();
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(getForeground());
        g.drawString(getLabel(), getX() + 10, getY() + getHeight() / 2 );
    }
}
