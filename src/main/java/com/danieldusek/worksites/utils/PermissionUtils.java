package com.danieldusek.worksites.utils;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermissionUtils {

    public static int getNumericPermissionNode(Player player, String permissionPrefix, int defaultValue) {
        for (PermissionAttachmentInfo attachmentInfo : player.getEffectivePermissions()) {
            if (attachmentInfo.getPermission().startsWith(permissionPrefix)) {
                String permission = attachmentInfo.getPermission();
                return Integer.parseInt(permission.substring(permission.lastIndexOf(".") + 1));
            }
        }

        return defaultValue;
    }
}
