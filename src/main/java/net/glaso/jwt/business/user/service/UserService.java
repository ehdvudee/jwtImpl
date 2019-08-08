package net.glaso.jwt.business.user.service;

import net.glaso.jwt.business.user.dao.UserDao;
import net.glaso.jwt.business.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService( UserDao dao ) {
        this.userDao = dao;
    }

    public void insertUserIdOne( UserVo vo ) {
        this.userDao.insertUserIdOne( vo );
    }

    public UserVo selectUserInfoOneUsingId(String id ) {
        return this.userDao.selectUserInfoOneUsingId( id );
    }

}
