package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.MountainTile;
import PaooGame.Tiles.Tile;

import javax.swing.text.Position;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Enemy extends Character
{
    public int counter; // sleep la schimbarea imaginilor
    public boolean last; // stanga 1 sau dreapta 0
    public int sleepBullet;
    public static int sleepBulletLimit;
    private int indexAnimation;
    private boolean isVisible; // vizibilitatea pe ecran
    public int offsetX; // pentru a nu se muta eney ul cate un tile intreg si a fi mai lina schimbarea pozitiei
    public int offsetY;// pentru a nu se muta eney ul cate un tile intreg si a fi mai lina schimbarea pozitiei
    public int BaseX;// x ul din matricea de iteme
    public int BaseY;// y ul din matricea de iteme
    protected ArrayList<Bullet> bullet = new ArrayList<Bullet>();

    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    /*! \fn public Hero(PaooGame.RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Enemy(RefLinks refLink, float x, float y)
    {
        ///Apel al constructorului clasei de baza
        super(refLink, x,y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = Assets.enemyLeft1;
        counter = 0;
        last = false;
        sleepBullet = 40;
        width = 60;
        height = 60;
        indexAnimation = 0;
        isVisible = false;
        offsetX = 0;
        offsetY = 0;
        BaseX = 0;
        BaseY = 0;
        sleepBulletLimit = 55;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update( )
    {
        //schimba orientarea enemy ului in functie de erou
        if(refLink.GetHero().GetX() + 15 > x)
        {
            last = true;
        }
        else
        {
            last = false;
        }
        //trage la 40 ticks
        if(refLink.GetMap().levelIndex >= 2) {
            sleepBullet++;
            if (sleepBullet > sleepBulletLimit && isVisible) {
                shoot();
                HitAnimation(last);
                sleepBullet = 0;
            }
        }
        //melee hit
        if(characterColision(refLink.GetHero()) == 1)
        {

            if (last) {
                if (counter > 20) {
                    HitAnimation(last);
                    counter = 0;
                    refLink.GetHero().HeroHit();
                }
            }
            else {

                if (counter > 20) {
                    HitAnimation(last);
                    counter = 0;
                    refLink.GetHero().HeroHit();
                }
            }

            counter++;
        }
        else {

            counter++;
            if (last) {
                if (counter > 10) {
                    StandAnimation(last);
                    counter = 0;
                }
            } else {
                if (counter > 10) {
                    StandAnimation(last);
                    counter = 0;
                }
            }
        }

    }
    @Override
    public void Draw(Graphics g)
    {
        if(isVisible) {
            g.drawImage(image, (int) x, (int) y, width, height, null);
        }
        ArrayList bullets = getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet p = (Bullet) bullets.get(i);
            p.Draw(g);
        }
    }
    //schimbarea pozitite in funcite de deplasarea eroului
    public void setPosition(int x, int y)
    {
            offsetX = refLink.GetMap().positionX % Tile.TILE_HEIGHT;
            offsetY = refLink.GetMap().positionY % Tile.TILE_HEIGHT;
            this.x = (x - (refLink.GetMap().positionX / Tile.TILE_HEIGHT)) * Tile.TILE_WIDTH - offsetX;
            this.y = (y - (refLink.GetMap().positionY / Tile.TILE_HEIGHT)) * Tile.TILE_HEIGHT - offsetY;

            bounds.setLocation((int) this.x, (int) this.y);
            attackBounds.setLocation((int) this.x, (int) this.y);
            if ((this.x >= Tile.TILE_HEIGHT * -1 &&
                    this.x <= (refLink.GetGame().GetWidth()) - 3) &&
                    (this.y >= Tile.TILE_HEIGHT * -1 &&
                            this.y <= (refLink.GetGame().GetHeight() - 3))) {
                isVisible = true;
            } else {

                isVisible = false;
            }
    }
    @Override
    public void MoveAnimationLeftRight(boolean LeftRight)
    {
        if(LeftRight)
            image = Assets.animationImagesEnemy[3][indexAnimation];
        else
            image = Assets.animationImagesEnemy[2][indexAnimation];
        indexAnimation++;
        if(indexAnimation == 3)
            indexAnimation = 0;
    }

    public void HitAnimation(boolean isRight)
    {
        if(isRight){
            if( image == Assets.enemyShootRight)
                image = Assets.animationImagesEnemy[3][0];
            else
                image = Assets.enemyShootRight;
        }
        else {
            if( image == Assets.enemyShootLeft)
                image = Assets.animationImagesEnemy[2][0];
            else {
                image = Assets.enemyShootLeft;
            }
        }
    }
    @Override
    public void MoveAnimationUpDown(boolean LeftRight) {
        if(indexAnimation == 1)
            indexAnimation = 0;
        if(LeftRight)
            image = Assets.animationImagesEnemy[3][indexAnimation];
        else
            image = Assets.animationImagesEnemy[2][indexAnimation];
        if(indexAnimation == 0)
            indexAnimation = 2;
        else if(indexAnimation == 2)
            indexAnimation = 0;
    }

    @Override
    public void StandAnimation(boolean LeftRight) {
        if(LeftRight)
            image = Assets.animationImagesEnemy[1][indexAnimation];
        else
            image = Assets.animationImagesEnemy[0][indexAnimation];
        indexAnimation++;
        if(indexAnimation == 3)
            indexAnimation = 0;
    }


    @Override
    public void shoot() {
        Bullet b;
        if(last)
            b = new Bullet(refLink, x + 50, (float) (y + 16.5), 2, last);
        else
            b = new Bullet(refLink, x - 20, (float) (y + 16.5), 2, last);
        bullet.add(b);
        counter = 0;
    }

    //not used
    @Override
    public int mapColision() {
        return 0;
    }

    public ArrayList getBullet() {
        return bullet;
    }

    //coliziunea cu eroul pentru melee hit
    @Override
    public int characterColision(Character character) {
        int line, column;
        for (line = (int) character.GetX(); line <= character.GetX() + character.GetWidth(); line++) {
            for (column = (int) character.GetY(); column <= character.GetY() + character.GetHeight(); column++) {
                if (line > attackBounds.x && line < attackBounds.width + attackBounds.x &&
                        column > attackBounds.y && column < attackBounds.height + attackBounds.y)
                {
                    return 1;
                }
            }
        }
        return 0;
    }

    public void UpdateBullets(int speed)
    {
        for(int i = 0; i < bullet.size(); i++)
            bullet.get(i).IncreaseY(speed);
    }

}
