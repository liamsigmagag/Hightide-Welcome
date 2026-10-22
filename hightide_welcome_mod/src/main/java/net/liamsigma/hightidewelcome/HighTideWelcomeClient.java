package net.liamsigma.hightidewelcome;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.regex.Pattern;

public final class HighTideWelcomeClient implements ClientModInitializer {
    /*
     * Matches the server announcement, allowing any player name and any
     * comma-separated number in parentheses, e.g.:
     * New → Welcome deputy_mm5 to Hightide Survival! (#2,276)
     *
     * This is registered on GAME messages, not CHAT messages. Player-authored
     * chat is delivered through the CHAT event and will not trigger this.
     */
    private static final Pattern ANNOUNCEMENT = Pattern.compile(
        "^\\s*New\\s*→\\s*Welcome\\s+.+?\\s+to\\s+Hightide Survival!\\s*\\(#\\s*\\d[\\d,]*\\)\\s*$",
        Pattern.CASE_INSENSITIVE
    );

    private static final Queue<Integer> PENDING_REPLIES = new ArrayDeque<>();
    private static int clientTicks = 0;

    @Override
    public void onInitializeClient() {
        ClientReceiveMessageEvents.GAME.register((Text message, boolean overlay) -> {
            String plainText = message.getString();
            if (ANNOUNCEMENT.matcher(plainText).matches()) {
                // 2 client ticks is approximately 100 ms at the normal 20 TPS.
                // Queue it rather than sending inside the message callback.
                PENDING_REPLIES.add(clientTicks + 2);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register((MinecraftClient client) -> {
            clientTicks++;

            while (!PENDING_REPLIES.isEmpty() && PENDING_REPLIES.peek() <= clientTicks) {
                PENDING_REPLIES.remove();

                if (client.player != null && client.getNetworkHandler() != null) {
                    client.getNetworkHandler().sendChatMessage("Welcome!");
                }
            }
        });
    }
}
