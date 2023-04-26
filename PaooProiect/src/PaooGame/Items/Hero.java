package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;

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
    protected int sleepBullet;
    protected int last;
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
        last = 0;
        sleepBullet = 0;
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 32;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update( )
    {
        ///Verifica daca a fost apasata o tasta
        GetInput();
        ///Actualizeaza pozitia

        Move();
        ///Actualizeaza imaginea
        counter++;
        if (refLink.GetKeyManager().left == true) {
            if(last == 1) {
                image = Assets.heroLeft1;
                last = 0;
                counter = 0;
            }
            else
            if(counter > 10) {
                if(image == Assets.heroLeftStand1 ||
                        image == Assets.heroLeftStand2||
                        image == Assets.heroLeftStand3 ||
                        image == Assets.heroShootLeft)
                    image = Assets.heroLeft1;
                else if (image == Assets.heroLeft1)
                    image = Assets.heroLeft2;
                else if (image == Assets.heroLeft2)
                    image = Assets.heroLeft3;
                else if (image == Assets.heroLeft3)
                    image = Assets.heroLeft1;
                counter = 0;
            }
        }
        else if (refLink.GetKeyManager().right == true) {
            if(last == 0) {
                image = Assets.heroRight1;
                last = 1;
                counter = 0;
            }
            else
            if(counter > 10) {
                if(image == Assets.heroRightStand1 ||
                        image == Assets.heroRightStand2||
                        image == Assets.heroRightStand3 ||
                        image == Assets.heroShootRight)
                    image = Assets.heroRight1;
                else if (image == Assets.heroRight1)
                    image = Assets.heroRight2;
                else if (image == Assets.heroRight2)
                    image = Assets.heroRight3;
                else if (image == Assets.heroRight3)
                    image = Assets.heroRight1;
                counter = 0;
            }
        }
        else if (refLink.GetKeyManager().up == true)
        {
            if(counter > 10) {
                if (last == 1) {
                    if (image == Assets.heroRightStand1 ||
                            image == Assets.heroRightStand2 ||
                            image == Assets.heroRightStand3 ||
                            image == Assets.heroRight2 ||
                            image == Assets.heroShootRight)
                        image = Assets.heroRight1;
                    else if (image == Assets.heroRight1)
                        image = Assets.heroRight3;
                    else if (image == Assets.heroRight3)
                        image = Assets.heroRight1;
                    counter = 0;
                }
                else{
                    if(image == Assets.heroLeftStand1 ||
                            image == Assets.heroLeftStand2 ||
                            image == Assets.heroLeftStand3 ||
                            image == Assets.heroLeft2 ||
                            image == Assets.heroShootLeft)
                        image = Assets.heroLeft1;
                    else if (image == Assets.heroLeft1)
                        image = Assets.heroLeft3;
                    else if (image == Assets.heroLeft3)
                        image = Assets.heroLeft1;
                    counter = 0;

                }
            }
        }
        else if (refLink.GetKeyManager().down == true)
        {
            if(counter > 10) {
                if (last == 1) {
                    if (image == Assets.heroRightStand1 ||
                            image == Assets.heroRightStand2 ||
                            image == Assets.heroRightStand3 ||
                            image == Assets.heroRight2 ||
                            image == Assets.heroShootRight)
                        image = Assets.heroRight3;
                    else if (image == Assets.heroRight1)
                        image = Assets.heroRight3;
                    else if (image == Assets.heroRight3)
                        image = Assets.heroRight1;
                    counter = 0;
                }
                else{
                    if(image == Assets.heroLeftStand1 ||
                            image == Assets.heroLeftStand2 ||
                            image == Assets.heroLeftStand3 ||
                            image == Assets.heroLeft2 ||
                            image == Assets.heroShootLeft)
                        image = Assets.heroLeft3;
                    else if (image == Assets.heroLeft1)
                        image = Assets.heroLeft3;
                    else if (image == Assets.heroLeft3)
                        image = Assets.heroLeft1;
                    counter = 0;

                }
            }
        }
        else { /// Stand animationasdw
            if(last == 1)
            {
                if(counter > 10) {
                    if (image == Assets.heroRight1 ||
                            image == Assets.heroRight2 ||
                            image == Assets.heroRight3 ||
                            image == Assets.heroShootRight)
                        image = Assets.heroRightStand1;
                    else if (image == Assets.heroRightStand1)
                        image = Assets.heroRightStand2;
                    else if (image == Assets.heroRightStand2)
                        image = Assets.heroRightStand3;
                    else if (image == Assets.heroRightStand3)
                        image = Assets.heroRightStand1;
                    counter = 0;
                }
            }
            else
            {
                if(counter > 10) {
                    if (image == Assets.heroLeft1 ||
                            image == Assets.heroLeft2 ||
                            image == Assets.heroLeft3 ||
                            image == Assets.heroShootLeft)
                        image = Assets.heroLeftStand1;
                    else if (image == Assets.heroLeftStand1)
                        image = Assets.heroLeftStand2;
                    else if (image == Assets.heroLeftStand2)
                        image = Assets.heroLeftStand3;
                    else if (image == Assets.heroLeftStand3)
                        image = Assets.heroLeftStand1;
                    counter = 0;
                }
            }

        }
        if(refLink.GetKeyManager().pPressed == true)
        {
            if(sleepBullet >= 10 )
            {
                refLink.GetKeyManager().pKey.add(1);
                shoot();
                sleepBullet = 0;
            }
            else{
                sleepBullet++;
            }
        }
        else if (refLink.GetKeyManager().pKey.size() > bullet.size())
        {
            shoot();
            System.out.println("ShootReleased " + refLink.GetKeyManager().pKey.size()  + " " +  bullet.size());
        }

    }
    @Override
    public void shoot() {
        Bullet b = new Bullet(refLink, x + 40, (float)(y  + 16.5), 0);
        bullet.add(b);
        if(last == 1)
            image = Assets.heroShootRight;
        else
            image = Assets.heroShootLeft;
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

    @Override
    public int enemyColision(Character character) {
        return 0;
    }

    public ArrayList getBullet() {
        return bullet;
    }


}