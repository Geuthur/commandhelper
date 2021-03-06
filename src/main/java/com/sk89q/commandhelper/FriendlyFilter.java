// $Id$
/*
 * CommandHelper
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.commandhelper;

import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 *
 * @author sk89q
 */
public class FriendlyFilter extends PlayerFilter {
    public FriendlyFilter(Server server) {
        super(server);
    }

    /**
     * Checks to see if a player matches this query.
     *
     * @param player
     * @return
     */
    @Override
    public boolean matches(Player player) {
        return true;
    }
}
