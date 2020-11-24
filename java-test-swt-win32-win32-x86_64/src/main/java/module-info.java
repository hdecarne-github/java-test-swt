/**
 * module-info
 */
module de.carne.test.swt {
	requires transitive org.eclipse.swt.win32.win32.x86_64;
	requires transitive org.eclipse.jdt.annotation;
	requires transitive de.carne.test;

	requires org.mockito;
	requires de.carne;

	exports de.carne.test.swt;
	exports de.carne.test.swt.condition;
	exports de.carne.test.swt.extension;
	exports de.carne.test.swt.tester;
	exports de.carne.test.swt.tester.accessor;
}
