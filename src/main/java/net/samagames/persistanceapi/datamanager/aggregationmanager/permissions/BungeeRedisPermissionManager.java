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

package net.samagames.persistanceapi.datamanager.aggregationmanager.permissions;

import net.samagames.persistanceapi.beans.PlayerBean;
import net.samagames.persistanceapi.beans.permissions.BungeeRedisPermissionsBean;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BungeeRedisPermissionManager
{
    // Defines
    Connection connection = null;
    Statement statement = null;
    ResultSet resultset = null;
    BungeeRedisPermissionsBean bungeeRedisPermissionsBean = null;

    public BungeeRedisPermissionsBean getPemission(PlayerBean player, DataSource dataSource)
    {
        try
        {
            // Set connection
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            // Query construction
            String sql = "select groups_id, bungeecord_command_list, bungeecord_command_find, redisbungee_command_lastseen, bungeecord_command_ip, redisbungee_command_sendtoall, ";
            sql += "redisbungee_command_serverid, redisbunge_command_serverids, redisbungee_command_pproxy, redisbungee_command_plist, bungeecord_command_server, bungeecord_command_send, ";
            sql += "bungeecord_command_end, bungeecord_command_alert";
            sql += " from bukkit_permissions where group_id=" + player.getGroupId();

            // Execute the query
            resultset = statement.executeQuery(sql);

            // Manage the result in a bean
            if(resultset.next())
            {
                // There's a result
                long groupId = resultset.getLong("group_id");
                boolean bungeecordCommandList = resultset.getBoolean("bungeecord_command_list");
                boolean bungeecordCommandFind = resultset.getBoolean("bungeecord_command_find");
                boolean redisbungeeCommandLastSeen = resultset.getBoolean("redisbungee_command_lastseen");
                boolean redisbungeeCommandSendtoAll = resultset.getBoolean("bungeecord_command_ip");
                boolean bungeecordCommandIp = resultset.getBoolean("redisbungee_command_sendtoall");
                boolean redisbungeeCommandServerId = resultset.getBoolean("redisbungee_command_serverid");
                boolean redisbungeCommandServerIds = resultset.getBoolean("redisbunge_command_serverids");
                boolean redisbungeeCommandPproxy = resultset.getBoolean("redisbungee_command_pproxy");
                boolean redisbungeeCommandPlist = resultset.getBoolean("redisbungee_command_plist");
                boolean bungeecordCommandServer = resultset.getBoolean("bungeecord_command_server");
                boolean bungeecordCommandSend = resultset.getBoolean("bungeecord_command_send");
                boolean bungeecordCommandEnd = resultset.getBoolean("bungeecord_command_end");
                boolean bungeecordCommandAlert = resultset.getBoolean("bungeecord_command_alert");
                bungeeRedisPermissionsBean = new BungeeRedisPermissionsBean(groupId, bungeecordCommandList, bungeecordCommandFind, redisbungeeCommandLastSeen, redisbungeeCommandSendtoAll,
                        bungeecordCommandIp, redisbungeeCommandServerId, redisbungeCommandServerIds, redisbungeeCommandPproxy, redisbungeeCommandPlist,bungeecordCommandServer,
                        bungeecordCommandSend, bungeecordCommandEnd, bungeecordCommandAlert);
            }
            else
            {
                // If there no dimension stats int the database
                return null;
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            // Close the query environment in order to prevent leaks
            close();
        }

        return bungeeRedisPermissionsBean;
    }

    // Close all connection
    public void close()
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
        }
    }
}
