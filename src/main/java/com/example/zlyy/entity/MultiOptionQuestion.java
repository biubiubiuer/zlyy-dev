package com.example.zlyy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("tb_multi_option_questtion")
public class MultiOptionQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Map<String, Object> D04Map;
    private Map<String, Object> D0202Map;

    public Map<String, Object> getD04Map() {
        return D04Map;
    }

    public Map<String, Object> getD0202Map() {
        return D0202Map;
    }

    public Map<String, Object> getE0101Map() {
        return E0101Map;
    }

    public Map<String, Object> getE0102Map() {
        return E0102Map;
    }

    public Map<String, Object> getE0201Map() {
        return E0201Map;
    }

    public Map<String, Object> getE0202Map() {
        return E0202Map;
    }

    public Map<String, Object> getF0101Map() {
        return F0101Map;
    }

    private Map<String, Object> E0101Map;
    private Map<String, Object> E0102Map;
    private Map<String, Object> E0201Map;
    private Map<String, Object> E0202Map;
    private Map<String, Object> F0101Map;
    
    
}
