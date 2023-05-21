package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bullet extends Item {
    private float speed;
    private BufferedImage image;
    private boolean isVisible;
    private int bulletType;
    private boolean last;
    int offsetX;


    public Bullet(RefLinks refLink, float x, float y, int bulletType, boolean last) {
        super(refLink, x, y + 10, 14, 5);
        if(bulletType == 2)
        {
            width = 12;
            height = 10;
        }
        this.bulletType = bulletType;
        if(!last) {
            speed = 5f;
            image = Assets.bulletHero2;
        }
        else
        {
            speed = -5f;
            image = Assets.bulletHero1;
        }
        if(bulletType == 2)
            image = Assets.bulletEnemy;
        isVisible = true;
        offsetX = 0;
        last = false;
    }
    //update ul pozitie gloantelor si vizibilitatii lor
    @Override
    public void Update() {
        int checkColision = mapColision();
        if(checkColision == 1)
        {
            isVisible = false;
        }
        for(int j = 0; j < refLink.GetEnemy().size(); j++) {
            checkColision = characterColision(refLink.GetEnemy().get(j));
            if (checkColision == 1 && bulletType == 0) {
                refLink.GetMap().SetItem(refLink.GetEnemy().get(j).BaseX, refLink.GetEnemy().get(j).BaseX, 0);
                {
                    refLink.GetEnemy().remove(j);
                    isVisible = false;
                    refLink.GetHero().IncreaseScore(10);
                }
            }
        }
        checkColision = characterColision(refLink.GetHero());
        if(checkColision == 1 && bulletType == 2)
        {
            refLink.GetHero().HeroHit();
            refLink.GetHero().HeroHit();
            isVisible = false;
        }
        //aici se calculeaza un offset pe axa x
        //daca eroul merge spre dreapta atunci glontul va trebui sa calatoreasca spre stanga default speed + hero speed]
        //                                                                       iar spre dreapta speed - hero speed
        if(refLink.GetKeyManager().left)
            offsetX = (int)Character.DEFAULT_SPEED * -1;
        else if(refLink.GetKeyManager().right)
            offsetX = (int)Character.DEFAULT_SPEED;
        else
            offsetX = 0;
        if(last)
        {
            x += speed + (-1 * offsetX);
        }
        else
        {
            x -= speed + offsetX;
        }

    }

    @Override
    public void Draw(Graphics g) {
         g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    // coliziunea cu limitele window-lui
    //gloantele nu isi vor continua deplasarea si in afara ecranului
    @Override
    public int mapColision() {
        if(x > refLink.GetWidth() || x < 0)
        {
            isVisible = false;
            return 1;
        }

        return 0;
    }
    //verificare coliziunii cu un caracter
    //daca orice pixel din bound-ul glontului ating orice pixel din bound-ul caracterului se considera HIT
    @Override
    public int characterColision(Character character) {
        int line, column;
        if(character != null) {
           for (line = (int) this.x; line <= this.x + this.width; line++) {
               for (column = (int) this.y; column <= this.y + this.height; column++) {
                   if (line > character.bounds.x && line < character.bounds.width + character.bounds.x &&
                           column > character.bounds.y && column < character.bounds.height + character.bounds.y)
                   {
                           return 1;

                   }
               }
           }
        }
        return 0;
    }

    public boolean isVisible() {
        return isVisible;
    }
    //folosita pntru in modifica y in functie de cum merge eroul
    public void IncreaseY(int speedd){y += speedd;}
}
