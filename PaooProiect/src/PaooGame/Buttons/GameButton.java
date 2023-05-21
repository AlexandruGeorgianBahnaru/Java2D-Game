package PaooGame.Buttons;

import javax.accessibility.Accessible;
import java.awt.*;
    //interfata butoanelor
public interface GameButton{
    public void SetForeground(Color c);
    public int GetX();
    public int GetY();
    public int GetWidth();
    public int GetHeight();
    public String GetText();

    public void Draw(Graphics g);
}
