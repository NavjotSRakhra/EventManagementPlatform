/*
 * Copyright (c) 2024 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.bean;

import io.github.NavjotSRakhra.neuralNetwork.NeuralNetwork;
import io.github.NavjotSRakhra.neuralNetwork.activation.Activation;
import io.github.NavjotSRakhra.neuralNetwork.activation.ReLU;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NeuralNetworkConfig {
    @Value("${io.github.navjotsrakhra.nnn-location}")
    private String neuralNetworkTrainedCheckpointPath;

    @Bean
    public NeuralNetwork configureNeuralNetwork(){
        if (neuralNetworkTrainedCheckpointPath != null){
            return NeuralNetwork.readFrom(neuralNetworkTrainedCheckpointPath);
        }else {
            NeuralNetwork neuralNetwork = new NeuralNetwork(new ReLU(), 1, 1,20,20);
            return neuralNetwork;
        }
    }
}
