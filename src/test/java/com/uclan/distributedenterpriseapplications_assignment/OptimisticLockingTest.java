package com.uclan.distributedenterpriseapplications_assignment;

import com.uclan.domain.Tutor;
import com.uclan.repository.TutorRepository;
import com.uclan.service.TutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OptimisticLockingTest {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorRepository tutorRepository;

    private final List<Integer> tutorsAmount = Arrays.asList(10, 5);

    @Test
    void shouldIncrementItemAmount_withoutConcurrency() {
        // given
        final Tutor tutor = tutorRepository.save(new Tutor("spock", "spock@enterprise.com", "spock"));
        assertEquals(0, tutor.getVersion());

        tutor.setEmail("spock@starfleet.com");
        tutorService.update(tutor.getId(), tutor);

        // then
        final Tutor tutor2 = tutorRepository.findById(tutor.getId()).get();

        assertAll(
                () -> assertEquals(1, tutor2.getVersion()),
                () -> assertEquals("spock@starfleet.com", tutor2.getEmail())
        );
    }
}
