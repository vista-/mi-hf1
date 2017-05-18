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

/**
 * Created by vista on 10/11/16.
 */
public class NeuronLayer {
    @Override
    public String toString() {
        String output = "";
        for(int i = 0; i < neurons.size(); ++i) {
            output += neurons.get(i).toString();
            output += '\n';
        }

        return output;
    }

    private ArrayList<Neuron> neurons = new ArrayList<Neuron>();

    public NeuronLayer(int NeuronCount, int prevLayerCount) {
        for(int i = 0; i < NeuronCount; ++i)
        {
            this.add(new Neuron(prevLayerCount));
        }
    }


    public void add(Neuron neuron) {
        neurons.add(neuron);
    }

    public int count() {
        return neurons.size();
    }

    public Neuron get(int i) {
        return neurons.get(i);
    }
}
