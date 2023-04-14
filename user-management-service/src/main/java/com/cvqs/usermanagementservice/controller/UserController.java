package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.model.User;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * UserController sınıfı,User servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);

    /**
     * Tüm kullanıcıları getirmek için http get metodu kullanılarak çağrı yapılır.
     * @return Kullanıcıların listesini içeren ResponseEntity döndürür.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser(){
        LOGGER.info("Incoming request for /users/getAll");
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Yeni bir kullanıcı eklemek için http post metodu kullanılarak çağrı yapılır.
     * @param userDto Kaydedilecek kullanıcının verilerini içeren UserDto nesnesi.
     * @return Kaydedilen kullanıcının verilerini içeren ResponseEntity döndürür.

     */
    @PostMapping ("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto){
        LOGGER.info("Incoming request for /users/saveUser");

        return ResponseEntity.ok(userService.save(userDto));
    }

    /**
     * Var olan bir kullanıcının bilgilerini güncellemek için http post metodu kullanılarak çağrı yapılır.
     * @param userDto Güncellenecek kullanıcının verilerini içeren UserDto nesnesi.
     * @return Güncellenen kullanıcının verilerini içeren ResponseEntity döndürür.
     */
    @PostMapping ("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        LOGGER.info("Incoming request for /users/update");

        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    /**
     * Kullanıcının silinmesi için http delete metodu kullanılarak çağrı yapılır.
     *
     * @param username silinecek kullanıcının kullanıcı ismi
     * @return ResponseEntity ile döndürülen silinmiş kullanıcının bilgilerini taşıyan UserDto nesnesi

     */
    @DeleteMapping ("/delete")
    public ResponseEntity<UserDto> deleteUser(@RequestParam String username){
        LOGGER.info("Incoming request for /users/delete");

        return ResponseEntity.ok(userService.delete(username));
    }

    /**
     * Kullanıcının kullanıcı adı ile aranması için http get metodu kullanılarak çağrı yapılır.
     * @param username aranacak kullanıcının kullanıcı adı
     * @return User nesnesi ile döndürülen kullanıcının bilgilerini içeren UserDto nesnesi
     */
    @GetMapping("/findUserByUsername")
    public User findUserByUsername(@RequestParam("username") String username){
        return userService.findUserByUserName(username);
    }
}
