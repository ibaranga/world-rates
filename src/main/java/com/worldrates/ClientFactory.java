package com.worldrates;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactory {
    public static Client defaultClient() {
        return ClientBuilder
                .newBuilder()
                .register(new CustomJacksonJsonProvider())
                .build();
    }
}
