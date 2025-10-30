package ani.rss.util.other;

import ani.rss.entity.Config;
import ani.rss.entity.Result;
import ani.rss.entity.TryOut;
import ani.rss.util.basic.GsonStatic;
import ani.rss.util.basic.HttpReq;
import cn.hutool.core.lang.Assert;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 请 "盗版者" 存放于私人仓库 私人docker镜像
 * 请 "盗版者" 存放于私人仓库 私人docker镜像
 * 请 "盗版者" 存放于私人仓库 私人docker镜像
 */
@Slf4j
public class AfdianUtil {
    /**
     * 检测爱发电订单
     * <p>
     * 请 "盗版者" 存放于私人仓库 私人docker镜像
     *
     * @param no 订单号
     * @return
     */
    public static Result<Void> verifyNo(String no) {
        Assert.notBlank(no, "订单号为空");
        return HttpReq.post("https://afdian.wushuo.top?out_trade_no=" + no)
                .timeout(1000 * 5)
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    Result<Void> result = new Result<>();
                    JsonObject jsonObject = GsonStatic.fromJson(res.body(), JsonObject.class);
                    result.setMessage(jsonObject.get("message").getAsString());
                    result.setCode(jsonObject.get("code").getAsInt());
                    return result;
                });
    }

    /**
     * 捐赠是否有效
     * <p>
     * 请 "盗版者" 存放于私人仓库 私人docker镜像
     *
     * @return
     */
    public static Boolean verifyExpirationTime() {
        return true;
    }

    /**
     * 获取试用设置
     *
     * @return
     */
    public static TryOut getTryOut() {
        return HttpReq.get("https://docs.wushuo.top/TryOut.json")
                .thenFunction(res -> {
                    HttpReq.assertStatus(res);
                    return GsonStatic.fromJson(res.body(), TryOut.class);
                });
    }

    /**
     * 校验捐赠信息
     * <p>
     * 请 "盗版者" 存放于私人仓库 私人docker镜像
     */
    public static void verify() {
        Config config = ConfigUtil.CONFIG;
        // 设置一个很远的过期时间，使授权始终有效
        config.setExpirationTime(Long.MAX_VALUE);
    }
}
