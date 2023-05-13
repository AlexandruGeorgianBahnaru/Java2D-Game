package PaooGame.States;

import PaooGame.Items.Bullet;
import PaooGame.Items.Character;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Tile;

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
    private ArrayList<Enemy> enemy;

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
            hero = new Hero(refLink, 460, 260);
            Hero.onlyOne = hero;
        }
        enemy = new ArrayList<>();
        int aux = 0;
        for(int i = 0; i < 50; i++)
        {
            for(int j = 0; j < 50; j++)
                if(refLink.GetMap().GetItems()[i][j] == 1)
                {
                    enemy.add(new Enemy(refLink, j * Tile.TILE_WIDTH, i * Tile.TILE_HEIGHT));
                    aux = enemy.size();
                    enemy.get(aux - 1).BaseX = i;
                    enemy.get(aux - 1).BaseY = j;
                }
        }
        //enemy.add(new Enemy(refLink, 100, 100));

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
            }
        }
        for(int j = 0; j < enemy.size(); j++)
        {
            enemy.get(j).Update();
            bullets = enemy.get(j).getBullet();
            for (int i = 0; i < bullets.size(); i++) {
                Bullet p = (Bullet) bullets.get(i);
                if (p.isVisible() == true) {
                    p.Update();
                } else {
                    bullets.remove(i);
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
        for(int i = 0; i < enemy.size(); i++)
            if(enemy.get(i).GetisVisible() == true)
            {
                System.out.println(enemy.get(1).GetX() +  " " + enemy.get(1).GetY() + " " + 1 +  " " + " " + enemy.get(1).GetisVisible());
                enemy.get(i).Draw(g);
            }
    }
    public Hero GetHero()
    {
        return hero;
    }
    public ArrayList<Enemy> GetEnemy()
    {
        return enemy;
    }
    public Enemy GetEnemyPosition(int x, int y){
        for(int i = 0; i < enemy.size(); i++)
        {
            if(enemy.get(i).BaseY == y
                && enemy.get(i).BaseX == x);
            {
                return enemy.get(i);
            }
        }
        return null;
    }
}
