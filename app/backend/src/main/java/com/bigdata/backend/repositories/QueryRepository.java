package com.bigdata.backend.repositories;


import com.bigdata.backend.dto.*;

import java.util.List;
import java.util.Map;

public interface QueryRepository {

    public List<String> getMetaData();

    public List<Map<String, Object>> getNodes(NodesRequest nodesRequest);

    public NodesCountResponse getNodesCount(NodesCountRequest nodesCountRequest);

    public List<RelatedNodesResponse> getRelatedNodes(RelatedNodesRequest relatedNodesRequest);

    public List<Map<String, Object>> getIdentifiers();


}
