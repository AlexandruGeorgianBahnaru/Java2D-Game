package PaooGame.States;

import PaooGame.DataBaseHandler.DataBaseData;
import PaooGame.DataBaseHandler.DataBaseHandler;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;

/*! \class public class SettingsState extends State
    \brief Implementeaza notiunea de settings pentru joc.

    Aici setarile vor trebui salvate/incarcate intr-un/dintr-un fisier/baza de date sqlite.
 */
public class LoadState extends State
{
    /*! \fn public SettingsState(PaooGame.RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    private int selectedRow;

    ArrayList<DataBaseData> data;
    public LoadState(RefLinks refLink)
    {
        ///Apel al construcotrului clasei de baza.
        super(refLink);
        data = refLink.GetGame().GetDataBaseHandler().GetDatabaseData();
        selectedRow = 0;
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea setarilor.
     */
    @Override
    public void Update()
    {
        if(refLink.GetKeyManager().oPressed)
        {
            selectedRow++;
            refLink.GetKeyManager().oPressed = false;
            if(selectedRow == data.size())
                selectedRow = 0;
        }
        if(refLink.GetKeyManager().iPressed)
        {
            selectedRow--;
            refLink.GetKeyManager().iPressed = false;
            if(selectedRow == -1)
                selectedRow = data.size() - 1;

        }
        if(refLink.GetKeyManager().sPressed)
        {
            refLink.GetGame().GetPlayState().LoadGame(data.get(selectedRow));
            refLink.GetEnemy().clear();
            refLink.GetMap().LoadWorld();
            refLink.GetGame().GetPlayState().RecreateEnemy();
            State.SetState(refLink.GetGame().GetPlayState());
        }
    }

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Deseneaza (randeaza) pe ecran setarile.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        DrawTitle(g, "Choose what save you want to load.", 40, 95, 100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        for (int i = 0; i < data.size(); i++)
        {
            g.setColor(Color.BLACK);
            g.drawString("Id: " + data.get(i).id + "  PlayerName: " + data.get(i).name +
                    " PositionX: " + data.get(i).positionX + " PositionY: " + data.get(i).positionY +
                    " MapId: " + data.get(i).mapID + " Score: " + data.get(i).score +
                    " Life: " + data.get(i).numLifes,60, i * 40 + 150);
            if(i == selectedRow)
            {
                g.setColor(Color.RED);
            }
            else
            {
                g.setColor(Color.BLACK);
            }
            g.drawRect(58, i * 40 + 132, 850, 25);
            g.drawRect(57, i * 40 + 131, 850, 27);
        }
    }
}
