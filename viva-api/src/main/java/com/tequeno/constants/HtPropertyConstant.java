package com.tequeno.constants;

public class HtPropertyConstant {

    /**
     * 数字字符串，应用于获取随机数验证码
     */
    public final static String NUMBER_STR = "0123456789";

    public final static String LETTER_STR = "abcdefghijklmnopqrstuvwxyz";

    public final static String CAPTCHA = "captcha";

    public final static String CAPTCHA_TYPE = "png";

    /**
     * 验证码长度
     */
    public final static int OTP_LENGTH = 6;

    /**
     * 验证码重新获取时间间隔 60*1000=1min
     */
    public final static long OTP_RETRY = 60000L;

    /**
     * 验证码失效时间 5*60*1000=5min
     */
    public final static long OTP_EXPIRED = 300000L;

    /**
     * session超时时间 60*30=30min
     */
    public final static long SESSION_TIMEOUT = 1800L;

    /**
     * redis key默认超时时间 30*24*60*60*1000=30day
     */
    public final static long DEFAULT_REDIS_KEY_TIMEOUT = 2592000000L;

    /**
     * 序列号 key默认超时时间 24*60*60*1000=1day
     */
    public final static long ONE_DAY = 86400000L;

    /**
     * 序列数字后缀
     */
    public final static String SEQ_SUFFIX = "0000";

    /**
     * 默认管理的session名称 区别于容器默认session名
     */
    public final static String DEFAULT_SESSION_NAME = "redSessionId";

    /**
     * rememberme cookie 名称
     */
    public final static String DEFAULT_REMEMBERME_NAME = "rememberMe";

    /**
     * rememberme cookie 最长缓存时间 60*60*24*30=30day
     */
    public final static int DEFAULT_REMEMBERME_COOKIE_TIMEOUT = 2592000;

    /**
     * rememberme 默认 cipher key
     */
    public final static String DEFAULT_CIPHER_KEY = "4AvVhmFLUs0KTA3Kprsdag==";

    /**
     * 系统默认使用UTF-8编码
     */
    public final static String CHARSET_UTF8 = "UTF-8";

    /**
     * MD5关键字
     */
    public final static String ALGORITHM_MD5 = "MD5";

    public final static String REG_PHONE = "^(13[0-9]|14[5,7]|15[0-3,5-9]|17[0,3,5-8]|18[0-9]|166|198|199|147)\\d{8}$";

    public final static String REG_MAIL = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
    //"^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"



    /**
     * jms主题订阅模式容器bean名字
     */
    public final static String TOPIC_CONTAINER_FACTORY = "jmsListenerContainerTopic";

    /**
     * jms队列模式容器bean名字
     */
    public final static String QUEUE_CONTAINER_FACTORY = "jmsListenerContainerQueue";

    /**
     * 队列名字
     */
    public final static String QUEUE_SCHEDULED_NAME = "scheduledQueue";
    public final static String QUEUE_SIMPLE_NAME = "simpleQueue";

    /**
     * 主题名字
     */
    public final static String TOPIC_SCHEDULED_NAME = "scheduledTopic";
    public final static String TOPIC_SIMPLE_NAME = "simpleTopic";

    public final static String ROCKET_TAG_A = "r_tag_a";
    public final static String ROCKET_TAG_B = "r_tag_b";
    public final static String PRODUCER_INSTANCE_NAME_A = "producerInstanceA";
    public final static String PRODUCER_INSTANCE_NAME_B = "producerInstanceB";
    public final static String CONSUMER_INSTANCE_NAME_A = "consumerInstanceA";
    public final static String CONSUMER_INSTANCE_NAME_B = "consumerInstanceB";
}
