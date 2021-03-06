package cn.yescallop.essentialsnk.command.defaults.teleport;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class TPAAllCommand extends CommandBase {

    public TPAAllCommand(EssentialsAPI api) {
        super("tpaall", api);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        if (essentialsAPI.hasCooldown(sender)) {
            return true;
        }

        Player player;
        if (args.length == 0) {
            if (!this.testInGame(sender)) {
                return false;
            }

            player = (Player) sender;
        } else if (args.length == 1) {
            player = Server.getInstance().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.generic.player.notfound", args[0]));
                return false;
            }
        } else {
            this.sendUsage(sender);
            return false;
        }

        Server.getInstance().getOnlinePlayers().forEach((uuid, target) -> {
            if (!target.equals(player)) {
                essentialsAPI.requestTP(player, target, false);
                target.sendMessage(Language.translate("commands.tpahere.invite", player.getDisplayName()));
            }
        });

        player.sendMessage(Language.translate("commands.tpaall.success"));
        return true;
    }
}