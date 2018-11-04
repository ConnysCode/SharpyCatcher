package tools;

import core.Config;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.*;
import java.util.concurrent.*;

public class Spammer {

    @Deprecated
    public static void spamInChannelTimer(TextChannel channel) {
        Timer t = new Timer();
        List<String> textes = Config.SPAM_WORDS;

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                String randomText = textes.get(rand.nextInt(textes.size()));
                channel.sendMessage(randomText).queue();
            }
        }, 0, 2500);
    }

    public static void spamInChannelExecutor(TextChannel channel) {
        List<String> textes = Config.SPAM_WORDS;

        ScheduledExecutorService execService
                =	Executors.newScheduledThreadPool(5);
        execService.scheduleAtFixedRate(()->{
            Random rand = new Random();
            String randomText = textes.get(rand.nextInt(textes.size()));
            channel.sendMessage(randomText).queue();
        }, 0, 2000, TimeUnit.MILLISECONDS);
    }



}
