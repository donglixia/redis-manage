package com.ztt.redistest;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.List;
import java.util.Map;

/**
 * @Author ztt
 */
public class RedisTest {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.233.128",6379);
        //0.key
        jedis.keys("*");
        jedis.exists("list1");
        jedis.exists("list1","aa");
        jedis.expire("list1",10);
        jedis.move("list1",1);
        jedis.type("list1");
        //有效时间，失效返回-2，永远有效为-1
        jedis.ttl("jie");

        //1.字符串类型
        String aa = jedis.get("aa");
        jedis.append("aa","haah");
        //可以自增自减，并可以设置步长
        jedis.flushAll();
        jedis.set("shu","9");
        String shu2 = jedis.get("shu");
        Long shu = jedis.incr("shu");
        Long shu4 = jedis.incrBy("shu",4);
        Long shu1 = jedis.decr("shu");
        //自动截取字符串 like substr
        jedis.flushAll();
        jedis.set("jie","8,r8eq,tere");
        String jie = jedis.getrange("jie", 0, 4);
        String jie1 = jedis.getrange("jie", 0, -1);
        System.out.println(aa);
        //替换  一样长度的字符串，多了就把多的补在后面  ，替换固定长度
        jedis.set("jie","8,r8eq,te");
        Long setrange = jedis.setrange("jie", 7, "gai");
        String jie2 = jedis.get("jie");
        //setnx  setex
        //不存在则设置
        Long setnx = jedis.setnx("jie", "new");
        String jie5 = jedis.get("jie");
        //设置值得同时设置有效期
        jedis.setex("jie",10,"fddf");
        //批量设置msetnx
        jedis.mset("key1","value1","key2","value2","key3","value3");
        List<String> mget = jedis.mget("key1", "jie", "key2");
        //对象
        jedis.set("student:赵敏","{name:1,age:2}");
        jedis.mset("student:赵敏:name","1","student:赵敏:age","2");

        //获取返回原来的值，设置新的值
        String set = jedis.getSet("aa", "newValue");

        //string 了类似的使用场景：value除了字符串还可以是数字
        //1.计数器
        //2.统计多单位的数量  uid:4353533:follow 0
        //3.粉丝数
        //4.对象存储


        //2.list 列表   有序可重列表    就是一个有序的管子，可作出可右出，可左进可右进
        jedis.lpush("list1","小明");
        jedis.lpush("list1","找个哦");
        jedis.lpush("list1","王二麻子");
        jedis.lindex("list1",2);
        jedis.rpush("list1","王二麻子");
        //全部查出来
        List<String> list11 = jedis.lrange("list1", 0, -1);
        //弹出最后一个
        String list1 = jedis.lpop("lisjedist1");
        jedis.llen("lisjedist1");
        //移除3个value，移除指定个数的值
        jedis.lrem("key",3,"value");
        //截取剩下1-3的值
        jedis.ltrim("key",1,3);
        //移除最后一个元素，并把它放到新的列表中
        jedis.rpoplpush("key","tt");
        //将指定下标的值替换成另外一个值,更新值操作，不存在会报错
        jedis.lset("key",3,"dds");
        //插入一个值
        jedis.linsert("key", BinaryClient.LIST_POSITION.AFTER,"dwdw","ddd");
        System.out.println(list1);
        //场景：消息队列

        //set中的值是不能重复的
        jedis.sadd("set_key","haha");
        //查看set中所有值
        jedis.smembers("set_key");
        //判断是否包含某个值
        jedis.sismember("set_key","sds");
        //元素个数
        jedis.scard("set_key");
        //srem
        //随机取值,取指定个数的值
        jedis.srandmember("set_key",3);
        jedis.srandmember("set_key");
        //随机移除元素，移除指定元素的
        //将一个集合中的元素移到另一个集合中
        //差集，交集，并集
        //共同关注，共同爱好，推荐好友

        //=========================hash====================
        //key-<key,value>
        //删除hash指定key，更新hash指定key的值，其实就是map对应的各种操作
        //指定增量，如果不存在设置，存在则不设置
//        jedis.hget();
//        jedis.hexists();
//        jedis.hdel()
        //jedis.hset()
        //修改
     //   jedis.hmset();//存在修改，不存在插入
        //经常变更的对象存储
//        jedis.hvals()//返回所有值
//        jedis.hkeys()//返回所有key
        Map<String, String> dsds = jedis.hgetAll("dsds");//返回map

        //zset  (有序集合)
        jedis.zadd("ddasd",23.4,"params");
        //根据字段排序获取值,可以加加分数
        //带分数的set用以排序
        //取区间的个数，区间的值
        //加权重set，统计数据，排行榜应用实现

        //geospatal  地理位置
        //两级无法直接添加，一般下载城市数据，程序导入（纬度，经度，名称）
        //朋友的定位，附近的人，打车距离,两地距离
        //多了一些针对对应数据结构的操作
        jedis.geoadd("中国",2132,313,"上海");

        //获取指定名称的经纬度
        //jedis.geodist() 距离
        //List<GeoRadiusResponse> georadius = jedis.georadius();//可以根据人，可以根据经纬度查询半径内的人

        //jedis.georadiusByMember(GeoRadiusParam)//还可以指定顺序个数
        //geo底层使用的是zset，我们可以通过zset操作geo

        //=======================hyperloglog=============================
        //基数统计的算法，2的64次方只需要12kb，减少内存使用，但是会存在误差
        //jedis.pfadd();  0.81%失误率

        //==========================bitmaps位存储============================
        //统计用户信息，活跃，不活跃，登录，未登录，打卡，
        //位图，数据结构  ，通过位存储大量的是否的数据
        //两个状态存储
        jedis.setbit("张三",1,"0");


        //redis 单条命令是保证原子性，事务不保证原子性
        //没有事务隔离级别的概念，所有命令入队不会执行吗，执行是才会一次执行
        //一致性，顺序性。排他性
        //开启输完液，命令入队，执行事务
        //事务中，编译型错误，全都不会执行；运行时异常，语法错误。跑错，其他命令正常执行（incr非数字等），不保证原子性

        //测试多线程修改值，使用watch可以当做redis的乐观锁




//        //分布式锁工具
//        Redisson redisson = new Redisson();
//        RLock lock = redisson.getLock();
//        lock.lock();

    }
}
