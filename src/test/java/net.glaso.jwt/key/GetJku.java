package net.glaso.jwt.key;

import net.glaso.jwt.common.TestBase;
import net.glaso.jwt.common.invoker.JwtInvoker;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetJku {

    TestBase tb;

    @Before
    public void init() throws JSONException {
        tb = new TestBase();
    }

    @Test
    public void sample001() throws IOException {
        // GIVEN
        JSONObject body = new JSONObject();
        String uri = String.format( "key/%s", "3d7b11c4-b918-11e9-a2a3-2a2ae2dbcce4" );

        // WHEN
        JwtInvoker invoker = new JwtInvoker.Builder().host( tb.getHost() )
                .uri( uri )
                .httpMethod( "GET" )
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
