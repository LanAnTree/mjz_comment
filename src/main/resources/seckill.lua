-- 3.脚本业务
-- 3.1.判断库存是否充足 get stockKey
if(tonumber(redis.call('get', 'seckill:stock:' .. ARGV[1])) <= 0) then
    -- 3.2.库存不足，返回1
    return 1
end
-- 3.2.判断用户是否下单 SISMEMBER orderKey userId
if(redis.call('sismember', 'seckill:order:' .. ARGV[1], ARGV[2]) == 1) then
    -- 3.3.存在，说明是重复下单，返回2
    return 2
end
-- 3.4.扣库存 incrby stockKey -1
redis.call('incrby', 'seckill:stock:' .. ARGV[1], -1)
-- 3.5.下单（保存用户）sadd orderKey userId
redis.call('sadd', 'seckill:order:' .. ARGV[1], ARGV[2])
-- 3.6.发送消息到队列中， XADD stream.orders * k1 v1 k2 v2 ...
redis.call('xadd', 'stream.orders', '*', 'userId', ARGV[2], 'voucherId', ARGV[1], 'id', ARGV[3])
return 0
