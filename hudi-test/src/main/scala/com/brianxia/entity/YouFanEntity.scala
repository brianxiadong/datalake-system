package com.brianxia.entity

/**
 * @author brianxia
 * @date 2021/7/9 18:40
 * @version 1.0
 */
/**
 * scala Case类只是常规类，默认情况下是不可变的，可通过模式匹配可分解。
 * 它使用相等(equal)方法在结构上比较实例。它不使用new关键字实例化对象。
 * 默认情况下，case类中列出的所有参数默认使用public和immutable修辞符//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/scala/scala-case-classes-and-case-object.html
 *
 */
case class YouFanEntity(
                         uid: Int,
                         uname: String,
                         dt: String
                       )
