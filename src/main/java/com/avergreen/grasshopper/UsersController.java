package com.avergreen.grasshopper;

import org.slf4j.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.*;


//w springu musze zanotowac klasy
@RestController()

@RequestMapping("/users")


public class UsersController {
    private static Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${hejka.msg}")
    private String hejkaMessage;

    @RequestMapping(value = "/hejka", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> hejka() {
        LOGGER.info("Hejka received");
        return ResponseEntity.ok(new MessageDto(hejkaMessage));
    }

    @RequestMapping(value = "/dupa/{msg}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> dupa(@PathVariable String msg) {
        try {
            throw new IllegalStateException("Test");
        } catch (Exception e) {
            LOGGER.error("dupa blad", e);
        }
        return ResponseEntity.status(500).body(new MessageDto(msg));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<MessageDto> login(@RequestBody MessageDto messageDto) {
        messageDto.setMessage(messageDto.getMessage() + "HAHA");
        return ResponseEntity.ok(messageDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> receiveUser(@RequestBody UserDto userDto) {
        userDto.setId(userRepository.insertUserToDb(
                userDto.getImie(),
                userDto.getNazwisko())
        );
        return ResponseEntity.ok(userDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> odczyt(@PathVariable int id) {
        UserEntity userEntity = userRepository.retriveUser(id);

        if (userEntity == null) {
            return ResponseEntity.status(404).body(new UserDto());
        }

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setImie(userEntity.getFirstName());
        userDto.setNazwisko(userEntity.getLastName());

        return ResponseEntity.ok(userDto);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, Integer>>> userReport() {
        List<UserEntity> userList = userRepository.userEntityList();
        Map<String, Map<String, Integer>> userReport = new HashMap<>();

        for (UserEntity userEntity : userList) {
            String lastName = userEntity.getLastName();
            Map<String, Integer> innerMap = userReport.get(lastName);

            if (innerMap == null) {
                innerMap = new HashMap<>();
                userReport.put(lastName, innerMap);
            }

            String firstName = userEntity.getFirstName();
            Integer count = innerMap.get(firstName);

            if(count == null) {
                count = 0;
            }

            count++;
            innerMap.put(firstName, count);

        }
        return ResponseEntity.ok(userReport);
    }

}
