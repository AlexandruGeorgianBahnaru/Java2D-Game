package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import PaooGame.Buttons.ButtonFactory;
import PaooGame.DataBaseHandler.DataBaseData;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character {
    private static Hero obj;
    public int score;
    public boolean saveLoadOnce;
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
    protected Hero(RefLinks refLink, float x, float y) {
        ///Apel al constructorului clasei de baza
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft1;
        counter = 0;
        last = false;
        sleepBullet = 10;
        indexAnimation = 0;
        score = 0;
        saveLoadOnce = false;
    }
    public static Hero GetInstance(RefLinks refLink, float x, float y)
    {
        if(obj == null)
            obj = new Hero(refLink, x, y);
        return obj;
    }


    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    //update la animatii si gestionarea actiunilor in functie de tastele apasate
    //pPressed - cand p este apasat eroul va trage un glont
    //lPressed - salveaza pozitia, viata, scorul, map in baza de date, poate fi efectuat o singura data
    @Override
    public void Update() {
        ///Verifica daca a fost apasata o tasta
        //GetInput();
        ///Actualizeaza pozitia
        ///Actualizeaza imaginea
        if(life <= 0)
        {
            State.SetState(refLink.GetGame().GetGameOverState());
        }
        counter++;
        if (refLink.GetKeyManager().left == true) {
            if (last) {
                indexAnimation = 0;
                last = false;
                MoveAnimationLeftRight(last);
                counter = 0;
            } else {
                if (counter > 10) {
                    MoveAnimationLeftRight(last);
                    counter = 0;
                }
            }
        } else if (refLink.GetKeyManager().right == true) {
            if (!last) {
                indexAnimation = 0;
                last = true;
                MoveAnimationLeftRight(last);
                counter = 0;
            } else {
                if (counter > 10) {
                    MoveAnimationLeftRight(last);
                    counter = 0;
                }
            }
        } else if (refLink.GetKeyManager().up == true) {
            if (counter > 10) {
                if (last) {
                    MoveAnimationUpDown(last);
                    counter = 0;
                } else {

                    MoveAnimationUpDown(last);
                    counter = 0;
                }
            }

        } else if (refLink.GetKeyManager().down == true) {
            if (counter > 10) {
                if (last) {
                    MoveAnimationUpDown(last);
                    counter = 0;
                } else {

                    MoveAnimationUpDown(last);
                    counter = 0;
                }
            }
        } else {
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
        if (refLink.GetKeyManager().pPressed == true) {
            if (sleepBullet >= 10) {
                shoot();
                sleepBullet = 0;
            } else {
                sleepBullet++;
            }
        }
        else {
            sleepBullet = 10;
        }
        if(refLink.GetKeyManager().lPressed)
        {
            if(saveLoadOnce == false) {
                refLink.GetKeyManager().lPressed = false;
                saveLoadOnce = true;
                DataBaseData dbData = new DataBaseData(0, "Guest", refLink.GetMap().positionX, refLink.GetMap().positionY, refLink.GetMap().levelIndex, score, life);
                refLink.GetGame().GetDataBaseHandler().SaveLoad(dbData);
            }
        }

    }
    // functia care creeaza gloante
    @Override
    public void shoot() {
        Bullet b;
        if (last)
        {
            b = new Bullet(refLink, x + 50, (float) (y + 16.5), 0, last);
            image = Assets.heroShootRight;
        }
        else
        {
            b = new Bullet(refLink, x - 15, (float) (y + 16.5), 0, last);
            image = Assets.heroShootLeft;
        }
        bullet.add(b);
        counter = 0;
    }

    @Override
    public void MoveAnimationLeftRight(boolean LeftRight) {
        if (LeftRight)
            image = Assets.animationImagesHero[3][indexAnimation];
        else
            image = Assets.animationImagesHero[2][indexAnimation];
        indexAnimation++;
        if (indexAnimation == 3)
            indexAnimation = 0;
    }

    @Override
    public void MoveAnimationUpDown(boolean LeftRight) {
        if (indexAnimation == 1)
            indexAnimation = 0;
        if (LeftRight)
            image = Assets.animationImagesHero[3][indexAnimation];
        else
            image = Assets.animationImagesHero[2][indexAnimation];
        if (indexAnimation == 0)
            indexAnimation = 2;
        else if (indexAnimation == 2)
            indexAnimation = 0;
    }

    @Override
    public void StandAnimation(boolean LeftRight) {
        if (LeftRight)
            image = Assets.animationImagesHero[1][indexAnimation];
        else
            image = Assets.animationImagesHero[0][indexAnimation];
        indexAnimation++;
        if (indexAnimation == 3)
            indexAnimation = 0;
    }

     //deseneaza pe ecran eroul si gloantele acestuia
    @Override
    public void Draw(Graphics g) {

        g.drawImage(image, (int) x, (int) y, width, height, null);
        ArrayList bullets = getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet p = (Bullet) bullets.get(i);
            p.Draw(g);
        }
    }

    @Override
    public int mapColision() {
        return tileBorderColision();
    }

    //coliziunea cu tile-urile pe care ar putea ajunge
    //datorita camerei trebuie calculele suplimentare (leftx, rightx, topy, bottomy)
    //coliziunea poate avea loc dora cu tile-uri care sunt Solid
    public int tileBorderColision() {
        //x , x + normalBounds.wigth
        // y , y + normaBounds.height
        int leftX = (int) (bounds.x + refLink.GetMap().positionX);
        int rightX = (int) (bounds.x + bounds.width + refLink.GetMap().positionX);
        int topY = (int) ((bounds.y + refLink.GetMap().positionY));
        int bottomY = (int) ((bounds.y + bounds.height + refLink.GetMap().positionY));

        //calcularea pozitie tile-urilor in matrice
        int leftCol = leftX / Tile.TILE_WIDTH;
        int rightCol = rightX / Tile.TILE_WIDTH;
        int topRow = topY / Tile.TILE_HEIGHT;
        int bottomRow = bottomY / Tile.TILE_HEIGHT;

        if (refLink.GetMap().GetTile(rightCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsSolid()
                && refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), bottomRow).IsSolid()) {
            return 5;//drjos
        }
        if (refLink.GetMap().GetTile(leftCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsSolid()
                && refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), bottomRow).IsSolid()) {
            return 6;//stjos
        }
        if (refLink.GetMap().GetTile(leftCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsSolid()
                && refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), topRow).IsSolid()) {
            return 7;//stsus
        }
        if (refLink.GetMap().GetTile(rightCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsSolid()
                && refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), topRow).IsSolid()) {
            return 8;//drsus
        }
        if (refLink.GetMap().GetTile(leftCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsSolid()
                || refLink.GetMap().GetTile(rightCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsSolid()) {
            return 1;///sus
        }
        if (refLink.GetMap().GetTile(leftCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsSolid()
                || refLink.GetMap().GetTile(rightCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsSolid()) {
            return 2;//jos
        }
        if (refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), topRow).IsSolid()
                || refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), bottomRow).IsSolid()) {
            return 3;//stg
        }
        if (refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), topRow).IsSolid()
                || refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), bottomRow).IsSolid()) {
            return 4;//dr
        }

        return 0;
    }
    //folosita cu coliziunea pentru tile ul care va face trecerea catre urmatorul nivel, asemanator ca functia anterioara
    public int specialTileColision() {
        //x , x + normalBounds.wigth
        // y , y + normaBounds.height
        int leftX = (int) (bounds.x + refLink.GetMap().positionX);
        int rightX = (int) (bounds.x + bounds.width + refLink.GetMap().positionX);
        int topY = (int) ((bounds.y + refLink.GetMap().positionY));
        int bottomY = (int) ((bounds.y + bounds.height + refLink.GetMap().positionY));

        int leftCol = leftX / Tile.TILE_WIDTH;
        int rightCol = rightX / Tile.TILE_WIDTH;
        int topRow = topY / Tile.TILE_HEIGHT;
        int bottomRow = bottomY / Tile.TILE_HEIGHT;
        if (refLink.GetMap().GetTile(leftCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsGate()
                || refLink.GetMap().GetTile(rightCol, (int) ((topY - speed) / Tile.TILE_HEIGHT)).IsGate()
                || refLink.GetMap().GetTile(leftCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsGate()
                || refLink.GetMap().GetTile(rightCol, (int) ((bottomY + speed) / Tile.TILE_HEIGHT)).IsGate()
                || refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), topRow).IsGate()
                || refLink.GetMap().GetTile((int) ((leftX - speed) / Tile.TILE_HEIGHT), bottomRow).IsGate()
                || refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), topRow).IsGate()
                || refLink.GetMap().GetTile((int) ((rightX + speed) / Tile.TILE_HEIGHT), bottomRow).IsGate()) {
            return 1;
        }
        return 0;
    }

    public void HeroHit()
    {
      life -= 1;
    }

    @Override
    public int characterColision(Character character){
        return 0;
    }

    public ArrayList getBullet() {
        return bullet;
    }

    public void UpdateBullets(int speed)
    {
        for(int i = 0; i < bullet.size(); i++)
            bullet.get(i).IncreaseY(speed);
    }

    public void IncreaseScore(int x){score += x;}
    public int GetLife(){return life;}

    public void SetLife(int lifee){life = lifee;}

}
