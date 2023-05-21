package PaooGame.States;

import PaooGame.Buttons.ButtonFactory;
import PaooGame.Buttons.GameButton;
import PaooGame.Buttons.LoadButton;
import PaooGame.Buttons.PlayButton;
import PaooGame.DataBaseHandler.DataBaseData;
import PaooGame.DataBaseHandler.DataBaseHandler;
import PaooGame.RefLinks;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State
{
    /*! \fn public MenuState(PaooGame.RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    private ArrayList<GameButton> buttons;
    private int selectedButton;
    public MenuState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();
        selectedButton = 0;
        ButtonFactory buttonFactory = new ButtonFactory();
        try {
           buttons.add(buttonFactory.GetButton("PlayButton", refLink, 360, 150, 200, 50, "New Game"));
           buttons.add(buttonFactory.GetButton("LoadButton", refLink, 360, 210, 200, 50, "Load Game"));
           buttons.add(buttonFactory.GetButton("QuitButton", refLink, 360, 270, 200, 50, "Quit"));
        }
        catch(NullPointerException e)
        {

        }
        buttons.get(selectedButton).SetForeground(Color.WHITE);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        if(refLink.GetKeyManager().oPressed)
        {
            buttons.get(selectedButton).SetForeground(Color.GRAY);
            selectedButton++;
            refLink.GetKeyManager().oPressed = false;
           if(selectedButton == buttons.size())
               selectedButton = 0;
            buttons.get(selectedButton).SetForeground(Color.WHITE);
        }
        if(refLink.GetKeyManager().iPressed)
        {
                buttons.get(selectedButton).SetForeground(Color.GRAY);
                selectedButton--;
                refLink.GetKeyManager().iPressed = false;
                if(selectedButton == -1)
                    selectedButton = buttons.size() - 1;
                buttons.get(selectedButton).SetForeground(Color.WHITE);

        }
        if(refLink.GetKeyManager().sPressed)
        {
            if(buttons.get(selectedButton).GetText().equalsIgnoreCase("New Game"))
            {
                    refLink.GetGame().GetPlayState().NewGame();
                    refLink.GetEnemy().clear();
                    refLink.GetMap().LoadWorld();
                    refLink.GetGame().GetPlayState().RecreateEnemy();
                    State.SetState(refLink.GetGame().GetPlayState());
                    refLink.GetKeyManager().sPressed = false;
            }
            else if(buttons.get(selectedButton).GetText().equalsIgnoreCase("Load Game"))
            {
                State.SetState(refLink.GetGame().GetLoadState());
                refLink.GetKeyManager().sPressed = false;
            }
            else if(buttons.get(selectedButton).GetText().equalsIgnoreCase("Quit"))
            {
                System.exit(1);
            }
        }

    }

    /*! \fn public void Draw(PaooGame.Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        DrawTitle(g, "Far away from home", 80, 95, 100);
        g.setFont(new Font("TimesRoman", Font.BOLD, 17));
       for(int i = 0; i < buttons.size(); i++)
       {
           if(buttons.get(i) != null)
               buttons.get(i).Draw(g);
       }
        g.setColor(Color.CYAN);
        g.drawRect(buttons.get(selectedButton).GetX(), buttons.get(selectedButton).GetY(), buttons.get(selectedButton).GetWidth(), buttons.get(selectedButton).GetHeight());
        g.drawRect(buttons.get(selectedButton).GetX() + 1, buttons.get(selectedButton).GetY() + 1, buttons.get(selectedButton).GetWidth() - 2, buttons.get(selectedButton).GetHeight() - 2);
    }
}
