package com.danieldusek.worksites.constants;

import org.bukkit.ChatColor;

public final class C {
    public static final class PLUGIN {
        public static final String DEFAULT_WORKSITE_NAME = "_default";
        public static final String MARKER = ChatColor.GRAY + "[" + ChatColor.YELLOW + "Worksites"  + ChatColor.GRAY + "] " + ChatColor.WHITE;
    }

    public static final class PERMISSIONS {
        public static final String USE_WORKSITE = "worksites.use"; // Typically assign these two to the default group.
        public static final String SET_WORKSITE = "worksites.set";

        public static final String SET_WORKSITE_PUBLIC = "worksites.set.public";
        public static final String SET_WORKSITE_PERMISSION_BASED = "worksites.set.permissionbased";

        public static final String DELETE_WORKSITE = "worksites.delete";

        public static final String USE_WORKSITE_OTHERS = "worksites.others.use";


        public static final String MULTIPLE_WORKSITES = "worksites.multiple";
        public static final String UNLIMITED_WORKSITES = "worksites.multiple.unlimited";
    }

    public static final class MSG {

    }


}
