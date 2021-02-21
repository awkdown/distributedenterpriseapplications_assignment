package service;

import com.sun.istack.NotNull;
import domain.Tutor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.TutorRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class TutorService {
    private final TutorRepository tutorRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Tutor> getTutors() {
        logger.debug("Retrieving {} tutor from repository", tutorRepository.count());
        return tutorRepository.findAll();
    }

    @Transactional()
    public Tutor createTutor(@NotNull @Valid Tutor tutor) {
        return tutorRepository.save(tutor);
    }
}
