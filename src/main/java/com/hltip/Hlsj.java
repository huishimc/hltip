package com.hltip;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.http.WebSocket;

public class Hlsj implements WebSocket.Listener, Listener {
    private static final String CONFIG_PATH = "join_message";
    private static final String PLAYER_PATH = "private_message";
    private static final String TITLE_PATH = "title_message";
    private static final String SUBTITLE_PATH = "subtitle_message";


    @EventHandler
    public void playerJoin(PlayerJoinEvent join ) {

        Player a = join.getPlayer();
        String name = a.getName();

        String rawMessage = Hltip.main.getConfig().getString("join_message");
        if (rawMessage == null || rawMessage.isEmpty()) {
            rawMessage = "&a欢迎 %player_join% 加入服务器!";
        }
        String formattedMessage = rawMessage.replace("&", "§");
        formattedMessage = formattedMessage.replace("%player_join%", name);
        formattedMessage = PlaceholderAPI.setPlaceholders(a, formattedMessage);
        join.setJoinMessage(formattedMessage);

        String Message = Hltip.main.getConfig().getString("private_message");
        if (Message == null || Message.isEmpty()) {
            Message = "";
        }
        String aM = Message.replace("&", "§");
        String fm = aM.replace("%player_join%", name);
        a.sendMessage(fm);

        String TMessage = Hltip.main.getConfig().getString("title_message");
        if (TMessage == null || TMessage.isEmpty()) {
            TMessage = "";
        }
        String TM = TMessage.replace("&", "§");
        String tm = TM.replace("%player_join%", name);
        tm = PlaceholderAPI.setPlaceholders(a, tm);
        String SMessage = Hltip.main.getConfig().getString("subtitle_message");
        if (SMessage == null || SMessage.isEmpty()) {
            SMessage = "";
        }
        String SM = SMessage.replace("&", "§");
        String sm = SM.replace("%player_join%", name);
        sm = PlaceholderAPI.setPlaceholders(a, sm);


        a.sendTitle(tm,
                sm,
                20, 100, 20);

    }

    @EventHandler
    public void click(InventoryClickEvent event) {

        if(event.getView().getTitle().equals("§bHL§7菜单") ||
                event.getView().getTitle().equals("§b标题§3更改")){

            event.setCancelled(true);

        }


        if(event.getView().getTitle().equals("§bHL§7菜单") && event.getRawSlot() == 4){

            Player p = (Player) event.getWhoClicked();
            p.performCommand("htip set join");
            p.closeInventory();


        }

        if(event.getView().getTitle().equals("§bHL§7菜单") && event.getRawSlot() == 3){

            Player p = (Player) event.getWhoClicked();

            Inventory a = Bukkit.createInventory(null, 9, "§b标题§3更改");

            p.openInventory(a);

            ItemStack asa = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta meat1 = asa.getItemMeta();
            meat1.setDisplayName("§7未开放");
            asa.setItemMeta(meat1);
            a.setItem(2, asa);
            a.setItem(3, asa);
            a.setItem(4, asa);
            a.setItem(5, asa);
            a.setItem(6, asa);
            a.setItem(7, asa);
            a.setItem(8, asa);

            ItemStack item = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta meat2 = item.getItemMeta();
            meat2.setDisplayName("§7设置§b标题");
            item.setItemMeta(meat2);
            a.setItem(0, item);

            ItemStack item1 = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta meat3 = item1.getItemMeta();
            meat3.setDisplayName("§7设置§b副标题");
            item1.setItemMeta(meat3);
            a.setItem(1, item1);

        }
        if(event.getView().getTitle().equals("§b标题§3更改") && event.getRawSlot() == 0){

            Player p = (Player) event.getWhoClicked();
            p.performCommand("htip set title-join");
            p.closeInventory();

        }

        if(event.getView().getTitle().equals("§b标题§3更改") && event.getRawSlot() == 1){

            Player p = (Player) event.getWhoClicked();
            p.performCommand("htip set subtitle-join");
            p.closeInventory();

        }


    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if (HlCommand.WAITING_INPUT.contains(player.getUniqueId())) {
            event.setCancelled(true); // 阻止公开显示
            String input = event.getMessage();
            String textWithNewlines = input.replace("\\n", "\n");
            String commandType = HlCommand.COMMAND_TYPE.getOrDefault(player.getUniqueId(), "default");
            switch (commandType) {

                case "join":
                    handlePlayerInput(player, textWithNewlines);
                    break;
                case "player-join":
                    handlePlayerJoinInput(player, textWithNewlines);
                    break;
                case "title-join":
                    handleTitleJoinInput(player, textWithNewlines);
                    break;
                case "subtitle-join":
                    handleSubtitleJoinInput(player, textWithNewlines);
                    break;


            }


            HlCommand.WAITING_INPUT.remove(player.getUniqueId());
            HlCommand.COMMAND_TYPE.remove(player.getUniqueId());
        }
    }
    private void handlePlayerInput(Player player, String text) {

        player.sendMessage("§7已保存你的公开加入提示: §b" + text.replace("\n", "§7\n§b"));
        // 这里执行你的插件逻辑
        FileConfiguration config = Hltip.main.getConfig();
        config.set(CONFIG_PATH, text);
        Hltip.main.saveConfig(); // 自动保存到磁盘
    }

    private void handlePlayerJoinInput(Player player, String text) {

        player.sendMessage("§7已保存你的私密加入提示: §b" + text.replace("\n", "§7\n§b"));
        // 这里执行你的插件逻辑
        FileConfiguration config = Hltip.main.getConfig();
        config.set(PLAYER_PATH, text);
        Hltip.main.saveConfig(); // 自动保存到磁盘
    }
    private void handleTitleJoinInput(Player player, String text) {

        player.sendMessage("§7已保存你的私密标题加入提示: §b" + text.replace("\n", "§7\n§b"));
        // 这里执行你的插件逻辑
        FileConfiguration config = Hltip.main.getConfig();
        config.set(TITLE_PATH, text);
        Hltip.main.saveConfig(); // 自动保存到磁盘
    }
    private void handleSubtitleJoinInput(Player player, String text) {

        player.sendMessage("§7已保存你的私密副标题加入提示: §b" + text.replace("\n", "§7\n§b"));
        // 这里执行你的插件逻辑
        FileConfiguration config = Hltip.main.getConfig();
        config.set(SUBTITLE_PATH, text);
        Hltip.main.saveConfig(); // 自动保存到磁盘
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        HlCommand.WAITING_INPUT.remove(event.getPlayer().getUniqueId());
    }
}
