package me.KrazyManJ.KrazyCommandClipboard.Listeners;

import me.KrazyManJ.KrazyCommandClipboard.ClipboardsManager;
import me.KrazyManJ.KrazyCommandClipboard.LanguageManager;
import me.KrazyManJ.KrazyCommandClipboard.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayerCommandPreprocess implements Listener {
    @EventHandler
    public void on(PlayerCommandPreprocessEvent event){
        if (event.getMessage().startsWith("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ")){
            event.setCancelled(true);
            String[] args = event.getMessage().replace("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ", "").split(" ", 2);
            boolean doIt = false;
            if (event.getPlayer() == Bukkit.getOfflinePlayer(args[0]) && event.getPlayer().hasPermission("krazycommandclipboard.clear.command")) doIt = true;
            else if (event.getPlayer().hasPermission("krazycommandclipboard.clear.command.others")) doIt = true;
            if (doIt){
                if (ClipboardsManager.getPlayerClipboard(Bukkit.getOfflinePlayer(args[0])) != null){
                    if (ClipboardsManager.getPlayerClipboard(Bukkit.getOfflinePlayer(args[0])).contains(args[1])){
                        ClipboardsManager.clearCommandFromClipboard(event.getPlayer(), args[1]);
                        ClipboardsManager.sendClipboardToPlayer(event.getPlayer(), Bukkit.getOfflinePlayer(args[0]));
                        if (event.getPlayer() == Bukkit.getOfflinePlayer(args[0])) event.getPlayer().sendMessage(LanguageManager.selfClearedCommand);
                        else event.getPlayer().sendMessage(LanguageManager.clearedOtherCommand);
                    } else {
                        if (event.getPlayer() == Bukkit.getOfflinePlayer(args[0])) event.getPlayer().sendMessage(LanguageManager.noCommandInSelfClipboard);
                        else event.getPlayer().sendMessage(LanguageManager.noCommandInOtherClipboard);
                    }
                } else {
                    if (event.getPlayer() == Bukkit.getOfflinePlayer(args[0])) event.getPlayer().sendMessage(LanguageManager.noClipboard);
                    else event.getPlayer().sendMessage(LanguageManager.noOtherClipboard);
                }
            }
            else event.getPlayer().sendMessage(LanguageManager.noPerm);

        }
        else if (event.getPlayer().hasPermission("krazycommandclipboard.save")){
            List<String> firstArgs = new ArrayList<>();
            String command = event.getMessage().split(" ")[0].toLowerCase();
            for(HelpTopic t : Main.getInstance().getServer().getHelpMap().getHelpTopics()) firstArgs.add(t.getName().split(" ")[0].toLowerCase());
            if(firstArgs.contains(command)){
                if (!ClipboardsManager.isBlacklisted(command)) ClipboardsManager.writeCommand(event.getMessage(), event.getPlayer());
            }
        }
    }
}
