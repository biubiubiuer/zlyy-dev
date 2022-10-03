package com.example.zlyy.util;

import com.example.zlyy.pojo.bo.QUserInfo;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayConstants {
    public static final String[] QUES_KEYS = new String[] {
            "sex", "birthYear", "durable",
            "A01", "A02", "A03", "A04", "A05", "A06", "A07",
            "B01", "B0201", "B0202", "B0203", "B0204", "B0205", "B0206", "B03", "B0401", "B0402", "B0403", "B0404", "B0405", "B0406", "B05",
            "C01", "C02", "C0201", "C0202", "C0203", "C03", "C0301", "C04", "C0401", "C0402", "C0403", "C0404", "C0405", "C0406", "C05", "C06", "C07", "C08", "C09", "C10", "C11",
            "D01", "D0201", "D0202Map", "D03", "D04Map",
            "E01", "E0101Map", "E0102Map", "E02", "E0201Map", "E0202Map",
            "F01", "F0101Map", "F0102", "F02", "F0201", "F0202", "F03"
    };
    
    public static final Integer[] QUES_VALUES = new Integer[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,
            33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 58, 59, 64, 65, 71, 79, 80, 86, 96, 97,
            104, 105, 106, 107, 108
    };
    
    public static final String[] QUES_KEYS_1 = new String[] {
            "tie", "weijiehetie", "zongtiejieheli", "zongdanhongsu", "zhijiedanhongsu", "jianjiedanhongsu", 
            "zongdanbai", "baidanbai", "qianbaidanbai", "qiudanbai", "baiqiubili", "bingansuananjizhuanyimei", 
            "mendongansuananjizhuanyimei", "yguanxianjizhuanyimei", "fivehegansuanmei", "jianxinglinsuanmei", 
            "zongdanzhisuan", "alyanzaotangganmei", "xiangantuoanmei", "ganjianzhimei", "neishengjiganqingchulv", 
            "niaosu", "jigan", "niaosuan", "putaotang", "jia", "na", "lv", "zongeryanghuatan", "tansuananyanceding", 
            "gai", "lin", "mei", "jisuanjimei", "ckmb", "rusuantuoqingmei", "qiangdingsuantuoqingmei", 
            "zhifangmei", "danguchun", "ganyousanzhi", "gaomiduzhidanbaidanguchun", "dimiduzhidanbaidanguchun", 
            "zaizhidanbaia1", "zaizhidanbaib", "guangyisuc", "shenxiaoqiulvhuolv", "tongxingbanguangansuan", 
            "butic1a"
    };
    
    public static final String[] QUES_KEYS_2 = new String[] {
            "baixibao", "zhongxinglixibaoshumu", "linbaxibaoshumu", "danhexibaoshumu", "shisuanxingxibaoshumu", 
            "shijianxingxibaoshumu", "zhongxinglixibaobili", "linbaxibaobili", "danhexibaobili", 
            "shisuanxingxibaobili", "shijianxingxibaobili", "hongxibaoshumu", "xuehongdanbai", 
            "hongxibaoyaji", "pingjunhongxibaotiji", "pingjunhongxibaoxuehongdanbai", "pingjunhongxibaoxuehongdanbainongdu", 
            "hongxibaofenbukuandubianyixishu", "hongxibaofenbukuandubiaozhuncha", "xuexiaobanshumu", 
            "pingjunxuexiaobantiji", "xuexiaobanfenbukuandu", "xuexiaobanyaji", "daxuexiaobanbilv", 
            "wangzhihongxibaojishu", "wangzhihongxibaobaifenlv", "youzhiwangzhihongxibaobaifenbi", 
            "wangzhihongdiyingguangqiangdu", "wangzhihongzhongyingguangqiangdu", "wangzhihonggaoyingguangqiangdu", 
            "chaomincrpzhi", "chaomincrp"
    };
    
    public static final String[] QUES_KEYS_3 = new String[] {
            "yanse", "xingzhuang", "nianye", "bianxue", "baixibao", "hongxibao", "nongxibao", 
            "tunshixibao", "dianfenkeli", "zhifangli", "sijiaomujun", "yinxue", "qita"
    };
    
    public static final String[] QUES_KEYS_4 = new String[] {
            "yanse", "toumingdu", "bizhong", "suanjiandu", "yinxue", "zhongxinglixibaozhimei", "niaodanbai", 
            "putaotang", "tongti", "yaxiaosuanyan", "danhongsu", "niaodanyuan", "hongxibaodingliangfenxi", 
            "baixibaodingliangfenxi", "xiaoyuanshangpixibao", "shangpixibaodingliangfenxi", 
            "guanxingdingliangfenxi", "bingliguanxing", "toumingguanxing", "feitoumingguanxing", "xijun", 
            "diandaolv", "jiejingshuliang", "hongxibaoxingtai", "sijiaomuyangjun", "hongxibaojingjian", 
            "baixibaojingjian", "nongxibao", "nianyesi", "shangpixibaojingjian", "guanxingjingjian", 
            "jiejing", "qita", "niaobaidanbai", "niaojigan", "niaobaidanbaiji"
    };

}
