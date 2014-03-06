/*
 * The MIT License
 *
 * Copyright 2014 SBPrime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primesoft.ironmansurvival;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_7_R1.CraftChunk;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author SBPrime
 */
public class IronmanSurvival extends JavaPlugin {

    private static final Logger s_log = Logger.getLogger("Minecraft.IronmanSurvival");
    private static ConsoleCommandSender s_console;
    private static String s_prefix = null;
    private static String s_logFormat = "%s %s";
    private static IronmanSurvival s_instance;
    
    private final EventListener m_listener = new EventListener(this);

    public static IronmanSurvival getInstance() {
        return s_instance;
    }

    public static void log(String msg) {
        if (s_log == null || msg == null || s_prefix == null) {
            return;
        }

        s_log.log(Level.INFO, String.format(s_logFormat, s_prefix, msg));
    }

    public static void say(String player, String msg) {
        say(getPlayer(player), msg);
    }

    public static void say(Player player, String msg) {
        if (player == null) {
            s_console.sendRawMessage(msg);
        } else {
            player.sendRawMessage(msg);
        }
    }

    /**
     * Get craft bukkit player
     *
     * @param player
     * @return
     */
    public static Player getPlayer(String player) {
        if (s_instance == null) {
            return null;
        }
        return s_instance.getServer().getPlayer(player);
    }

    @Override
    public void onEnable() {
        s_instance = this;
        PluginDescriptionFile desc = getDescription();
        s_prefix = String.format("[%s]", desc.getName());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(m_listener, this);        
        
        FloatingTorch.registerEntity();

        log("Enabled");

        getServer().getWorlds().get(0).getPopulators().add(new BlockPopulator() {

            @Override
            public void populate(World world, Random random, Chunk chunk) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @Override
    public void onDisable() {
        log("Disabled");
    }

}
