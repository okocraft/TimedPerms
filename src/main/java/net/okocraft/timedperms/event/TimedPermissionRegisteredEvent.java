package net.okocraft.timedperms.event;

import java.util.UUID;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TimedPermissionRegisteredEvent extends TimedPermissionEvent {

    private static final HandlerList handlers = new HandlerList();

    private final int initialSeconds;
    private final TimedPermissionEvent cause;

    public TimedPermissionRegisteredEvent(UUID userUid, PermissionNode permission, int initialSeconds, TimedPermissionEvent cause) {
        super(userUid, permission);
        this.initialSeconds = initialSeconds;
        this.cause = cause;
    }

    public int getInitialSeconds() {
        return this.initialSeconds;
    }

    public TimedPermissionEvent getCause() {
        return this.cause;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
