package PaooGame.States;

import PaooGame.DataBaseHandler.DataBaseData;
import PaooGame.DataBaseHandler.DataBaseHandler;
import PaooGame.GUI.GUI;
import PaooGame.Items.*;
import PaooGame.Items.Character;
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
    private GUI gui;

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
        hero = Hero.GetInstance(refLink, 460, 260);
        gui = new GUI(refLink);
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
             enemy.get(i).Draw(g);
        gui.Draw(g);
    }
    public Hero GetHero()
    {
        return hero;
    }
    public GUI GetGui()
    {
        return gui;
    }
    public ArrayList<Enemy> GetEnemy()
    {
        return enemy;
    }

    // returneaza un inamic care se afla la pozitia x y in matricea de iteme
    public Enemy GetEnemyPosition(int x, int y){
        for(int i = 0; i < enemy.size(); i++)
        {
            if(enemy.get(i).BaseY == y && enemy.get(i).BaseX == x)
            {
                return enemy.get(i);
            }
        }
        return null;
    }
    //recreaza inamicii la schimbarea hartii, la load sau la new game
    public void RecreateEnemy()
    {
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
    }
    //la load initializam toate proprietatile cu ce se gaseste in baza de date
    public void LoadGame(DataBaseData data)
    {
        map.positionX = data.positionX;
        map.positionY = data.positionY;
        map.levelIndex = data.mapID;
        hero.score = data.score;
        hero.SetLife(data.numLifes);
        hero.saveLoadOnce = false;
    }
    //la load initializam toate proprietatile cu valori default
    public void NewGame()
    {
        map.positionX = -350;
        map.positionY = -100;
        map.levelIndex = 1;
        hero.score = 0;
        hero.SetLife(Character.DEFAULT_LIFE);
        hero.saveLoadOnce = false;
    }
}
