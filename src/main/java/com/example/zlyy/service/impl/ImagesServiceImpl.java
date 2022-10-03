package com.example.zlyy.service.impl;

import com.example.zlyy.common.R;
import com.example.zlyy.service.ImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ImagesServiceImpl implements ImagesService {


    @Override
    public R getIndexImages() {
        return R.ok()
                .put("list", new ArrayList<Map<String, Object>>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 1);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/%E7%97%85%E6%83%85%E9%A2%84%E6%B5%8B.webp");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/34121664529428_.pic.jpg");
                                put("url", "https://175.178.242.121/34121664529428_.pic.jpg");

                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 2);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/%E9%98%B2%E7%99%8C%E5%A4%A7%E8%AE%B2%E5%A0%82.png");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/34131664529428_.pic.jpg");
                                put("url", "https://175.178.242.121/34131664529428_.pic.jpg");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 3);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/%E5%81%A5%E5%BA%B7%E7%A7%91%E6%99%AE.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/bingqingyuce.webp");
                                put("url", "https://175.178.242.121/bingqingyuce.webp");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 4);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/hospitalInfo.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/fangaidajiangtang.png");
                                put("url", "https://175.178.242.121/fangaidajiangtang.png");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 5);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/34121664529428_.pic.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/jiankangkepu.jpg");
                                put("url", "https://175.178.242.121/jiankangkepu.jpg");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 6);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/34131664529428_.pic.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/hospitalInfo.jpg");
                                put("url", "https://175.178.242.121/hospitalInfo.jpg");
                            }
                        });
                    }
                });
    }

    @Override
    public R getSwiperImages() {
        return R.ok()
                .put("list", new ArrayList<Map<String, Object>>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 1);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/swiper/swiper_bg.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/swiper/swiper_bg.jpg");
                                put("url", "https://127.0.0.1/swiper/swiper_bg.jpg");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 2);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/swiper/swiper_bg2.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/swiper/swiper_bg2.jpg");
                                put("url", "https://175.178.242.121/swiper/swiper_bg2.jpg");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 3);
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/swiper/swiper_bg3.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/swiper/swiper_bg3.jpg");
                                put("url", "https://175.178.242.121/swiper/swiper_bg3.jpg");
                            }
                        });
                    }
                });
    }

    @Override
    public R getTeamsImages() {
        return R.ok()
                .put("list", new ArrayList<Map<String, Object>>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 1);
                                put("name", "赵平");
                                put("rank", "主任医师");
                                put("expert", "门诊二级专家");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃癌根治性切除及先进的重建消化道手术技术，尤其是胃癌、胃间质瘤手术及综合治疗。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E8%B5%B5%E5%B9%B3.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/zhaoping.jpg");
                                put("url", "https://175.178.242.121/teams/zhaoping.jpg");
                                put("introduce", "省卫健委学术技术带头人，四川省抗癌协会胰腺癌、胃癌专业委员会委员。现任四川省肿瘤医院外科中心胃肠外科中心二病区主任,长期从事胃癌和结肠癌的诊断和治疗工作，拥有了扎实的外科基础和丰富的临床经验。长期独立进行院内及院外手术会诊，主持较多的高难度手术，如残胃癌再切除，贲门癌穿孔急诊切除，胃癌累及胰头时胰十二指肠联合切除术，严重而复杂的胸腹联合伤处理及下腔静脉修补术，多次院内外手术会诊等，使自己的手术技术日趋成熟与完善。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 2);
                                put("name", "唐令超");
                                put("rank", "主任医师");
                                put("expert", "门诊二级专家");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃各种疾病及胃部肿瘤外科现代各种手术治疗，尤其是胃癌、胃肠间质瘤的综合靶向治疗。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E5%94%90%E4%BB%A4%E8%B6%85.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/tanglingchao.jpg");
                                put("url", "https://175.178.242.121/teams/tanglingchao.jpg");
                                put("introduce", "曾任中国抗癌协会胃癌专委会委员，四川省抗癌协会胰腺癌、胃癌专委会副主任委员，四川省医师协会外科医师专委会胃肠间质瘤学组副组长。在多种医学专业期刊上发表医学论文十余篇，参与编写医学专著（副主编）三部，编译（副主译）一部。获省、市科技进步奖各一项。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 3);
                                put("name", "丁志");
                                put("rank", "副主任医师");
                                put("expert", "门诊三级专家");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃癌、贲门癌、残胃癌，胃肠间质瘤等外科现代手术和综合治疗。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E4%B8%81%E5%BF%97.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/dingzhi.jpg");
                                put("url", "https://175.178.242.121/teams/dingzhi.jpg");
                                put("introduce", "四川省医学会外科专委会营养学组成员。从事胃肠专业20年，主要从事胃癌，贲门癌的诊断及治疗，对于残胃癌的再手术治疗，贲门癌经腹手术（减少开胸造成的较多并发症），术前术后综合治疗有独到见解。同时开展多例腹部实性肿瘤手术，联合多器官切除，开展腹腔镜早期胃癌根治术。曾任中国抗癌协会胃癌专委会委员，四川省抗癌协会胰腺癌、胃癌专委会副主任委员，四川省医师协会外科医师专委会胃肠间质瘤学组副组长。在多种医学专业期刊上发表医学论文十余篇，参与编写医学专著（副主编）三部，编译（副主译）一部。获省、市科技进步奖各一项。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 4);
                                put("name", "陈小东");
                                put("rank", "副主任医师");
                                put("expert", "");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃部肿瘤的外科临床诊治。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E9%99%88%E5%B0%8F%E4%B8%9C.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/chenxiaodong.jpg");
                                put("url", "https://175.178.242.121/teams/chenxiaodong.jpg");
                                put("introduce", "医学博士。具有扎实的临床技能和丰富的临床经验，作为主研人参与国家自然科学基金重点项目1项、四川省科技计划项目3项");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 5);
                                put("name", "周祥");
                                put("rank", "副主任医师");
                                put("expert", "");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃癌、胃肠间质瘤等胃恶性肿瘤的诊治。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E5%91%A8%E7%A5%A5.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/zhouxiang.jpg");
                                put("url", "https://175.178.242.121/teams/zhouxiang.jpg");
                                put("introduce", "医学博士，毕业于四川大学华西医院。从事胃肠肿瘤专业十多年，在国内外期刊发表论文十余篇，获省级课题十余项。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 6);
                                put("name", "肖硕萌");
                                put("rank", "主治医师");
                                put("expert", "");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃部肿瘤的外科临床诊治。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E8%82%96%E7%A1%95%E8%90%8C.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/xiaoshuomeng.jpg");
                                put("url", "https://175.178.242.121/teams/xiaoshuomeng.jpg");
                                put("introduce", "医学硕士。主要从事胃癌、胃肠道间质瘤、临床营养支持的基础与临床工作。曾赴日本癌症研究会有明医院研修。参研省级课题4项，共发表论文共10余篇，SCI");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 7);
                                put("name", "徐锐");
                                put("rank", "主治医师");
                                put("expert", "");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃癌、胃间质瘤等胃肠肿瘤的诊治及营养支持治疗。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E5%BE%90%E9%94%90.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/xurui.jpg");
                                put("url", "https://175.178.242.121/teams/xurui.jpg");
                                put("introduce", "徐锐，男，主治医师，四川省医学会肠外肠内营养分会青委秘书。以第一作者身份发表论文6篇，SCI收录2篇，参与四川省科技厅重点研发课题1项。擅长胃癌、胃间质瘤等胃肠肿瘤的诊治及营养支持治疗。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 8);
                                put("name", "赵法芝");
                                put("rank", "主治医师");
                                put("expert", "");
                                put("section", "胃肠外科2区");
                                put("speciality", "擅长胃部肿瘤的临床诊治。");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E8%B5%B5%E6%B3%95%E8%8A%9D.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/zhaofazhi.jpg");
                                put("url", "https://175.178.242.121/teams/zhaofazhi.jpg");
                                put("introduce", "从事胃肿瘤诊断及治疗。成都市抗癌协会MDT专委会委员，《中国普通外科杂志》青年编委。以第一作者身份发表核心期刊4篇，作为课题负责人完成省卫健委科研课题1项，参与卫生部、四川省科技厅课题多项。擅长胃部肿瘤的临床诊治。");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("num", 9);
                                put("name", "专家团队");
                                put("rank", "");
                                put("expert", "");
                                put("section", "");
                                put("speciality", "");
//                                put("url", "https://picgo-dong-1310563377.cos.ap-chengdu.myqcloud.com/zlyy/images/teams/%E4%B8%93%E5%AE%B6%E5%9B%A2%E9%98%9F.jpg");
//                                put("url", "https://biubiubiuer.zlyy-dev.xyz/teams/zhuanjiatuandui.jpg");
                                put("url", "https://175.178.242.121/teams/zhuanjiatuandui.jpg");
                                put("introduce", "");
                            }
                        });
                        
                    }
                });
    }
}
