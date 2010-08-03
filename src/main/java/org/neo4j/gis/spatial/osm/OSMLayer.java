package org.neo4j.gis.spatial.osm;

import org.neo4j.gis.spatial.DynamicLayer;
import org.neo4j.gis.spatial.NullListener;
import org.neo4j.gis.spatial.SpatialDataset;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * Instances of this class represent individual layers or GeoResources of the OSM Dataset.
 * 
 * @author craig
 * @since 1.0.0
 */
public class OSMLayer extends DynamicLayer {
    private OSMDataset osmDataset;

    public SpatialDataset getDataset() {
        return osmDataset;
    }

    public OSMDataset getDataset(long datasetId) {
        if(osmDataset==null){
            osmDataset = new OSMDataset(this.getSpatialDatabase(),this, layerNode, datasetId);
        }
        return osmDataset;
    }

    protected void clear() {
        index.clear(new NullListener());
    }

    public void addWay(Node way) {
        Relationship geomRel = way.getSingleRelationship(OSMRelation.GEOM, Direction.OUTGOING);
        if (geomRel != null) {
            Node geomNode = geomRel.getEndNode();
            add(geomNode);
        }
    }

}
