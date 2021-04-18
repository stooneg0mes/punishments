package net.stonegomes.punishments.module;

import lombok.Getter;
import net.stonegomes.commons.module.Module;
import net.stonegomes.commons.storage.SqlCredentials;
import net.stonegomes.commons.storage.SqlStorage;
import net.stonegomes.commons.storage.impl.MySQLStorage;
import net.stonegomes.commons.storage.impl.SQLiteStorage;
import net.stonegomes.commons.storage.query.Query;
import net.stonegomes.punishments.PunishmentsPlugin;
import org.bukkit.configuration.ConfigurationSection;

public class StorageModule extends Module {

    @Getter
    private static SqlStorage sqlStorage;

    private final PunishmentsPlugin punishmentsPlugin = PunishmentsPlugin.getInstance();

    @Override
    public void handleEnable() {
        ConfigurationSection storageSection = punishmentsPlugin.getConfig().getConfigurationSection("storage");

        String storageType = storageSection.getString("type");
        if (storageType == null) {
            punishmentsPlugin.getServer().getPluginManager().disablePlugin(punishmentsPlugin);
            return;
        }

        switch (storageType.toUpperCase()) {
            case "MYSQL": {
                sqlStorage = new MySQLStorage();
                break;
            }
            case "SQLITE": {
                sqlStorage = new SQLiteStorage();
                break;
            }
            default: {
                punishmentsPlugin.getServer().getPluginManager().disablePlugin(punishmentsPlugin);
                return;
            }
        }

        ConfigurationSection credentialsSection = storageSection.getConfigurationSection("credentials");
        if (credentialsSection == null) {
            punishmentsPlugin.getServer().getPluginManager().disablePlugin(punishmentsPlugin);
            return;
        }

        SqlCredentials sqlCredentials = SqlCredentials.builder()
            .database(credentialsSection.getString("database"))
            .password(credentialsSection.getString("password"))
            .host(credentialsSection.getString("host"))
            .user(credentialsSection.getString("user"))
            .fileName("storage")
            .parent(punishmentsPlugin.getDataFolder())
            .tableCreation((storage) -> {
                Query tableQuery = Query.builder()
                    .query("CREATE TABLE IF NOT EXISTS punishmentUsers (bypass BOOLEAN NOT NULL," +
                        "uniqueId VARCHAR(36) PRIMARY KEY NOT NULL," +
                        "punishments TEXT NOT NULL)")
                    .build();

                storage.executeQuery(tableQuery);
            })
            .build();

        if (!sqlStorage.startConnection(sqlCredentials))
            punishmentsPlugin.getServer().getPluginManager().disablePlugin(punishmentsPlugin);
    }

}
