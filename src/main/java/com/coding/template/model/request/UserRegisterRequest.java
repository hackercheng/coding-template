package com.coding.template.model.request;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String checkCode;

}
