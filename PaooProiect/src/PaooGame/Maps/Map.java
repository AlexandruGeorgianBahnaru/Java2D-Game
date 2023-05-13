package PaooGame.Maps;


import PaooGame.RefLinks;
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


    /*! \fn public Map(PaooGame.RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink)
    {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        positionX = -200;
        positionY = -100;
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        LoadWorld();
    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public  void Update()
    {
        if (refLink.GetKeyManager().left == true && positionX > -460 && refLink.GetHero().tileBorderColision() != 3)
        {
            positionX -= 3.0f;
        }
        if (refLink.GetKeyManager().right == true && positionX < 39 * 48 + 18 && refLink.GetHero().tileBorderColision() != 4)
        {
            positionX += 3.0f;
        }
        if (refLink.GetKeyManager().up == true && positionY > -260 && refLink.GetHero().tileBorderColision() != 1)
        {
            positionY -= 3.0f;
        }
        if (refLink.GetKeyManager().down == true && positionY < 44*48 - 27 && refLink.GetHero().tileBorderColision() != 2)
        {
            positionY += 3.0f;
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
                            //System.out.println(y + " " + x);
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
   /* public void Draw(Graphics g)
    {
        ///Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        for(int y = 0; y < refLink.GetGame().GetHeight()/Tile.TILE_HEIGHT; y++)
        {
            for(int x = 0; x < refLink.GetGame().GetWidth()/Tile.TILE_WIDTH; x++)
            {
                GetTile((int)(x + positionX), (int)(y + positionY)).Draw(g, (int)x * Tile.TILE_HEIGHT, (int)y * Tile.TILE_WIDTH);
            }
        }
    }
*/


    public Tile GetTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= refLink.GetMap().maxWorldX || y >= refLink.GetMap().maxWorldY)
        {
            return Tile.MountainTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];

        return t;
    }

    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld()
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
                tiles[x][y] = LoadMap1(y, x);

            }
        }
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                items[y][x] = LoadItems1(y, x);

            }
        }

    }

    /*! \fn private int MiddleEastMap(int x ,int y)
        \brief O harta incarcata static.

        \param x linia pe care se afla codul dalei de interes.
        \param y coloana pe care se afla codul dalei de interes.
     */
    private int LoadMap1(int x ,int y)
    {
        ///Definire statica a matricei de coduri de dale.
        ArrayList<ArrayList<Integer>> auxMap = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("res/maps/map1.txt")))
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
    private int LoadItems1(int x ,int y)
    {
        ///Definire statica a matricei de coduri de dale.
        ArrayList<ArrayList<Integer>> auxMap = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("res/itemsMap/items1.txt")))
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
    public int[][] GetItems(){return items;}
}