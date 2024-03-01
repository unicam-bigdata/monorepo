package com.bigdata.backend.services;

import com.bigdata.backend.dto.*;
import com.bigdata.backend.repositories.QueryRepository;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    private final QueryRepository queryRepository;

    @Autowired
    public QueryService(QueryRepository queryRepository){
        this.queryRepository = queryRepository;
    }
    public MetaDataResponse getMetaData(){
        return new MetaDataResponse(this.queryRepository.getMetaData());
    }

    public List<Map<String,Object>> getNodes(NodesRequest nodesRequest) {
        return this.queryRepository.getNodes(nodesRequest);
    }

    public NodesCountResponse getNodesCount(NodesCountRequest nodesCountRequest) {
        return this.queryRepository.getNodesCount(nodesCountRequest);
    }

    public List<RelatedNodesResponse> getRelatedNodes(RelatedNodesRequest relatedNodesRequest) {
        return this.queryRepository.getRelatedNodes(relatedNodesRequest);
    }
}
