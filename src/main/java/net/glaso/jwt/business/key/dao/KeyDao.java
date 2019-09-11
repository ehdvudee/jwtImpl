package net.glaso.jwt.business.key.dao;

import net.glaso.jwt.business.key.vo.KeyPairVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KeyDao {

    private final SqlSession session;

    public static final String namespace = "net.glaso.mapper.key";

    @Autowired
    public KeyDao( SqlSession session ) {
        this.session = session;
    }

    public KeyPairVo selectKeyPairInfoTrueLastOne() {
        return session.selectOne( namespace + ".selectKeyPairInfoTrueLastOne" );
    }

    public KeyPairVo selectKeyPairInfoUsingKid( String kid ) {
        return session.selectOne( namespace + ".selectKeyPairInfoUsingKid", kid );
    }

    public int insertKeyInfoOne( KeyPairVo vo ) {
        return session.insert( namespace + ".insertKeyInfoOne", vo );
    }

    public KeyPairVo selectKeyPairInfoFalseLastOne() {
        return session.selectOne( namespace + ".selectKeyPairInfoFalseLastOne" );
    }

    public int updateKeyPairUsageOne( int seqId ) {
        return session.update( namespace + ".updateKeyPairUsageOne", seqId );
    }
}

