<#if ejb3>
@LgrHibernateValidated(champ = "",table = "${clazz.table.name}",clazz = ${pojo.getJavaTypeName(property, jdk5)}.class)
</#if>