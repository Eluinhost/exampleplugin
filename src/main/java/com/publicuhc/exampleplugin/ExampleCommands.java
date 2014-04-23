package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.commands.annotation.CommandMethod;
import com.publicuhc.pluginframework.commands.annotation.RouteInfo;
import com.publicuhc.pluginframework.commands.requests.CommandRequest;
import com.publicuhc.pluginframework.commands.requests.SenderType;
import com.publicuhc.pluginframework.commands.routing.BaseRoute;
import com.publicuhc.pluginframework.commands.routing.CommandRestrictedRoute;
import com.publicuhc.pluginframework.commands.routing.Route;
import com.publicuhc.pluginframework.commands.routing.SenderTypeRestrictedRoute;
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
    public Route echoCommandDetails() {
        return new CommandRestrictedRoute(new BaseRoute(), "echo");
    }

    @CommandMethod
    public void translate(CommandRequest request) {
        String firstArg = request.getFirstArg();
        if(null == firstArg) {
            request.sendMessage(ChatColor.RED + "Must supply a key");
            return;
        }
        request.sendMessage(m_translate.translate(firstArg, m_translate.getLocaleForSender(request.getSender())));
    }

    @RouteInfo
    public Route translateDetails() {
        CommandRestrictedRoute route = new CommandRestrictedRoute(new BaseRoute(), "translate");
        return new SenderTypeRestrictedRoute(route, SenderType.CONSOLE);
    }

    @CommandMethod
    public void exampleConfig(CommandRequest request) {
        request.sendMessage(m_configurator.getConfig("exampleconfig").getString("exampleString"));
    }

    @RouteInfo
    public Route exampleConfigDetails() {
        return new CommandRestrictedRoute(new BaseRoute(), "config");
    }
}
