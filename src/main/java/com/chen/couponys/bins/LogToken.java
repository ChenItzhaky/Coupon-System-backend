package com.chen.couponys.bins;
import com.chen.couponys.login.ClientsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogToken {
    private UUID token;
    private String email;
    private ClientsType type;
}
