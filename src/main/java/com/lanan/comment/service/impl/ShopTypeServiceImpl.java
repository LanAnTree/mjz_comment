package com.lanan.comment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lanan.comment.dto.Result;
import com.lanan.comment.entity.ShopType;
import com.lanan.comment.mapper.ShopTypeMapper;
import com.lanan.comment.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.lanan.comment.utils.RedisConstants.CACHE_SHOP_TYPE_KEY;
import static com.lanan.comment.utils.RedisConstants.CACHE_SHOP_TYPE_TTL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById() {
        String json = stringRedisTemplate.opsForValue().get(CACHE_SHOP_TYPE_KEY);
        if (StrUtil.isNotBlank(json)) {
            List<ShopType> list = JSONUtil.toList(json, ShopType.class);
            return Result.ok(list);
        }
        List<ShopType> sort = this.query().orderByAsc("sort").list();
        if (ObjectUtil.isEmpty(sort)) {
            return Result.fail("商品类型不存在！");
        }
        stringRedisTemplate.opsForValue().set(CACHE_SHOP_TYPE_KEY,
                JSONUtil.toJsonStr(sort), CACHE_SHOP_TYPE_TTL, TimeUnit.MINUTES);
        return Result.ok(sort);
    }
}
