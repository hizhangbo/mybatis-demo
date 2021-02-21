package io.github.hizhangbo.entity;

import io.github.hizhangbo.handler.ValueEnum;
import lombok.*;

/**
 * @author Bob
 * @since 2021/2/20 21:26
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Gender gender;

    @Getter
    public enum Gender implements ValueEnum {
        male(1, "男"),
        female(0, "女");

        private final int code;
        private final String name;

        Gender(int code, String name) {
            this.code = code;
            this.name = name;
        }


        @Override
        public int getValue() {
            return code;
        }
    }
}
