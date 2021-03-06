package com.uclan.service;

import com.sun.istack.NotNull;
import com.uclan.domain.Tutor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.uclan.repository.TutorRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;



@Service
@Transactional
public class TutorService {
    private final TutorRepository tutorRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder encoder;

    public TutorService(TutorRepository tutorRepository, PasswordEncoder encoder) {
        this.tutorRepository = tutorRepository;
        this.encoder = encoder;
    }

    public List<Tutor> getTutors() {
        logger.debug("Retrieving {} tutor from com.uclan.repository", tutorRepository.count());
        return tutorRepository.findAll();
    }

    public Tutor createTutor(@NotNull @Valid Tutor tutor) {
        tutor.setPassword(encoder.encode(tutor.getPassword()));
        return tutorRepository.save(tutor);
    }


    public void delete(long id) {
        tutorRepository.deleteById(id);
    }

    public void update(@NotNull @Valid Tutor tutor) {
        Tutor tutorToUpdate = tutorRepository.findByEmail(tutor.getEmail()).orElseThrow();
        tutorToUpdate.setEmail(tutor.getEmail());
        tutorToUpdate.setTutorname(tutor.getTutorname());
        tutorToUpdate.setPassword(tutor.getPassword());
    }

    public Tutor getTutorById(String email) {
        Tutor tutor = null;
        try {
            tutor =  tutorRepository.findByEmail(email).orElseThrow();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return tutor;
    }
}