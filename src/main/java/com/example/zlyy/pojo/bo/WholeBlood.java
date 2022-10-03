package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.zlyy.common.Question;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class WholeBlood extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String baixibao;
    private String zhongxinglixibaoshumu;
    private String linbaxibaoshumu;
    private String danhexibaoshumu;
    private String shisuanxingxibaoshumu;
    private String shijianxingxibaoshumu;
    private String zhongxinglixibaobili;
    private String linbaxibaobili;
    private String danhexibaobili;
    private String shisuanxingxibaobili;
    private String shijianxingxibaobili;
    private String hongxibaoshumu;
    private String xuehongdanbai;
    private String hongxibaoyaji;
    private String pingjunhongxibaotiji;
    private String pingjunhongxibaoxuehongdanbai;
    private String pingjunhongxibaoxuehongdanbainongdu;
    private String hongxibaofenbukuandubianyixishu;
    private String hongxibaofenbukuandubiaozhuncha;
    private String xuexiaobanshumu;
    private String pingjunxuexiaobantiji;
    private String xuexiaobanfenbukuandu;
    private String xuexiaobanyaji;
    private String daxuexiaobanbilv;
    private String wangzhihongxibaojishu;
    private String wangzhihongxibaobaifenlv;
    private String youzhiwangzhihongxibaobaifenbi;
    private String wangzhihongdiyingguangqiangdu;
    private String wangzhihongzhongyingguangqiangdu;
    private String wangzhihonggaoyingguangqiangdu;
    private String chaomincrpzhi;
    private String chaomincrp;
    
    
}
