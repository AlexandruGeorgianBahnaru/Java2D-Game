package PaooGame.States;

import java.awt.*;

import PaooGame.RefLinks;

/*! \class State
    \brief Implementeaza notiunea abstracta de stare a jocului/programului.

    Un joc odata ce este lansat in executie nu trebuie "sa arunce jucatorul direct in lupta", este nevoie de
    un meniu care sa contine optiuni: New PaooGame.Game, Load PaooGame.Game, Settings, About etc. Toate aceste optiuni nu sunt altceva
    decat stari ale programului (jocului) ce trebuiesc incarcate si afisate functie de starea curenta.
 */
public abstract class State
{
    ///Urmatoarele atribute sunt statice pentru a evita dealocarea spatiului de memorie la trecerea dintr-o stare in alta.
    private static State previousState  = null; /*!< Referinta catre starea anterioara a jocului.*/
    private static State currentState   = null; /*!< Referinta catre starea curenta a jocului: game, meniu, settings, about etc.*/
    protected RefLinks refLink;
    public State(RefLinks refLink)
    {
        this.refLink = refLink;
    }

    /*! \fn public static void SetState(State state)
        \brief Seteaza starea curenta a jocului.

        \param state Noua stare a programului (jocului).
     */
    public static void SetState(State state)
    {
        previousState = currentState;
        currentState = state;
    }

    public static State GetState()
    {
        return currentState;
    }

    ///Metoda abstracta destinata actualizarii starii curente
    public abstract void Update();
    ///Metoda abstracta destinata desenarii starii curente
    public abstract void Draw(Graphics g);
    public void DrawTitle(Graphics g, String text, int fontSize, int x, int y)
    {
        g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
        g.drawString(text, x, y);
        g.setColor(Color.GRAY);
        g.drawString(text, x + 2, y + 2);
    }
}
