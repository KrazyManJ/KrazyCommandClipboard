package me.KrazyManJ.KrazyCommandClipboard;

public class LanguageManager {
    public static String prefix = Format.colorize("&8[&#fcba03&lKʀᴀᴢʏCCB&8]&r");

    public static String noPerm = prefix+" "+Format.colorize("&cYou don't have permission to do this!");
    public static String notConsole = prefix+" "+Format.colorize("&cYou cannot use this command, because you need to be player mr. console!");

    public static String selfClearedClipboard = prefix+" "+Format.colorize("&aYour clipboard was successfully cleared!");
    public static String clearedOtherClipboard = prefix+" "+Format.colorize("&aClipboard of {player} was successfully cleared!");
    public static String noClipboard = prefix+" "+Format.colorize("&cYou don't have any clipboard!");
    public static String noOtherClipboard = prefix+" "+Format.colorize("&cThis player does not have any clipboard!");

    public static String selfClearedCommand = prefix+" "+Format.colorize("&aCommand was successfully removed from your clipboard!");
    public static String clearedOtherCommand = prefix+" "+Format.colorize("&aCommand was successfully removed from player's clipboard!");
    public static String noCommandInSelfClipboard = prefix+" "+Format.colorize("&cThere isn't this command in your clipboard!");
    public static String noCommandInOtherClipboard = prefix+" "+Format.colorize("&cThere isn't this command in player's clipboard!");

    public static String reloadWarn = prefix+" "+Format.colorize("&#ff0000⚠ &cThere was a problem with loading config file! Check console for more details!");
    public static String reloadSuccess = prefix+" "+Format.colorize("&aConfig was successfully reloaded!");


    public static String commandHint = prefix+" "+Format.colorize("&eIf you need to display your clipboard, you can use only &l&o/kccb&e for that!");


}
