package com.example.Success18.Authentication.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDTO {
    private Long sn;
    private String status;
    private String remarks;
}
