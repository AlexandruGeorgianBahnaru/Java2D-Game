package PaooGame.GUI;

import PaooGame.RefLinks;

import java.awt.*;
//Graphic User Interface
//pentru afisarea continua a vietii si a scorului
public class GUI {
    RefLinks refLink;
    public GUI(RefLinks refLinks)
    {
        refLink = refLinks;
    }

    public void Draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(10, 10, refLink.GetHero().GetLife() * 15, 15);
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 90, 15);
        g.drawRect(10, 10, 75, 15);
        g.drawRect(10, 10, 60, 15);
        g.drawRect(10, 10, 45, 15);
        g.drawRect(10, 10, 30, 15);
        g.drawRect(10, 10, 15, 15);
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString(String.valueOf("Score: " + refLink.GetHero().score), 860, 30);
    }
}
