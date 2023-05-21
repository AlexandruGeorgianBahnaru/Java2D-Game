package PaooGame.DataBaseHandler;

import java.awt.*;
//clasa folosita pentru gestionarea datelor din baza de data
//I/O
public class DataBaseData {
    public int id;
    public String name;
    public int positionX;
    public int positionY;
    public int mapID;
    public int score;
    public int numLifes;
    public DataBaseData(int idd, String namee, int posX, int posY, int mapid, int scoree, int numlifes)
    {
        id = idd;
        name = namee;
        positionX = posX;
        positionY = posY;
        mapID = mapid;
        score = scoree;
        numLifes = numlifes;
    }
    public void Draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Far away from home", 10, 10);
    }
}
