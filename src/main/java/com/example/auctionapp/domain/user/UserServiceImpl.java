package com.example.auctionapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Setter
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public User update(User incoming, User current) {
        BeanUtils.copyProperties(incoming, current, "id" );
        return save(current);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
