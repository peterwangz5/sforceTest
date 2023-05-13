package org.example;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class Main {
    static final String USERNAME = "peterzwang.deloitte@hotmail.com";
    static final String PASSWORD = "Gaimima@209";
    static final String SECURITY_TOKEN = "1LQTRF2nU4Pz0hyICbub1uyj6";
    static EnterpriseConnection connection;

    public static void main(String[] args) {

        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD+SECURITY_TOKEN);

        try {

            connection = Connector.newConnection(config);

            // display some current settings
            System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
            System.out.println("Service EndPoint: "+config.getServiceEndpoint());
            System.out.println("Username: "+config.getUsername());
            System.out.println("Access Token: "+config.getSessionId());

        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }
    }
}