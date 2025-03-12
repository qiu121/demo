package com.github.qiu121.common;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:qiu0089@foxmail.com">qiu121</a>
 * @version 1.0
 * @date 2023/3/14
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Result<T> {
    @NonNull
    private Integer code;
    @NonNull
    private String msg;
    private T data;


    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功");
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(0, "操作完成");
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(0, msg);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(0, "操作完成", data);
    }


}
