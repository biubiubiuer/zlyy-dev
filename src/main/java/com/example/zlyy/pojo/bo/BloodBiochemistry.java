package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.zlyy.common.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString()
public class BloodBiochemistry extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String tie;
    private String weijiehetie;
    private String zongtiejieheli;
    private String zongdanhongsu;
    private String zhijiedanhongsu;
    private String jianjiedanhongsu;
    private String zongdanbai;
    private String baidanbai;
    private String qianbaidanbai;
    private String qiudanbai;
    private String baiqiubili;
    private String bingansuananjizhuanyimei;
    private String mendongansuananjizhuanyimei;
    private String yguanxianjizhuanyimei;
    private String fivehegansuanmei;
    private String jianxinglinsuanmei;
    private String zongdanzhisuan;
    private String alyanzaotangganmei;
    private String xiangantuoanmei;
    private String ganjianzhimei;
    private String neishengjiganqingchulv;
    private String niaosu;
    private String jigan;
    private String niaosuan;
    private String putaotang;
    private String jia;
    private String na;
    private String lv;
    private String zongeryanghuatan;
    private String tansuananyanceding;
    private String gai;
    private String lin;
    private String mei;
    private String jisuanjimei;
    private String ckmb;
    private String rusuantuoqingmei;
    private String qiangdingsuantuoqingmei;
    private String zhifangmei;
    private String danguchun;
    private String ganyousanzhi;
    private String gaomiduzhidanbaidanguchun;
    private String dimiduzhidanbaidanguchun;
    private String zaizhidanbaia1;
    private String zaizhidanbaib;
    private String guangyisuc;
    private String shenxiaoqiulvhuolv;
    private String tongxingbanguangansuan;
    private String butic1a;
    
    
    
}
