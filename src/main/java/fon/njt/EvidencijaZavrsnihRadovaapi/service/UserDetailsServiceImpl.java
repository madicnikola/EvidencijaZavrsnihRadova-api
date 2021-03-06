package fon.njt.EvidencijaZavrsnihRadovaapi.service;

import fon.njt.EvidencijaZavrsnihRadovaapi.entity.UserProfile;
import fon.njt.EvidencijaZavrsnihRadovaapi.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<UserProfile> userOptional = userProfileRepository.findByUsername(username);
        UserProfile userProfile = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));

        return new User(userProfile.getUsername(), userProfile.getPassword(),
                userProfile.isEnabled(), true, true,
                true, getAuthorities(userProfile.getRole().getName()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
