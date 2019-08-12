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

public class UpdateKey {

    TestBase tb;

    @Before
    public void init() throws JSONException {
        tb = new TestBase();
    }

    @Test
    public void sample001() throws IOException {
        // GIVEN
        JSONObject body = new JSONObject();

        body.put( "pri", "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDDopp3WnIJLFq3CgbKygxh/8xLjA5DkQ9g7J4vmwKYVOu0WwqwStXbvJY8i8h5S663NGQrCjbKLjwhx4w1K/D8WKE1Xk4zaHCYzB7cU+h0o75JDbVMnho7clPEOs5nn/isZ9Zk7O/ZtL7SeIi9xwglZPG1VovLe/EdJghJzQA/b4+XVGFQlL9EpPFhqkL5tGFoAW26LgIUziICU2lOBY89lAt8h7De5rY4RpS4CLetag3IrVwLKPtcYHoONoQ69V2u2zBFmfo2H+E2+KagKnI0VvaaWhse+ZaQKCzpw48iKO6QCt5++AzvuXVPsvQ2WOwc0wQB9pn0pCam5/T2n35bAgMBAAECggEBAI5bXz1kgkCsluJEmIauGe6h3b5HjwWZo/Aqnj0T9gJ1IQMfkJqSCiaYh1ZNfTbOXLeRlyY+i3n5xOXrt9nlPLnToJMeKZd88xosxnQbVraxhEDSic/T6FDDs7LB+/opzSFfegH869emj4jGDvLRxtq3Jq+G57jNWrmUOEnDs99z/0wSWA70z7hZl8wDolBm5zC6adpxTlv4yfZyIrAJ6planN7Sjj6Pq8hdq4dHYjCxYmq2WX/z2g1T8u2SFMzSWobEJ5nPURVj5jiFpczlraC3FIq7dXMDo+O9D2MOww62CT5c4XdSBOQvPQjfTO5kJ31jEbLGlnyghBYTDp9Y0vkCgYEA7uQJ3BGsUKZcYb8towqCqExghj7BCoPQBo697AXGvMJycuk3eQx2ZDa5YDcRnaIW+TdJs1XX+ha2B8CbRKqkkclwM/F1SzSuXpXPvOX/PnG5f7h5zpBuiCNoW14b5Md9alFpvSZ+ZRgvSybmseCR/iUBsut7PRpZz4IvP09RDD8CgYEA0aV92YYFIbw8X+oOvNjJW79mrbS1O9IJiUrnYnZREgLbhPlgAVykLKpOeq/QMO2Xw8hcK1sWV5nm+dnB0lK4lkt+i4t3r5vCu1+khzgqpB47+QGlCLpn03AcKQCQ+UuNiTEgwLJudORfyaEck/Fa9Lk9EKkZguU26Mx2rZPl9uUCgYEArrUVRN2j8KOJ4m6HeLqCDYU1/le8o+z8YR9ZZFhhHMD0JHfM+vkTSH0BG42Kk4bsWDyH6OC6AAtF+utwYkqN6pSwOzy2NFcFwuS7NYHbJpbTO8X1Rw032NZURqXProP6g5ugwhV50INcaxC7HJGJE52K3Yf77BbuYaTq2tkuA88CgYB3D1g4h880SHW1NvSltSwtqbaDYgQm8/KsgNiFZ5JE+1B15TXrsBQgtZuaO2ytzNEKxCfsC2/V0jaFoHBdpNLlr/wu/ca7+WaPCRMkI/a7e9wtXwy9tzctshacBpFOsuSvjDIAnZTzV5s6o//Z2REBuI2Af7hYSRp0/WFM1JieLQKBgQC0fyfkIIIYCvjPNxhmDjBjaKFlRYcivpRXJhM3ZN9IoMXTz9iDP8RuoAqEyyUezKfGFz8bvFNjV2wTiCdaE1sB04qk/ZANOst0357ALlrronp7s6fvmQ5GY0RQaDGWSXhDogjqkPELKy2lobLgmEorx3MBSx4i5g4rPe46KM/U4A==" );
        body.put( "cert", "MIIDhzCCAm+gAwIBAgIBAzANBgkqhkiG9w0BAQsFADAuMSwwKgYDVQQDDCNSTkQgT1UgPSBKV1RfTSBPID0gR2xhc29Db3JwIEMgPSBLUjAeFw0xOTA4MTEwNjM4NTJaFw0yMDA4MTAwNjM4NTJaMEYxRDBCBgNVBAMTO2p3dC5nbGFzby5uZXQgTyA9IGp3dC5nbGFzby5uZXQtMiBMID0gc2VvdWwgUyA9IG1vb24gQyA9IEtSMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw6Kad1pyCSxatwoGysoMYf/MS4wOQ5EPYOyeL5sCmFTrtFsKsErV27yWPIvIeUuutzRkKwo2yi48IceMNSvw/FihNV5OM2hwmMwe3FPodKO+SQ21TJ4aO3JTxDrOZ5/4rGfWZOzv2bS+0niIvccIJWTxtVaLy3vxHSYISc0AP2+Pl1RhUJS/RKTxYapC+bRhaAFtui4CFM4iAlNpTgWPPZQLfIew3ua2OEaUuAi3rWoNyK1cCyj7XGB6DjaEOvVdrtswRZn6Nh/hNvimoCpyNFb2mlobHvmWkCgs6cOPIijukArefvgM77l1T7L0NljsHNMEAfaZ9KQmpuf09p9+WwIDAQABo4GXMIGUMB8GA1UdIwQYMBaAFP9bLlNKjeF6mZWuZicnvhmbuzCtMAkGA1UdEwQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMA4GA1UdDwEB/wQEAwIFoDAYBgNVHREEETAPgg1qd3QuZ2xhc28ubmV0MB0GA1UdDgQWBBTDRWQrJKPIwi/7cX496Usk31NS4zANBgkqhkiG9w0BAQsFAAOCAQEAU/6yu7jC6Eu17z+hF6Ceb4Pe6sWb598JRSDOpX1Q9YAzh3QBdDpVdA4lH5SG+JmkNwg03wd/Iu+up9WM0kE++p6EvUGIdfK85o9sxjW8bCqX/W8uDJ40xH7wgCQK/FL0f/+vj/Hj2FLBOz5QirEl9dDnUQToY4LlmQG8TjkKdv9XdgBhydZcN5RHg+hP9fJMab8C9R52iJXnn7vjFp4jiwxLTimCO+EUyzZ6PBD4gWrFsZimMfsRXgwJxOO4Ror8vgqMn/RGZ0YJOiVOV3Rv8BHRbN5ifUvrRQgEWG0bCVUIu19fsjHa/WLF/MhnCX3NIwjxb3CPhL1RSmDGejQTLw==" );

        // WHEN
        JwtInvoker invoker = new JwtInvoker.Builder().host( tb.getHost() )
                .uri( "key/updated-key" )
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
