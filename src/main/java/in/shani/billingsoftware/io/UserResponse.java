package in.shani.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String userId;
    private String name;
    private String email;
    // ye dekhna agr kabhi password issue aye

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String role;
}
