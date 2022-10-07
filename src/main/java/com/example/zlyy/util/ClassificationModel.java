package com.example.zlyy.util;

import lombok.extern.slf4j.Slf4j;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.jpmml.model.PMMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zlyy.util.StringConstants.PMML_PATH;

@Slf4j
public class ClassificationModel {
    
    private static final Logger logger = LoggerFactory.getLogger(ClassificationModel.class);
    private Evaluator modelEvaluator;

    /**
     * 通过传入 PMML 文件路径来生成机器学习模型
     *
     * @param pmmlFileName pmml 文件路径
     */
    public ClassificationModel(String pmmlFileName) {
        PMML pmml = null;

        try {
            if (pmmlFileName != null) {
                InputStream is = new FileInputStream(pmmlFileName);
                pmml = PMMLUtil.unmarshal(is);
                try {
                    is.close();
                } catch (IOException e) {
//                    System.out.println("InputStream close error!");
                    logger.error("InputStream close error!");
                }

                ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

                this.modelEvaluator = (Evaluator) modelEvaluatorFactory.newModelEvaluator(pmml);
                modelEvaluator.verify();
//                System.out.println("加载模型成功！");
                logger.debug("加载模型成功!");
                
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 获取模型需要的特征名称
    public List<String> getFeatureNames() {
        List<String> featureNames = new ArrayList<String>();

        List<InputField> inputFields = modelEvaluator.getInputFields();

        for (InputField inputField : inputFields) {
            featureNames.add(inputField.getName().toString());
        }
        return featureNames;
    }

    // 获取目标字段名称
    public String getTargetName() {
        return modelEvaluator.getTargetFields().get(0).getName().toString();
    }

    // 使用模型生成概率分布
    private ProbabilityDistribution getProbabilityDistribution(Map<FieldName, ?> arguments) {
        Map<FieldName, ?> evaluateResult = modelEvaluator.evaluate(arguments);

        FieldName fieldName = new FieldName(getTargetName());

        return (ProbabilityDistribution) evaluateResult.get(fieldName);

    }

    // 预测不同分类的概率
    public ValueMap<String, Number> predictProba(Map<FieldName, Number> arguments) {
        ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);
        return probabilityDistribution.getValues();
    }

    // 预测结果分类
    public Object predict(Map<FieldName, ?> arguments) {
        ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);

        return probabilityDistribution.getPrediction();
    }

    public static void main(String[] args) {
        ClassificationModel clf = new ClassificationModel(PMML_PATH);

        List<String> featureNames = clf.getFeatureNames();
        System.out.println("feature: " + featureNames);

        // 构建待预测数据
        Map<FieldName, Number> waitPreSample = new HashMap<>();
        waitPreSample.put(new FieldName("0"), 1);
        waitPreSample.put(new FieldName("1"), 1958);
        waitPreSample.put(new FieldName("2"), 14);
        waitPreSample.put(new FieldName("3"), 178);
        waitPreSample.put(new FieldName("4"), 47);
        waitPreSample.put(new FieldName("5"), 65);
        waitPreSample.put(new FieldName("6"), 4);
        waitPreSample.put(new FieldName("7"), 6);
        waitPreSample.put(new FieldName("8"), 1);
        waitPreSample.put(new FieldName("9"), 7);
        waitPreSample.put(new FieldName("10"), 2);
        waitPreSample.put(new FieldName("11"), 4);
        waitPreSample.put(new FieldName("12"), 2);
        waitPreSample.put(new FieldName("13"), 6);
        waitPreSample.put(new FieldName("14"), 3);
        waitPreSample.put(new FieldName("15"), 1);
        waitPreSample.put(new FieldName("16"), 3);
        waitPreSample.put(new FieldName("17"), 2);
        waitPreSample.put(new FieldName("18"), 2);
        waitPreSample.put(new FieldName("19"), 2);
        waitPreSample.put(new FieldName("20"), 3);
        waitPreSample.put(new FieldName("21"), 2);
        waitPreSample.put(new FieldName("22"), 4);
        waitPreSample.put(new FieldName("23"), 3);
        waitPreSample.put(new FieldName("24"), 3);
        waitPreSample.put(new FieldName("25"), 1);
        waitPreSample.put(new FieldName("26"), 1);
        waitPreSample.put(new FieldName("27"), -3);
        waitPreSample.put(new FieldName("28"), -3);
        waitPreSample.put(new FieldName("29"), -3);
        waitPreSample.put(new FieldName("30"), 1);
        waitPreSample.put(new FieldName("31"), 39);
        waitPreSample.put(new FieldName("32"), 1);
        waitPreSample.put(new FieldName("33"), -3);
        waitPreSample.put(new FieldName("34"), -3);
        waitPreSample.put(new FieldName("35"), -3);
        waitPreSample.put(new FieldName("36"), -3);
        waitPreSample.put(new FieldName("37"), -3);
        waitPreSample.put(new FieldName("38"), -3);
        waitPreSample.put(new FieldName("39"), 4);
        waitPreSample.put(new FieldName("40"), 1);
        waitPreSample.put(new FieldName("41"), 1);
        waitPreSample.put(new FieldName("42"), 4);
        waitPreSample.put(new FieldName("43"), 1);
        waitPreSample.put(new FieldName("44"), 2);
        waitPreSample.put(new FieldName("45"), 3);
        waitPreSample.put(new FieldName("46"), 4);
        waitPreSample.put(new FieldName("47"), 1);
        waitPreSample.put(new FieldName("48"), -3);
        waitPreSample.put(new FieldName("49"), -3);
        waitPreSample.put(new FieldName("50"), -3);
        waitPreSample.put(new FieldName("51"), -3);
        waitPreSample.put(new FieldName("52"), -3);
        waitPreSample.put(new FieldName("53"), -3);
        waitPreSample.put(new FieldName("54"), -3);
        waitPreSample.put(new FieldName("55"), -3);
        waitPreSample.put(new FieldName("56"), -3);
        waitPreSample.put(new FieldName("57"), -3);
        waitPreSample.put(new FieldName("58"), 1);
        waitPreSample.put(new FieldName("59"), 1);
        waitPreSample.put(new FieldName("60"), 0);
        waitPreSample.put(new FieldName("61"), 0);
        waitPreSample.put(new FieldName("62"), 0);
        waitPreSample.put(new FieldName("63"), 0);
        waitPreSample.put(new FieldName("64"), 1);
        waitPreSample.put(new FieldName("65"), -3);
        waitPreSample.put(new FieldName("66"), -3);
        waitPreSample.put(new FieldName("67"), -3);
        waitPreSample.put(new FieldName("68"), -3);
        waitPreSample.put(new FieldName("69"), -3);
        waitPreSample.put(new FieldName("70"), -3);
        waitPreSample.put(new FieldName("71"), -3);
        waitPreSample.put(new FieldName("72"), -3);
        waitPreSample.put(new FieldName("73"), -3);
        waitPreSample.put(new FieldName("74"), -3);
        waitPreSample.put(new FieldName("75"), -3);
        waitPreSample.put(new FieldName("76"), -3);
        waitPreSample.put(new FieldName("77"), -3);
        waitPreSample.put(new FieldName("78"), -3);
        waitPreSample.put(new FieldName("79"), 1);
        waitPreSample.put(new FieldName("80"), -3);
        waitPreSample.put(new FieldName("81"), -3);
        waitPreSample.put(new FieldName("82"), -3);
        waitPreSample.put(new FieldName("83"), -3);
        waitPreSample.put(new FieldName("84"), -3);
        waitPreSample.put(new FieldName("85"), -3);
        waitPreSample.put(new FieldName("86"), -3);
        waitPreSample.put(new FieldName("87"), -3);
        waitPreSample.put(new FieldName("88"), -3);
        waitPreSample.put(new FieldName("89"), -3);
        waitPreSample.put(new FieldName("90"), -3);
        waitPreSample.put(new FieldName("91"), -3);
        waitPreSample.put(new FieldName("92"), -3);
        waitPreSample.put(new FieldName("93"), -3);
        waitPreSample.put(new FieldName("94"), -3);
        waitPreSample.put(new FieldName("95"), -3);
        waitPreSample.put(new FieldName("96"), 1);
        waitPreSample.put(new FieldName("97"), -3);
        waitPreSample.put(new FieldName("98"), -3);
        waitPreSample.put(new FieldName("99"), -3);
        waitPreSample.put(new FieldName("100"), -3);
        waitPreSample.put(new FieldName("101"), -3);
        waitPreSample.put(new FieldName("102"), -3);
        waitPreSample.put(new FieldName("103"), -3);
        waitPreSample.put(new FieldName("104"), -3);
        waitPreSample.put(new FieldName("105"), 1);
        waitPreSample.put(new FieldName("106"), -3);
        waitPreSample.put(new FieldName("107"), -3);
        waitPreSample.put(new FieldName("108"), 2);


        System.out.println("waitPreSample predict result: " + clf.predict(waitPreSample).toString());
        System.out.println("waitPreSample predictProba result: " + clf.predictProba(waitPreSample).toString());
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);
        String str = format.format(Double.valueOf(clf.predictProba(waitPreSample).toString().substring(3, 7)));
        System.out.println(str);

    }

}
