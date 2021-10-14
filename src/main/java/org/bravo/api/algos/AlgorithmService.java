package org.bravo.api.algos;

import org.bravo.api.entity.Algorithm;
import org.bravo.api.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AlgorithmService {


    private final AlgorithmRepository algorithmRepository;

    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public Algorithm getAlgoBy(String id){

        return algorithmRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("algo " + id + " not found"));
    }

    public Algorithm findAlgorithmById(String algorithmId){
        return algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new NotFoundException("Algorithm " + algorithmId + " wasn' found!"));
    }

    public Set<Algorithm> findAllAlgorithms(){

        return algorithmRepository.findAll();
    }

}
