【${docName}】

dubbo接口文档
<#--${time?string("yyyy-MM-dd")}-->

版本历史

|文档版本|撰写时间|变更人|属性|
|--|--|--|--|
|V1.0|2018-04-10|${author}|新增|


# 依赖地址
Maven：

~~~
<dependency>
    <groupId>credit-yooli</groupId>
    <artifactId>credit-fourelement-intf</artifactId>
    <version>4.0.1</version>
</dependency>
~~~


# 调用说明
<#list methodZS as method>

## 接口

`${method.responses[0].className} ${method.methodName}(${method.requests[0].className})`


* 1.请求参数

    <#list method.requests as request>
    接口请求参数：`${request.className}`

        <#if request.fieldList ?? && (request.fieldList ?size > 0) >

|属性名|类型|是否必要|说明|
|--|--|--|--|
<#list request.fieldList as rf>|${rf.fieldName}|${rf.type}|是|${rf.desc}|
</#list>
        </#if>
    </#list>


* 2.返回值

<#list method.responses as response>
接口返回参数：`${response.className}`

|属性名|类型|说明|
|--|--|--|
    <#if response.fieldList ?? && (response.fieldList ?size > 0) >
<#list response.fieldList as rf>|${rf.fieldName}|${rf.type}|${rf.desc}|
</#list>
    </#if>
</#list>
</#list>

* 3.返回码

征信平台的接口返回值返回码分为几种类型：

返回码|    说明
--|--
L0000    |查询第三方成功且查得结果
L0001|    查询第三方成功但未查得结果
L0002|    业务方token没有当前调用接口的权限
L0003|    业务方token不合法
L0004    |参数校验失败
L0005|    调用方ip白名单验证未通过
L0006|    业务方接口配置出错
L0009|    第三方调用失败或征信内部错误
L0010|    查询第三方成功且查得结果，不收费
-402|    征信调用第三方超时触发服务降级
-403|    征信调用第三方频繁失败触发熔断
其他|    第三方返回码（异常情况）或http异常码


# 征信服务环境
* 1.beta环境
Zookeeper：172.16.2.158:2181
服务地址：172.16.2.158 / 172.16.2.159
* 2.生产环境
Zookeeper集群：zk1.service.yooli.cn:5181,zk2.service.yooli.cn:5181,zk3.service.yooli.cn:5181,zk4.service.yooli.cn:5181,zk5.service.yooli.cn:5181
hosts配置：192.168.1.131:5181,192.168.1.132:5181,192.168.1.133:5181,192.168.1.134:5181,192.168.1.135:5181
服务地址：192.168.4.124 / 192.168.4.125

* 3.接口配置：
版本：1.0.0
超时时间：默认为10秒，重试次数建议为0
配置文件示例：

~~~
<dubbo:registry protocol="zookeeper" address="172.16.2.158:2181"/>
<dubbo:consumer timeout="120000" retries="0" check="false"/>

<dubbo:reference interface="${packageName}.${className}"
                 id="${className}" version="1.0.0"/>
~~~
