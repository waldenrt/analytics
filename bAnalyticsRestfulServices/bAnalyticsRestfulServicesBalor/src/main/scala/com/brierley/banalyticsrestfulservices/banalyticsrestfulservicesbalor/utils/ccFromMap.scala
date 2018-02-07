package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.utils

import scala.reflect._
import scala.reflect.runtime.universe._

package object ccFromMap {
  def fromMap[T: TypeTag : ClassTag](m: Map[String, _]) = {
    val rm = runtimeMirror(classTag[T].runtimeClass.getClassLoader)
    val classTest = typeOf[T].typeSymbol.asClass
    val classMirror = rm.reflectClass(classTest)
    val constructor = typeOf[T].decl(termNames.CONSTRUCTOR).asMethod
    val constructorMirror = classMirror.reflectConstructor(constructor)

    val constructorArgs = constructor.paramLists.flatten.map((param: Symbol) => {
      val paramName = param.name.toString
      if (param.typeSignature <:< typeOf[Option[Any]])
        m.get(paramName)
      else
        m.getOrElse(paramName, throw new IllegalArgumentException("Map is missing required parameter named " + paramName))
    })

    constructorMirror(constructorArgs: _*).asInstanceOf[T]
  }
}
