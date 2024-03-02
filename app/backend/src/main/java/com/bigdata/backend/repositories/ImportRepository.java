package com.bigdata.backend.repositories;

import com.bigdata.backend.models.ImportConfig;

import java.util.List;

public interface ImportRepository {
    public void loadData(List<ImportConfig> importConfigs);

    public void clearDatabase();

}
