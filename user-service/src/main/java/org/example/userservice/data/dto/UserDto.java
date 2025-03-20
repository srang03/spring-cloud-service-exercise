package org.example.userservice.data.dto;

import lombok.Data;
import org.example.userservice.data.vo.ResponseOrder;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String userId;
    private String email;
    private String name;
    private String pwd;
    private Date createAt;

    private String encryptedPwd;
    List<ResponseOrder> orders;
}
