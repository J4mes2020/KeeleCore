package dev.joey.keelecore.util.formatting;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.joey.keelecore.util.UtilClass.keeleCore;

/**
 * A super simple chat formatting plugin using Vault.
 */
public class ChatFormatting implements Listener {
    private static final String NAME_PLACEHOLDER = "{name}";
    private static final String DISPLAYNAME_PLACEHOLDER = "{displayname}";
    private static final String MESSAGE_PLACEHOLDER = "{message}";
    private static final String PREFIX_PLACEHOLDER = "{prefix}";
    private static final String SUFFIX_PLACEHOLDER = "{suffix}";
    private static final Pattern NAME_PLACEHOLDER_PATTERN = Pattern.compile(NAME_PLACEHOLDER, Pattern.LITERAL);
    private static final Pattern PREFIX_PLACEHOLDER_PATTERN = Pattern.compile(PREFIX_PLACEHOLDER, Pattern.LITERAL);
    private static final Pattern SUFFIX_PLACEHOLDER_PATTERN = Pattern.compile(SUFFIX_PLACEHOLDER, Pattern.LITERAL);
    private static final String DEFAULT_FORMAT = "<" + PREFIX_PLACEHOLDER + NAME_PLACEHOLDER + SUFFIX_PLACEHOLDER + "> " + MESSAGE_PLACEHOLDER;

    private static final Pattern NICER_HEX_COLOR_PATTERN = Pattern.compile("&#([0-9a-fA-F]{6})");

    private String format;

    private Chat vaultChat = null;

    public ChatFormatting() {

        keeleCore.saveDefaultConfig();
        reloadConfigValues();
        refreshVault();
        keeleCore.getServer().getPluginManager().registerEvents(this, keeleCore);

    }

    private void reloadConfigValues() {
        this.format = colorize(keeleCore.getConfig().getString("format", DEFAULT_FORMAT)
                .replace(DISPLAYNAME_PLACEHOLDER, "%1$s")
                .replace(MESSAGE_PLACEHOLDER, "%2$s"));
    }

    private void refreshVault() {
        Chat vaultChat = keeleCore.getServer().getServicesManager().load(Chat.class);
        if (vaultChat != this.vaultChat) {
            keeleCore.getLogger().info("New Vault Chat implementation registered: " + (vaultChat == null ? "null" : vaultChat.getName()));
        }
        this.vaultChat = vaultChat;
    }

    @EventHandler
    public void onServiceChange(ServiceRegisterEvent e) {
        if (e.getProvider().getService() == Chat.class) {
            refreshVault();
        }
    }

    @EventHandler
    public void onServiceChange(ServiceUnregisterEvent e) {
        if (e.getProvider().getService() == Chat.class) {
            refreshVault();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatLow(AsyncPlayerChatEvent e) {
        // Set out format on the lowest priority - allow other plugins to override or add their own parts.
        e.setFormat(this.format);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatHigh(AsyncPlayerChatEvent e) {
        // Replace our placeholders on highest - just before
        String format = e.getFormat();

        if (this.vaultChat != null) {
            format = replaceAll(PREFIX_PLACEHOLDER_PATTERN, format, () -> colorize(this.vaultChat.getPlayerPrefix(e.getPlayer())));
            format = replaceAll(SUFFIX_PLACEHOLDER_PATTERN, format, () -> colorize(this.vaultChat.getPlayerSuffix(e.getPlayer())));
        }
        format = replaceAll(NAME_PLACEHOLDER_PATTERN, format, () -> e.getPlayer().getName());

        e.setFormat(format);
    }

    private static String replaceAll(Pattern pattern, String input, Supplier<String> replacement) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.replaceAll(Matcher.quoteReplacement(replacement.get()));
        }
        return input;
    }

    public static String colorize(String string) {
        if (string == null) {
            return "null";
        }

        // Convert from the '&#rrggbb' hex color format to the '&x&r&r&g&g&b&b' one used by Bukkit.
        Matcher matcher = NICER_HEX_COLOR_PATTERN.matcher(string);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            StringBuilder replacement = new StringBuilder(14).append("&x");
            for (char character : matcher.group(1).toCharArray()) {
                replacement.append('&').append(character);
            }
            matcher.appendReplacement(sb, replacement.toString());
        }
        matcher.appendTail(sb);

        // Translate from '&' to '§' (section symbol)
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }

}