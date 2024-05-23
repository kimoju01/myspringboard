package org.example.myspringboard.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Board {

    @Id
    private Long id;

    @NotBlank(message = "이름을 입력해주세요.")  // 필드가 null이 아니고 공백이 아니어야함
    @Size(max = 100, message = "이름은 100자 까지만 허용됩니다.")
    private String name;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 255, message = "제목은 255자 까지만 허용됩니다.")
    private String title;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 255, message = "비밀번호는 255자 까지만 허용됩니다.")
    private String password;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 5000, message = "내용은 5000자 까지만 허용됩니다.")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
