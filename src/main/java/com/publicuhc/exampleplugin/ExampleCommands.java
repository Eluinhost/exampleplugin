package com.publicuhc.exampleplugin;

import com.publicuhc.pluginframework.commands.annotation.CommandMethod;
import com.publicuhc.pluginframework.commands.annotation.RouteInfo;
import com.publicuhc.pluginframework.commands.requests.CommandRequest;
import com.publicuhc.pluginframework.commands.requests.SenderType;
import com.publicuhc.pluginframework.commands.routing.BaseMethodRoute;
import com.publicuhc.pluginframework.commands.routing.MethodRoute;
import com.publicuhc.pluginframework.shaded.inject.Inject;
import com.publicuhc.pluginframework.translate.Translate;
import org.bukkit.ChatColor;

import java.util.regex.Pattern;

public class ExampleCommands {

    private final Translate m_translate;

    @Inject
    protected ExampleCommands(Translate translate) {
        m_translate = translate;
    }

    @CommandMethod
    public void echoCommand(CommandRequest request) {
        request.sendMessage(request.getMatcherResult().group(0));
    }

    @RouteInfo
    public MethodRoute echoCommandDetails() {
        return new BaseMethodRoute(Pattern.compile(".*"), new SenderType[] { SenderType.CONSOLE }, "bukkit.command.tell", "echo");
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
        return new BaseMethodRoute(Pattern.compile(".*"), new SenderType[] { SenderType.CONSOLE }, "bukkit.command.tell", "translate");
    }
}
