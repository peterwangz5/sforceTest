package org.example;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SForceUtil {

    private MetadataConnection metadataConnection;

    public static void main(String[] args) throws Exception {
/*
        "peterzwang.deloitte@hotmail.com", "Gaimima@209", "1LQTRF2nU4Pz0hyICbub1uyj6";
*/
        final String USERNAME = "chriszhu@deloitte.poc";
        final String PASSWORD = "@wsx3edc";
        final String SECURITY_TOKEN = "";
        final String URL = "https://login.salesforce.com/services/Soap/c/57.0";

        SForceUtil sample = new SForceUtil(USERNAME, PASSWORD+SECURITY_TOKEN, URL);
        sample.listMetadata();
    }

    public SForceUtil(final String username, final String password, final String loginUrl) throws ConnectionException {
        createMetadataConnection(username, password, loginUrl);
    }
    private void createMetadataConnection(
            final String username,
            final String password,
            final String loginUrl) throws ConnectionException {

        final ConnectorConfig loginConfig = new ConnectorConfig();
        loginConfig.setAuthEndpoint(loginUrl);
        loginConfig.setServiceEndpoint(loginUrl);
        loginConfig.setManualLogin(true);
        LoginResult loginResult = (new EnterpriseConnection(loginConfig)).login(
                username, password);

        final ConnectorConfig metadataConfig = new ConnectorConfig();
        metadataConfig.setServiceEndpoint(loginResult.getMetadataServerUrl());
        metadataConfig.setSessionId(loginResult.getSessionId());
        this.metadataConnection = new MetadataConnection(metadataConfig);
    }

    public void listMetadata() {
        try {
            ListMetadataQuery query = new ListMetadataQuery();
            query.setType("CustomObject");
//            query.setFolder(null);
            double asOfVersion = 57.0;
            // Assuming that the SOAP binding has already been established.
            FileProperties[] lmr = metadataConnection.listMetadata(
                    new ListMetadataQuery[] {query}, asOfVersion);
            if (lmr != null) {
                for (FileProperties n : lmr) {
                    System.out.println("Component fullName: " + n.getFullName());
                    System.out.println("Component type: " + n.getType());
                    System.out.println("Last Modified Date: " + n.getLastModifiedDate().getTime());
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }
}