package com.example.zlyy.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString()
//@Accessors(chain = true)
public class MultiOptionQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Map<String, Object> D04Map;
    private Map<String, Object> D0202Map;
    private Map<String, Object> E0101Map;
    private Map<String, Object> E0102Map;
    private Map<String, Object> E0201Map;
    private Map<String, Object> E0202Map;
    private Map<String, Object> F0101Map;
    
    
}
