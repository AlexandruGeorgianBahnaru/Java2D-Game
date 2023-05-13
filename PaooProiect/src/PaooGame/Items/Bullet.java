package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bullet extends Item {
    private float speed;
    private BufferedImage image;
    private boolean isVisible;
    private int bulletType;

    public Bullet(RefLinks refLink, float x, float y, int bulletType) {
        super(refLink, x, y + 10, 14, 5);
        if(bulletType == 2)
        {
            width = 12;
            height = 10;
        }

        if(bulletType == 0)
            image = Assets.bulletHero1;
        else if(bulletType == 1)
            image = Assets.bulletHero2;
        else if(bulletType == 2)
            image = Assets.bulletEnemy;
        this.bulletType = bulletType;
        speed = 5f;
        isVisible = true;
    }

    @Override
    public void Update() {
        int checkColision = mapColision();
        if(checkColision == 1)
        {
            System.out.println("MapCColisoin");
            isVisible = false;
        }
        ArrayList<Enemy> auxEnemy = refLink.GetEnemy();
        for(int j = 0; j < auxEnemy.size(); j++) {
            checkColision = enemyColision(auxEnemy.get(j));
            if (checkColision == 1) {
                refLink.GetEnemy().remove(j);
                System.out.println("EnemyCColisoin");
            }
        }
        if(isVisible && bulletType != 2)
            x += speed;
        else
            x -= speed;
    }

    @Override
    public void Draw(Graphics g) {
         g.drawImage(image, (int) x, (int) y, width, height, null);
    }

    @Override
    public int mapColision() {
        if(x > refLink.GetWidth() || x < 0)
        {
            isVisible = false;
            return 1;
        }

        return 0;
    }

    @Override
    public int enemyColision(Character character) {
        int line, column;
        if(character != null) {
           for (line = (int) this.x; line <= this.x + this.width; line++) {
               for (column = (int) this.y; column <= this.y + this.height; column++) {
                   if (line > character.bounds.x && line < character.bounds.width + character.bounds.x &&
                           column > character.bounds.y && column < character.bounds.height + character.bounds.y)
                       return 1;
               }
           }
        }
        return 0;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
