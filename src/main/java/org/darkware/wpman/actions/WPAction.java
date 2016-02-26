/*******************************************************************************
 * Copyright (c) 2016. darkware.org and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.darkware.wpman.actions;

import java.util.concurrent.Callable;

/**
 * @author jeff
 * @since 2016-02-10
 */
public interface WPAction<T> extends Callable<T>
{
    /**
     * Checks to see if this action has requested a timeout value be applied to its
     * execution.
     *
     * @return {@code true} if the action has a timeout, {@code false} if not.
     */
    boolean hasTimeout();

    /**
     * Fetches the number of seconds this action is allowed to execute for, or zero if no
     * timeout is enabled.
     *
     * @return The timeout, in seconds, or zero if no timeout is requested.
     */
    int getTimeout();

    /**
     * Fetch a description of the goal of this action.
     *
     * @return The description as a {@code String}.
     */
    String getDescription();
}
