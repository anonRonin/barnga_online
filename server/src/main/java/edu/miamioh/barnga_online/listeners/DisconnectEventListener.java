package edu.miamioh.barnga_online.listeners;

import edu.miamioh.barnga_online.*;
import edu.miamioh.barnga_online.events.*;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;

/**
 * Listeners for when receiving the EVENT_DISCONNECT event.
 *
 * @author Naoki Mizuno
 */
public class DisconnectEventListener implements DisconnectListener {
    protected WorldState world;
    protected BarngaOnlineConfigs configs;
    protected SocketIOServer server;

    public DisconnectEventListener(BarngaOnlineConfigs configs, WorldState world,
            SocketIOServer server) {
        super();

        this.world = world;
        this.configs = configs;
        this.server = server;
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        Player p = world.getClients().get(client.getRemoteAddress());
        Util.debug("Player %d of Team %d has disconnected\n", p.id, p.teamId);
        // TODO: Inform other players about the disconnect?
    }
}
