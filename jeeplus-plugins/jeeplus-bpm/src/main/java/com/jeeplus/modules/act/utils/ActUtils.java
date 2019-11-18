/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.common.utils.SpringContextHolder;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.annotation.FieldName;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.utils.ObjectUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;

/**
 * 流程工具
 *
 * @author jeeplus
 * @version 2016-11-03
 */
public class ActUtils {

    //	private static Logger logger = LoggerFactory.getLogger(ActUtils.class);
    private static JeePlusProperites jeePlusProperites = SpringContextHolder.getBean(JeePlusProperites.class);


    @SuppressWarnings({"unused"})
    public static Map<String, Object> getMobileEntity(Object entity, String spiltType) {
        if (spiltType == null) {
            spiltType = "@";
        }
        Map<String, Object> map = Maps.newHashMap();

        List<String> field = Lists.newArrayList();
        List<String> value = Lists.newArrayList();
        List<String> chinesName = Lists.newArrayList();

        try {
            for (Method m : entity.getClass().getMethods()) {
                if (m.getAnnotation(JsonIgnore.class) == null && m.getAnnotation(JsonBackReference.class) == null && m.getName().startsWith("get")) {
                    if (m.isAnnotationPresent(FieldName.class)) {
                        Annotation p = m.getAnnotation(FieldName.class);
                        FieldName fieldName = (FieldName) p;
                        chinesName.add(fieldName.value());
                    } else {
                        chinesName.add("");
                    }
                    if (m.getName().equals("getAct")) {
                        Object act = m.invoke(entity, new Object[]{});
                        Method actMet = act.getClass().getMethod("getTaskId");
                        map.put("taskId", ObjectUtils.toString(m.invoke(act, new Object[]{}), ""));
                    } else {
                        field.add(StringUtils.uncapitalize(m.getName().substring(3)));
                        value.add(ObjectUtils.toString(m.invoke(entity, new Object[]{}), ""));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("beanTitles", StringUtils.join(field, spiltType));
        map.put("beanInfos", StringUtils.join(value, spiltType));
        map.put("chineseNames", StringUtils.join(chinesName, spiltType));

        return map;
    }

    /**
     * 获取流程表单URL
     *
     * @param formKey
     * @param act     表单传递参数
     * @return
     */
    public static String getFormUrl(String formKey, Act act) {
        StringBuilder formUrl = new StringBuilder();
       // String formServerUrl = jeePlusProperites.getActivitiFormServerUrl();
        String formServerUrl ="";
        if (StringUtils.isBlank(formServerUrl)) {
            formUrl.append(jeePlusProperites.getAdminPath());
        } else {
            formUrl.append(formServerUrl);
        }

        formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
        formUrl.append("act.taskId=").append(act.getTaskId() != null ? act.getTaskId() : "");
        formUrl.append("&act.taskName=").append(act.getTaskName() != null ? Encodes.urlEncode(act.getTaskName()) : "");
        formUrl.append("&act.taskDefKey=").append(act.getTaskDefKey() != null ? act.getTaskDefKey() : "");
        formUrl.append("&act.procInsId=").append(act.getProcInsId() != null ? act.getProcInsId() : "");
        formUrl.append("&act.procDefId=").append(act.getProcDefId() != null ? act.getProcDefId() : "");
        formUrl.append("&act.status=").append(act.getStatus() != null ? act.getStatus() : "");
        formUrl.append("&act.isNextGatewaty=").append(act.getIsNextGatewaty());
        formUrl.append("&id=").append(act.getBusinessId() != null ? act.getBusinessId() : "");

        return formUrl.toString();
    }

    /**
     * 转换流程节点类型为中文说明
     *
     * @param type 英文名称
     * @return 翻译后的中文名称
     */
    public static String parseToZhType(String type) {
        Map<String, String> types = new HashMap<String, String>();
        types.put("userTask", "用户任务");
        types.put("serviceTask", "系统任务");
        types.put("startEvent", "开始节点");
        types.put("endEvent", "结束节点");
        types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
        types.put("inclusiveGateway", "并行处理任务");
        types.put("callActivity", "子流程");
        return types.get(type) == null ? type : types.get(type);
    }

    public static UserEntity toActivitiUser(User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getLoginName());
        userEntity.setFirstName(user.getName());
        userEntity.setLastName(StringUtils.EMPTY);
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setRevision(1);
        return userEntity;
    }

    public static GroupEntity toActivitiGroup(Role role) {
        if (role == null) {
            return null;
        }
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(role.getEnname());
        groupEntity.setName(role.getName());
        groupEntity.setType(role.getRoleType());
        groupEntity.setRevision(1);
        return groupEntity;
    }

    public static void main(String[] args) {
        User user = new User();
        System.out.println(getMobileEntity(user, "@"));
    }
}
