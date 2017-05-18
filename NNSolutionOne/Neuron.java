/*
This file is part of BME Artificial Intelligence homework 1.

Foobar is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with BME Artificial Intelligence homework 1.
If not, see <http://www.gnu.org/licenses/>.
*/

package NNSolutionOne;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Created by vista on 10/11/16.
 */
public class Neuron {

    public double getBias() {
        return bias;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private double bias;

    public Neuron(int inputCount) {
        for(int i = 0; i < inputCount; ++i) {
            connections.add(new Connection());
        }
    }

    public Neuron(ArrayList<Connection> connections, double bias) {
        this.connections = connections;
        this.bias = bias;
    }

    @Override
    public String toString() {
        String output = "";
        for(int i = 0; i < connections.size(); ++i) {
            output += String.format("%f,", connections.get(i).getWeight());
        }
        output += String.format("%f", bias);
        return output;
    }
}
