package org.bravo.api.db;

import org.bravo.api.algos.AlgorithmRepository;
import org.bravo.api.entity.Algorithm;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@Scope("singleton")
public class InMemoryAlgorithmRepository implements AlgorithmRepository {


    private final Set<Algorithm> algorithms = Collections.synchronizedSet(new HashSet<>());

    public InMemoryAlgorithmRepository() {
        this.save(new Algorithm("workerA", "http://127.0.0.1", Short.valueOf("8180")));
        this.save(new Algorithm("workerB", "http://127.0.0.1", Short.valueOf("8181")));
    }

    @Override
    public Algorithm save(Algorithm algorithm) {
        algorithms.add(algorithm);
        return algorithm;
    }

    @Override
    public Set<Algorithm> findAll() {
        return Set.copyOf(algorithms);
    }

    @Override
    public Optional<Algorithm> findById(final String id) {

        synchronized (algorithms) {
            return algorithms.stream()
                    .filter(algorithm -> algorithm.getId().equals(id))
                    .findAny();
        }
    }
}
