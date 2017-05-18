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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
* Created by vista on 10/17/16.
*/

public class NNSolutionFour {


    public static List<NeuronLayer> layers = new ArrayList<NeuronLayer>();

    public static List<Integer> architecture = new ArrayList<Integer>();

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = {};
        String[] learningLine = {};
        try {
            learningLine = br.readLine().split(",");
            line = br.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int epoch = Integer.parseInt(learningLine[0]);
        double braveness = Double.parseDouble(learningLine[1]);
        double learningRate = Double.parseDouble(learningLine[2]);
        for (String token : line) {
            architecture.add(Integer.parseInt(token));
        }

        NeuronLayer inputLayer = new NeuronLayer(architecture.get(0));
        List<Integer> hiddenArch = architecture.subList(1, architecture.size() - 1);
        NeuronLayer prevLayer = inputLayer;
        int prevLayerCount = architecture.get(0);
        for (Integer layerCount : hiddenArch) {
            NeuronLayer layer = new NeuronLayer(layerCount, prevLayerCount, prevLayer, NeuronType.RELU);
            layers.add(layer);
            prevLayerCount = layerCount;
            prevLayer = layer;
        }
        NeuronLayer outputLayer = new NeuronLayer((Integer) architecture.get(architecture.size() - 1), prevLayerCount, prevLayer, NeuronType.LINEAR);
        layers.add(outputLayer);

        for (NeuronLayer layer : layers) {
            for (int i = 0; i < layer.count(); ++i) {
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
            //System.out.println(inputCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String[]> inputList = new ArrayList<>();
        try {
            for (int i = 0; i < inputCount; ++i) {
                inputList.add(br.readLine().split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int epochCount = 0; epochCount < epoch; ++epochCount) {
            int verifycount = 0;
            double totalError = 0;
            for (int i = 0; i < inputCount; ++i) {
                if (i < Math.floor(inputCount * learningRate)) {
                    String[] input = inputList.get(i);
                    for (int j = 0; j < inputLayer.count(); ++j) {
                        inputLayer.get(j).setInputValue(Double.parseDouble(input[j]));
                    }
                    double systemOutput = 0;
                    for (int j = 0; j < outputLayer.count(); ++j) {
                        systemOutput = outputLayer.get(j).activate();
                        double desired = Double.parseDouble(input[(inputLayer.count()) + j]);
                        double error = desired - systemOutput;
                        outputLayer.get(j).setError(error);
                    }
                    //            for (int j = 0; j < outputLayer.count(); ++j) {
                    //                    outputLayer.get(j).errorPropagate(null, -1);
                    //            }
                    NeuronLayer nextLayer = outputLayer;
                    outputLayer.calculateShit(null);
                    for (int j = layers.size() - 2; j >= 0; --j) {
                        layers.get(j).calculateShit(nextLayer);
                        nextLayer = layers.get(j);
                    }

                    for (NeuronLayer n : layers) {
                        n.applyErrorCorrection(braveness);
                    }
                } else {
                    ++verifycount;
                    String[] input = inputList.get(i);
                    for (int j = 0; j < inputLayer.count(); ++j) {
                        inputLayer.get(j).setInputValue(Double.parseDouble(input[j]));
                    }
                    double systemOutput = 0;
                    for (int j = 0; j < outputLayer.count(); ++j) {
                        double desired = Double.parseDouble(input[(inputLayer.count()) + j]);
                        systemOutput = outputLayer.get(j).activate();
                        totalError += Math.pow(desired - systemOutput, 2);
                    }
                }
            }

            System.out.println(totalError / ((double)verifycount * (double)outputLayer.count()));
        }

        for (int i = 0; i < architecture.size() - 1; ++i) {
            System.out.print(architecture.get(i) + ",");
        }
        System.out.println(architecture.get(architecture.size() - 1));

        for (NeuronLayer n : layers) {
            System.out.print(n.toString());
        }
    }
}
