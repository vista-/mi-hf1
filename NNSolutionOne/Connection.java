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

import java.util.Random;

/**
 * Created by vista on 10/12/16.
 */
public class Connection {
    public Double getWeight() {
        return weight;
    }

    private Double weight;

    public Connection(Double weight) {
        this.weight = weight;
    }

    public Connection() {

        Random rnd = new Random();
        this.weight = rnd.nextGaussian()*0.1;
    }
}
