package io.github.hizhangbo.handler;

/**
 * @author Bob
 * @since 2021/2/21 1:20
 */
public interface ValueEnum {
    default int getValue() {
        return -1;
    }
}
