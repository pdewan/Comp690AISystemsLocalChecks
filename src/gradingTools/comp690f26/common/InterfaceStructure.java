package gradingTools.comp690f26.common;

import java.util.*;

/** Inherited-interface rules shared with A1. */
public final class InterfaceStructure {
    private InterfaceStructure() { }
	public static Set<Class<?>> mostSpecificInterfaces(
			Set<Class<?>> someInterfaces) {
		Set<Class<?>> result = new LinkedHashSet<>(someInterfaces);
		for (Class<?> possibleParent : someInterfaces) {
			for (Class<?> possibleChild : someInterfaces) {
				if (!possibleParent.equals(possibleChild)
						&& possibleParent.isAssignableFrom(possibleChild)) {
					result.remove(possibleParent);
					break;
				}
			}
		}
		return result;
	}
	public static Set<Class<?>> allInterfaces(Class<?> aClass) {
		Set<Class<?>> result = new LinkedHashSet<>();
		for (Class<?> current = aClass;
				current != null && current != Object.class;
				current = current.getSuperclass()) {
			for (Class<?> anInterface : current.getInterfaces()) {
				addInterfaceHierarchy(anInterface, result);
			}
		}
		return result;
	}
	public static void addInterfaceHierarchy(
			Class<?> anInterface,
			Set<Class<?>> aResult) {
		if (!aResult.add(anInterface)) {
			return;
		}
		for (Class<?> parentInterface : anInterface.getInterfaces()) {
			addInterfaceHierarchy(parentInterface, aResult);
		}
	}
}
