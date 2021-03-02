package com.uclan.security;

import com.uclan.domain.Tutor;
import com.uclan.repository.TutorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service("detailService")
public class DetailService implements UserDetailsService {

    private final TutorRepository tutorRepository;

    public DetailService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Tutor> tutorByMail = tutorRepository.findByEmail(s);
        if (tutorByMail.isPresent()) {
            return new UserDetailsTutor(tutorByMail.get());
        }
        throw new UsernameNotFoundException("No user found by mail");
    }
}