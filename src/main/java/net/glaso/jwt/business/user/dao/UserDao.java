package net.glaso.jwt.business.user.dao;

import net.glaso.jwt.business.user.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private  SqlSession session;

    public static final String namespace = "net.glaso.mapper.user";

    @Autowired
    public UserDao( SqlSession session ) {
        this.session = session;
    }

    public void insertUserIdOne( UserVo vo ) {
        this.session.insert( namespace + ".insertUserIdOne", vo );
    }

    public UserVo selectUserInfoOneUsingId( String id ) {
        return  session.selectOne( namespace + ".selectUserInfoOneUsingId", id );
    }
}
