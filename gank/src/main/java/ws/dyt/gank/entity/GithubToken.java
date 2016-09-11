package ws.dyt.gank.entity;

/**
 * Created by yangxiaowei on 16/9/4.
 */
public class GithubToken {
    public String access_token;
    public String token_type;
    public String scope;

    @Override
    public String toString() {
        return "GithubToken{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
