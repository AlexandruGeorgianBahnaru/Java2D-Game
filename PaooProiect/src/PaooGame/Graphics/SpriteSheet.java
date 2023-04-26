package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class SpriteSheet
    \brief Clasa retine o referinta catre o imagine formata din dale (sprite sheet)

    Metoda crop() returneaza o dala de dimensiuni fixe (o subimagine) din sprite sheet
    de la adresa (x * latimeDala, y * inaltimeDala)
 */
public class SpriteSheet
{
    private BufferedImage       spriteSheet;        /*!< Referinta catre obiectul BufferedImage ce contine sprite sheet-ul.*/
    private static final int    tileWidth   = 48;   /*!< Latimea unei dale din sprite sheet.*/
    private static final int    tileHeight  = 48;   /*!< Inaltime unei dale din sprite sheet.*/

    /*! \fn public SpriteSheet(BufferedImage sheet)
        \brief Constructor, initializeaza spriteSheet.

        \param buffImg Un obiect BufferedImage valid.
     */
    public SpriteSheet(BufferedImage buffImg)
    {
        /// Retine referinta catre BufferedImage object.
        spriteSheet = buffImg;
    }

    public BufferedImage cropCharacter(int x, int y)
    {
        return spriteSheet.getSubimage(x * 16, y * 16, 16, 16);
    }
    public BufferedImage cropEnemy(int x, int y)
    {
        int aux = 32;
        return spriteSheet.getSubimage(x * 25, aux + y * 25, 25, 25);
    }

    public BufferedImage cropBullet(int x, int y)
    {
        if(y != 2)
            return spriteSheet.getSubimage(x * 12, y * 4, 12, 4);
        return spriteSheet.getSubimage(x * 12, y * 4, 8, 7);
    }
    public BufferedImage cropTile(int x, int y)
    {
        return spriteSheet.getSubimage(x * 35, y * 35, 35, 35);
    }
}
