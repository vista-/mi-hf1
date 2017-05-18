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

package NNSolutionFour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vista on 10/11/16.
 */
public class Neuron {

    private NeuronType type;

    // Input value (x_{i})
    private double inputValue;
    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    // Connections => weights (w_{i,j})
    private List<Connection> connections = new ArrayList<Connection>();
    public List<Connection> getConnections() {
        return connections;
    }
    public void setWeights(String[] weightStrings) {
        for(int i = 0; i < connections.size(); ++i) {
            connections.get(i).setWeight(Double.parseDouble(weightStrings[i]));
        }
        this.bias = Double.parseDouble(weightStrings[weightStrings.length-1]);
    }

    private NeuronLayer layer;
    public NeuronLayer getLayer() {
        return layer;
    }

    // Same bias for the entire layer (b_{i})
    private double bias;

    // Reference to previous layer (l-1)
    private NeuronLayer previousLayer;

    // Derivate of activation function (f')
    private double derivate;
    public void setDerivate(double sum) {
        switch(type) {
            case INPUT:
                derivate = 0;
                break;
            case RELU:
                derivate = sum > 0.0 ? 1.0 : 0.0;
                break;
            case LINEAR:
                derivate = 1;
                break;
        }
    }

    // Delta of neuron
    private double delta;
    public double getDelta() { return delta; }

    private double biasDelta;
    public double getBiasDelta() { return biasDelta; }

    // Ctor for initial layer neurons
    public Neuron() {
        type = NeuronType.INPUT;
    }

    // Ctor for hidden layer neurons
    public Neuron(int inputCount, NeuronLayer previousLayer, NeuronType type, NeuronLayer layer) {
        this.previousLayer = previousLayer;
        this.layer=layer;
        this.type = type;
        for(int i = 0; i < inputCount; ++i) {
            connections.add(new Connection());
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(int i = 0; i < connections.size(); ++i) {
            output += connections.get(i).getWeight();
            output += ',';
        }
        output += bias;
        return output;
    }

    public String getFormattedDerivatives() {
        String output = "";
        for(int i = 0; i < connections.size(); ++i) {
            output += String.format("%f,", connections.get(i).getPartialDerivate());
        }
        output += String.format("%f", biasDelta);
        return output;
    }

    // Activation
    public double activate() {
        double output = 0.0;
        switch(type) {
            case INPUT:
                output = inputValue;
                break;
            case RELU:
                output = Math.max(this.sum(), 0.0);
                break;
            case LINEAR:
                output = this.sum();
        }
        return output;
    }

    public void calculateDelta(NeuronLayer nextLayer, int index) {
        if (index == -1)
            delta = error;
        else {
            delta = 0;
            for (int i = 0; i < nextLayer.count(); ++i) {
                delta += nextLayer.get(i).getConnections().get(index).getWeight() * nextLayer.get(i).getDelta();
                //System.out.println(nextLayer.get(i).getConnections().get(index).getWeight() + " * " + nextLayer.get(i).getDelta() + "");
            }
            delta *= derivate;
        }

        for (Connection conn : connections) {
            conn.setPartialDerivate(conn.getLastInput() * delta * -2);
        }
    }

    public void calculateDelta_NN3(NeuronLayer nextLayer, int index) {
        if (index == -1)
            delta = 1;
        else {
            for (int i = 0; i < nextLayer.count(); ++i) {
                delta += nextLayer.get(i).getConnections().get(index).getWeight() * nextLayer.get(i).getDelta();
                //            System.out.println(nextLayer.get(i).getConnections().get(index).getWeight() + " * " + nextLayer.get(i).getDelta() + "");
            }
            delta *= derivate;
        }
        //        System.out.println();
        //        System.out.println("=====");
        biasDelta = delta;
        //System.out.print(delta + " D| ");
        for (Connection conn : connections) {
            conn.setPartialDerivate(conn.getLastInput() * delta);
        }
    }

    //public void errorPropagate(Neuron caller, int index) {
    ////System.out.print(conn.getLastInput() + " LI| ");
    //    for(int i = 0; i < connections.size(); ++i) {
    //        System.out.println();
    //        System.out.println("=======");
    //        previousLayer.get(i).calculateDelta(this, i);
    //    }
    //
    // for(int i = 0; i < connections.size(); ++i) {
            //System.out.println();
            //System.out.println("=======");
    //        previousLayer.get(i).errorPropagate(this, i);
    //    }

    //}

    // Calculate output (s_{i})
    private double sum() {
        double sum = bias;
        for(int i = 0; i < connections.size(); ++i)
        {
            double input = previousLayer.get(i).activate();
            connections.get(i).setLastInput(input);
            sum += input * connections.get(i).getWeight();
        }
        this.setDerivate(sum);

        return sum;
    }

    private double error;
    public void setError(double error) {
        this.error = error;
    }

    public void applyErrorCorrection(double bravery) {
        biasDelta = delta * -2;
        bias = bias - (delta * -2 * bravery);
        for (Connection conn : connections) {
            conn.setWeight(conn.getWeight() - (bravery*conn.getPartialDerivate()));
        }
    }
}
