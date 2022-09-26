package com.example.zlyy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GetterTest {
    
    static Logger logger = LoggerFactory.getLogger(GetterTest.class);
            
    
    @Getter
    @Setter
    static
    class A {
        private Integer A;
        private Integer B;
        private Integer C;
        
        public void haha(Integer A, Integer B, Integer C) {
            test();
        }
    }
    
    private static void test() {
        A template = new A();
        template.setA(1);
        template.setB(2);
        template.setC(3);
        List<Field> fields = Arrays.stream(template.getClass().getDeclaredFields()).filter(f -> {
            String name = f.getName();
            //过滤掉不需要修改的属性
            return !"id".equals(name) && !"serialVersionUID".equals(name);
        }).collect(Collectors.toList());

        for (Field field : fields) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), template.getClass());
                Method readMethod = descriptor.getReadMethod();
                String name = descriptor.getName();
                
//                Method[] methods = template.getClass().getMethods();
//                for (Method method : methods) {
//                    logger.debug("method: " + method);
//                }
                
//                logger.debug("name: " + name);
//                logger.debug("readMethod: " + readMethod.toString());
                Object o = readMethod.invoke(template);
                logger.debug("getter method : {} ", o);
                logger.debug("getter method to string: {} ", o.toString());
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } 
    }

    public static void main(String[] args) {
        test();
    }
    
}
