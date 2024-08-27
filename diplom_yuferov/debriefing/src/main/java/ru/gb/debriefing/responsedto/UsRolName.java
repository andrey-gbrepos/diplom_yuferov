package ru.gb.debriefing.responsedto;

import lombok.Data;

@Data
public class UsRolName {

    private Long userId;
    private String userName;
    private Long roleId;
    private String roleName;
}
