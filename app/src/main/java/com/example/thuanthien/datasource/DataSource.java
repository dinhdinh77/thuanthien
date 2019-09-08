package com.example.thuanthien.datasource;

import com.example.thuanthien.api.APIClient;
import com.example.thuanthien.api.APIInterface;

public class DataSource {
    private static APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public APIInterface getApiInterface() {
        if (apiInterface == null)
            apiInterface = APIClient.getClient().create(APIInterface.class);
        return apiInterface;
    }
}
