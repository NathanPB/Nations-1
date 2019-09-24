package com.arckenver.nations.cmdexecutor.nation;

import com.arckenver.nations.DataHandler;
import com.arckenver.nations.LanguageHandler;
import com.arckenver.nations.Utils;
import com.arckenver.nations.object.Nation;
import com.arckenver.nations.object.NationSpawn;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;

public class NationSpawnFlagExecutor implements CommandExecutor {

    public static void create(CommandSpec.Builder cmd) {
        cmd.child(CommandSpec.builder()
            .description(Text.of(""))
            .permission("nations.command.nation.togglespawnflag")
            .arguments(
                GenericArguments.optional(GenericArguments.string(Text.of("spawn"))),
                GenericArguments.optional(GenericArguments.string(Text.of("flag"))),
                GenericArguments.optional(GenericArguments.string(Text.of("boolean")))
            )
            .executor(new NationSpawnFlagExecutor())
            .build(), "spawnflag");
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        Player player = (Player) src;
        Nation nation = DataHandler.getNationOfPlayer(((Player) src).getUniqueId());

        if(nation == null) {
            src.sendMessage(Text.of(
                TextColors.RED,
                LanguageHandler.ERROR_NONATION
            ));
            return CommandResult.success();
        }

        if(!ctx.<String>getOne("spawn").isPresent()) {
            src.sendMessage(Text.builder()
                .append(Text.of(TextColors.RED, LanguageHandler.ERROR_SPAWNNAME.split("\\{SPAWNLIST\\}")[0]))
                .append(Utils.formatNationSpawns(nation, TextColors.YELLOW, "spawnflag"))
                .append(Text.of(TextColors.RED, LanguageHandler.ERROR_SPAWNNAME.split("\\{SPAWNLIST\\}")[1]))
                .append(Text.of(TextColors.DARK_GRAY, " <- " + LanguageHandler.CLICK)).build());
            return CommandResult.success();
        }

        NationSpawn spawn = nation.getSpawn(ctx.<String>getOne("spawn").get());
        if(spawn == null) {
            src.sendMessage(Text.builder()
                .append(Text.of(TextColors.RED, LanguageHandler.ERROR_SPAWNNAME.split("\\{SPAWNLIST\\}")[0]))
                .append(Utils.formatNationSpawns(nation, TextColors.YELLOW))
                .append(Text.of(TextColors.RED, LanguageHandler.ERROR_SPAWNNAME.split("\\{SPAWNLIST\\}")[1]))
                .append(Text.of(TextColors.DARK_GRAY, " <- " + LanguageHandler.CLICK)).build());
            return CommandResult.success();
        }

        if(ctx.<String>getOne("flag").isPresent() && ctx.<String>getOne("boolean").isPresent()) {
            if(!nation.isStaff(player.getUniqueId())) {
                src.sendMessage(Text.of(TextColors.RED, LanguageHandler.ERROR_PERM_NATIONSTAFF));
                return CommandResult.success();
            }

            boolean bool =  ctx.<String>getOne("boolean").get().equals("true");

            if(!ctx.<String>getOne("flag").get().equals("public")) {
                src.sendMessage(Text.of(
                    TextColors.RED,
                    LanguageHandler.ERROR_SPAWN_UNKNOWNTAG.replace("{TAGNAME}", ctx.<String>getOne("flag").get())
                ));
                return CommandResult.success();
            }

            spawn.setFlag(ctx.<String>getOne("flag").get(), bool);
        }

        src.sendMessage(Utils.formatNationSpawnDescription(spawn));
        return CommandResult.success();
    }
}
