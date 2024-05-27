package com.example.alumni.feature.user;

import com.example.alumni.domain.AccountType;
import com.example.alumni.domain.Role;
import com.example.alumni.domain.User;
import com.example.alumni.feature.repo.AccountTypeRepository;
import com.example.alumni.feature.repo.RoleRepository;
import com.example.alumni.feature.user.UserDto.CreateUserRequest;
import com.example.alumni.feature.user.UserDto.RegisterRequest;
import com.example.alumni.feature.user.UserDto.UserResponse;
import com.example.alumni.feature.user.UserDto.UserUpdateRequest;
import com.example.alumni.mapper.UserMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserMapper userMapper;


    public CustomPageUtils<UserResponse> CustomPagination(Page<UserResponse> page, String baseUrl){

        CustomPageUtils<UserResponse> customPage = new CustomPageUtils<>();

        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }

        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }

        customPage.setTotal((int) page.getTotalElements());


        customPage.setResults(page.getContent());
        return customPage;
    }

    @Override
    public CustomPageUtils<UserResponse> getAllUsers(int page, int size, String baseUrl) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> users = userRepository.findAll(pageable);

        return CustomPagination(users.map(userMapper::toUserResponse), baseUrl);
    }

    @Override
    public UserResponse registerAccount(RegisterRequest registerRequest) {

        User user = userMapper.requestToUserResponse(registerRequest);

        Role userRole = roleRepository.findByName("USER");

        AccountType accountType = accountTypeRepository.findByName("ALUMNI");

        user.setRole(userRole);

        user.setAccType(accountType);

        user.setIsDisabled(false);

        user.setIsVerified(false);

        user.setIsCredentialsExpired(true);

        user.setIsAccountExpired(true);

        user.setIsAccountLocked(true);

        user.setIsAdmin(false);

        user.setPassword(new BCryptPasswordEncoder().encode(registerRequest.password()));

        user.setConfirmPassword(new BCryptPasswordEncoder().encode(registerRequest.confirmPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse createUsers(CreateUserRequest createUserRequest) {

        User user = userMapper.createToUserResponse(createUserRequest);

        Role roleName = roleRepository.findByName(createUserRequest.roleName());


        AccountType accType = accountTypeRepository.findByName(createUserRequest.AccTypeName());

        user.setRole(roleName);

        user.setAccType(accType);

        user.setIsVerified(true);

        user.setIsDisabled(false);

        user.setIsCredentialsExpired(true);

        user.setIsAccountExpired(true);

        user.setIsAccountLocked(true);

        user.setPassword(new BCryptPasswordEncoder().encode(createUserRequest.password()));

        user.setConfirmPassword(new BCryptPasswordEncoder().encode(createUserRequest.confirmPassword()));

        // Set isAdmin to true if the role is ADMIN
        if (createUserRequest.roleName().equalsIgnoreCase("ADMIN")) {
            user.setIsAdmin(true);
        }

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));
        user.setAvatar(userUpdateRequest.avatar());

        user.setFirstName(userUpdateRequest.firstName());

        user.setLastName(userUpdateRequest.lastName());

        user.setPhone(userUpdateRequest.phone());

        user.setUsername(userUpdateRequest.username());

        user.setCoverUrl(userUpdateRequest.coverUrl());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(String id) {

        userRepository.deleteById(id);

    }

    @Override
    public UserResponse isVerified(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));

        user.setIsVerified(true);

        // Check the account type and set the role accordingly
        if (user.getAccType().getName().equalsIgnoreCase("ALUMNI")) {
            Role alumniRole = roleRepository.findByName("ALUMNI");

            user.setRole(alumniRole);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public UserResponse isDisabled(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));
        user.setIsDisabled(true); // Set is_disabled to true
        User disabledUser = userRepository.save(user); // Save the updated user
        return userMapper.toUserResponse(disabledUser);
    }

    @Override
    public UserResponse isEnable(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));

        // Set is_disabled to false
        user.setIsDisabled(false);

        User enabledUser = userRepository.save(user);

        return userMapper.toUserResponse(enabledUser);
    }

    @Override
    public List<UserResponse> getAllUsersByIsVerify() {

        List<User> users = userRepository.findByIsVerified(false);

        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
}
