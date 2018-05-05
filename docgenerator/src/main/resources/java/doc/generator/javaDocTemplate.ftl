【${docName}】

dubbo接口文档
${time?string("yyyy-MM-dd")}

版本历史

|文档版本|撰写时间|变更人|属性|
|--|--|--|--|
|V1.0|${time?string("yyyy-MM-dd")}|${author}|新增|


# 依赖地址
Maven：

~~~
//补充
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

