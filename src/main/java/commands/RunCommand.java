package commands;

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import core.Config;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import static core.Config.pokecordID;
import static tools.Tools.isBotAdmin;

public class RunCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent commandEvent, Member member, TextChannel textChannel, String[] strings) {
        if (!isBotAdmin(member.getUser()))
            return;

            textChannel.sendMessage(tools.Tools.argsToString(strings, 0)).queue();

    }
}
