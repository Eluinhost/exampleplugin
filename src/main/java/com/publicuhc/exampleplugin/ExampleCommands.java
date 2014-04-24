package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.commands.annotation.CommandMethod;
import com.publicuhc.pluginframework.commands.annotation.RouteInfo;
import com.publicuhc.pluginframework.commands.requests.CommandRequest;
import com.publicuhc.pluginframework.commands.requests.SenderType;
import com.publicuhc.pluginframework.commands.routing.*;
import com.publicuhc.pluginframework.configuration.Configurator;
import com.publicuhc.pluginframework.shaded.inject.Inject;
import com.publicuhc.pluginframework.translate.Translate;
import org.bukkit.ChatColor;

public class ExampleCommands {

    private final Translate m_translate;
    private final Configurator m_configurator;

    @Inject
    protected ExampleCommands(Translate translate, Configurator configurator) {
        m_translate = translate;
        m_configurator = configurator;
    }

    @CommandMethod
    public void echoCommand(CommandRequest request) {
        StringBuilder builder = new StringBuilder();
        for(String s : request.getArgs()) {
            builder.append(s).append(" ");
        }
        builder.substring(0, builder.length());
        request.sendMessage(builder.toString());
    }

    @RouteInfo
    public void echoCommandDetails(RouteBuilder builder) {
        builder.restrictCommand("echo");
    }

    @CommandMethod
    public void translate(CommandRequest request) {
        String firstArg = request.getFirstArg();
        if(null == firstArg) {
            request.sendMessage(ChatColor.RED + "Must supply a key");
            return;
        }
        request.sendMessage(m_translate.translate(firstArg, request.getLocale()));
    }

    @RouteInfo
    public void translateDetails(RouteBuilder builder) {
        builder.restrictCommand("translate");
        builder.restrictSenderType(SenderType.CONSOLE);
    }

    @CommandMethod
    public void exampleConfig(CommandRequest request) {
        request.sendMessage(m_configurator.getConfig("exampleconfig").getString("exampleString"));
    }

    @RouteInfo
    public void exampleConfigDetails(RouteBuilder builder) {
        builder.restrictCommand("config");
    }
}
