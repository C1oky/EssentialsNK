package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class KickAllCommand extends CommandBase {

    public KickAllCommand(EssentialsAPI api) {
        super("kickall", api);
        commandParameters.clear();
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        int count = Server.getInstance().getOnlinePlayers().size();
        if (count == 0 || (sender instanceof Player && count == 1)) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.kickall.noplayer"));
            return false;
        }

        String reason = String.join(" ", args);
        Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
            if (!player.equals(sender)) {
                player.kick(reason);
            }
        });

        sender.sendMessage(Language.translate("commands.kickall.success"));
        return true;
    }
}