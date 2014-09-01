package com.publicuhc.exampleplugin;

import com.google.common.base.Optional;
import com.publicuhc.pluginframework.configuration.Configurator;
import com.publicuhc.pluginframework.routing.annotation.*;
import com.publicuhc.pluginframework.routing.converters.OnlinePlayerValueConverter;
import com.publicuhc.pluginframework.shaded.inject.Inject;
import com.publicuhc.pluginframework.shaded.joptsimple.OptionDeclarer;
import com.publicuhc.pluginframework.shaded.joptsimple.OptionSet;
import com.publicuhc.pluginframework.translate.Translate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class ExampleCommands {

    private final Translate m_translate;
    private final Configurator m_configurator;

    @Inject
    protected ExampleCommands(Translate translate, Configurator configurator)
    {
        m_translate = translate;
        m_configurator = configurator;
    }

    @CommandMethod("pm")
    @PermissionRestriction("TEST.PERMISSION")
    @SenderRestriction(ConsoleCommandSender.class)
    @CommandOptions({"[arguments]", "m"})
    public void echoCommand(OptionSet set, ConsoleCommandSender sender, List<Player[]> players, String message)
    {
        Set<Player> p = OnlinePlayerValueConverter.recombinePlayerLists(players);

        for(Player player : p) {
            player.sendMessage(message);
        }

        sender.sendMessage(message);
    }

    @OptionsMethod
    public void echoCommand(OptionDeclarer declarer)
    {
        declarer.accepts("m", "Message to send").withRequiredArg().required();
        declarer.nonOptions().withValuesConvertedBy(new OnlinePlayerValueConverter(true));
    }

    @CommandMethod("translate")
    @CommandOptions
    public void translate(OptionSet set, CommandSender sender)
    {
        String key = (String) set.valueOf("k");
        sender.sendMessage(m_translate.translate(key, "en"));
    }

    @OptionsMethod
    public void translate(OptionDeclarer parser)
    {
        parser.accepts("k")
                .withRequiredArg()
                .ofType(String.class)
                .required()
                .describedAs("Key to check for");
    }

    @CommandMethod("config")
    public void exampleConfig(OptionSet set, CommandSender sender)
    {
        Optional<FileConfiguration> configuration = m_configurator.getConfig("exampleconfig");
        if(!configuration.isPresent()) {
            sender.sendMessage(ChatColor.RED + "Config file failed to load");
            return;
        }

        sender.sendMessage(configuration.get().getString("exampleString"));
    }
}
