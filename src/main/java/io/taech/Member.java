package io.taech;

import java.time.LocalDateTime;

public class Member {

    private Long id;
    private Long accountId;
    private String username;
    private String nickname;
    private String birthday;
    private Job job;
    private String delYn;
    private LocalDateTime createTime;

    public Member(Long id, Long accountId, String username, String nickname, String birthday, String delYn, LocalDateTime createTime) {
        this.id = id;
        this.accountId = accountId;
        this.username = username;
        this.nickname = nickname;
        this.birthday = birthday;
        this.job = new Job("Programmer");
        this.delYn = delYn;
        this.createTime = createTime;
    }

    public static class Job {
        private String jobName;
        public Job(String jobName) {
            this.jobName = jobName;
        }
    }
}
