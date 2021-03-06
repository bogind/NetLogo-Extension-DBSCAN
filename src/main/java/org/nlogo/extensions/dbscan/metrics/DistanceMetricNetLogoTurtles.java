package org.nlogo.extensions.dbscan.metrics;

import org.christopherfrantz.dbscan.DBSCANClusteringException;
import org.christopherfrantz.dbscan.DistanceMetric;
import org.nlogo.agent.Turtle;
import org.nlogo.api.AgentException;

/**
 * Distance metric implementation for agent variables for agents of type turtle.
 * This extension is used for unit tests.
 *
 * @author <a href="mailto:cf@christopherfrantz.org>Christopher Frantz</a>
 *
 */
public class DistanceMetricNetLogoTurtles implements DistanceMetric<Turtle>{

    private String field = null;

    public DistanceMetricNetLogoTurtles(String field) {
        this.field = field;
    }

    @Override
    public double calculateDistance(Turtle val1, Turtle val2) throws DBSCANClusteringException {
        try {
            // Check on turtle level first ...
            Double val1Num = Double.parseDouble(val1.getTurtleOrLinkVariable(field).toString());
            Double val2Num = Double.parseDouble(val2.getTurtleOrLinkVariable(field).toString());
            return Math.abs(val1Num - val2Num);
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                // ... before looking at breed level
                Double val1Num = Double.parseDouble(val1.getBreedVariable(field).toString());
                Double val2Num = Double.parseDouble(val2.getBreedVariable(field).toString());
                return Math.abs(val1Num - val2Num);
            } catch (NumberFormatException | AgentException e1) {
                throw new DBSCANClusteringException(e1.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new DBSCANClusteringException(e.getMessage());
        }
    }

}
