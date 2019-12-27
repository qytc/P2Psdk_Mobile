package io.qytc.p2psdk.http;


import io.qytc.p2psdk.http.response.BaseResponse;
import io.qytc.p2psdk.http.response.CreatConfResponse;
import io.qytc.p2psdk.http.response.LoginResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 终端接口
 */
public interface TerminalHttpService {

    @FormUrlEncoded
    @POST("terminal/auth")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<LoginResponse> auth(@Field("appId") String appId, @Field("type") String type, @Field("number") String number, @Field("password") String password, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("conference/p2pCall")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<CreatConfResponse> p2pCall(@Field("pmi") String targetId, @Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("conference/acceptCall")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> acceptCall(@Field("pmi") String targetId, @Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("conference/endP2PCall")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> endP2PCall(@Field("pmi") String targetId, @Field("accessToken") String accessToken);
}
