package com.example.demo.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static{
        users.add(new User(1,"Kenneth",new Date(),"pass1","343423-3432432"));
        users.add(new User(2,"Alice",new Date(),"pass2","67878-745632"));
        users.add(new User(3,"Elena",new Date(),"pass3","453463-456546"));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id){
        for (User user : users) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){

        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }

        }
        return null;

    }
}
