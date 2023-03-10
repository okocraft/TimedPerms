package net.okocraft.timedperms.event;

import java.util.UUID;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TimedPermissionExpireEvent extends TimedPermissionRemoveEvent {

    private static final HandlerList handlers = new HandlerList();

    public TimedPermissionExpireEvent(UUID userUid, PermissionNode permission) {
        super(userUid, permission, 1);
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
