package org.sandbag.model.relationships;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by root on 16/02/16.
 */
public interface ParentCompanyModel extends RelationshipType{
    String LABEL = "PARENT_COMPANY";

    String name = "name";

}