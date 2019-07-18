
package handson.impl;

import java.io.IOException;
import java.util.Properties;

import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereAsyncHttpClientFactory;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.http.HttpClient;

public class ClientService {
    /**
     * Creates a blocking sphere client
     * @return Sphere client
     * @throws IOException
     */
    public static SphereClient createSphereClient() throws IOException {
        final SphereClientConfig clientConfig = loadCTPClientConfig();
        
		
		/*
		 * final SphereClientFactory factory = SphereClientFactory.of(); SphereClient
		 * client = factory.createClient(clientConfig.getProjectKey(),
		 * clientConfig.getClientId(), clientConfig.getClientSecret());
		 */
		   
        
      

            final HttpClient httpClient = new SphereAsyncHttpClientFactory().getClient();
            final SphereAccessTokenSupplier sphereAccessTokenSupplier = SphereAccessTokenSupplier
                .ofAutoRefresh(clientConfig, httpClient, true);

            return SphereClient.of(clientConfig, httpClient, sphereAccessTokenSupplier); 
     

        
        
        
        //
       // final SphereClient client = SphereClientFactory.of().createClient(clientConfig);
        //TODO 1.3 Create the client
       
    }

    /**
     * Sets a sphere client configuration
     * @return sphere client configuration
     * @throws IOException
     */
    private static SphereClientConfig loadCTPClientConfig() throws IOException {
        final Properties prop = new Properties();
        prop.load(ClientService.class.getResourceAsStream("/dev.properties"));
       // SphereClientConfig config = SphereClientConfig.of(prop.getProperty("projectKey"), prop.getProperty("clientId"), prop.getProperty("clientSecret"), prop.getProperty("apiUrl"), prop.getProperty("authUrl"));
        SphereClientConfig config = SphereClientConfig.ofProperties(prop, "ctp.");
        
        // String projectkey=prop.getProperty("projectKey");
        //TODO 1.2 Create the configuration for the sphere client
        return config;
    }
}
