package me.amr.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("User")
public class User {

    @Id
    private String userId;
    private String password;
    private String userName;
    private String token;

    public User() {}

    @Builder
    public User(String userId, String password, String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }

    public String toString() {
        return "[User#" + this.userId + "] " + this.userName;
    }
}
