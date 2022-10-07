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
public class Urine extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String yanse;
    private String toumingdu;
    private String bizhong;
    private String suanjiandu;
    private String yinxue;
    private String zhongxinglixibaozhimei;
    private String niaodanbai;
    private String putaotang;
    private String tongti;
    private String yaxiaosuanyan;
    private String danhongsu;
    private String niaodanyuan;
    private String hongxibaodingliangfenxi;
    private String baixibaodingliangfenxi;
    private String xiaoyuanshangpixibao;
    private String shangpixibaodingliangfenxi;
    private String guanxingdingliangfenxi;
    private String bingliguanxing;
    private String toumingguanxing;
    private String feitoumingguanxing;
    private String xijun;
    private String diandaolv;
    private String jiejingshuliang;
    private String hongxibaoxingtai;
    private String sijiaomuyangjun;
    private String hongxibaojingjian;
    private String baixibaojingjian;
    private String nongxibao;
    private String nianyesi;
    private String shangpixibaojingjian;
    private String guanxingjingjian;
    private String jiejing;
    private String qita;
    private String niaobaidanbai;
    private String niaojigan;
    private String niaobaidanbaiji;
}
