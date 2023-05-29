package fer.seminar2.infrastructure.configuration;

import fer.seminar2.application.PredictionModel;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class PredictionConfiguration {
    @Value("${weather-data-collector.url}")
    private String weatherDataCollectorUrl;
    @Value("${model.path}")
    private String modelPath;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MultiLayerNetwork getMultiLayerNetwork() throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        return KerasModelImport.importKerasSequentialModelAndWeights(modelPath);
    }

    @Bean
    public NormalizerMinMaxScaler getNormalizerMinMaxScaler() {
        NormalizerMinMaxScaler minMaxScaler = new NormalizerMinMaxScaler();
        double[][] x = {{0.0, 0.0}, {86400000.0, 35.0}};
        double[] y = {0.0, 3500.0};
        INDArray x_data = Nd4j.create(x);
        INDArray y_data = Nd4j.create(y);
        DataSet dataSet = new DataSet(x_data, y_data);
        minMaxScaler.fit(dataSet);
        return minMaxScaler;
    }

    @Bean
    public PredictionModel getPredictionModel() throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        return new PredictionModel(getMultiLayerNetwork(), getNormalizerMinMaxScaler());
    }
}
