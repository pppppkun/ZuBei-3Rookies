package com.hackathon.backend.controller;


import com.hackathon.backend.constant.ServerException;
import com.hackathon.backend.constant.SimpleResponse;
import com.hackathon.backend.entity.UserEntity;
import com.hackathon.backend.form.AuthForm;
import com.hackathon.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {


    @Autowired
    UserService userService;

    /**
     *
     * @param session
     * @param authForm
     * @return
     */
    @ApiOperation(value = "登录", response = UserEntity.class ,
    notes = "返回SimpleResponse对象，如果登录成功，SimpleResponse对象是User")
    @PostMapping("/login")
    public SimpleResponse login(@ApiIgnore HttpSession session, @RequestBody AuthForm authForm)
    {
        UserEntity userEntity;
        try
        {
            userEntity = userService.getUser(authForm.getUsername());
            if(!userEntity.getPassword().equals(authForm.getPassword()))
            {
                return SimpleResponse.error("wrong password");
            }
        }
        catch (ServerException sec)
        {
            return SimpleResponse.error(sec.getMessage());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return SimpleResponse.error("Server login error");
        }
        session.setAttribute("user",userEntity);
        return SimpleResponse.ok(userEntity);

    }

    //TODO 退出登录

}
