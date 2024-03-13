package com.bigdata.backend.repositories;


import com.bigdata.backend.dto.*;

import java.util.List;
import java.util.Map;

public interface QueryRepository {

    List<Map<String, Object>> getNodes(NodesRequest nodesRequest);

    NodesCountResponse getNodesCount(NodesCountRequest nodesCountRequest);

    List<RelatedNodesResponse> getRelatedNodes(RelatedNodesRequest relatedNodesRequest);

    List<Map<String, Object>> getIdentifiers();

    List<String> getNodeProperties(String label);
}
