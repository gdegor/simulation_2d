package com.egovoryn.actions;

//  Action - действие, совершаемое над миром.

import com.egovoryn.WorldMap;

public abstract class Action {
    public abstract void perform(WorldMap map);
}
