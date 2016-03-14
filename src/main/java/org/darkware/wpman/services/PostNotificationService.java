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

package org.darkware.wpman.services;

import com.google.common.eventbus.Subscribe;
import org.darkware.wpman.data.WPSite;
import org.darkware.wpman.events.WPStartupEvent;
import org.darkware.wpman.wpcli.WPCLI;
import org.darkware.wpman.wpcli.WPCLIOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@code PostNotificationService} is a {@link WPService} which listens for various events and
 * posts summaries of the results to one or more WordPress blogs on the WordPress instance being
 * managed.
 *
 * @author jeff
 * @since 2016-03-12
 */
public class PostNotificationService extends WPService
{
    protected static final Logger log = LoggerFactory.getLogger("PostNotify");

    public WPSite postSite;

    /**
     * Create a new {@code PostNotificationService} based on the current context.
     */
    public PostNotificationService()
    {
        super();
    }

    @Override
    protected void beforeActivation()
    {
        super.beforeActivation();

        String postSiteName = this.getConfig().readVariable("notify.post.site");
        this.postSite = this.getManager().getData().getSites().get(postSiteName);
        if (this.postSite == null) throw new IllegalStateException("Notification post site does not exist.");
    }

    protected WPCLI createPost(final String title)
    {
        WPCLI poster = this.getManager().getBuilder().build("post", "create", "-");

        poster.setSite(this.postSite);
        //poster.setOption(new WPCLIOption<>("post_type", "page"));
        poster.setOption(new WPCLIOption<>("post_title", title));
        poster.setOption(new WPCLIOption<>("post_status", "publish"));

        return poster;
    }

    @Subscribe()
    public void onStartup(WPStartupEvent startup)
    {
        /*
        WPCLI poster = this.createPost("WP Startup");

        poster.getStdin().println("WPManager has started up.");

        poster.execute();
        PostNotificationService.log.info("Posted new notification: WP Startup");
        */
    }
}