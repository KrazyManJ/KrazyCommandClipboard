package me.KrazyManJ.KrazyCommandClipboard.Commands;

import me.KrazyManJ.KrazyCommandClipboard.ClipboardsManager;
import me.KrazyManJ.KrazyCommandClipboard.LanguageManager;
import me.KrazyManJ.KrazyCommandClipboard.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class KrazyCommandClipboardCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label,@Nonnull String[] args) {
        Player player = null;
        if (sender instanceof Player) player = (Player) sender;
        if (command.getName().equals("krazycommandclipboard")){
            if (args.length == 0){
                if (player == null) return isNotConsoleAndSend(sender);
                ClipboardsManager.sendClipboardToPlayer(player, player);
                return true;
            }
            else if (args.length == 1){
                if (args[0].equalsIgnoreCase("clear")){
                    if (player == null) return isNotConsoleAndSend(sender);
                    if (!player.hasPermission("krazycommandclipboard.clipboard.clear")) return hasNotPermissionAndSend(player);
                    if (ClipboardsManager.getExistingClipboards().contains(player.getName())){
                        ClipboardsManager.clearClipboard(((Player)sender).getUniqueId().toString());
                        player.sendMessage(LanguageManager.selfClearedClipboard);
                        return true;
                    }
                    else {
                        player.sendMessage(LanguageManager.noClipboard);
                        return false;
                    }

                }
                else if (args[0].equalsIgnoreCase("reload")){
                    if (!sender.hasPermission("krazycommandclipboard.reload")) return hasNotPermissionAndSend(sender);
                    Main.getInstance().reloadConfig();
                    if (Main.checkConfig()) {
                        if (player != null) player.sendMessage(LanguageManager.reloadWarn);
                    }
                    else {
                        if (player != null) player.sendMessage(LanguageManager.reloadSuccess);
                        Main.getInstance().getLogger().info("Config was successfully reloaded!");
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("show")){
                    if (player == null) return isNotConsoleAndSend(sender);
                    ClipboardsManager.sendClipboardToPlayer(player, player);
                    player.sendMessage(LanguageManager.commandHint);
                    return true;
                }
            }
            else if (args.length == 2){
                if (args[0].equalsIgnoreCase("clear")){
                    if (!sender.hasPermission("krazycommandclipboard.clear.clipboard.others")) return hasNotPermissionAndSend(sender);
                    if (ClipboardsManager.getExistingClipboards().contains(args[1])){
                        ClipboardsManager.clearClipboard(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());
                        sender.sendMessage(LanguageManager.clearedOtherClipboard.replace("{player}", args[1]));
                    }
                    else {
                        sender.sendMessage(LanguageManager.noOtherClipboard);
                        return false;
                    }
                }
                else if (args[0].equalsIgnoreCase("show")){
                    if (player == null) return isNotConsoleAndSend(sender);
                    if (!player.hasPermission("krazycommandclipboard.show.others")) return hasNotPermissionAndSend(player);
                    if (ClipboardsManager.getExistingClipboards().contains(args[1])){
                        ClipboardsManager.sendClipboardToPlayer(player, Bukkit.getOfflinePlayer(args[1]));
                        if (player.getName().equals(args[1])) player.sendMessage(LanguageManager.commandHint);
                    }
                    else player.sendMessage(LanguageManager.noOtherClipboard);
                }
            }
        }
        return false;
    }

    public static boolean isNotConsoleAndSend(CommandSender sender){
        sender.sendMessage(LanguageManager.notConsole);
        return false;
    }
    public static boolean hasNotPermissionAndSend(Object player){
        if (player instanceof Player) ((Player)player).sendMessage(LanguageManager.noPerm);
        else if (player instanceof CommandSender) ((CommandSender)player).sendMessage(LanguageManager.noPerm);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender,@Nonnull Command command,@Nonnull String alias,@Nonnull String[] args) {
        List<String> arguments = new ArrayList<>();
        List<String> empty = new ArrayList<>();
        arguments.add("show");
        if (sender.hasPermission("krazycommandclipboard.clear.clipboard")) arguments.add("clear");
        if (sender.hasPermission("krazycommandclipboard.reload")) arguments.add("reload");
        if (command.getName().equals("krazycommandclipboard")){
            if (args.length == 0) return arguments;
            else if (args.length == 1) return completeOnInput(arguments, args[0]);
            else if (args.length == 2){
                if (args[0].equals("clear") && sender.hasPermission("krazycommandclipboard.clear.clipboard.others")){
                    if (args[1].length() == 0) return ClipboardsManager.getExistingClipboards();
                    else return completeOnInput(ClipboardsManager.getExistingClipboards(), args[1]);
                }
                else if (args[0].equals("show") && sender.hasPermission("krazycommandclipboard.show.others")){
                    if (args[1].length() == 0) return ClipboardsManager.getExistingClipboards();
                    else return completeOnInput(ClipboardsManager.getExistingClipboards(), args[1]);
                }
            }
            return empty;
        }
        return null;
    }

    public static List<String> completeOnInput(List<String> possibleArgs, String input){
        List<String> result = new ArrayList<>();
        for (String arg : possibleArgs) if (arg.toLowerCase().startsWith(input.toLowerCase())) result.add(arg);
        return result;
    }
}
