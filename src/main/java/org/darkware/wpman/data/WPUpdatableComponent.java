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

package org.darkware.wpman.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.darkware.wpman.config.UpdatableCollectionConfig;
import org.darkware.wpman.config.UpdatableConfig;

/**
 * This is a base implementation of a single updatable WordPress component. All updatable
 * components share some similar behavior, particularly with respect to the handling of
 * updates and the metadata used to support them (<em>eg:</em> versions).
 *
 * @author jeff
 * @since 2016-01-25
 */
public abstract class WPUpdatableComponent extends WPDataComponent
{
    @JsonProperty("name") private String id;
    private Version version;
    @JsonProperty("update_version")
    private Version latestVersion;

    @JsonIgnore
    private final WPUpdatableType componentType;
    private boolean allowUpdates;
    private Version updateLimit;

    /**
     * Create a new base instance of an updatable component.
     *
     * @param componentType The type of component in the concrete implementation
     */
    public WPUpdatableComponent(final WPUpdatableType componentType)
    {
        super();

        this.componentType = componentType;
    }

    @Deprecated
    protected abstract String getConfigSection();

    @Override
    protected void refreshBaseData()
    {
        // Not currently supported.
    }


    protected void readConfig()
    {
        UpdatableCollectionConfig collectionConfig = this.getManager().getConfig().getUpdatableCollection(this.componentType);
        UpdatableConfig config = collectionConfig.getConfig(this.getId());

        this.allowUpdates = config.getUpdatable();
        this.updateLimit = config.getMaxVersion();
    }

    public final String getId()
    {
        return id;
    }

    public final void setId(final String id)
    {
        this.id = id;
        this.readConfig();
    }

    public final Version getVersion()
    {
        return version;
    }

    public final void setVersion(final Version version)
    {
        this.version = version;
    }

    public final Version getLatestVersion()
    {
        return latestVersion;
    }

    public final void setLatestVersion(final Version latestVersion)
    {
        this.latestVersion = latestVersion;
    }

    public final boolean hasUpdate()
    {
        return this.latestVersion != null;
    }

    public final boolean canUpdate()
    {
        if (!this.allowUpdates) return false;
        if (!this.hasUpdate()) return false;

        if (!this.version.lessThan(this.getUpdateVersion())) return false;

        return true;
    }

    @JsonIgnore
    public final Version getUpdateVersion()
    {
        if (this.hasUpdate())
        {
            if (this.updateLimit != null && this.latestVersion.greaterThan(this.updateLimit)) return this.updateLimit;
            else return this.latestVersion; 
        }
        else return this.version;
    }

}
