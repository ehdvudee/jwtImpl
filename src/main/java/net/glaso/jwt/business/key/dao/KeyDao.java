package net.glaso.jwt.business.key.dao;

import net.glaso.jwt.business.key.vo.KeyPairVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KeyDao {

    private  SqlSession session;

    public static final String namespace = "net.glaso.mapper.key";

    @Autowired
    public KeyDao(SqlSession session ) {
        this.session = session;
    }

    public KeyPairVo selectKeyPairInfoLastOne() {
        return session.selectOne( namespace + ".selectKeyPairInfoLastOne" );
    }
}
