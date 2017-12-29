package de.axelspringer.ideas.mate;

import org.apache.commons.lang3.StringUtils;

public class MateChallengeProperties {

    public static final MateChallengeProperties INSTANCE = new MateChallengeProperties();

    public String elasticMailApiKey;
    public String encryptionPassword;
    // de.axelspringer.ideas.mate.crypto.password:changeit


    private MateChallengeProperties() {
        this.elasticMailApiKey = getProperty("de.axelspringer.ideas.mate.elasticMailApiKey");
        this.encryptionPassword = getProperty("de.axelspringer.ideas.mate.crypto.password:changeit");

    }

    private String getProperty(String keyWithDefault) {
        String key = keyWithDefault.split(":")[0];
        String result = System.getProperty(key);
        if (StringUtils.isBlank(result)) {
            result = System.getenv(key);
        }
        if (StringUtils.isBlank(result)) {
            result = keyWithDefault.split(":")[1];
        }
        return result;
    }


}
