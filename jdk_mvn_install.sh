#!/bin/bash
# check and install java1.8 and maven3.85

echo "check java version"
java -version
if [ $? -ne 0 ]; then
    echo -e "java not found,install java:"
    result=$(uname -m) 
    echo "machine:$result"
    if [ "$result" = "x86_64" ]; then
        url="https://mirrors.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-linux-x64.tar.gz"
    elif [ "$result" = "arm64" ]; then
        url="https://mirrors.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-linux-arm64-vfp-hflt.tar.gz"
    elif [ "$result" = "aarch64" ]; then
        url="https://mirrors.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-linux-arm64-vfp-hflt.tar.gz"
    else 
        echo "unknow machine,please install java manually"
        exit 1
    fi
    wget -nc $url  #download if not exists
    sudo mkdir /usr/lib/jvm
    if [ "$result" = "x86_64" ]; then
      sudo tar -xvf jdk-8u202-linux-x64.tar.gz -C /usr/lib/jvm
    elif [ "$result" = "arm64" ]; then
      sudo tar -xvf jdk-8u202-linux-arm64-vfp-hflt.tar.gz -C /usr/lib/jvm
    elif [ "$result" = "aarch64" ]; then
      sudo tar -xvf jdk-8u202-linux-arm64-vfp-hflt.tar.gz -C /usr/lib/jvm
    else 
      echo "unknow machine,please install java manually"
      exit 1
    fi 

    echo '#Java Env' | sudo tee -a /etc/profile 
    echo 'export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_202' | sudo tee -a /etc/profile
    echo 'export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar' | sudo tee -a /etc/profile
    echo 'export PATH=$PATH:$JAVA_HOME/bin' | sudo tee -a /etc/profile
    # rm -rf  jdk-11u202-linux-x64.tar.gz 
fi

echo -e "\ncheck maven version"
mvn -v
if [ $? -ne 0 ]; then
    echo -e "maven not found,install maven:\n"  
    wget -nc --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz
    sudo tar -zxvf  apache-maven-3.8.6-bin.tar.gz -C /usr/local/

    echo 'export MAVEN_HOME=/usr/local/apache-maven-3.8.6' | sudo tee -a /etc/profile
    echo 'export PATH=$PATH:$MAVEN_HOME/bin' | sudo tee -a /etc/profile
    
    sudo tar -zxvf  apache-maven-3.8.6-bin.tar.gz -C /usr/local/
    
    sudo mv /usr/local/apache-maven-3.8.6/conf/settings.xml /usr/local/apache-maven-3.8.6/conf/settings.xml.bak
    sudo cp settiings.xml /usr/local/apache-maven-3.8.6/conf/
 
fi

source /etc/profile
