package com.dipanshu.habitTracker.model;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String password;

    private String username;;


}