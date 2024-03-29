package com.stylefeng.guns.modular.system.factory;

import com.stylefeng.guns.modular.system.transfer.UserDto;
import com.stylefeng.guns.common.persistence.model.User;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 *
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
