package kr.devis.entityprinter;

import kr.devis.entityprinter.print.impl.VerifyType;

import java.time.LocalDateTime;

public class History {

    private Long id;
    private String groupType;
    private String description;
    private String verifyToken;
    private VerifyType verifyType;
    private LocalDateTime sendTime;
    private LocalDateTime expiredTime;
    private LocalDateTime verifyTime;

    public History(Long id, String groupType, String description, String verifyToken) {
        this.id = id;
        this.groupType = groupType;
        this.description = description;
        this.verifyToken = verifyToken;
        this.verifyType = VerifyType.EMAIL;

        final LocalDateTime now = LocalDateTime.now();
        this.sendTime = now;
        this.expiredTime = now.plusSeconds(600);
        this.verifyTime = now.plusSeconds(10);
    }

}
