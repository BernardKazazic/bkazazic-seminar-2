package fer.seminar2.application;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;

@RequiredArgsConstructor
public class PredictionModel {
    private static final int BATCH_SIZE = 1;
    private static final int TIME_STEPS = 24;
    private static final int INPUT_DIM = 2;
    @NonNull
    private MultiLayerNetwork model;
    @NonNull
    private NormalizerMinMaxScaler minMaxNormalizer;

    public void predictPowerOutput24h(double[][] inputData) {
        INDArray modelInput = Nd4j.create(inputData).reshape(BATCH_SIZE, TIME_STEPS, INPUT_DIM);
        minMaxNormalizer.transform(modelInput);

        INDArray modelOutput = model.output(modelInput);
        System.out.println(modelOutput);
    }
}
