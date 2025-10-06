package kr.hanjari.backend.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    public UserDetails loadUserByClubIdAndRole(Long clubId, String role) {
        return new CustomUserDetails(clubId, role);
    }

}
