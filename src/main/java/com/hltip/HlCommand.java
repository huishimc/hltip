package com.hltip;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HlCommand implements CommandExecutor {
    public static final Set<UUID> WAITING_INPUT = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public  static final Map<UUID, String> COMMAND_TYPE = new ConcurrentHashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;

        if (strings.length == 0){
            sender.sendMessage("§b请输入 §7/htip §bhelp §7使用帮助命令");
            return true;
        }

        if (strings.length == 1 && strings[0].equals("help")){

            sender.sendMessage("""
                    §bHL帮助菜单
                    §7/htip §bopen §7- §a打开插件菜单
                    §7/htip §breload §7- §a重新加载插件
                    §7/htip §3set §bjoin §7- §a设置加入公开信息
                    §7/htip §3set §bplayer-join §7- §a设置加入私密信息
                    §7/htip §3set §btitle-join §7- §a设置加入私密标题
                    §7/htip §3set §bsubtitle-join §7- §a设置加入私密副标题""");

            return true;

        }

        if (strings.length == 1 && strings[0].equals("reload")){

            sender.sendMessage("§7正在重新加载§b插件§7!!!");
            Hltip.main.reloadConfig();

            return true;

        }

        if (strings.length == 1 && strings[0].equals("open")){

            Inventory a1 = Bukkit.createInventory(null, 9, "§bHL§7菜单");
            Player p1 = (Player) sender;
            p1.openInventory(a1);

            ItemStack setTip = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta meat = setTip.getItemMeta();
            meat.setDisplayName("§7设置§b公开加入消息 ");
            setTip.setItemMeta(meat);
            a1.setItem(4, setTip);

            ItemStack setTipT = new ItemStack(Material.BOOK, 1);
            ItemMeta meat2 = setTipT.getItemMeta();
            meat2.setDisplayName("§7设置§b私密加入标题 ");
            setTipT.setItemMeta(meat2);
            a1.setItem(3, setTipT);

            ItemStack setTipS = new ItemStack(Material.ANVIL, 1);
            ItemMeta meat3 = setTipS.getItemMeta();
            meat3.setDisplayName("§7设置§b私密加入消息 ");
            setTipS.setItemMeta(meat3);
            a1.setItem(5, setTipS);


            ItemStack asa = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta meat1 = asa.getItemMeta();
            meat1.setDisplayName("§7未开放");
            asa.setItemMeta(meat1);
            a1.setItem(0, asa);
            a1.setItem(1, asa);
            a1.setItem(2, asa);
            a1.setItem(6, asa);
            a1.setItem(7, asa);
            a1.setItem(8, asa);

            return true;

        }

        if (strings.length == 2 && strings[0].equals("set")) {
            switch (strings[1].toLowerCase()) {
                case "join":
                  WAITING_INPUT.add(player.getUniqueId()); // 标记玩家为等待状态
                  COMMAND_TYPE.put(player.getUniqueId(), "join");
                  player.sendMessage("§7请输入§b公开加入提示"); // 发送提示
                  return true;

                case "player-join" :
                    WAITING_INPUT.add(player.getUniqueId()); // 标记玩家为等待状态
                    COMMAND_TYPE.put(player.getUniqueId(), "player-join");
                    player.sendMessage("§7请输入§b私密加入提示"); // 发送提示
                    return true;
                case "title-join" :
                    WAITING_INPUT.add(player.getUniqueId()); // 标记玩家为等待状态
                    COMMAND_TYPE.put(player.getUniqueId(), "title-join");
                    player.sendMessage("§7请输入§b私密标题加入提示"); // 发送提示
                    return true;
                case "subtitle-join" :
                    WAITING_INPUT.add(player.getUniqueId()); // 标记玩家为等待状态
                    COMMAND_TYPE.put(player.getUniqueId(), "subtitle-join");
                    player.sendMessage("§7请输入§b私密副标题加入提示"); // 发送提示
                    return true;
            }
        }

        else {
            player.sendMessage("§7未知§b命令,§7请检查后§b重试");
        }


        return false;
    }
}
