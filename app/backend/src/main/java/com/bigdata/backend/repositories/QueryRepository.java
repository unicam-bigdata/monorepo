package com.bigdata.backend.repositories;


import com.bigdata.backend.dto.*;
import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.utils.Filter;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class QueryRepository {
    private final Neo4JDriver neo4JDriver;

    public QueryRepository(Neo4JDriver neo4JDriver) {
        this.neo4JDriver = neo4JDriver;
    }


    public List<String> getMetaData() {
        List<String> labels = new ArrayList<>();
        try (var session = this.neo4JDriver.getDriver().session()) {
            // Get all labels
            var query = new Query("MATCH (n) RETURN distinct labels(n) AS labels");
            var result = session.run(query);


            while (result.hasNext()) {
                Record record = result.next();
                List<Object> label = record.get("labels").asList();
                if (!label.isEmpty()) {
                    labels.add(label.get(0).toString());
                }

            }

        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }

        return labels;
    }

    public List<Map<String, Object>> getNodes(NodesRequest nodesRequest) {
        List<Map<String, Object>> items = new ArrayList<>();
        try (var session = this.neo4JDriver.getDriver().session()) {
            String queryString = CypherQueryBuilder.generateNodesQuery(nodesRequest);
            var query = new Query(queryString);
            var result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                items.add(record.get("n").asMap());
            }

        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }

        return items;
    }

    public NodesCountResponse getNodesCount(NodesCountRequest nodesCountRequest) {
        NodesCountResponse nodesCountResponse = new NodesCountResponse(0);
        try (var session = this.neo4JDriver.getDriver().session()) {
            String queryString = CypherQueryBuilder.generateNodesCountQuery(nodesCountRequest);
            var query = new Query(queryString);
            var result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                nodesCountResponse.setCount(record.get("count").asInt());
            }

        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }

        return nodesCountResponse;
    }

    public List<RelatedNodesResponse> getRelatedNodes(RelatedNodesRequest relatedNodesRequest) {
        List<RelatedNodesResponse> items = new ArrayList<>();
        try (var session = this.neo4JDriver.getDriver().session()) {
            String queryString = CypherQueryBuilder.generateRelatedNodesQuery(relatedNodesRequest);
            System.out.println(queryString);
            var query = new Query(queryString);
            var result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next();
                RelatedNodesResponse relatedNodesResponse = new RelatedNodesResponse();
                NodeResponse nodeResponse = new NodeResponse();
                RelationshipResponse relationshipResponse = new RelationshipResponse();
                String nodeLabel;
                String relationshipLabel;

                nodeLabel = record.get("relatedNode").asNode().labels().iterator().next();
                nodeResponse.setName(nodeLabel);
                nodeResponse.setProperties(record.get("relatedNode").asMap());

                relationshipLabel = record.get("r").asRelationship().type();
                relationshipResponse.setName(relationshipLabel);
                relationshipResponse.setProperties(record.get("r").asMap());

                relatedNodesResponse.setNode(nodeResponse);
                relatedNodesResponse.setRelationship(relationshipResponse);

                items.add(relatedNodesResponse);
            }

        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }

        return items;
    }


}
