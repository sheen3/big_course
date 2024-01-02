package org.tools.exception;

import org.tools.log.LogEnum;

/**
 * @Author: eensh
 * @CreateDate: 2023/12/31
 */
public class FormatException extends Exception {

    public FormatException() {
        super(LogEnum.EXCEPTION_FORMAT_ERROR.getMsg());
    }

    public FormatException(String message) {
        super(LogEnum.EXCEPTION_FORMAT_ERROR.getMsg() + "——" + message);
    }
}
