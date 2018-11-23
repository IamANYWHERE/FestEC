package com.toplyh.latte.ec.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {
    icon_scan('\ue66c'),
    icon_ali_pay('\ue632'),
    icon_empty('\ue653');

    private char character;

    EcIcons(char character){
        this.character=character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
