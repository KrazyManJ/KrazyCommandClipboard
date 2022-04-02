package me.KrazyManJ.KrazyCommandClipboard;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
    public static String colorize(String string){
        Pattern hex = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher match = hex.matcher(string);
        while (match.find()){
            string = string.replace(string.substring(match.start(), match.end()),
                    ChatColor.of(string.substring(match.start(), match.end()).replace("&", ""))+"");
            match = hex.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    public static BaseComponent toBaseComponent(String text){
        Pattern hexpattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        if (!hexpattern.matcher(text).find()) return new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
        List<String> hexColorList = new ArrayList<>();
        List<String> sentenceSeqList = new ArrayList<>();
        boolean foundFirst = false;
        String lastText = "";
        Matcher match = hexpattern.matcher(text);
        while (match.find()) {
            if (!foundFirst && match.start() != 0){
                hexColorList.add("");
            }
            String color = text.substring(match.start(), match.end());
            String[] split = text.split(color, 2);
            hexColorList.add(color.replace("&", ""));
            if (split[0].length() != 0) sentenceSeqList.add(split[0]);
            else if (foundFirst) sentenceSeqList.add("");
            text = split[1];
            lastText = split[1];
            match = hexpattern.matcher(text);
            foundFirst = true;
        }
        sentenceSeqList.add(lastText);

        //změna listů do array
        String[] hexColor = hexColorList.toArray(new String[0]);
        String[] sentenceSeq = sentenceSeqList.toArray(new String[0]);
        BaseComponent result = new TextComponent("");
        for (int loop = 0; loop < hexColor.length; loop++){
            BaseComponent current = new TextComponent(ChatColor.translateAlternateColorCodes('&' ,sentenceSeq[loop]));
            if (!hexColor[loop].equals("")) current.setColor(ChatColor.of(hexColor[loop]));
            result.addExtra(current);
        }
        return result;
    }
    public static BaseComponent button(String button, String hoverText, ClickEvent clickEvent){
        BaseComponent returnment;
        returnment = toBaseComponent(button);
        returnment.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(toBaseComponent(hoverText)).create()));
        returnment.setClickEvent(clickEvent);
        return returnment;
    }
}
