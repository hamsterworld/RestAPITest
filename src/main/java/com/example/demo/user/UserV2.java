package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User{
    //default 생성자가 부모한테 있지않아서 발생햇다. 부모에다가 @Noarg를 생성해주자
    private String grade;

}
