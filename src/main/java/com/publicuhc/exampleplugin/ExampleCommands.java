package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.commands.annotation.CommandMethod;
import com.publicuhc.pluginframework.commands.annotation.RouteInfo;
import com.publicuhc.pluginframework.commands.matchers.AnyRouteMatcher;
import com.publicuhc.pluginframework.commands.requests.CommandRequest;
import com.publicuhc.pluginframework.commands.requests.SenderType;
import com.publicuhc.pluginframework.commands.routing.DefaultMethodRoute;
import com.publicuhc.pluginframework.commands.routing.MethodRoute;
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
        request.sendMessage(request.getMatcherResult().group(0));
    }

    @RouteInfo
    public MethodRoute echoCommandDetails() {
        return new DefaultMethodRoute(new AnyRouteMatcher(), new SenderType[] { SenderType.CONSOLE }, "bukkit.command.tell", "echo");
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
    public MethodRoute translateDetails() {
        return new DefaultMethodRoute(new AnyRouteMatcher(), new SenderType[] { SenderType.CONSOLE }, "bukkit.command.tell", "translate");
    }

    @CommandMethod
    public void exampleConfig(CommandRequest request) {
        request.sendMessage(m_configurator.getConfig("exampleconfig").getString("exampleString"));
    }

    @RouteInfo
    public MethodRoute exampleConfigDetails() {
        return new DefaultMethodRoute(new AnyRouteMatcher(), new SenderType[] { SenderType.CONSOLE }, "bukkit.command.tell", "config");
    }
}
