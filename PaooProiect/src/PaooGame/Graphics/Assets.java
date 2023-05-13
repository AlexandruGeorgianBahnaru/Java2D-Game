package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage[][] animationImagesHero;
    public static BufferedImage[][] animationImagesEnemy;
    public static BufferedImage heroLeftStand1;
    public static BufferedImage heroLeftStand2;
    public static BufferedImage heroLeftStand3;
    public static BufferedImage heroLeft1;
    public static BufferedImage heroLeft2;
    public static BufferedImage heroLeft3;
    public static BufferedImage heroRightStand1;
    public static BufferedImage heroRightStand2;
    public static BufferedImage heroRightStand3;
    public static BufferedImage heroRight1;
    public static BufferedImage heroRight2;
    public static BufferedImage heroRight3;
    public static BufferedImage enemyRightStand1;
    public static BufferedImage enemyRightStand2;
    public static BufferedImage enemyRightStand3;
    public static BufferedImage enemyRight1;
    public static BufferedImage enemyRight2;
    public static BufferedImage enemyRight3;
    public static BufferedImage enemyLeftStand1;
    public static BufferedImage enemyLeftStand2;
    public static BufferedImage enemyLeftStand3;
    public static BufferedImage enemyLeft1;
    public static BufferedImage enemyLeft2;
    public static BufferedImage enemyLeft3;
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage grass1;
    public static BufferedImage mountain;
    public static BufferedImage water;
    public static BufferedImage waterSandTile;
    public static BufferedImage sand;
    public static BufferedImage sand1;
    public static BufferedImage bulletHero1;
    public static BufferedImage bulletHero2;
    public static BufferedImage bulletEnemy;

    public static BufferedImage heroShootLeft;
    public static BufferedImage heroShootRight;
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
        mountain = tileSheet.cropTile(2,1);

        sand = tileSheet.cropTile(1, 1);
        sand1 = tileSheet.cropTile(1, 0);
        waterSandTile = tileSheet.cropTile(5, 0);
        water = tileSheet.cropTile(5, 1);

        bulletHero1 = bulletSheet.cropBullet(0, 0);
        bulletHero2 = bulletSheet.cropBullet(0, 1);
        bulletEnemy = bulletSheet.cropBullet(0, 2);

        heroLeftStand1 = characterSheet.cropCharacter(0, 0);
        heroLeftStand2 = characterSheet.cropCharacter(1, 0);
        heroLeftStand3 = characterSheet.cropCharacter(2, 0);

        heroLeft1 = characterSheet.cropCharacter(0, 1);
        heroLeft2 = characterSheet.cropCharacter(1, 1);
        heroLeft3 = characterSheet.cropCharacter(2, 1);

        heroRightStand1 = characterSheet.cropCharacter(9, 0);
        heroRightStand2 = characterSheet.cropCharacter(10, 0);
        heroRightStand3 = characterSheet.cropCharacter(11, 0);

        heroRight1 = characterSheet.cropCharacter(9, 1);
        heroRight2 = characterSheet.cropCharacter(10, 1);
        heroRight3 = characterSheet.cropCharacter(11, 1);

        enemyLeftStand1 = characterSheet.cropEnemy(5, 0);
        enemyLeftStand2 = characterSheet.cropEnemy(4, 0);
        enemyLeftStand3 = characterSheet.cropEnemy(3, 0);

        enemyLeft1 = characterSheet.cropEnemy(5, 1);
        enemyLeft2 = characterSheet.cropEnemy(4, 1);
        enemyLeft3 = characterSheet.cropEnemy(3, 1);

        enemyRightStand1 = characterSheet.cropEnemy(0, 0);
        enemyRightStand2 = characterSheet.cropEnemy(1, 0);
        enemyRightStand3 = characterSheet.cropEnemy(2, 0);

        enemyRight1 = characterSheet.cropEnemy(0, 1);
        enemyRight2 = characterSheet.cropEnemy(1, 1);
        enemyRight3 = characterSheet.cropEnemy(2, 1);

        heroShootLeft = characterSheet.cropCharacter(12, 0);
        heroShootRight = characterSheet.cropCharacter(12, 1);

        enemyShootLeft = characterSheet.cropEnemy(6, 1);
        enemyShootRight = characterSheet.cropEnemy(6, 0);

    }
}
