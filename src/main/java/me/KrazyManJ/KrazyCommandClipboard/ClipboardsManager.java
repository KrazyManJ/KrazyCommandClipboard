package me.KrazyManJ.KrazyCommandClipboard;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClipboardsManager {
    private static File clipboardsFile;
    private static FileConfiguration clipboardsData;

    public static void createOrReloadFile() {
        clipboardsFile = new File(Main.getInstance().getDataFolder().getAbsolutePath(), "clipboards.yml");
        if (!clipboardsFile.exists()) try { clipboardsFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        clipboardsData = YamlConfiguration.loadConfiguration(clipboardsFile);
        clearCommandsOverLimit();
    }
    public static void writeCommand(String command, Player player){
        List<String> commands = new ArrayList<>();
        if (getPlayerClipboard(player) != null){
            commands = clipboardsData.getStringList("clipboards."+player.getUniqueId());
            if (!commands.contains(command)){
                if (commands.size() == Main.getInstance().getConfig().getInt("command limit")) commands.remove(0);
                commands.add(command);
            }
            else{
                commands.remove(command); commands.add(command);
            }
        }
        else commands.add(command);
        clipboardsData.set("clipboards."+player.getUniqueId(), commands);
    }
    public static void saveFile(){
        try { clipboardsData.save(clipboardsFile); } catch (IOException e) { e.printStackTrace(); }
    }
    public static List<String> getPlayerClipboard(OfflinePlayer player){
        if (!clipboardsData.isSet("clipboards."+player.getUniqueId())) return null;
        else return clipboardsData.getStringList("clipboards."+player.getUniqueId());
    }
    public static void clearCommandsOverLimit(){
        if (clipboardsData.getConfigurationSection("clipboards") != null){
            List<String> commands;
            for (String id : clipboardsData.getConfigurationSection("clipboards").getKeys(false)){
                commands = clipboardsData.getStringList("clipboards."+id);
                if (commands.size() > Main.getInstance().getConfig().getInt("command limit")){
                    commands.subList(Main.getInstance().getConfig().getInt("command limit"), commands.size()).clear();
                    clipboardsData.set("clipboards."+id, commands);
                }
            }
        }
        saveFile();
    }
    public static void clearClipboard(String uniqueId){ clipboardsData.set("clipboards."+uniqueId, null); }
    public static void clearCommandFromClipboard(OfflinePlayer player, String command){
        if (getPlayerClipboard(player) != null) {
            List<String> commands = getPlayerClipboard(player);
            if (commands.contains(command)){
                commands.remove(command);
                clipboardsData.set("clipboards."+player.getUniqueId(), commands);
            }
        }
    }
    public static boolean isBlacklisted(String command){
        if (Main.getInstance().getServer().getPluginCommand(command.replace("/", "")) != null){
            if (Main.getInstance().getConfig().isSet("blacklisted plugins")){
                List<String> blacklistedPlugins = Main.getInstance().getConfig().getStringList("blacklisted plugins");
                for (String blacklistedPlugin : blacklistedPlugins){
                    if (Main.getInstance().getServer().getPluginCommand(command.replace("/", "")).getPlugin().getName().equals(blacklistedPlugin)) return true;
                }
            }
        }
        else if (Main.getInstance().getConfig().isSet("blacklisted plugins")){
            return Main.getInstance().getConfig().getStringList("blacklisted commands").contains(command);
        }
        return false;
    }
    public static List<String> getExistingClipboards(){
        List<String> result = new ArrayList<>();
        if (clipboardsData.isSet("clipboards")){
            for (String uuid : clipboardsData.getConfigurationSection("clipboards").getKeys(false)) result.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
        }
        return result;
    }




    public static void sendClipboardToPlayer(Player player, OfflinePlayer clipboardPlayer){
        player.sendMessage(Format.colorize("\n&#9e9c98I&#a09d94☰&#a39e90☰&#a59e8c☰&#a79f88☰&#aaa084☰&#aca080☰&#aea17d☰&#b1a279☰&#b3a275☰&#b5a371I&r &#fcba03&l&nKʀᴀᴢʏCᴏᴍᴍᴀɴᴅCʟɪᴘʙᴏᴀʀᴅ&r &#b3a275I&#b1a279☰&#aea17c☰&#aca080☰&#aaa084☰&#a79f88☰&#a59e8c☰&#a39e90☰&#a09d94☰&#9e9c98☰&#9c9c9bI\n&r"));
        if (player != clipboardPlayer) player.sendMessage(Format.colorize(" &#fcba03&l◆&#a39e90 Clipboard of: &#fcba03"+clipboardPlayer.getName()+"\n&r"));
        if (ClipboardsManager.getPlayerClipboard(clipboardPlayer) != null) {
            for (String commandString : ClipboardsManager.getPlayerClipboard(clipboardPlayer)) {
                String cuttedCommand;
                if (commandString.length() > 35) cuttedCommand = commandString.substring(0, 35) + "...";
                else cuttedCommand = commandString;
                BaseComponent wholeCommandLine = new TextComponent("");
                wholeCommandLine.addExtra(new TextComponent(Format.colorize(" &8◆ ")));
                if (Main.getInstance().getConfig().getBoolean("execute button") || Main.getInstance().getConfig().getBoolean("copy button") || Main.getInstance().getConfig().getBoolean("send to chat button")){
                    if (Main.getInstance().getConfig().getBoolean("execute button")){
                        wholeCommandLine.addExtra(Format.button("&#eb4034Ⓔ", " &#eb4034Ⓔ &l&nEXECUTE BUTTON&r &#eb4034Ⓔ \n&r\n&#ff0000Click to execute command: \n &8◆ &4" + commandString + "\n&r", new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandString)));
                        wholeCommandLine.addExtra(new TextComponent(" "));
                    }
                    if (Main.getInstance().getConfig().getBoolean("copy button")){
                        wholeCommandLine.addExtra(Format.button("&#79cf38Ⓒ", " &#79cf38Ⓒ &l&nCOPY BUTTON&r &#79cf38Ⓒ \n&r\n&#00ff00Click to copy command to clipboard: \n &8◆ &a" + commandString + "\n&r", new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, commandString)));
                        wholeCommandLine.addExtra(new TextComponent(" "));
                    }
                    if (Main.getInstance().getConfig().getBoolean("send to chat button")){
                        wholeCommandLine.addExtra(Format.button("&#4a7affⓈ", " &#4a7affⓈ &l&nSEND TO CHAT BUTTON&r &#4a7affⓈ \n&r\n&#0000ffClick to send command to chat: \n &8◆ &9" + commandString + "\n&r", new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "."+commandString)));
                        wholeCommandLine.addExtra(new TextComponent(" "));
                    }
                }
                wholeCommandLine.addExtra(Format.button("&#ff0000✗", " &#ff0000✗ &l&nREMOVE BUTTON&r &#ff0000✗ \n&r\n&#ff0000Click to remove command from your clipboard: \n &8◆ &4" + commandString + "\n&r", new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/CN<j0H\"1c0D..<m@r,ju@W,[qA, "+clipboardPlayer.getName()+" "+commandString)));
                wholeCommandLine.addExtra(new TextComponent(" "));
                wholeCommandLine.addExtra(Format.button("&#ffc54a"+cuttedCommand, "&7Click to suggest command: \n &8◆ &#ffc54a" + commandString + "\n&r", new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandString)));
                player.spigot().sendMessage(wholeCommandLine);
            }
        }
        else{
            player.sendMessage(Format.colorize("      &#e06d67&oYou don't have any commands in clipboard..."));
        }
        player.sendMessage(Format.colorize("\n&#9e9c98I&#a09d94☰&#a39e90☰&#a59e8c☰&#a79f88☰&#aaa084☰&#aca080☰&#aea17d☰&#b1a279☰&#b3a275☰&#b5a371☰&#b8a46d☰&#baa469☰&#bca565☰&#bfa661☰&#c1a65e☰&#c3a75a☰" +
                "&#c5a856☰&#c8a852☰&#caa94e☰&#cdaa4a☰&#cfaa46☰&#d1ab42☰&#d3ab3f☰&#d1ab42☰&#cfaa46☰&#caa94e☰&#c8a852☰&#c6a856☰&#c3a75a☰&#c1a65e☰&#bfa661☰&#bca565☰&#baa469☰&#b8a46d☰&#b5a371☰&#b3a275☰&#b1a279☰" +
                "&#aea17c☰&#aca080☰&#aaa084☰&#a79f88☰&#a59e8c☰&#a39e90☰&#a09d94☰&#9e9c98☰&#9c9c9bI\n\n&r"));
    }
}
