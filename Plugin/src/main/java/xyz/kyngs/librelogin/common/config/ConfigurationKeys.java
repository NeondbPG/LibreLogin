/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package xyz.kyngs.librelogin.common.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import xyz.kyngs.librelogin.common.authorization.ProfileConflictResolutionStrategy;
import xyz.kyngs.librelogin.common.config.key.ConfigurationKey;

import java.util.List;

/**
 * All the keys for the configuration.
 * BTW: Most of the comments were generated by GitHub's Copilot :D
 */
public class ConfigurationKeys {

    public static final ConfigurationKey<List<String>> ALLOWED_COMMANDS_WHILE_UNAUTHORIZED = new ConfigurationKey<>(
            "allowed-commands-while-unauthorized",
            List.of(
                    "login",
                    "register",
                    "2fa",
                    "2faconfirm",
                    "l",
                    "log",
                    "reg",
                    "resetpassword",
                    "confirmpasswordreset"
            ),
            "Commands that are allowed while the user is not authorized.",
            ConfigurateHelper::getStringList
    );

    public static final ConfigurationKey<List<String>> LIMBO = new ConfigurationKey<>(
            "limbo",
            List.of("limbo0", "limbo1"),
            "The authentication servers/worlds, players should be sent to, when not authenticated. On Paper, players will be spawned on the world spawn. THIS SERVERS MUST BE REGISTERED IN THE PROXY CONFIG. IN CASE OF PAPER, THE WORLDS MUST EXIST.",
            ConfigurateHelper::getStringList
    );

    public static final Multimap<String, String> LOBBY_DEFAULT = HashMultimap.create();
    public static final ConfigurationKey<Multimap<String, String>> LOBBY = new ConfigurationKey<>(
            "lobby",
            LOBBY_DEFAULT,
            """
                    !!WHEN USING PAPER, PUT ALL WORLDS UNDER "root"!!
                    On Paper, players will be spawned on the world spawn.
                                        
                    The servers/worlds player should be sent to when they are authenticated. THE SERVERS MUST BE REGISTERED IN THE PROXY CONFIG. IN CASE OF PAPER, THE WORLDS MUST EXIST.
                    The configuration allows configuring forced hosts; the servers/worlds in "root" are used when players do not connect from a forced host. Use § instead of dots.
                    See: https://github.com/kyngs/LibreLogin/wiki/Configuring-Servers
                    """,
            ConfigurateHelper::getServerMap
    );

    static {
        LOBBY_DEFAULT.put("root", "lobby0");
        LOBBY_DEFAULT.put("root", "lobby1");
    }

    public static final ConfigurationKey<Boolean> IGNORE_MAX_PLAYERS_FROM_BACKEND_PING = new ConfigurationKey<>(
            "ignore-max-players-from-backend-ping",
            false,
            """
                    By default, when choosing available lobby/limbos LibreLogin will rule out all the servers which are full.
                    Sometimes this may not work as expected. In such case, you can enable this option, which will ignore the max players field obtained by pinging the backend server.
                    """,
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<String> DEFAULT_CRYPTO_PROVIDER = new ConfigurationKey<>(
            "default-crypto-provider",
            "BCrypt-2A",
            """
                    The default crypto provider. This is used for hashing passwords. Available Providers:
                    SHA-256 - Older, not recommended. Kept for compatibility reasons.
                    SHA-512 - More safer than SHA-256, but still not recommended. Kept for compatibility reasons.
                    BCrypt-2A - Newer, more safe, recommended
                    Argon-2ID - Newest, should be safer than BCrypt-2A, however, it can slow down the server.
                    """,
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<String> PROFILE_CONFLICT_RESOLUTION_STRATEGY = new ConfigurationKey<>(
            "profile-conflict-resolution-strategy",
            "BLOCK",
            """
                    Sets the strategy for resolving profile conflicts. Available strategies:
                    BLOCK - Kick both players with the message key "kick-name-mismatch". An admin must resolve the conflict manually.
                    USE_OFFLINE - Use the offline profile. When both of the players attempt to join, they will be provided with a login screen and will be able to login with the offline player's password. The online player will have to change their nickname to a available one in order to recover their account. Beware, that there's a 30 days cool down for changing nicknames.
                    OVERWRITE - Overwrite the offline profile's data with the online profile's data. This will irreversibly delete the offline player's data. !!USE WITH CAUTION; PLAYERS CAN AND WILL ABUSE THIS!!
                    """,
            (helper, key) -> ProfileConflictResolutionStrategy.valueOf(helper.getString(key).toUpperCase()).name() // Sanity check
    );

    public static final ConfigurationKey<Integer> MAX_LOGIN_ATTEMPTS = new ConfigurationKey<>(
            "max-login-attempts",
            -1,
            "Kick the player, if the password was incorrect more or equal times. -1 means disabled",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Integer> MILLISECONDS_TO_EXPIRE_LOGIN_ATTEMPTS = new ConfigurationKey<>(
            "milliseconds-to-refresh-login-attempts",
            10000,
            "Time to reset login attempts. The amount of time the player should have waited for their login attempts to expire. On every rejoin, login attempts are reset.",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Boolean> USE_TITLES = new ConfigurationKey<>(
            "use-titles",
            true,
            "Whether or not to use titles when player is awaiting authentication.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Boolean> USE_ACTION_BAR = new ConfigurationKey<>(
            "use-action-bar",
            false,
            "Whether or not to use action bar when player is awaiting authentication.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<String> NEW_UUID_CREATOR = new ConfigurationKey<>(
            "new-uuid-creator",
            "CRACKED",
            """
                    Sets which method should be used for creating fixed UUID when a new player is created.
                    See the wiki for further information: https://github.com/kyngs/LibreLogin/wiki/UUID-Creators
                    Available Creators:
                    RANDOM - Generates a random UUID
                    CRACKED - Generates a UUID based on the player's name, the same method as if the server was in offline mode
                    MOJANG - If the player exists in the Mojang's database, it will be used. Otherwise, CRACKED will be used.
                    """,
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<Integer> IP_LIMIT = new ConfigurationKey<>(
            "ip-limit",
            -1,
            """
                    Sets the maximum amount of accounts that can be registered from the same IP address.
                    Set to zero or less to disable.
                    !!THIS IS NOT RECOMMENDED!! Due to the lack of IPv4 addresses, sometimes even entire villages share the same IP address.
                    """,
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Boolean> AUTO_REGISTER = new ConfigurationKey<>(
            "auto-register",
            false,
            """
                    Should we automatically register all players with a premium nickname?
                    !!CRACKED PLAYERS WILL NOT BE ABLE TO REGISTER PREMIUM USERNAMES!!
                    """,
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Integer> MILLISECONDS_TO_REFRESH_NOTIFICATION = new ConfigurationKey<>(
            "milliseconds-to-refresh-notification",
            10000,
            """
                    This specifies how often players should be notified when not authenticated. Set to negative to disable.
                    This includes (but is not limited to):
                    - Message in chat
                    - Title
                    """,
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Integer> SECONDS_TO_AUTHORIZE = new ConfigurationKey<>(
            "seconds-to-authorize",
            -1,
            "Sets the login/register time limit in seconds. Set to negative to disable.",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<?> DATABASE = ConfigurationKey.getComment(
            "database",
            "This section is used for MySQL database configuration."
    );

    public static final ConfigurationKey<String> DATABASE_TYPE = new ConfigurationKey<>(
            "database.type",
            "librelogin-sqlite",
            """
                    The type of the database. Built-in types:
                    librelogin-mysql - MySQL database, you must fill out the mysql section below.
                    librelogin-postgresql - PostgreSQL database, you must fill out the postgresql section below.
                    librelogin-sqlite - SQLite database, default file is "database.db", you can change it in the sqlite section below.
                    """,
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<?> MIGRATION = ConfigurationKey.getComment(
            "migration",
            """
                    This is used for migrating the database from other plugins.
                    Please see the wiki for further information: https://github.com/kyngs/LibreLogin/wiki/Database-Migration
                    """
    );

    public static final ConfigurationKey<Boolean> MIGRATION_ON_NEXT_STARTUP = new ConfigurationKey<>(
            "migration.on-next-startup",
            false,
            "Migrate the database on the next startup.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<String> MIGRATION_TYPE = new ConfigurationKey<>(
            "migration.type",
            "authme-sqlite",
            """
                    The type of the migration. Available Types:
                    jpremium-mysql - Can convert from MySQL JPremium SHA256, SHA512 and BCrypt
                    authme-mysql - Can convert from MySQL AuthMe BCrypt and SHA256
                    authme-sqlite - Can convert from SQLite AuthMe BCrypt and SHA256
                    authme-postgresql - Can convert from PostgreSQL AuthMe BCrypt and SHA256
                    aegis-mysql - Can convert from MySQL Aegis BCrypt
                    dba-mysql - Can convert from MySQL DynamicBungeeAuth, which was configured to use SHA-512
                    nlogin-sqlite - Can convert from SQLite NLogin SHA512
                    nlogin-mysql - Can convert from MySQL NLogin SHA512
                    loginsecurity-mysql - Can convert from MySQL LoginSecurity BCrypt
                    loginsecurity-sqlite - Can convert from SQLite LoginSecurity BCrypt
                    fastlogin-sqlite - Can convert from SQLite FastLogin, !!YOU MUST RUN CONVERSION FROM AUTHME FIRST!!
                    fastlogin-mysql - Can convert from MySQL FastLogin, !!YOU MUST RUN CONVERSION FROM AUTHME FIRST!!
                    limboauth-mysql - Can convert from MySQL LimboAuth BCrypt and SHA256
                    authy-mysql - Can convert from MySQL Authy SHA256
                    authy-sqlite - Can convert from SQLite Authy SHA256
                    logit-mysql - Can convert from MySQL LogIt SHA256
                    librelogin-mysql - Can convert from MySQL LibreLogin, useful for migrating to a different database
                    librelogin-sqlite - Can convert from SQLite LibreLogin, useful for migrating to a different database
                    """,
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<String> MIGRATION_MYSQL_OLD_DATABASE_TABLE = new ConfigurationKey<>(
            "migration.old-database.mysql.table",
            "user-data",
            "The table of the old database.",
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<String> MIGRATION_POSTGRESQL_OLD_DATABASE_TABLE = new ConfigurationKey<>(
            "migration.old-database.postgresql.table",
            "user-data",
            "The table of the old database.",
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<?> TOTP = ConfigurationKey.getComment(
            "totp",
            """
                    This section is used for 2FA configuration.
                    !! YOU MUST HAVE PROTOCOLIZE INSTALLED FOR THIS TO WORK !!
                                        
                    You can find more information on the wiki: https://github.com/kyngs/LibreLogin/wiki/2FA
                    """
    );

    public static final ConfigurationKey<Boolean> TOTP_ENABLED = new ConfigurationKey<>(
            "totp.enabled",
            true,
            """
                    Should we enable TOTP-Based Two-Factor Authentication? If you don't know what this is, this is the 2FA used in applications like Google Authenticator etc.
                    I heavily suggest you to read this wiki page: https://github.com/kyngs/LibreLogin/wiki/2FA
                    """,
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<String> TOTP_LABEL = new ConfigurationKey<>(
            "totp.label",
            "LibreLogin Network",
            "The label to be displayed in the 2FA app. Change this to your network name.",
            ConfigurateHelper::getString
    );

    public static final ConfigurationKey<Integer> MINIMUM_PASSWORD_LENGTH = new ConfigurationKey<>(
            "minimum-password-length",
            -1,
            "The minimum length of a password. Set to negative to disable.",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Integer> MINIMUM_USERNAME_LENGTH = new ConfigurationKey<>(
            "minimum-username-length",
            -1,
            "The minimum length the player's name can have. Only applies to new players, set to 0 or lower to disable.",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Long> SESSION_TIMEOUT = new ConfigurationKey<>(
            "session-timeout",
            0L,
            "Defines a time in seconds after a player's session expires. Default value is one week (604800 seconds). Set to zero or less to disable sessions.",
            ConfigurateHelper::getLong
    );

    public static final ConfigurationKey<Boolean> PING_SERVERS = new ConfigurationKey<>(
            "ping-servers",
            false,
            "!!THIS OPTION IS IRRELEVANT WHEN USING PAPER!! Should we ping servers to check if they are online, and get their player count? If you disable this, the pinging servers message will still appear in the console, even though the servers will not be pinged.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Boolean> REMEMBER_LAST_SERVER = new ConfigurationKey<>(
            "remember-last-server",
            false,
            "Should we remember the last server/world a player was on? This is not recommended for large networks.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Boolean> DEBUG = new ConfigurationKey<>(
            "debug",
            false,
            "Should we enable debug mode? This will print out debug messages to the console.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Boolean> FALLBACK = new ConfigurationKey<>(
            "fallback",
            false,
            "!!THIS OPTION IS IRRELEVANT WHEN USING PAPER!! Should we fallback players to lobby servers if the server they are on shutdowns? If set to false, they will be kicked.",
            ConfigurateHelper::getBoolean
    );

    public static final ConfigurationKey<Integer> TOTP_DELAY = new ConfigurationKey<>(
            "totp.delay",
            1000,
            "The delay in milliseconds until player is given a map to scan the QR code. Increase this value if the map disappears too quickly.",
            ConfigurateHelper::getInt
    );

    public static final ConfigurationKey<Boolean> MAIL_ENABLED = new ConfigurationKey<>(
            "mail.enabled",
            false,
            "Should we enable the email password recovery feature?",
            ConfigurateHelper::getBoolean
    );
    public static final ConfigurationKey<String> MAIL_HOST = new ConfigurationKey<>(
            "mail.host",
            "smtp.gmail.com",
            "The host of the SMTP server.",
            ConfigurateHelper::getString
    );
    public static final ConfigurationKey<Integer> MAIL_PORT = new ConfigurationKey<>(
            "mail.port",
            587,
            "The port of the SMTP server.",
            ConfigurateHelper::getInt
    );
    public static final ConfigurationKey<String> MAIL_USERNAME = new ConfigurationKey<>(
            "mail.username",
            "username",
            "The username used to login to the SMTP server.",
            ConfigurateHelper::getString
    );
    public static final ConfigurationKey<String> MAIL_PASSWORD = new ConfigurationKey<>(
            "mail.password",
            "password",
            "The password used to login to the SMTP server. We highly recommend you use a unique one-time password for this.",
            ConfigurateHelper::getString
    );
    public static final ConfigurationKey<String> MAIL_SENDER = new ConfigurationKey<>(
            "mail.sender",
            "LibreLogin Network",
            "The sender of the email.",
            ConfigurateHelper::getString
    );
    public static final ConfigurationKey<String> MAIL_EMAIL = new ConfigurationKey<>(
            "mail.email",
            "email@something.com",
            "The email to use as a sender in the From field.",
            ConfigurateHelper::getString
    );

    private static final ConfigurationKey<?> MAIL = ConfigurationKey.getComment(
            "mail",
            """
                    This section is used for configuring the email password recovery feature.
                    """
    );
    public static final ConfigurationKey<Boolean> ALLOW_PROXY_CONNECTIONS = new ConfigurationKey<>(
            "allow-proxy-connections",
            true,
            """
                    !!!THIS ONLY AFFECTS PAPER!!!
                    Verifies whether the IP the players had used when authenticating to Mojang matches the IP they are connecting from. Disabling this may break LibreLogin if the server is running under a reverse proxy/VPN.
                    """,
            ConfigurateHelper::getBoolean
    );
    public static final ConfigurationKey<String> LIMBO_PORT_RANGE = new ConfigurationKey<>(
            "limbo-port-range",
            "30000-40000",
            "!!THIS OPTION IS IRRELEVANT WHEN USING PAPER!! Defines port(s) that limbo server can be bounded to.",
            ConfigurateHelper::getString
    );
}
