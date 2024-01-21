package kr.devis.util.entityprinter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Member {
    private Long id;
    private String nickname;
    private String email;
    private boolean verifiedEmail;
    private LocalDate birthday;
    private LocalTime birthTime;
    private LocalDateTime createAt;

    public Member(Long id, String nickname, String email, boolean verifiedEmail, LocalDate birthday, LocalTime birthTime, LocalDateTime createAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.birthday = birthday;
        this.birthTime = birthTime;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
