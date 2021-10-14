package org.bravo.api.algos;

import org.bravo.api.entity.Algorithm;

import java.util.Optional;
import java.util.Set;

public interface AlgorithmRepository {

    Algorithm save(Algorithm algorithm);

    Set<Algorithm> findAll();

    Optional<Algorithm> findById(String id);

}
