package com.bootloadercheck.magiskkiller;

import android.util.Log;
import java.util.List;

/**
 * @author canyie
 */
public class MagiskKiller {
    public static final String TAG = "MagiskKiller";

    /** Bootloader is unlocked */
    public static final int FOUND_BOOTLOADER_UNLOCKED = 1 << 1;

    /** Device is running a self-signed ROM */
    public static final int FOUND_BOOTLOADER_SELF_SIGNED = 1 << 2;

    /** Some system properties are modified by resetprop (a tool provided by Magisk) */
    public static final int FOUND_RESETPROP = 1 << 4;

    public static int detectBootloaderProperties() {
        int result = 0;
        try {
            // The better way to get the filename would be `getprop -Z`
            // But "-Z" option requires Android 7.0+, and I'm lazy to implement it
            PropArea bootloader = PropArea.any("bootloader_prop", "exported2_default_prop", "default_prop");
            if (bootloader == null) return 0;
            List<String> values = bootloader.findPossibleValues("ro.boot.verifiedbootstate");
            // ro properties are read-only, multiple values found = the property has been modified by resetprop
            if (values.size() > 1) {
                result |= FOUND_RESETPROP;
            }
            for (String value : values) {
                if ("orange".equals(value)) {
                    result |= FOUND_BOOTLOADER_UNLOCKED;
                    result &= ~FOUND_BOOTLOADER_SELF_SIGNED;
                } else if ("yellow".equals(value) && (result & FOUND_BOOTLOADER_UNLOCKED) == 0) {
                    result |= FOUND_BOOTLOADER_SELF_SIGNED;
                }
            }

            values = bootloader.findPossibleValues("ro.boot.vbmeta.device_state");
            if (values.size() > 1) {
                result |= FOUND_RESETPROP;
            }
            for (String value : values) {
                if ("unlocked".equals(value)) {
                    result |= FOUND_BOOTLOADER_UNLOCKED;
                    result &= ~FOUND_BOOTLOADER_SELF_SIGNED;
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to check props", e);
        }
        return result;
    }
}
