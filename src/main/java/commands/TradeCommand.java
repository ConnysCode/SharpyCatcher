package commands;

import com.github.johnnyjayjay.discord.commandapi.AbstractCommand;
import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.SubCommand;
import core.Config;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.concurrent.TimeUnit;

import static core.Config.pokecordID;
import static core.Main.jda;
import static tools.Tools.isBotAdmin;

public class TradeCommand extends AbstractCommand {

    @SubCommand(isDefault = true) // will be called if no other sub command matches
    public void onWrongUsage(CommandEvent event, Member member, TextChannel channel, String[] args) {
        channel.sendMessage("Invalid syntax").queue();
    }

    @SubCommand(args = {".*"})
    public void onTrade(CommandEvent event, Member member, TextChannel channel, String[] args) {
        if (!isBotAdmin(member.getUser()))
            return;
        try {
            Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            channel.sendMessage("Invalid syntax").queue();
            return;
        } catch(NullPointerException e) {
            channel.sendMessage("Invalid syntax").queue();
            return;
        }

        if (Integer.parseInt(args[0]) <= 25) {

            event.getChannel().sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "trade " + member.getAsMention()).queue((msg) -> {
                EventListener messageListener = new EventListener() {
                    @Override
                    public void onEvent(Event eventMSG) {
                        if (!(eventMSG instanceof MessageReceivedEvent))
                            return;


                        if (((MessageReceivedEvent) eventMSG).getGuild().getId().equals(((MessageReceivedEvent) eventMSG).getGuild().getId())
                                && ((MessageReceivedEvent) eventMSG).getAuthor().getId().equals(member.getUser().getId())
                                && ((MessageReceivedEvent) eventMSG).getMessage().getContentRaw().equalsIgnoreCase(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "accept")) {

                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }

                            String numbers = "";

                            for (int i = 1; i < Integer.parseInt(args[0]) + 1; i++) {
                                numbers = numbers + i + " ";
                            }

                            channel.sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "p add " + numbers).queue();

                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }

                            channel.sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "confirm").queue();

                            jda.removeEventListener(this);
                        }

                    }
                };
                jda.addEventListener(messageListener);
            });

        } else {
            channel.sendMessage("Invalid syntax").queue();
        }

    }

}
