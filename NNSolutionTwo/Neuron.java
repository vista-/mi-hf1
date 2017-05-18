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

package NNSolutionTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vista on 10/11/16.
 */
public class Neuron {

    private NeuronType type;

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public void setWeights(String[] weightStrings) {
        for(int i = 0; i < connections.size(); ++i) {
            connections.get(i).setWeight(Double.parseDouble(weightStrings[i]));
        }
        this.bias = Double.parseDouble(weightStrings[weightStrings.length-1]);
    }

    private double inputValue;

    private List<Connection> connections = new ArrayList<Connection>();
    private double bias;
    private NeuronLayer previousLayer;

    // Ctor for initial layer neurons
    public Neuron() {
        type = NeuronType.INPUT;
    }

    public double activate() {
        switch(type) {
            case INPUT:
                return inputValue;
            case RELU:
                return Math.max(this.sum(), 0.0);
            case LINEAR:
                return this.sum();
        }
        return 0.0;
    }

    private double sum() {
        double sum = bias;
        for(int i = 0; i < connections.size(); ++i)
        {
            sum += connections.get(i).getWeight() * previousLayer.get(i).activate();
        }

        return sum;
    }

    public double getBias() {
        return bias;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Neuron(int inputCount, NeuronLayer previousLayer, NeuronType type) {
        this.previousLayer = previousLayer;
        this.type = type;
        for(int i = 0; i < inputCount; ++i) {
            connections.add(new Connection());
        }
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
