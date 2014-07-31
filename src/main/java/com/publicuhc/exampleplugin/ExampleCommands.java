package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.configuration.Configurator;
import com.publicuhc.pluginframework.routing.CommandMethod;
import com.publicuhc.pluginframework.shaded.inject.Inject;
import com.publicuhc.pluginframework.translate.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;

public class ExampleCommands {

    private final Translate m_translate;
    private final Configurator m_configurator;

    @Inject
    protected ExampleCommands(Translate translate, Configurator configurator)
    {
        m_translate = translate;
        m_configurator = configurator;
    }

    @CommandMethod(command = "echo")
    public void echoCommand(Command command, CommandSender sender, OptionSet set)
    {
        StringBuilder builder = new StringBuilder();
        for(String s : set.nonOptionArguments()) {
            builder.append(s).append(" ");
        }
        sender.sendMessage(builder.substring(0, builder.length() - 1));
    }

    @CommandMethod(command = "translate", options = true)
    public void translate(Command command, CommandSender sender, OptionSet set)
    {
        String key = (String) set.valueOf("k");
        sender.sendMessage(m_translate.translate(key, "en"));
    }

    public String[] translate(OptionParser parser)
    {
        parser.accepts("k").withRequiredArg().ofType(String.class).describedAs("Key to check for");
        return new String[]{"k"};
    }

    @CommandMethod(command = "config")
    public void exampleConfig(Command command, CommandSender sender, OptionSet set)
    {
        sender.sendMessage(m_configurator.getConfig("exampleconfig").getString("exampleString"));
    }
}
