package net.glaso.jwt.business.key.service;

import com.auth0.jwt.algorithms.Algorithm;
import net.glaso.jwt.business.key.dao.KeyDao;
import net.glaso.jwt.business.key.vo.KeyPairVo;
import net.glaso.jwt.business.token.common.TokenConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
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
import java.util.UUID;

@Service
public class KeyService {

    private final KeyDao keyDao;

    private static Map<String, Algorithm> jwtKey = new HashMap<>();
    private static Map<String, String> kids = new HashMap<>();

    @Autowired
    public KeyService(KeyDao dao ) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.keyDao = dao;
        getInstanceJwtKey( keyDao.selectKeyPairInfoTrueLastOne() );
    }

    public String getCurrentKid() { return kids.get( "cKid" ); }

    public Algorithm getAlgo() {
        return getAlgo( getCurrentKid() );
    }

    public Algorithm getAlgo( String kId ) {
        return jwtKey.get( kId );
    }

    public void putAlgo( String kid, Algorithm algo ) {
        jwtKey.put( kid, algo );
    }

    public void putOldKid( String kid ) {
        kids.put( "oKid", kid );
    }

    public void getInstanceJwtKey( KeyPairVo vo ) throws NoSuchAlgorithmException, CertificateException, InvalidKeySpecException {

        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec( vo.getPriKey() ) );
        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream( vo.getCert() ) );

        Algorithm algo = Algorithm.RSA256((RSAPublicKey) cert.getPublicKey(), priKey);

        jwtKey.put( vo.getKid(), algo );
        kids.put( "cKid", vo.getKid() );

        System.out.println( jwtKey );
        System.out.println( kids );
    }

    public Map<String, Object> getJku( String kid ) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairVo vo = keyDao.selectKeyPairInfoUsingKid( kid );
        Map<String, Object> jkuMap = new HashMap<>();

        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream( vo.getCert() ) );
        RSAPublicKey pubKey = (RSAPublicKey) cert.getPublicKey();

        MessageDigest sha1 = MessageDigest.getInstance( "SHA-1" );

        JSONArray jArr = new JSONArray();
        jArr.put( DatatypeConverter.printBase64Binary( cert.getEncoded() ) );

        jkuMap.put( "alg", TokenConstants.ALGORITHM );
        jkuMap.put( "key", TokenConstants.KTY );
        jkuMap.put( "use", TokenConstants.USE );
        jkuMap.put( "x5c", jArr );
        jkuMap.put( "n", pubKey.getModulus() );
        jkuMap.put( "e", pubKey.getPublicExponent() );
        jkuMap.put( "kid", kid );
        jkuMap.put( "x5t", DatatypeConverter.printHexBinary( sha1.digest( cert.getEncoded() ) ).toLowerCase() );

        return jkuMap;
    }

    public void updateKey(HttpServletRequest request ) {
        JSONObject body = (JSONObject) request.getAttribute( "body" );
        KeyPairVo vo = new KeyPairVo();

        if ( body.isNull( "pri" ) || body.isNull( "cert" ) ) {
            throw new IllegalArgumentException( "pri or cert is invalid." );
        }

        vo.setPriKey( DatatypeConverter.parseBase64Binary( body.getString( "pri" ) ) );
        vo.setCert( DatatypeConverter.parseBase64Binary( body.getString( "cert" ) ) );
        vo.setKid( UUID.randomUUID().toString() );
        vo.setUsage( false );

        keyDao.insertKeyInfoOne( vo );
    }
}
