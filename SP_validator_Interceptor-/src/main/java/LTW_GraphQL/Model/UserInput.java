package LTW_GraphQL.Model;

import lombok.Data;

@Data
public class UserInput {
    private String fullname;
    private String email;
    private String password;
    private String phone;
}