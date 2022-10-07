---
--- Generated by Luanalysis
--- Created by biubiubiuer.
--- DateTime: 2022/10/7 14:13
---

-- 1. 参数列表
-- 1.1 token
local token = ARGV[1]

-- 2. 数据 key
-- 2.1 token key
local tokenKey = "token:" .. token

-- 3. 脚本业务
-- 3.1 判断 redis 里是否存在键: "tokenKey"
local is_exists = redis.call("EXISTS", tokenKey)

if is_exists == 1 then
    redis.call("DEL", tokenKey)
    return 1
else
    return 0
end


