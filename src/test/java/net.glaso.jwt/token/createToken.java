package net.glaso.jwt.token;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import net.glaso.jwt.common.TestBase;
import net.glaso.jwt.common.invoker.JwtInvoker;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class createToken {

    TestBase tb;

    @Before
    public void init() throws NoSuchAlgorithmException, JSONException, IOException {
        tb = new TestBase();
    }

    @Test
    public void sample001() throws IOException {
        // GIVEN
        JSONObject body = new JSONObject();

        body.put( "id", "dev" );
        body.put( "password", "72ab994fa2eb426c051ef59cad617750bfe06d7cf6311285ff79c19c32afd236" );

        // WHEN
        JwtInvoker invoker = new JwtInvoker.Builder().host( tb.getHost() )
                .uri( "token" )
                .httpMethod( "POST" )
                .body( body.toString() )
                .build();

        JSONObject ret = new JSONObject( invoker.invokeCreJwt() );

        // THEN
        System.out.println( ret );
        assertThat(
                ret.getString( "status" ),
                equalTo( "success" )
        );
    }

}
