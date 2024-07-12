package org.m2sec;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import lombok.extern.slf4j.Slf4j;
import org.m2sec.abilities.MasterHttpHandler;
import org.m2sec.abilities.MaterProxyHandler;
import org.m2sec.core.common.Config;
import org.m2sec.core.common.Helper;
import org.m2sec.core.common.WorkExecutor;
import org.m2sec.core.enums.RuntimeEnv;
import org.m2sec.core.common.Constants;
import org.m2sec.panels.MainPanel;

/**
 * @author: outlaws-bai
 * @date: 2024/7/9 0:42
 * @description:
 */
@Slf4j
public class Galaxy implements BurpExtension {


    private static RuntimeEnv env = RuntimeEnv.LOCAL;

    @Override
    public void initialize(MontoyaApi api) {
        try {
            env = RuntimeEnv.BURP;
            api.extension().setName(Constants.BURP_SUITE_EXT_NAME + "-" + Constants.VERSION);
            api.logging().logToOutput(Constants.BURP_SUITE_EXT_INIT_DEF + "Version -> " + Constants.VERSION + "\r\n");
            // 加载配置并初始化
            Config config = Helper.initAndLoadConfig(api);
            // 注册UI
            api.userInterface().registerSuiteTab(Constants.BURP_SUITE_EXT_NAME, new MainPanel(api, config));
            // 注册插件能力
            registerAbilities(api, config);
            // 注册销毁事件
            api.extension().registerUnloadingHandler(() -> this.destroy(config));
        } catch (Exception e) {
            Helper.initExceptionClean();
            throw e;
        }
    }


    private void registerAbilities(MontoyaApi api, Config config) {
        // 注册http hook 能力
        api.http().registerHttpHandler(new MasterHttpHandler(config));
        MaterProxyHandler materProxyHandler = new MaterProxyHandler(config);
        api.proxy().registerRequestHandler(materProxyHandler);
        api.proxy().registerResponseHandler(materProxyHandler);
    }


    /**
     * 1. dump本次使用中的选项到磁盘中的配置文件
     * 2. 删除日志文件
     * 3. 清空tmp目录
     * 4. 关闭任务执行起
     *
     * @param config 全局配置
     */
    private void destroy(Config config) {
        config.dumpOption();
        Helper.deleteLogFile();
        Helper.cleanTmpDir();
        WorkExecutor.INSTANCE.shutdown();
    }


    public static boolean isInBurp() {
        return env.equals(RuntimeEnv.BURP);
    }


}
