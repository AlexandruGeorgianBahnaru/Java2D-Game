package PaooGame.States;

import PaooGame.Buttons.ButtonFactory;
import PaooGame.Buttons.GameButton;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;

public class GameOverState extends State
{
    private ArrayList<GameButton> buttons;
    private int selectedButton;
    public GameOverState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        buttons = new ArrayList<>();
        selectedButton = 0;
        ButtonFactory buttonFactory = new ButtonFactory();
        try {
            buttons.add(buttonFactory.GetButton("MenuButton", refLink, 410, 250, 200, 50, "Menu"));
            buttons.add(buttonFactory.GetButton("QuitButton", refLink, 410, 310, 200, 50, "Quit"));
        }
        catch(NullPointerException e)
        {

        }
        buttons.get(selectedButton).SetForeground(Color.WHITE);
    }
    /*! \fn public void Update()
        \parcurgera butoanelor folosind tastatura si apasarea folosind tot tastatura
        \'o' pentru a merge in jos cu focusul si 'i' pentru a merge in sus
        \'s' pentru a apasa butonul
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
            if(buttons.get(selectedButton).GetText().equalsIgnoreCase("Menu"))
            {
                State.SetState(refLink.GetGame().GetMenuState());
                refLink.GetKeyManager().sPressed = false;
            }
            else if(buttons.get(selectedButton).GetText().equalsIgnoreCase("Quit"))
            {
                System.exit(1);
            }
        }
    }

    /*! \fn public void Draw(PaooGame.Graphics g)
      \deseneaza pe ecran butoanele si titlul
     */
    @Override
    public void Draw(Graphics g)
    {
        DrawTitle(g, "Game Over", 80, 295, 100);
        if(refLink.GetHero().GetLife() > 0)
        {
            g.setColor(Color.BLACK);
            DrawTitle(g, "You Win", 80, 350, 190);
        }
        else {
            g.setColor(Color.BLACK);
            DrawTitle(g, "You Lose", 80, 350, 190);
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 17));
        g.setColor(Color.BLACK);
        for(int i = 0; i < buttons.size(); i++)
        {
            if(buttons.get(i) != null)
                buttons.get(i).Draw(g);
        }
        g.setColor(Color.CYAN);
        g.drawRect(buttons.get(selectedButton).GetX(), buttons.get(selectedButton).GetY(), buttons.get(selectedButton).GetWidth(), buttons.get(selectedButton).GetHeight());
        g.drawRect(buttons.get(selectedButton).GetX() + 1, buttons.get(selectedButton).GetY() + 1, buttons.get(selectedButton).GetWidth() - 2, buttons.get(selectedButton).GetHeight() - 2);
        refLink.GetGame().GetPlayState().GetGui().Draw(g);
    }

}
