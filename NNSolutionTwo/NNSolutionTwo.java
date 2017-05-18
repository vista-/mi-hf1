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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vista on 10/12/16.
 */
public class NNSolutionTwo {
    public static List<NeuronLayer> layers = new ArrayList<NeuronLayer>();

    public static List<Integer> architecture = new ArrayList<Integer>();

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = {};
        try {
            line = br.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String token : line) {
            architecture.add(Integer.parseInt(token));
        }

        NeuronLayer inputLayer = new NeuronLayer(architecture.get(0));
        List<Integer> hiddenArch = architecture.subList(1, architecture.size()-1);
        NeuronLayer prevLayer = inputLayer;
        int prevLayerCount = architecture.get(0);
        for (Integer layerCount : hiddenArch) {
            NeuronLayer layer = new NeuronLayer(layerCount, prevLayerCount, prevLayer, NeuronType.RELU);
            layers.add(layer);
            prevLayerCount = layerCount;
            prevLayer = layer;
        }
        NeuronLayer outputLayer = new NeuronLayer((Integer)architecture.get(architecture.size()-1), prevLayerCount, prevLayer, NeuronType.LINEAR);
        layers.add(outputLayer);


//        for(int i = 0; i < architecture.size()-1; ++i) {
//            System.out.print(architecture.get(i) + ",");
//        }
//        System.out.println(architecture.get(architecture.size()-1));

        for(NeuronLayer layer : layers)
        {
            for(int i = 0; i < layer.count(); ++i)
            {
                try {
                    layer.get(i).setWeights(br.readLine().split(","));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int inputCount = 0;
        try {
            inputCount = Integer.parseInt(br.readLine());
            System.out.println(inputCount);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < inputCount; ++i) {
            String[] input = {};
            try {
                input = br.readLine().split(",");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            for(int j = 0; j < inputLayer.count(); ++j){
                inputLayer.get(j).setInputValue(Double.parseDouble(input[j]));
            }
            for(int j = 0; j < outputLayer.count()-1; ++j) {
                System.out.print(outputLayer.get(j).activate() + ",");
            }
            System.out.println(outputLayer.get(outputLayer.count()-1).activate());
        }
    }
}
