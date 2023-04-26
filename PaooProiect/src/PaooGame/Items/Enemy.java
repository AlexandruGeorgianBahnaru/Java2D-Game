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
public class Enemy extends Hero
{
    public static Enemy onlyOne = null;
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
        super(refLink, x,y);
        ///Seteaza imaginea de start a eroului
        image = Assets.enemyLeft1;
        counter = 0;
        last = 0;
        width = 60;
        height = 60;
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 25;
        normalBounds.height = 16;

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
        bounds.move((int)this.x, (int)this.y);

        if (refLink.GetKeyManager().left == true) {
            if(last == 1) {
                image = Assets.enemyLeft1;
                last = 0;
                counter = 0;
            }
            else

            if(counter > 10) {
                if (image == Assets.enemyLeftStand1 ||
                        image == Assets.enemyLeftStand2 ||
                        image == Assets.enemyLeftStand3 ||
                        image == Assets.enemyShootLeft)
                    image = Assets.enemyLeft1;
                else if (image == Assets.enemyLeft1)
                    image = Assets.enemyLeft2;
                else if (image == Assets.enemyLeft2)
                    image = Assets.enemyLeft3;
                else if (image == Assets.enemyLeft3)
                    image = Assets.enemyLeft1;
                counter = 0;
            }
        }

        else if (refLink.GetKeyManager().right == true) {
            if(last == 0) {
                image = Assets.enemyRight1;
                last = 1;
                counter = 0;
            }
            else

            if(counter > 10) {
                if (image == Assets.enemyRightStand1 ||
                        image == Assets.enemyRightStand2 ||
                        image == Assets.enemyRightStand3 ||
                        image == Assets.enemyShootRight)
                    image = Assets.enemyRight1;
                else if (image == Assets.enemyRight1)
                    image = Assets.enemyRight2;
                else if (image == Assets.enemyRight2)
                    image = Assets.enemyRight3;
                else if (image == Assets.enemyRight3)
                    image = Assets.enemyRight1;
                counter = 0;
            }

        }
        else if (refLink.GetKeyManager().up == true)
        {
            if(counter > 10) {
                if (last == 1) {
                    if (image == Assets.enemyRightStand1 ||
                            image == Assets.enemyRightStand2 ||
                            image == Assets.enemyRightStand3 ||
                            image == Assets.enemyRight2 ||
                            image == Assets.enemyShootRight)
                        image = Assets.enemyRight1;
                    else if (image == Assets.enemyRight1)
                        image = Assets.enemyRight3;
                    else if (image == Assets.enemyRight3)
                        image = Assets.enemyRight1;
                    counter = 0;
                }
                else{
                    if(image == Assets.enemyLeftStand1 ||
                            image == Assets.enemyLeftStand2 ||
                            image == Assets.enemyLeftStand3 ||
                            image == Assets.enemyLeft2 ||
                            image == Assets.enemyShootLeft)
                        image = Assets.enemyLeft1;
                    else if (image == Assets.enemyLeft1)
                        image = Assets.enemyLeft3;
                    else if (image == Assets.enemyLeft3)
                        image = Assets.enemyLeft1;
                    counter = 0;

                }
            }
        }
        else if (refLink.GetKeyManager().down == true)
        {
            if(counter > 10) {
                if (last == 1) {
                    if (image == Assets.enemyRightStand1 ||
                            image == Assets.enemyRightStand2 ||
                            image == Assets.enemyRightStand3 ||
                            image == Assets.enemyRight2 ||
                            image == Assets.enemyShootRight)
                        image = Assets.enemyRight3;
                    else if (image == Assets.enemyRight1)
                        image = Assets.enemyRight3;
                    else if (image == Assets.enemyRight3)
                        image = Assets.enemyRight1;
                    counter = 0;
                }
                else{
                    if(image == Assets.enemyLeftStand1 ||
                            image == Assets.enemyLeftStand2 ||
                            image == Assets.enemyLeftStand3 ||
                            image == Assets.enemyLeft2 ||
                            image == Assets.enemyShootLeft)
                        image = Assets.enemyLeft3;
                    else if (image == Assets.enemyLeft1)
                        image = Assets.enemyLeft3;
                    else if (image == Assets.enemyLeft3)
                        image = Assets.enemyLeft1;
                    counter = 0;

                }
            }
        }
        else { // stand animation
            if (last == 1) {
                if (counter > 15) {
                    if (image == Assets.enemyRight1 ||
                            image == Assets.enemyRight2 ||
                            image == Assets.enemyRight3 ||
                            image == Assets.enemyShootRight)
                        image = Assets.enemyRightStand1;
                    else if (image == Assets.enemyRightStand1)
                        image = Assets.enemyRightStand2;
                    else if (image == Assets.enemyRightStand2)
                        image = Assets.enemyRightStand3;
                    else if (image == Assets.enemyRightStand3)
                        image = Assets.enemyRightStand1;
                    counter = 0;
                }
            } else {
                if (counter > 15) {
                    if (image == Assets.enemyLeft1 ||
                            image == Assets.enemyLeft2 ||
                            image == Assets.enemyLeft3 ||
                            image == Assets.enemyShootLeft)
                        image = Assets.enemyLeftStand1;
                    else if (image == Assets.enemyLeftStand1)
                        image = Assets.enemyLeftStand2;
                    else if (image == Assets.enemyLeftStand2)
                        image = Assets.enemyLeftStand3;
                    else if (image == Assets.enemyLeftStand3)
                        image = Assets.enemyLeftStand1;
                    counter = 0;
                }
            }

        }
        if(refLink.GetKeyManager().oPressed == true)
        {
            if(sleepBullet >= 10 )
            {
                refLink.GetKeyManager().oKey.add(1);
                shoot();
                sleepBullet = 0;
            }
            else{
                sleepBullet++;
            }
        }
        else if (refLink.GetKeyManager().oKey.size() > bullet.size())
        {
            shoot();
            System.out.println("ShootReleased " + refLink.GetKeyManager().oKey.size()  + " " +  bullet.size());
        }

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
        if(last == 1)
            image = Assets.enemyShootRight;
        else
            image = Assets.enemyShootLeft;
    }
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



}
