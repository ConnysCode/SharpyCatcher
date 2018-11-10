package listener;

import core.Config;
import core.Pokes;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tools.PrivateMessageCreator;

import java.util.concurrent.TimeUnit;

import static core.Config.BOT_ADMINS;
import static core.Config.pokecordID;
import static core.Main.jda;

public class PrivateMessageRecieved extends ListenerAdapter {

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

        if (event.getAuthor().getId().equalsIgnoreCase(pokecordID) && event.getMessage().getContentRaw().contains("was sold for") && Config.PRIVATE_MSG) {

            for (int i = 0; i < BOT_ADMINS.size(); i++) {

                PrivateMessageCreator.normalUnbeddedMessage("**Pokémon sold!**\n" + event.getMessage().getContentRaw(), event.getJDA().getUserById(BOT_ADMINS.get(i)));
            }

        }

    }

}
