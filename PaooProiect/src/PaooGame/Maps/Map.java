package PaooGame.Maps;


import PaooGame.Items.Bullet;
import PaooGame.Items.Enemy;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map
{
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    private int width;          /*!< Latimea hartii in numar de dale.*/
    private int height;         /*!< Inaltimea hartii in numar de dale.*/
    private int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    private int [][] items;
    public int positionX;
    public int positionY;
    public final int maxWorldX = 50;
    public final int maxWorldY = 50;
    public int levelIndex;


    /*! \fn public Map(PaooGame.RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink)
    {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        positionX = -350;
        positionY = -100;
        levelIndex = 1;
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        LoadWorld();
    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    // update la harta in functie de nivel si pozitia pentru camera
    public  void Update()
    {
        if(refLink.GetHero().specialTileColision() == 1)
        {
            if(levelIndex < 3)
            {
                if(refLink.GetEnemy().size() == 0) {
                    levelIndex++;
                    refLink.GetMap().LoadWorld();
                    refLink.GetGame().GetPlayState().RecreateEnemy();

                    if (levelIndex == 3) {
                        refLink.GetMap().positionX = 990;
                        refLink.GetMap().positionY = 1120;
                        Enemy.sleepBulletLimit = 65;
                    }
                }

            }
            else
            {
                if(refLink.GetGame().GetPlayState().GetEnemy().size() == 0)
                    State.SetState(refLink.GetGame().GetGameOverState());
            }

        }
        if (refLink.GetKeyManager().left == true
                && refLink.GetHero().tileBorderColision() != 3
                && refLink.GetHero().tileBorderColision() != 6
                && refLink.GetHero().tileBorderColision() != 7)
        {
            positionX -= refLink.GetHero().GetSpeed();
        }
        if (refLink.GetKeyManager().right == true
                && refLink.GetHero().tileBorderColision() != 4
                && refLink.GetHero().tileBorderColision() != 5
                && refLink.GetHero().tileBorderColision() != 8)
        {

            positionX += refLink.GetHero().GetSpeed();
        }
        if (refLink.GetKeyManager().up == true
                && refLink.GetHero().tileBorderColision() != 1
                && refLink.GetHero().tileBorderColision() != 7
                && refLink.GetHero().tileBorderColision() != 8)
        {
            positionY -= refLink.GetHero().GetSpeed();
            refLink.GetHero().UpdateBullets((int)refLink.GetHero().GetSpeed());
            UpdateEnemyBullets((int)refLink.GetHero().GetSpeed());
        }
        if (refLink.GetKeyManager().down == true
                && refLink.GetHero().tileBorderColision() != 2
                && refLink.GetHero().tileBorderColision() != 5
                && refLink.GetHero().tileBorderColision() != 6)
        {
            positionY += refLink.GetHero().GetSpeed();
            refLink.GetHero().UpdateBullets((int)refLink.GetHero().GetSpeed() * -1);
            UpdateEnemyBullets((int)refLink.GetHero().GetSpeed() * -1);
        }

        int startX = (int) (positionX / Tile.TILE_WIDTH) - 1;
        int startY = (int) (positionY / Tile.TILE_HEIGHT) - 1;
        int endX = (int) ((positionX + refLink.GetGame().GetWidth()) / Tile.TILE_WIDTH + 1);
        int endY = (int) ((positionY + refLink.GetGame().GetHeight()) / Tile.TILE_HEIGHT + 1);

        for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++)
                if (y >= 0 && x >= 0 && x < 50 && y < 50) {

                    if (items[y][x] == 1) {
                        if (refLink.GetGame().GetPlayState().GetEnemyPosition(y, x) != null) {

                            refLink.GetGame().GetPlayState().GetEnemyPosition(y, x).setPosition(x, y);
                        }
                    }
                }
        }
    }

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public void Draw(Graphics g)
    {
        int startX = (int) (positionX / Tile.TILE_WIDTH) - 1;
        int startY = (int) (positionY / Tile.TILE_HEIGHT) - 1;
        int endX = (int) ((positionX + refLink.GetGame().GetWidth()) / Tile.TILE_WIDTH + 1);
        int endY = (int) ((positionY + refLink.GetGame().GetHeight()) / Tile.TILE_HEIGHT + 1);

        for(int y = startY; y < endY; y++)
        {
            for(int x = startX; x < endX; x++) {
                GetTile(x, y).Draw(g, (int) ((x * Tile.TILE_WIDTH) - positionX), (int) ((y * Tile.TILE_HEIGHT) - positionY));
            }
        }


    }
    //update gloantelor pentru a se misca in functie de camera
    //update pentru gloantele fiecarui inamic
    public void UpdateEnemyBullets(int speed)
    {
        ArrayList<Enemy> e = refLink.GetEnemy();
        for(int i = 0; i < e.size(); i++)
        {
            e.get(i).UpdateBullets(speed);
        }
    }


    public Tile GetTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= refLink.GetMap().maxWorldX || y >= refLink.GetMap().maxWorldY)
        {
            return Tile.mountainTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];

        return t;
    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    public void LoadWorld()
    {
        //atentie latimea si inaltimea trebuiesc corelate cu dimensiunile ferestrei sau
        //se poate implementa notiunea de camera/cadru de vizualizare al hartii
        ///Se stabileste latimea hartii in numar de dale.
        width = maxWorldX;
        ///Se stabileste inaltimea hartii in numar de dale
        height = maxWorldY;
        ///Se construieste matricea de coduri de dale
        tiles = new int[width][height];
        items = new int[width][height];
        //Se incarca matricea cu coduri
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                tiles[x][y] = LoadMap(y, x);

            }
        }
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                items[y][x] = LoadItems(y, x);
            }
        }
    }

    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
    private int LoadMap(int x ,int y)
    {
        ArrayList<ArrayList<Integer>> auxMap = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("res/maps/map" + levelIndex + ".txt")))
        {
            String line;

            while((line = br.readLine()) != null)
            {
                if(line.isEmpty())
                    continue;
                ArrayList<Integer> row = new ArrayList<>();
                String[] values = line.trim().split(" ");
                for(String aux:values)
                {
                    if(!aux.isEmpty())
                    {
                        int id = Integer.parseInt(aux);
                        row.add(id);
                    }
                }
                auxMap.add(row);
            }
        }catch(IOException e)
        {

        }
        return auxMap.remove(x).remove(y);
    }
    private int LoadItems(int x ,int y)
    {
        ///Definire statica a matricei de coduri de dale.
        ArrayList<ArrayList<Integer>> auxMap = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("res/itemsMap/items" + levelIndex + ".txt")))
        {
            String line;

            while((line = br.readLine()) != null)
            {
                if(line.isEmpty())
                    continue;
                ArrayList<Integer> row = new ArrayList<>();
                String[] values = line.trim().split(" ");
                for(String aux:values)
                {
                    if(!aux.isEmpty())
                    {
                        int id = Integer.parseInt(aux);
                        row.add(id);
                    }
                }
                auxMap.add(row);
            }
        }catch(IOException e)
        {

        }
        return auxMap.remove(x).remove(y);
    }
    public void SetItem(int x, int y, int i){items[x][y] = i;}
    public int[][] GetItems(){return items;}
}