package com.cvqs.usermanagementservice.service.concretes;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.repository.UserRepository;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<UserDto> getAll() {
        List <User> users=userRepository.findAll();
        List<UserDto> userDtos=users.stream().map(user1 -> modelMapper.map(user1,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user=modelMapper.map(userDto,User.class);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }
//tamamlanmadı
    @Override
    public UserDto updateUser(UserDto userDto) {
        User user=userRepository.findById(userDto.getId()).orElseThrow(() -> new EntityNotFoundException("user can't be found"));;
        user.setUserName(userDto.getUserName());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setRoles(userDto.getRoles());
        final User savedUser=userRepository.save(user);

        return null;
    }
}
