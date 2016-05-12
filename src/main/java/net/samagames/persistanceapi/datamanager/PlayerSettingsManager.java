/*
===============================================================
   _____                       ______
  / ___/____ _____ ___  ____ _/ ____/___ _____ ___  ___  _____
  \__ \/ __ `/ __ `__ \/ __ `/ / __/ __ `/ __ `__ \/ _ \/ ___/
 ___/ / /_/ / / / / / / /_/ / /_/ / /_/ / / / / / /  __(__  )
/____/\__,_/_/ /_/ /_/\__,_/\____/\__,_/_/ /_/ /_/\___/____/

===============================================================
  Persistance API
  Copyright (c) for SamaGames, all right reserved
  By MisterSatch, January 2016
===============================================================
*/

package net.samagames.persistanceapi.datamanager;

import net.samagames.persistanceapi.beans.players.PlayerBean;
import net.samagames.persistanceapi.beans.players.PlayerSettingsBean;
import net.samagames.persistanceapi.utils.Transcoder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class PlayerSettingsManager
{
    // Defines
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultset = null;

    // Get the player ingame settings
    public PlayerSettingsBean getPlayerSettings(PlayerBean player, DataSource dataSource) throws Exception
    {
        // Defines
        PlayerSettingsBean playerSettingsBean = null;

        // Make the research of player by UUID
        try
        {
            // Set connection
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            // Query construction
            String sql = "";
            sql += "select (HEX(uuid)) as uuid, jukebox_listen, group_demand_receive, friendship_demand_receive, notification_receive, private_message_receive, chat_visible, player_visible";
            sql += ", waiting_line_notification, other_player_interaction, click_on_me_activation, allow_statistic_onclick, allow_coins_onclick, allow_stars_onclick, allow_click_on_other, elytra_activated";
            sql += " from player_settings where uuid=(UNHEX('" + Transcoder.Encode(player.getUuid().toString()) + "'))";

            // Execute the query
            resultset = statement.executeQuery(sql);

            // Manage the result in a bean
            if (resultset.next())
            {
                // There's a result
                String playerUuid = Transcoder.Decode(resultset.getString("uuid"));
                UUID uuid = UUID.fromString(playerUuid);
                boolean jukeboxListen = resultset.getBoolean("jukebox_listen");
                boolean groupDemandReceive = resultset.getBoolean("group_demand_receive");
                boolean friendshipDemandReceive =  resultset.getBoolean("friendship_demand_receive");
                boolean notificationReceive = resultset.getBoolean("notification_receive");
                boolean privateMessageReceive = resultset.getBoolean("private_message_receive");
                boolean chatVisible = resultset.getBoolean("chat_visible");
                boolean playerVisible = resultset.getBoolean("player_visible");
                boolean waitingLineNotification = resultset.getBoolean("waiting_line_notification");
                boolean otherPlayerInteraction = resultset.getBoolean("other_player_interaction");
                boolean clickOnMeActivation = resultset.getBoolean("click_on_me_activation");
                boolean allowStatisticOnClick = resultset.getBoolean("allow_statistic_onclick");
                boolean allowCoinsOnClick = resultset.getBoolean("allow_coins_onclick");
                boolean allowStarsOnclick = resultset.getBoolean("allow_stars_onclick");
                boolean allowClickOnOther = resultset.getBoolean("allow_click_on_other");
                boolean elytraActivated = resultset.getBoolean("elytra_activated");
                playerSettingsBean = new PlayerSettingsBean(uuid, jukeboxListen, groupDemandReceive, friendshipDemandReceive, notificationReceive, privateMessageReceive, chatVisible,
                        playerVisible, waitingLineNotification, otherPlayerInteraction, clickOnMeActivation, allowStatisticOnClick, allowCoinsOnClick, allowStarsOnclick, allowClickOnOther, elytraActivated);
                return playerSettingsBean;
            }
            else
            {
                // If there no player settings for the uuid in database create a new player settings
                this.close();
                this.createDefaultPlayerSettings(player, dataSource);
                PlayerSettingsBean settings = this.getPlayerSettings(player, dataSource);
                this.close();
                return settings;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
        finally
        {
            // Close the query environment in order to prevent leaks
            close();
        }
    }

    // Set player ingame settings
    public void setPlayerSettings(PlayerBean player, PlayerSettingsBean settingsBeans, DataSource dataSource) throws Exception
    {
        // Update the players data
        try
        {
            // Set connection
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            // Query construction
            String sql = "";
            sql += "update player_settings set jukebox_listen=" + settingsBeans.isJukeboxListen();
            sql += ", group_demand_receive=" +  settingsBeans.isGroupDemandReceive();
            sql += ", friendship_demand_receive=" + settingsBeans.isFriendshipDemandReceive();
            sql += ", notification_receive=" + settingsBeans.isNotificationReceive();
            sql += ", private_message_receive=" + settingsBeans.isPrivateMessageReceive();
            sql += ", private_message_receive=" + settingsBeans.isChatVisible();
            sql += ", chat_visible=" + settingsBeans.isPlayerVisible();
            sql += ", player_visible=" + settingsBeans.isWaitingLineNotification();
            sql += ", other_player_interaction=" + settingsBeans.isOtherPlayerInteraction();
            sql += ", click_on_me_activation=" + settingsBeans.isClickOnMeActivation();
            sql += ", allow_statistic_onclick=" + settingsBeans.isAllowStatisticOnClick();
            sql += ", allow_coins_onclick=" + settingsBeans.isAllowCoinsOnClick();
            sql += ", allow_stars_onclick=" + settingsBeans.isAllowStarsOnclick();
            sql += ", allow_click_on_other=" + settingsBeans.isAllowClickOnOther();
            sql += ", elytra_activated=" + settingsBeans.isElytraActivated();
             sql += " where uuid=(UNHEX('" + Transcoder.Encode(player.getUuid().toString()) + "'))";

            // Execute the query
            statement.executeUpdate(sql);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
        finally
        {
            // Close the query environment in order to prevent leaks
            close();
        }
    }

    // Create default player settings
    public void createDefaultPlayerSettings(PlayerBean player, DataSource dataSource) throws Exception
    {
        try
        {
            // Set connection
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            // Query construction
            String sql = "insert into player_settings (uuid, jukebox_listen, group_demand_receive, friendship_demand_receive, notification_receive, private_message_receive";
            sql += ", chat_visible, player_visible, waiting_line_notification, other_player_interaction, click_on_me_activation, allow_statistic_onclick, allow_coins_onclick";
            sql += ", allow_stars_onclick, allow_click_on_other, elytra_activated)";
            sql += " values (UNHEX('"+ Transcoder.Encode(player.getUuid().toString())+"')";
            sql += ", true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)";

            // Execute the query
            statement.executeUpdate(sql);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
        finally
        {
            // Close the query environment in order to prevent leaks
            close();
        }
    }

    // Close the connection
    public void close() throws Exception
    {
        // Close the query environment in order to prevent leaks
        try
        {
            if (resultset != null)
            {
                // Close the resulset
                resultset.close();
            }
            if (statement != null)
            {
                // Close the statement
                statement.close();
            }
            if (connection != null)
            {
                // Close the connection
                connection.close();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
    }
}