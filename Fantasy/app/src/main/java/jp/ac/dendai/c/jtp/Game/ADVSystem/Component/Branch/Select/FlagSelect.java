package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Conditions;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;

/**
 * Created by テツヤ on 2016/10/07.
 */
public class FlagSelect extends Select{
    protected Conditions conditions;
    public boolean evaluation(Event event){
        return conditions.evaluation(event);
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {

    }

    @Override
    public String getTagName() {
        return null;
    }
}
