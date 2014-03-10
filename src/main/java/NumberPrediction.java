/**
 * Created by amid on 10.03.14.
 */

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.temporal.TemporalDataDescription;
import org.encog.ml.data.temporal.TemporalMLDataSet;
import org.encog.ml.data.temporal.TemporalPoint;

import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.util.simple.EncogUtility;

public class NumberPrediction {
    public static void main(String[] args)
    {
        //set the amount of input numbers and numbers to predict(output)
        final int INPUT_SIZE = 2;
        final int OUTPUT_SIZE = 3;

        //create the set of data
        TemporalMLDataSet temporal = new TemporalMLDataSet(INPUT_SIZE,OUTPUT_SIZE);
        temporal.addDescription(new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true));

        //set the number trainings
        for(int i=0;i<10;i++)
        {
            TemporalPoint tp = temporal.createPoint(i);
            tp.setData(0, i);
        }

        temporal.generate();

        //create the network
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,INPUT_SIZE));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,30));
        network.addLayer(new BasicLayer(new ActivationLinear(),true,OUTPUT_SIZE));
        network.getStructure().finalizeStructure();
        network.reset();

        EncogUtility.trainToError(network, temporal, 0.002);
        double[] array = {1,2};
        MLData input = new BasicMLData(array);
        MLData output = network.compute(input);
        EncogUtility.evaluate(network, temporal);
        System.out.println(output.toString());
    }
}
