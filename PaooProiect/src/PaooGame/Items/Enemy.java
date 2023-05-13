package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Tiles.MountainTile;
import PaooGame.Tiles.Tile;

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
    public boolean last; // stanga sau dreapta
    public int sleepBullet;
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
        sleepBullet = 10;
        width = 60;
        height = 60;
        indexAnimation = 0;
        isVisible = false;
        offsetX = 0;
        offsetY = 0;
        BaseX = 0;
        BaseY = 0;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update( )
    {
        ///Verifica daca a fost apasata o tasta

        ///Actualizeaza pozitia
        ///Actualizeaza imaginea

        counter++;
            if(last)
            {
                if(counter > 10) {
                    StandAnimation(last);
                    counter = 0;
                }
            }
            else
            {
                if(counter > 10) {
                    StandAnimation(last);
                    counter = 0;
                }
            }


    }

    public void setPosition(int x, int y)
    {
        offsetX = refLink.GetMap().positionX % Tile.TILE_HEIGHT;
        offsetY = refLink.GetMap().positionY % Tile.TILE_HEIGHT;
        this.x = (x  - (refLink.GetMap().positionX / Tile.TILE_HEIGHT))* Tile.TILE_WIDTH - offsetX;
        this.y = (y  - (refLink.GetMap().positionY / Tile.TILE_HEIGHT)) * Tile.TILE_HEIGHT - offsetY;
        if( (this.x >= 0 &&
                this.x <= refLink.GetGame().GetWidth()) &&
                (this.y >= 0 &&
                this.y <= refLink.GetGame().GetHeight()))
        {
            //System.out.println(y + " " + x  + " " + refLink.GetMap().positionX +  " " + (refLink.GetMap().positionX + refLink.GetGame().GetWidth()) + " " + this.x +  " " + isVisible);

            isVisible = true;
        }
        else
        {
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

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void shoot() {
        Bullet b = new Bullet(refLink, x-20, (float)(y  + 16.5), 2);
        bullet.add(b);
        if(last)
            image = Assets.enemyShootRight;
        else
            image = Assets.enemyShootLeft;
    }
    @Override
    public void Draw(Graphics g)
    {
        //System.out.println(y + " " + x);
        //System.out.println(refLink.GetMap().positionY + " " + refLink.GetMap().positionX);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.drawImage(image, (int)x, (int)y, width, height, null);
        ArrayList bullets = getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet p = (Bullet) bullets.get(i);
            p.Draw(g);
        }
        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //\g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }

    public ArrayList getBullet() {
        return bullet;
    }
    public int mapColision(){
        if(refLink.GetMap().GetTile((int) (x + 17*speed), (int)y).getClass() == MountainTile.class) // dreapta sus
        {
            return 7;
        }
        else  if(x + 17 * speed> refLink.GetWidth() && y + 17 * speed> refLink.GetHeight()) //dreapta jos
        {
            return 8;
        }
        else if(x < 0 && y < 0) //stanga sus
        {
            return 5;
        }
        else if(x < 0 && (y + 17 * speed> refLink.GetHeight()))// stanga jos
        {
            return 6;
        }
        else if(x + 17 * speed> refLink.GetWidth()) // dreapta
        {
            return 1;
        }
        else if(x < 0)  //stanga
        {
            return 2;
        }
        else if (y < 0) //sus
        {
            return 3;
        }
        else if (y + 17 * speed> refLink.GetHeight()) // jos
        {
            return 4;
        }

        return 0;
    }


    public int tileBorderColision() {

        return 0;
    }

    public boolean GetisVisible(){return isVisible;}
    public int enemyColision(Character character){return 0;};

}
