package PaooGame.Buttons;

import PaooGame.RefLinks;

import java.awt.*;
//factory design pattern
//creeza butoane
public class ButtonFactory {
    public GameButton GetButton(String buttonType, RefLinks reflink, int x, int y, int width, int height, String text){
        if(buttonType == null)
            return null;
        if(buttonType.equalsIgnoreCase("PlayButton"))
            return new PlayButton(reflink, x, y, width, height, text);
        if(buttonType.equalsIgnoreCase("LoadButton"))
            return new LoadButton(reflink, x, y, width, height, text);
        if(buttonType.equalsIgnoreCase("QuitButton"))
            return new LoadButton(reflink, x, y, width, height, text);
        if(buttonType.equalsIgnoreCase("MenuButton"))
            return new LoadButton(reflink, x, y, width, height, text);
        return null;
    }
}
