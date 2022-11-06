package com.example.demo.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService service;

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}/",params = "version=1")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") //->참고로이런건 Header의 이름과 값까지 전부설정해준것
    //@GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json") // Header이름이 accept로 정해져있다. MIME타입을 이용하는방법.
    //참고로 consumes는 Content-type인가? 그렇다.
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = service.findOne(id);

        if( user == null ){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/",params = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    //@GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv2+json") // Header이름이 accept로 정해져있다. MIME타입을 이용하는방법.
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);

        //bean의 변환
        UserV2 user2 = new UserV2("VIP");
        BeanUtils.copyProperties(user,user2);

        if( user == null ){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user2);
        mapping.setFilters(filters);

        return mapping;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
