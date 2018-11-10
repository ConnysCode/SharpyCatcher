package listener;

import core.Config;
import core.Main;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tools.PrivateMessageCreator;

import java.util.Arrays;

public class OnReady extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        System.out.println("\u001B[32m" + "Successfully connected with User:  " + event.getJDA().getSelfUser().getName() + "\u001B[0m");
        System.out.println("\u001B[33m" + "Active Guilds: " + Arrays.asList(Config.getValue("active_guilds").split(",")) + "\u001B[0m");
        System.out.println();

        for (int guild = 0; guild < Config.ACTIVE_GUILDS.size(); guild++) {

            if (event.getJDA().getGuildById(Config.ACTIVE_GUILDS.get(guild)).getTextChannelById(Config.SPAM_CHANNEL) != null) {
                //System.out.println(Config.ACTIVE_GUILDS.get(guild) + " || " + Config.SPAM_CHANNEL);
                tools.Spammer.spamInChannelExecutor(event.getJDA().getGuildById(Config.ACTIVE_GUILDS.get(guild)).getTextChannelById(Config.SPAM_CHANNEL));
            }
        }

    }

}
