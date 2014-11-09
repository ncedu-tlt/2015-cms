/*
 * Copyright 2014 ncedu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ncedu.core.data.accessobjects.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import ru.ncedu.core.data.accessobjects.UserDAO;
import ru.ncedu.core.data.entities.User;

/**
 *
 * @author Alexander Zvyagintsev <alzv0411@gmail.com>
 */
public class LocalUserDAO implements UserDAO {

    private static final List<User> localStorage = Collections.synchronizedList(new ArrayList<User>());
    static {
        localStorage.add(new User(1L, "1@ncedu.ru", "123", 0L, "User1", "User1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        localStorage.add(new User(2L, "2@ncedu.ru", "123", 0L, "User2", "User2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        localStorage.add(new User(3L, "3@ncedu.ru", "123", 0L, "User3", "User3", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        localStorage.add(new User(4L, "4@ncedu.ru", "123", 0L, "User4", "User4", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
        localStorage.add(new User(5L, "5@ncedu.ru", "123", 0L, "User5", "User5", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis())));
    }

    @Override
    public int insertUser(User entity) {
        if (entity == null) {
            return 0;
        }

        User user = findById(entity.getUserId());

        if (user != null) {
            return -1;
        }

        localStorage.add(entity);

        return 1;
    }

    @Override
    public boolean updateUser(User entity) {
        if (entity == null) {
            return false;
        }

        User user = findById(entity.getUserId());

        if (user == null) {
            return false;
        }

        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setGroupId(entity.getGroupId());
        user.setLastLogin(entity.getLastLogin());
        user.setLastName(entity.getLastName());
        user.setPassword(entity.getPassword());
        user.setRegisterWhen(entity.getRegisterWhen());

        return true;
    }

    @Override
    public boolean deleteUser(User entity) {
        if (entity == null) {
            return false;
        }
        
        for (Iterator<User> it = localStorage.iterator(); it.hasNext();) {
            User user = it.next();
            if (user.getUserId() == entity.getUserId()) {
                it.remove();
                return true;
            }
        }
        
        return false;
    }

    @Override
    public User findById(long id) {
        for (User user : localStorage) {
            if (user.getUserId() == id) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        return localStorage;
    }

}
