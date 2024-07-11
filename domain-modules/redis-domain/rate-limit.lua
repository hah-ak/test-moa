local key = KEYS[1]
local max_requests = tonumber(ARGV[1])
local window_seconds = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

redis.call('ZREMRANGEBYSCORE', key, 0, now - window_seconds)
local count = redis.call('ZCARD', key)

if count < max_requests then
    redis.call('ZADD', key, now, now)
    redis.call('EXPIRE', key, window_seconds)
    return true
else
    return false
end