package PaooGame;

import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.Maps.Map;

import PaooGame.Input.KeyManager;

/*! \class public class PaooGame.RefLinks
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor accesibile.

    Altfel ar trebui ca functiile respective sa aiba o serie intreaga de parametri si ar ingreuna programarea.
 */
public class RefLinks
{
    private Game game;          /*!< Referinta catre obiectul PaooGame.Game.*/
    private Map map;            /*!< Referinta catre harta curenta.*/

    /*! \fn public PaooGame.RefLinks(PaooGame.Game game)
        \brief Constructorul de initializare al clasei.

        \param game Referinta catre obiectul game.
     */
    public RefLinks(Game game)
    {
        this.game = game;
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public PaooGame.Game GetGame()
        \brief Intoarce referinta catre obiectul PaooGame.Game.
     */
    public Game GetGame()
    {
        return game;
    }

    /*! \fn public void SetGame(PaooGame.Game game)
        \brief Seteaza referinta catre un obiect PaooGame.Game.

        \param game Referinta obiectului PaooGame.Game.
     */
    public void SetGame(Game game)
    {
        this.game = game;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }
    public Hero GetHero(){return game.GetPlayState().GetHero();}
    public Enemy GetEnemy(){return game.GetPlayState().GetEnemy();}
    public void SetEnemyNull(){game.GetPlayState().SetEnemy();}

    /*! \fn public void SetMap(Map map)
        \brief Seteaza referinta catre harta curenta.

        \param map Referinta catre harta curenta.
     */
    public void SetMap(Map map)
    {
        this.map = map;
    }
}
