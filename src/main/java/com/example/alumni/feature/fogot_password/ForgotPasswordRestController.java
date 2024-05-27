package com.example.alumni.feature.fogot_password;


import com.example.alumni.domain.ForgotPassword;
import com.example.alumni.domain.User;
import com.example.alumni.feature.fogot_password.dto.ChangePassword;
import com.example.alumni.feature.fogot_password.dto.ChangePasswordWithOldPassword;
import com.example.alumni.feature.fogot_password.dto.MailBody;
import com.example.alumni.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgot-password")
public class ForgotPasswordRestController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("verify-email/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
        int otp = generateOTP();
        MailBody mailBody = MailBody.builder()
                .to(user.getEmail())
                .subject("Reset Password")
                .build();
        ForgotPassword fp = new ForgotPassword();
        fp.setOtp(otp);
        fp.setUser(user);
        fp.setExpiryDate(new Date(System.currentTimeMillis() + 70 * 1000));

        mailService.sendOtpMessage(mailBody, String.valueOf(otp));
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("OTP sent to your email");
    }

    @PostMapping("verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable String email, @PathVariable Integer otp) {
        User existingUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        // If a User with the given email already exists, update the password
        if (existingUser != null) {
            // Find the existing ForgotPassword entity for the existing User
            ForgotPassword existingFp = forgotPasswordRepository.findByUser(existingUser);

            // If an existing ForgotPassword entity is found, delete it
            if (existingFp != null) {
                forgotPasswordRepository.deleteById(existingFp.getId());
            }

            // Verify the new OTP for the existing User
            ForgotPassword newFp = forgotPasswordRepository.findByOtpAndUser(otp, existingUser);

            if (newFp != null && newFp.getExpiryDate().before(Date.from(Instant.now()))) {
                forgotPasswordRepository.deleteById(newFp.getId());
                return new ResponseEntity<>("OTP expired", HttpStatus.EXPECTATION_FAILED);
            }

            // Update the password for the existing User
            // existingUser.setPassword(newPassword);
            // userRepository.save(existingUser);

            return new ResponseEntity<>("OTP verified and password updated", HttpStatus.OK);
        }

        // If no existing User is found, create a new User with a new user_id
        User newUser = new User();
        newUser.setEmail(email);
        // Set other fields as necessary
        userRepository.save(newUser);

        // Verify the new OTP for the new User
        ForgotPassword newFp = forgotPasswordRepository.findByOtpAndUser(otp, newUser);

        if (newFp != null && newFp.getExpiryDate().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(newFp.getId());
            return new ResponseEntity<>("OTP expired", HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<>("OTP verified and new user created", HttpStatus.OK);
    }



    @PostMapping("reset-password/{email}")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePassword changePassword, String email) {

        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
        //change to encoded Password later
        String password = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, password);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

    @PostMapping("reset-password-with-old/{email}")
    public ResponseEntity<String> resetPasswordWithOld(@RequestBody ChangePasswordWithOldPassword changePassword, @PathVariable String email) {
        if(!Objects.equals(changePassword.newPassword(), changePassword.confirmPassword())){
            return new ResponseEntity<>("New passwords do not match", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if(!passwordEncoder.matches(changePassword.oldPassword(), user.getPassword())){
            return new ResponseEntity<>("Old password is incorrect", HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(passwordEncoder.encode(changePassword.newPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

    private Integer generateOTP() {
        Random random = new Random();
        return random.nextInt(100000,999999);
    }

}
