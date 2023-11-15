package springblog.myblog.service.jwt;

public interface JwtService {

    public String getToken(String key, Object value);
}
