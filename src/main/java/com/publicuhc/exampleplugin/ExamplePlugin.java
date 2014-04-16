package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.FrameworkJavaPlugin;
import com.publicuhc.pluginframework.commands.exceptions.CommandClassParseException;

public class ExamplePlugin extends FrameworkJavaPlugin {

    @Override
    public void onEnable() {
        try {
            getRouter().registerCommands(ExampleCommands.class);
        } catch (CommandClassParseException e) {
            e.printStackTrace();
        }
    }
}
