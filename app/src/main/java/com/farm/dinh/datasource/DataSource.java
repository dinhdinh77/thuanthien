package com.farm.dinh.datasource;

import com.farm.dinh.api.APIClient;
import com.farm.dinh.api.APIInterface;

public class DataSource {
    private static APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public APIInterface getApiInterface() {
        if (apiInterface == null)
            apiInterface = APIClient.getClient().create(APIInterface.class);
        return apiInterface;
    }
}
