package net.glaso.jwt.business.key.service;

import com.auth0.jwt.algorithms.Algorithm;
import net.glaso.jwt.business.key.dao.KeyDao;
import net.glaso.jwt.business.key.vo.KeyPairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeyService {

    private final KeyDao keyDao;

    private static Map<String, Algorithm> jwtKey;
    private static Map<String, String> kids;

    @Autowired
    public KeyService(KeyDao dao ) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.keyDao = dao;
        getInstanceJwtKey();
    }

    public String getCurrentKid() { return kids.get( "cKid" ); }

    public Algorithm getAlgo() {
        return getAlgo( getCurrentKid() );
    }

    public Algorithm getAlgo( String kId ) {
        return jwtKey.get( kId );
    }

    private void getInstanceJwtKey() throws NoSuchAlgorithmException, CertificateException, InvalidKeySpecException {
        KeyPairVo vo = keyDao.selectKeyPairInfoLastOne();

        jwtKey = new HashMap<>();
        kids = new HashMap<>();

        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec( vo.getPriKey() ) );
        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream( vo.getCert() ) );

        Algorithm algo = Algorithm.RSA256((RSAPublicKey) cert.getPublicKey(), priKey);

        jwtKey.put( vo.getKid(), algo );
        kids.put( "cKid", vo.getKid() );
    }
}
