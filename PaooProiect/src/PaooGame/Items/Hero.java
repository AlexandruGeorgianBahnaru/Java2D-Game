package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.Tiles.Tile;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    public static Hero onlyOne = null;
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    protected int counter;
    protected int indexAnimation;
    protected int sleepBullet;
    protected boolean last;
    protected ArrayList<Bullet> bullet = new ArrayList<Bullet>();
    /*! \fn public Hero(PaooGame.RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
        ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft1;
        counter = 0;
        last = false;
        sleepBullet = 10;
        indexAnimation = 0;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update( )
    {
        ///Verifica daca a fost apasata o tasta
        //GetInput();
        ///Actualizeaza pozitia

        ///Actualizeaza imaginea
        counter++;
        if (refLink.GetKeyManager().left == true) {
            if(last) {
                indexAnimation = 0;
                last = false;
                MoveAnimationLeftRight(last);
                counter = 0;
            }
            else {
                if (counter > 10) {
                    MoveAnimationLeftRight(last);
                    counter = 0;
                }
            }
        }
        else if (refLink.GetKeyManager().right == true) {
            if(!last) {
                indexAnimation = 0;
                last = true;
                MoveAnimationLeftRight(last);
                counter = 0;
            }
            else {
                if (counter > 10) {
                    MoveAnimationLeftRight(last);
                    counter = 0;
                }
            }
        }
        else if (refLink.GetKeyManager().up == true)
        {
            if(counter > 10)
            {
                if (last) {
                    MoveAnimationUpDown(last);
                    counter = 0;
                }
                else{

                    MoveAnimationUpDown(last);
                    counter = 0;
                }
            }

        }
        else if (refLink.GetKeyManager().down == true)
        {
            if(counter > 10)
            {
                if (last) {
                    MoveAnimationUpDown(last);
                    counter = 0;
                }
                else{

                    MoveAnimationUpDown(last);
                    counter = 0;
                }
            }
        }
        else {
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
        if(refLink.GetKeyManager().pPressed == true)
        {
            if(sleepBullet >= 10 )
            {
                shoot();
                sleepBullet = 0;
            }
            else{
                sleepBullet++;
            }
        }
        else {
            sleepBullet = 10;
        }


    }
    @Override
    public void shoot() {
        Bullet b = new Bullet(refLink, x + 40, (float)(y  + 16.5), 0);
        bullet.add(b);
        if(last)
            image = Assets.heroShootRight;
        else
            image = Assets.heroShootLeft;
    }

    @Override
    public void MoveAnimationLeftRight(boolean LeftRight) {
        if(LeftRight)
            image = Assets.animationImagesHero[3][indexAnimation];
        else
            image = Assets.animationImagesHero[2][indexAnimation];
        indexAnimation++;
        if(indexAnimation == 3)
            indexAnimation = 0;
    }

    @Override
    public void MoveAnimationUpDown(boolean LeftRight) {
        if(indexAnimation == 1)
            indexAnimation = 0;
        if(LeftRight)
            image = Assets.animationImagesHero[3][indexAnimation];
        else
            image = Assets.animationImagesHero[2][indexAnimation];
        if(indexAnimation == 0)
            indexAnimation = 2;
        else if(indexAnimation == 2)
            indexAnimation = 0;
    }

    @Override
    public void StandAnimation(boolean LeftRight) {
        if(LeftRight)
            image = Assets.animationImagesHero[1][indexAnimation];
        else
            image = Assets.animationImagesHero[0][indexAnimation];
        indexAnimation++;
        if(indexAnimation == 3)
            indexAnimation = 0;
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    protected void GetInput()
    {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
        ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up && mapColision() != 3 &&  mapColision() != 5 &&  mapColision() != 7)
        {
            yMove = -speed;
        }
        ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down && mapColision() != 4 &&  mapColision() != 6 &&  mapColision() != 8)
        {
            yMove = speed;
        }
        ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left && mapColision() != 2 &&  mapColision() != 5 &&  mapColision() != 6)
        {
            xMove = -speed;
        }
        ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right && mapColision() != 1 &&  mapColision() != 7 &&  mapColision() != 8)
        {
            xMove = speed;
        }
    }

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {

        g.drawImage(image, (int)x, (int)y, width, height, null);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        ArrayList bullets = getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet p = (Bullet) bullets.get(i);
            p.Draw(g);
        }
        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }

    @Override
    public int mapColision() {
        if(x + 17 * speed> refLink.GetWidth() && y < 0) // dreapta sus
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
        else if (tileBorderColision() == 1) //sus
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
        //x , x + normalBounds.wigth
        // y , y + normaBounds.height
        int leftX = (int) (bounds.x + refLink.GetMap().positionX);
        int rightX = (int) (bounds.x + bounds.width + refLink.GetMap().positionX);
        int topY =(int)((bounds.y + refLink.GetMap().positionY));
        int bottomY = (int)((bounds.y + bounds.height + refLink.GetMap().positionY));
        int leftCol = leftX / Tile.TILE_WIDTH;
        int rightCol = rightX/ Tile.TILE_WIDTH;
        int topRow =  topY / Tile.TILE_HEIGHT;
        int bottomRow = bottomY / Tile.TILE_HEIGHT;
        //System.out.println(" PozMatrice " + col + " " + row + " " + col1 + " "+ row1);
        //System.out.println(" dr " + refLink.GetMap().GetTile(col, row1).getClass().toString() + " " + refLink.GetMap().GetTile(col1, row1).getClass().toString());
        if(refLink.GetMap().GetTile(leftCol, (int) ((topY - speed)/Tile.TILE_HEIGHT)).IsSolid() || refLink.GetMap().GetTile(rightCol, (int) ((topY - speed)/Tile.TILE_HEIGHT)).IsSolid())
        {
            //System.out.println(" SUS " + col + " " + row + " " + col + " "+ row1);
            //System.out.println(" adasdasdasda " + refLink.GetMap().GetTile(row, col).getClass().toString() + " " + refLink.GetMap().GetTile(row1, col).getClass().toString());
            return 1;///sus
        }
        if (refLink.GetMap().GetTile(leftCol, (int) ((bottomY + speed)/Tile.TILE_HEIGHT)).IsSolid() || refLink.GetMap().GetTile(rightCol, (int) ((bottomY + speed)/Tile.TILE_HEIGHT)).IsSolid())
        {

            //System.out.println(" jos " + refLink.GetMap().GetTile(col, row1).IsSolid() + " " + refLink.GetMap().GetTile(col1, row1).IsSolid());
            return 2;//jos
        }
        if   (refLink.GetMap().GetTile((int) ((leftX - speed)/Tile.TILE_HEIGHT), topRow).IsSolid() || refLink.GetMap().GetTile((int) ((leftX - speed)/Tile.TILE_HEIGHT), bottomRow).IsSolid())
        {

            //System.out.println(" stg " + row + " " + row1 + " " + col1 +  " " + row1);
            return 3;//stg
        }
        if (refLink.GetMap().GetTile((int) ((rightX + speed)/Tile.TILE_HEIGHT), topRow).IsSolid() || refLink.GetMap().GetTile((int) ((rightX + speed)/Tile.TILE_HEIGHT), bottomRow).IsSolid())
        {

            //System.out.println(" dr " + refLink.GetMap().GetTile(col, row1).IsSolid() + " " + refLink.GetMap().GetTile(col1, row1).IsSolid());
            return 4;//dr
        }
        return 0;
    }
    @Override
    public int enemyColision(Character character) {
        return 0;
    }

    public ArrayList getBullet() {
        return bullet;
    }


}
