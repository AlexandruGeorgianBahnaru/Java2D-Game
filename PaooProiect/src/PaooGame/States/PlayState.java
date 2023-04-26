package PaooGame.States;

import PaooGame.Items.Bullet;
import PaooGame.Items.Character;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map map;
    private Enemy enemy;

/*!< Referinta catre harta curenta.*/
    /*! \fn public PlayState(PaooGame.RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map(refLink);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        ///Construieste eroul
        if(Hero.onlyOne == null) {
            hero = new Hero(refLink, 100, 100);
            Hero.onlyOne = hero;
        }
        enemy = new Enemy(refLink,300, 100);

    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        map.Update();
        hero.Update();

        ArrayList bullets = hero.getBullet();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet p = (Bullet) bullets.get(i);
            if (p.isVisible() == true) {
                p.Update();
            } else {
                bullets.remove(i);
                refLink.GetKeyManager().pKey.remove(i);
            }
        }
        if(enemy != null) {
            enemy.Update();
            bullets = enemy.getBullet();
            for (int i = 0; i < bullets.size(); i++) {
                Bullet p = (Bullet) bullets.get(i);
                if (p.isVisible() == true) {
                    p.Update();
                } else {
                    bullets.remove(i);
                    refLink.GetKeyManager().oKey.remove(i);
                }
            }
        }
    }

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */

    @Override
    public void Draw(Graphics g)
    {
        map.Draw(g);
        hero.Draw(g);
        if(enemy != null)
            enemy.Draw(g);
    }
    public Hero GetHero()
    {
        return hero;
    }
    public Enemy GetEnemy()
    {
        return enemy;
    }
    public void SetEnemy()
    {
        enemy = null;
    }
}
