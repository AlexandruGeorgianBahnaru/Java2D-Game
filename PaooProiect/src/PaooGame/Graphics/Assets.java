package PaooGame.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage[][] animationImagesHero;
    public static BufferedImage[][] animationImagesEnemy;

    public static BufferedImage heroLeft1;
    public static BufferedImage enemyLeft1;
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage grass1;
    public static BufferedImage mountain;
    public static BufferedImage water;
    public static BufferedImage waterSand;
    public static BufferedImage mountainGate;
    public static BufferedImage mountainHole;
    public static BufferedImage pavement;
    public static BufferedImage pavement1;
    public static BufferedImage sand;
    public static BufferedImage sand1;
    public static BufferedImage bulletHero1;
    public static BufferedImage bulletHero2;
    public static BufferedImage bulletEnemy;
    public static BufferedImage heroShootRight;
    public static BufferedImage heroShootLeft;
    public static BufferedImage enemyShootLeft;
    public static BufferedImage enemyShootRight;


    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizatep
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {

        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.LoadImage("res/textures/Tile.png"));
        SpriteSheet characterSheet = new SpriteSheet(ImageLoader.LoadImage("res/textures/SpriteCharacter.png"));
        SpriteSheet bulletSheet = new SpriteSheet(ImageLoader.LoadImage("res/textures/Bullets.png"));

        animationImagesHero = new BufferedImage[4][3];
        animationImagesEnemy = new BufferedImage[4][3];

        //Stand hero animation left and right
        animationImagesHero[0][0] = characterSheet.cropCharacter(0, 0);
        animationImagesHero[0][1] = characterSheet.cropCharacter(1, 0);
        animationImagesHero[0][2] = characterSheet.cropCharacter(2, 0);
        animationImagesHero[1][0] = characterSheet.cropCharacter(9, 0);
        animationImagesHero[1][1] = characterSheet.cropCharacter(10, 0);
        animationImagesHero[1][2] = characterSheet.cropCharacter(11, 0);

        //Move hero animation left and right
        animationImagesHero[2][0] = characterSheet.cropCharacter(0, 1);
        animationImagesHero[2][1] = characterSheet.cropCharacter(1, 1);
        animationImagesHero[2][2] = characterSheet.cropCharacter(2, 1);
        animationImagesHero[3][0] = characterSheet.cropCharacter(9, 1);
        animationImagesHero[3][1] = characterSheet.cropCharacter(10, 1);
        animationImagesHero[3][2] = characterSheet.cropCharacter(11, 1);

        //Stand enemy animation left and right
        animationImagesEnemy[0][0] = characterSheet.cropEnemy(5, 0);
        animationImagesEnemy[0][1] = characterSheet.cropEnemy(4, 0);
        animationImagesEnemy[0][2] = characterSheet.cropEnemy(3, 0);
        animationImagesEnemy[1][0] = characterSheet.cropEnemy(0, 0);
        animationImagesEnemy[1][1] = characterSheet.cropEnemy(1, 0);
        animationImagesEnemy[1][2] = characterSheet.cropEnemy(2, 0);

        //Move enemy animation left and right
        animationImagesEnemy[2][0] = characterSheet.cropEnemy(5, 1);
        animationImagesEnemy[2][1] = characterSheet.cropEnemy(4, 1);
        animationImagesEnemy[2][2] = characterSheet.cropEnemy(3, 1);
        animationImagesEnemy[3][0] = characterSheet.cropEnemy(0, 1);
        animationImagesEnemy[3][1] = characterSheet.cropEnemy(1, 1);
        animationImagesEnemy[3][2] = characterSheet.cropEnemy(2, 1);

        grass = tileSheet.cropTile(0,0);
        grass1 = tileSheet.cropTile(0,1);
        soil = tileSheet.cropTile(2,0);

        mountainGate = tileSheet.cropTile(4,1);
        mountainHole = tileSheet.cropTile(4,0);
        mountain = tileSheet.cropTile(2,1);

        pavement = tileSheet.cropTile(3,0);
        pavement1 = tileSheet.cropTile(3,1);
        sand = tileSheet.cropTile(1, 0);

        sand1 = tileSheet.cropTile(1, 1);
        soil = tileSheet.cropTile(2, 0);

        waterSand = tileSheet.cropTile(5, 0);
        water = tileSheet.cropTile(5, 1);

        bulletHero1 = bulletSheet.cropBullet(0, 0);
        bulletHero2 = bulletSheet.cropBullet(0, 1);
        bulletEnemy = bulletSheet.cropBullet(0, 2);

        heroShootRight = characterSheet.cropCharacter(12, 1);
        heroShootLeft = characterSheet.cropCharacter(12, 0);
        enemyShootLeft = characterSheet.cropEnemy(6, 1);
        enemyShootRight = characterSheet.cropEnemy(6, 0);

    }
}
