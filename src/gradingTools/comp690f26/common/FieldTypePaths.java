package gradingTools.comp690f26.common;

import java.lang.reflect.*;
import java.util.*;

/** Declared instance-field traversal shared with A1, including generic arguments and arrays. */
public final class FieldTypePaths {
    private FieldTypePaths() { }
	public static String findRequiredTypePath(
			Class<?> aCurrentClass,
			Class<?> aRequiredType,
			Set<Class<?>> aProjectClasses,
			Set<Class<?>> aVisited,
			String aPath) {
		if (aCurrentClass == null || !aVisited.add(aCurrentClass)) {
			return null;
		}

		for (Class<?> current = aCurrentClass;
				current != null && current != Object.class;
				current = current.getSuperclass()) {
			for (Field field : current.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				String fieldPath =
						aPath
								+ "."
								+ field.getName()
								+ ":"
								+ field.getGenericType().getTypeName();
				for (Class<?> referencedType :
						referencedClasses(field.getGenericType())) {
					if (aRequiredType.isAssignableFrom(referencedType)) {
						return fieldPath;
					}
				}
				Class<?> fieldType = field.getType();
				if (fieldType.isArray()) {
					fieldType = fieldType.getComponentType();
				}
				if (aProjectClasses.contains(fieldType)) {
					String nestedPath =
							findRequiredTypePath(
									fieldType,
									aRequiredType,
									aProjectClasses,
									aVisited,
									fieldPath);
					if (nestedPath != null) {
						return nestedPath;
					}
				}
			}
		}
		return null;
	}
	public static Set<Class<?>> referencedClasses(Type aType) {
		Set<Class<?>> result = new HashSet<>();
		collectReferencedClasses(aType, result);
		return result;
	}
	public static void collectReferencedClasses(
			Type aType,
			Set<Class<?>> aResult) {
		if (aType instanceof Class<?> typeClass) {
			aResult.add(
					typeClass.isArray()
							? typeClass.getComponentType()
							: typeClass);
		} else if (aType instanceof ParameterizedType parameterizedType) {
			collectReferencedClasses(
					parameterizedType.getRawType(),
					aResult);
			for (Type argument : parameterizedType.getActualTypeArguments()) {
				collectReferencedClasses(argument, aResult);
			}
		} else if (aType instanceof GenericArrayType arrayType) {
			collectReferencedClasses(
					arrayType.getGenericComponentType(),
					aResult);
		} else if (aType instanceof WildcardType wildcardType) {
			for (Type bound : wildcardType.getUpperBounds()) {
				collectReferencedClasses(bound, aResult);
			}
			for (Type bound : wildcardType.getLowerBounds()) {
				collectReferencedClasses(bound, aResult);
			}
		} else if (aType instanceof TypeVariable<?> typeVariable) {
			for (Type bound : typeVariable.getBounds()) {
				collectReferencedClasses(bound, aResult);
			}
		}
	}
}
