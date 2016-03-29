package org.sandbag.model.relationships.installations;

import org.neo4j.graphdb.Relationship;
import org.sandbag.model.nodes.Installation;
import org.sandbag.model.nodes.Period;

/**
 * Created by root on 16/03/16.
 */
public class SurrenderedUnits implements SurrenderedUnitsModel {

    protected Relationship relationship;

    public SurrenderedUnits(Relationship relationship){
        this.relationship = relationship;
    }

    @Override
    public String name() {
        return LABEL;
    }

    public Installation getInstallation(){
        return new Installation(relationship.getStartNode());
    }

    public Period getPeriod(){
        return new Period(relationship.getEndNode());
    }

    @Override
    public double getValue() {
        return Double.parseDouble(String.valueOf(relationship.getProperty(SurrenderedUnitsModel.value)));
    }

    @Override
    public void setValue(double value) {
        relationship.setProperty(SurrenderedUnitsModel.value, value);
    }
}