package me.KrazyManJ.KrazyCommandClipboard;

import me.KrazyManJ.KrazyCommandClipboard.Commands.KrazyCommandClipboardCommand;
import me.KrazyManJ.KrazyCommandClipboard.Listeners.PlayerCommandPreprocess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Logger coreLogger = (Logger) LogManager.getRootLogger();
        coreLogger.addFilter(new LogFilter());
        this.saveDefaultConfig();
        ClipboardsManager.createOrReloadFile();
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        checkConfig();
        Objects.requireNonNull(getCommand("krazycommandclipboard")).setExecutor(new KrazyCommandClipboardCommand());
        Objects.requireNonNull(getCommand("krazycommandclipboard")).setTabCompleter(new KrazyCommandClipboardCommand());
    }
    @Override
    public void onDisable() { ClipboardsManager.saveFile(); }

    public static Main getInstance(){ return instance; }

    public static boolean checkConfig(){
        boolean error = false;
        if (instance.getConfig().getInt("command limit") < 3 || instance.getConfig().getInt("command limit") > 20){
            instance.getConfig().set("command limit", 10);
            instance.getLogger().log(Level.SEVERE, "\"Command limit\" option has to be in range from 5 to 20! Setting value to default = 10");
            error = true;
        }
        if (instance.getConfig().isSet("blacklisted plugins")){
            List<String> plugins = instance.getConfig().getStringList("blacklisted plugins");
            for (String plugin : instance.getConfig().getStringList("blacklisted plugins")){
                if (Bukkit.getPluginManager().getPlugin(plugin) == null) {
                    instance.getLogger().log(Level.SEVERE, "plugin under option \"blacklisted plugins\" named \""+plugin+"\" is not valid plugin or is not enabled! Is won't be used in the future!");
                    plugins.remove(plugin);
                    error = true;
                }
            }
            if (!instance.getConfig().getStringList("blacklisted plugins").equals(plugins)) instance.getConfig().set("blacklisted plugins", plugins);
        }
        return !(error);
    }
}
