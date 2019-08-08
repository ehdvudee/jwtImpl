package net.glaso.jwt.sample;

import net.glaso.jwt.common.TestBase;
import net.glaso.jwt.common.invoker.JwtInvoker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class createVal {

    TestBase tb;

    @Before
    public void init() throws JSONException {
        tb = new TestBase();
    }

    @Test
    public void sample001() throws IOException {
        // GIVEN
        JSONObject body = new JSONObject();

        body.put( "id", "dev" );
        body.put( "asdasd", "asdasd" );
        body.put( "asdassd", "asdasdasd" );

        // WHEN
        JwtInvoker invoker = new JwtInvoker.Builder().host( tb.getHost() )
                .uri( "sample" )
                .httpMethod( "POST" )
                .authToken( tb.getToken() )
                .body( body.toString() )
                .build();

        JSONObject ret = new JSONObject( invoker.invokeAuthorizedJwt() );

        // THEN
        System.out.println( ret );
        assertThat(
                ret.getString( "status" ),
                equalTo( "success" )
        );
    }

}
