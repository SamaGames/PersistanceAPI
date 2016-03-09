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
package net.samagames.persistanceapi.beans.permissions;

public class UHCPermissionsBean
{
    /* Database Structure

    Table : uhc_permissions
    +-----------------+------------+------+-----+---------+----------------+
    | Field           | Type       | Null | Key | Default | Extra          |
    +-----------------+------------+------+-----+---------+----------------+
    | groups_id       | bigint(20) | NO   | PRI | NULL    | auto_increment |
    | uhc_team_lock   | tinyint(1) | NO   |     | NULL    |                |
    | uhc_team_name   | tinyint(1) | NO   |     | NULL    |                |
    | uhc_team_invite | tinyint(1) | NO   |     | NULL    |                |
    +-----------------+------------+------+-----+---------+----------------+
    */

    // Defines
    private long groupsId;
    private boolean uhcTeamLock;
    private boolean uhcTeamName;
    private boolean uhcTeamInvite;

    // Getters
    public long getGroupsId() { return this.groupsId; }
    public boolean isUhcTeamlock() { return this.uhcTeamLock; }
    public boolean isUhcTeamName() { return this.uhcTeamName; }
    public boolean isUhcTeaminvite() { return this.uhcTeamInvite; }

    // Setters
    public void setUhcTeamlock(boolean uhcTeamlock) { this.uhcTeamLock = uhcTeamlock; }
    public void setUhcTeamName(boolean uhcTeamName) { this.uhcTeamName = uhcTeamName; }
    public void setUhcTeaminvite(boolean uhcTeaminvite) { this.uhcTeamInvite = uhcTeaminvite; }

}
