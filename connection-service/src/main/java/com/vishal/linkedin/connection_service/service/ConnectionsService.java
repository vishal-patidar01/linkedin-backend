package com.vishal.linkedin.connection_service.service;

import com.vishal.linkedin.connection_service.entity.Person;
import com.vishal.linkedin.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId) {
        log.info("Getting first degree connections for user with id: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }



    public List<Person> getAllConnections() {
        log.info("Getting All Connection");
        return personRepository.getAllConnections();
    }
}
