package listener;

import core.Config;
import core.Pokes;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.util.concurrent.TimeUnit;

import static core.Config.pokecordID;
import static core.Main.jda;

public class MessageRecieved extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        String pokeName, pokeHex;
        final boolean[] waitingForConfirm = {false};

        if (Config.ACTIVE_GUILDS.contains(event.getGuild().getId()) && event.getAuthor().getId().equalsIgnoreCase(pokecordID) && event.getMessage().getEmbeds().size() != 0 && event.getMessage().getEmbeds().get(0).getTitle().contains("has appeared!")) {
            try {
                System.out.println();
                pokeHex = tools.HashStuff.getMD5(event.getMessage().getEmbeds().get(0).getImage().getUrl());
                if (Pokes.getValue(pokeHex) == null) {
                    System.out.println("\u001B[31m" + "[ERROR] Couldn't detect Pokémon! Please update your Database at https://discord.gg/MCpK7aY\u001B[0m");
                    return;
                }
                pokeName = Pokes.getValue(pokeHex);
                System.out.println("\u001B[36m" + "Detected: " + pokeName + " || " + event.getGuild().getName() + "\u001B[0m");
                System.out.println("\u001B[33m" + "Trying to Catch...\u001B[0m");

                final int[] trys = {0};
                event.getChannel().sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "catch " + pokeName).queue((msg) -> {
                    EventListener messageListener = new EventListener() {
                        @Override
                        public void onEvent(Event eventMSG) {
                            if (!(eventMSG instanceof MessageReceivedEvent))
                                return;
                            if (trys[0] < 10) {
                                System.out.println("\u001B[35m" + "Waiting for Confirmation... [" + trys[0] + "]\u001B[0m");
                                if (((MessageReceivedEvent) eventMSG).getGuild().getId().equals(((MessageReceivedEvent) eventMSG).getGuild().getId()) && ((MessageReceivedEvent) eventMSG).getAuthor().getId().equals(pokecordID) && ((MessageReceivedEvent) eventMSG).getMessage().getContentRaw().startsWith("Congratulations ") && ((MessageReceivedEvent) eventMSG).getMessage().getContentRaw().contains(pokeName) /* && ((MessageReceivedEvent) eventMSG).getMessage().getMentionedUsers().get(0).getId().equals(event.getJDA().getSelfUser())*/) {
                                    System.out.println("\u001B[32m" + "Successfully catched " + pokeName + "\u001B[0m");

                                    if (Config.COIN_FARMER) {
                                        try {
                                            event.getMessage().getTextChannel().sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "market list 1 " + Config.COIN_FARMER_CREDITS).queue();
                                            TimeUnit.SECONDS.sleep(1);
                                            event.getTextChannel().sendMessage(Config.getValue(event.getGuild().getId() + "_pokecord_prefix") + "confirmlist").queue();
                                            System.out.println("\u001B[32m" + "Successful listed Pokémon on the market for " + Config.COIN_FARMER_CREDITS + " credits" + "\u001B[0m");
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    jda.removeEventListener(this);
                                } else {
                                    trys[0]++;
                                }
                            } else {
                                System.out.println("\u001B[31m" + "[ERROR] Couldn't confirm Catch after 10 tries.\u001B[0m");
                                jda.removeEventListener(this);
                            }

                        }
                    };
                    jda.addEventListener(messageListener);
                });





                TimeUnit.SECONDS.sleep(2);
                waitingForConfirm[0] = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Config.ACTIVE_GUILDS.contains(event.getGuild().getId()) && event.getAuthor().getId().equalsIgnoreCase(pokecordID) && event.getMessage().getEmbeds().size() != 0 && event.getMessage().getEmbeds().get(0).getFooter().getText().contains("To buy this pokémon, type")) {

            try {
                String pokeName2, pokeHex2;
                pokeHex2 = tools.HashStuff.getMD5(event.getMessage().getEmbeds().get(0).getImage().getUrl());
                pokeName2 = Pokes.getValue(pokeHex2);

                System.out.println("pokeHex2 -> " + pokeHex2);

                event.getTextChannel().sendMessage(pokeHex2 + " || " + pokeName2);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        }

}
