/**
 * module-info
 */
module de.carne.test.swt {
	requires transitive org.eclipse.swt.gtk.linux.x86_64;
	requires transitive de.carne.test;

	requires org.eclipse.jdt.annotation;
	requires org.jmockit;
	requires de.carne;

	exports de.carne.test.swt;
	exports de.carne.test.swt.condition;
	exports de.carne.test.swt.extension;
	exports de.carne.test.swt.tester;
	exports de.carne.test.swt.tester.accessor;
}
