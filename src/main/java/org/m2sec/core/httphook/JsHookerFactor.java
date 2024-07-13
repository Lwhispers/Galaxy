package org.m2sec.core.httphook;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.m2sec.core.common.Option;
import org.m2sec.core.dynamic.ICodeHooker;
import org.m2sec.core.models.Request;
import org.m2sec.core.models.Response;

/**
 * @author: outlaws-bai
 * @date: 2024/7/12 22:49
 * @description:
 */
@Slf4j
@Getter
public class JsHookerFactor extends IHttpHooker implements ICodeHookerFactor {

    private ICodeHooker hooker;

    @Override
    public void init(Option opt) {

    }

    @Override
    public void init(String filepath) {

    }

    @Override
    public Request hookRequestToBurp(Request request) {
        return null;
    }

    @Override
    public Request hookRequestToServer(Request request) {
        return null;
    }

    @Override
    public Response hookResponseToBurp(Response response) {
        return null;
    }

    @Override
    public Response hookResponseToClient(Response response) {
        return null;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return new byte[0];
    }

    @Override
    public void destroy() {

    }
}
