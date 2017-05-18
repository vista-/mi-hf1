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
import java.util.jar.Pack200;

/**
 * Created by vista on 10/11/16.
 */
public class NeuronLayer {

    // Neurons in a layer
    private List<Neuron> neurons = new ArrayList<Neuron>();
    public Neuron get(int i) {
        return neurons.get(i);
    }
    public void add(Neuron neuron) {
        neurons.add(neuron);
    }
    public int count() {
        return neurons.size();
    }

    // Hidden layer ctor
    public NeuronLayer(int NeuronCount, int prevLayerCount, NeuronLayer prevLayer, NeuronType type) {
        for (int i = 0; i < NeuronCount; ++i) {
            this.add(new Neuron(prevLayerCount, prevLayer, type, this));
        }
    }

    // Input layer ctor
    public NeuronLayer(Integer NeuronCount) {
        for (int i = 0; i < NeuronCount; ++i) {
            this.add(new Neuron());
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < neurons.size(); ++i) {
            output += neurons.get(i).toString();
            output += '\n';
        }
        return output;
    }

    public void calculateShit(NeuronLayer nextLayer) {
        for (int i = 0; i < neurons.size(); ++i) {
            if (nextLayer != null) {
                neurons.get(i).calculateDelta(nextLayer, i);
            } else {
                neurons.get(i).calculateDelta(null, -1);
            }
        }
    }

    public void calculateShit_NN3(NeuronLayer nextLayer) {
        for (int i = 0; i < neurons.size(); ++i) {
            if (nextLayer != null) {
                neurons.get(i).calculateDelta_NN3(nextLayer, i);
            } else {
                neurons.get(i).calculateDelta_NN3(null, -1);
            }
        }
    }

    public String getFormattedLayerDerivates() {
        String output = "";
        for (int i = 0; i < neurons.size(); ++i) {
            output += neurons.get(i).getFormattedDerivatives();
            output += '\n';
        }
        return output;
    }

    public void applyErrorCorrection(double bravery) {
        for (Neuron n : neurons) {
            n.applyErrorCorrection(bravery);
        }

    }
}
